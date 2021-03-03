package com.apsol.ecopass.core.dhtmlx.builder;


import com.apsol.ecopass.core.dhtmlx.model.path.TotalDataPath;
import com.apsol.ecopass.core.dhtmlx.model.path.TotalQueryPath;
import com.apsol.ecopass.core.dhtmlx.model.record.Record;
import com.apsol.ecopass.core.dhtmlx.model.record.RecordSet;

import com.apsol.ecopass.util.DateFormatHelper;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.math.BigDecimal;
import java.util.*;

public class RecordBuilder {

    private static final String PK_NAME = "code";

    // PATH
    private final TotalDataPath totalDataPath;
    private final TotalQueryPath totalQueryPath;

    // BEAN
    private final JPAQueryFactory jpaQueryFactory;

    // ENTITY
    private final EntityPath<?> table;

    // QUERY
    private static final String[] DELIMITERS = { ">=", "<=", ">", "<", ".." };
    private IWhere where;
    private IOrderBy orderBy;

    // CONSTRUCTOR
    public RecordBuilder(	TotalDataPath           totalDataPath,
                            TotalQueryPath			totalQueryPath,
                            JPAQueryFactory			jpaQueryFactory,
                            EntityPath<?>			table				) {

        this.totalDataPath = totalDataPath;
        this.totalQueryPath = totalQueryPath;
        this.jpaQueryFactory = jpaQueryFactory;
        this.table = table;
    }

    public RecordSet buildRecordSet() {
        RecordSet result = new RecordSet();
        Integer posStart = totalDataPath.getPosStart();
        Integer count = totalDataPath.getCount();
        result.setPos(posStart);

        if (posStart == 0 && count != null) {
            JPAQuery<?> totalQuery = jpaQueryFactory.from(table);
            filterQuery(totalQuery);
            result.setTotal_count((int) totalQuery.fetchCount());
        }

        result.setRecords(getRecords());
        return result;
    }

    public List<Record> getRecords() {
        Map<String, Expression<?>> pathMap = totalDataPath.getPathMap();
        List<Tuple> tuples = getTuples();
        List<Record> records = new ArrayList<>();
        List<String> frontIds = totalDataPath.getFrontIds();
        List<String> backIds = totalDataPath.getBackIds();

        boolean pkFlag = frontIds.contains(PK_NAME);
        int posStart = totalDataPath.getPosStart();
        int tempId = posStart + 1; // 임시 아이디

        for (Tuple tuple : tuples) {
            Record record = pkFlag ? new Record(tuple.get(pathMap.get(PK_NAME))) :  new Record(tempId);
            for (String frontId : frontIds) {
                Object data = tuple.get(pathMap.get(frontId));
                if (data instanceof Date) {
                    record.putData((Date) data);
                } else {
                    record.putObject(data);
                }
            }
            Map<String, Object> userdata = new HashMap<>();
            record.setUserdata(userdata);
            for (String backId : backIds) {
                Object data = tuple.get(pathMap.get(backId));
                userdata.put(backId, data);
            }
            records.add(record);
            tempId++;
        }
        return records;
    }
    public List<Tuple> getTuples() {
        Map<String, Expression<?>> pathMap = totalDataPath.getPathMap();
        List<Expression<?>> colList = new ArrayList<>();

        for (Map.Entry<String, Expression<?>> pathEntry : pathMap.entrySet())
            colList.add(pathEntry.getValue());

        Expression<?>[] colArr = colList.toArray(new Expression<?>[0]);
        JPAQuery<Tuple> jpaQuery = this.jpaQueryFactory.select(colArr).from(table);
        jpaQuery.limit(totalDataPath.getCount()).offset(totalDataPath.getPosStart());

        buildQuery(jpaQuery);
        return jpaQuery.fetch();
    }

    public void buildQuery(JPAQuery<Tuple> jpaQuery) {
        filterQuery(jpaQuery);
        sortQuery(jpaQuery);
    }
    private void filterQuery(JPAQuery<?> jpaQuery) {
        if( where != null )
            where.where(jpaQuery);

        Map<Expression<?>, String> filterMap = totalQueryPath.getFilterMap();
        for (Map.Entry<Expression<?>, String> entry : filterMap.entrySet())
            buildFilter(jpaQuery, entry.getKey(), entry.getValue(), false);
    }
    private void sortQuery(JPAQuery<Tuple> jpaQuery) {
        if( orderBy != null )
            orderBy.orderBy(jpaQuery);

        Map<Expression<?>, String> sortMap = totalQueryPath.getSortMap();
        for (Map.Entry<Expression<?>, String> entry : sortMap.entrySet())
            buildSort(jpaQuery, entry.getKey(), entry.getValue());
    }

    // Builder and Generator
    /*public static interface IRecordGenerator {
        Record generate(DataSet dataSet);
    }
    public static interface IRecordStyleBuilder {
        String build(DataSet dataSet);
    }
    public static interface IRecordDataBuilder {
        Object build(Object val, DataSet dataSet);
    }

    private Map<String, IRecordDataBuilder> builders = new HashMap<>();
    private IRecordGenerator generator;
    private IRecordStyleBuilder styleBuilder;

    public void setGenerator(IRecordGenerator generator) {
        this.generator = generator;
        this.idName = null;
    }
    public void setRecordStyleBuilder(IRecordStyleBuilder styleBuilder) {
        this.styleBuilder = styleBuilder;
    }
    public void putDataBuilder(String name, IRecordDataBuilder builder) {
        builders.put(name, builder);
    }*/

    // QUERY INTERFACE
    public static interface IWhere {
        void where(JPAQuery<?> jpaQuery);
    }
    public static interface IOrderBy {
        void orderBy(JPAQuery<?> jpaQuery);
    }
    public void setOrderBy(IOrderBy orderBy) {
        this.orderBy = orderBy;
    }
    public void setWhere(IWhere where) {
        this.where = where;
    }

    @SuppressWarnings("unchecked")
    private void buildFilter(JPAQuery<?> jpaQuery, Expression<?> exp, String keyword, boolean fixedEq) {
        boolean finished = false;
        try {
            if (exp instanceof DatePath<?>) {
                keyword = keyword.replace("-", "");
                DatePath<Date> path = (DatePath<Date>) exp;
                if (keyword.length() == 4) { // 년도만
                    jpaQuery.where(path.year().eq(Integer.parseInt(keyword)));
                    finished = true;
                } else if (keyword.length() == 6) {
                    jpaQuery.where(path.year().eq(Integer.parseInt(keyword.substring(0, 4))));
                    jpaQuery.where(path.month().eq(Integer.parseInt(keyword.substring(4))));
                    finished = true;
                } else {
                    Date date = DateFormatHelper.parseDate8(keyword);
                    jpaQuery.where(path.eq(date));
                    finished = true;
                }
            } else if (exp instanceof DateTimePath<?>) {
                keyword = keyword.replace("-", "");
                DateTimePath<Date> path = (DateTimePath<Date>) exp;
                if (keyword.length() == 4) { // 년도만
                    jpaQuery.where(path.year().eq(Integer.parseInt(keyword)));
                    finished = true;
                } else if (keyword.length() == 6) {
                    jpaQuery.where(path.year().eq(Integer.parseInt(keyword.substring(0, 4))));
                    jpaQuery.where(path.month().eq(Integer.parseInt(keyword.substring(4))));
                    finished = true;
                } else if (keyword.length() == 8) {
                    jpaQuery.where(path.year().eq(Integer.parseInt(keyword.substring(0, 4))));
                    jpaQuery.where(path.month().eq(Integer.parseInt(keyword.substring(4, 6))));
                    jpaQuery.where(path.dayOfMonth().eq(Integer.parseInt(keyword.substring(6, 8))));
                    finished = true;
                } else {
                    Date date = DateFormatHelper.parseDateTime(keyword);
                    jpaQuery.where(path.eq(date));
                    finished = true;
                }
            }
            if (!finished) {
                if (exp instanceof StringPath) {
                    StringPath path = (StringPath) exp;
                    if (fixedEq) {
                        jpaQuery.where(path.eq(keyword));
                    } else {
                        jpaQuery.where(path.like("%" + keyword + "%"));
                    }
                } else if (exp instanceof StringExpression) {
                    StringExpression path = (StringExpression) exp;
                    if (fixedEq) {
                        jpaQuery.where(path.eq(keyword));
                    } else {
                        jpaQuery.where(path.like("%" + keyword + "%"));
                    }
                } else if (exp instanceof NumberPath<?>) {
                    NumberPath<?> path = (NumberPath<?>) exp;
                    for (String delimiter : DELIMITERS) {
                        if (keyword.contains(delimiter)) {
                            if (delimiter.equals(".."))
                                delimiter = "\\.\\.";
                            String[] numbers = keyword.split(delimiter);
                            BigDecimal val = new BigDecimal(numbers[0].trim());
                            switch (delimiter) {
                                case ">=":
                                    jpaQuery.where(path.goe(Expressions.constant(val)));
                                    finished = true;
                                    break;
                                case "<=":
                                    jpaQuery.where(path.loe(Expressions.constant(val)));
                                    finished = true;
                                    break;
                                case ">":
                                    jpaQuery.where(path.gt(Expressions.constant(val)));
                                    finished = true;
                                    break;
                                case "<":
                                    jpaQuery.where(path.lt(Expressions.constant(val)));
                                    finished = true;
                                    break;
                                case "\\.\\.":
                                    if (numbers.length == 2) {
                                        BigDecimal val1 = new BigDecimal(numbers[1].trim());
                                        jpaQuery.where(path.between(Expressions.constant(val), Expressions.constant(val1)));
                                        finished = true;
                                        break;
                                    }
                                    break;
                            }
                            break;
                        }
                    }
                    if (!finished) {
                        BigDecimal val = new BigDecimal(keyword);
                        jpaQuery.where(path.eq(Expressions.constant(val)));
                    }
                } else if (exp instanceof NumberExpression<?>) {
                    NumberExpression<?> path = (NumberExpression<?>) exp;
                    for (String delimiter : DELIMITERS) {
                        if (keyword.contains(delimiter)) {
                            if (delimiter.equals(".."))
                                delimiter = "\\.\\.";
                            String[] numbers = keyword.split(delimiter);
                            BigDecimal val = new BigDecimal(numbers[0].trim());
                            switch (delimiter) {
                                case ">=":
                                    jpaQuery.where(path.goe(Expressions.constant(val)));
                                    finished = true;
                                    break;
                                case "<=":
                                    jpaQuery.where(path.loe(Expressions.constant(val)));
                                    finished = true;
                                    break;
                                case ">":
                                    jpaQuery.where(path.gt(Expressions.constant(val)));
                                    finished = true;
                                    break;
                                case "<":
                                    jpaQuery.where(path.lt(Expressions.constant(val)));
                                    finished = true;
                                    break;
                                case "\\.\\.":
                                    if (numbers.length == 2) {
                                        BigDecimal val1 = new BigDecimal(numbers[1].trim());
                                        jpaQuery.where(path.between(Expressions.constant(val), Expressions.constant(val1)));
                                        finished = true;
                                        break;
                                    }
                                    break;
                            }
                            break;
                        }
                    }
                    if (!finished) {
                        BigDecimal val = new BigDecimal(keyword);
                        jpaQuery.where(path.eq(Expressions.constant(val)));
                    }
                } else if (exp instanceof BooleanPath) {
                    BooleanPath path = (BooleanPath) exp;
                    jpaQuery.where(path.eq(Boolean.valueOf(keyword)));
                } else if (exp instanceof EnumPath) {
                    EnumPath<?> path = (EnumPath<?>) exp;
                    jpaQuery.where(path.eq(Expressions.constant(keyword)));
                } else {
                    buildFilterOther(jpaQuery, exp, keyword, fixedEq);
                }
            }
        } catch (NumberFormatException ignored) {
        }
    }
    private void buildFilterOther(JPAQuery<?> jpaQuery, Expression<?> exp, String keyword, boolean fixedEq) {
        throw new RuntimeException("미구현된 항목입니다. " + exp.getClass().getName());
    }

    private void buildSort(JPAQuery<Tuple> jpaQuery, Expression<?> exp, String direct) {
        if (exp instanceof StringPath) {
            StringPath path = (StringPath) exp;
            if (direct.equals("des")) {
                jpaQuery.orderBy(path.desc());
            } else {
                jpaQuery.orderBy(path.asc());
            }
        }
        else if (exp instanceof StringExpression) {
            StringExpression path = (StringExpression) exp;
            if (direct.equals("des")) {
                jpaQuery.orderBy(path.desc());
            } else {
                jpaQuery.orderBy(path.asc());
            }
        }
        else if (exp instanceof NumberPath<?>) {
            NumberPath<?> path = (NumberPath<?>) exp;
            if (direct.equals("des")) {
                jpaQuery.orderBy(path.desc());
            } else {
                jpaQuery.orderBy(path.asc());
            }
        }
        else if (exp instanceof NumberExpression<?>) {
            NumberExpression<?> path = (NumberExpression<?>) exp;
            if (direct.equals("des")) {
                jpaQuery.orderBy(path.desc());
            } else {
                jpaQuery.orderBy(path.asc());
            }
        }
        else if (exp instanceof BooleanPath) {
            BooleanPath path = (BooleanPath) exp;
            if (direct.equals("des")) {
                jpaQuery.orderBy(path.desc());
            } else {
                jpaQuery.orderBy(path.asc());
            }
        }
        else if (exp instanceof DatePath) {
            DatePath<?> path = (DatePath<?>) exp;
            if (direct.equals("des")) {
                jpaQuery.orderBy(path.desc());
            } else {
                jpaQuery.orderBy(path.asc());
            }
        }
        else if (exp instanceof DateTimePath) {
            DateTimePath<?> path = (DateTimePath<?>) exp;
            if (direct.equals("des")) {
                jpaQuery.orderBy(path.desc());
            } else {
                jpaQuery.orderBy(path.asc());
            }
        }
        else {
            buildSortOther(jpaQuery, exp, direct);
        }
    }
    private void buildSortOther(JPAQuery<Tuple> jpaQuery, Expression<?> exp, String direct) {
        throw new RuntimeException("미구현된 항목입니다. " + exp.getClass().getName());
    }

}
