package com.apsol.ecopass.core.tui;


import com.apsol.ecopass.core.tui.model.TuiPage;
import com.apsol.ecopass.core.tui.model.TuiPageGridData;
import com.apsol.ecopass.core.tui.model.TuiPageGridResult;
import com.apsol.ecopass.util.DateFormatHelper;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.math.BigDecimal;
import java.util.*;

public class TuiGridDataBuilder {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityPath<?> table;
    private DataPath dataPath;
    private SearchPath searchPath;
    private int page;
    private int perPage;

    private static final String[] DELIMITERS = { ">=", "<=", ">", "<", ".." };

    private IWhere where;
    private IOrderBy orderBy;
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

    public TuiGridDataBuilder( JPAQueryFactory jpaQueryFactory,
                                EntityPath<?> table,
                                DataPath dataPath,
                                SearchPath searchPath,
                                int page,
                                int perPage     ) {

        this.jpaQueryFactory = jpaQueryFactory;
        this.table = table;
        this.dataPath = dataPath;
        this.searchPath = searchPath;
        this.page = page;
        this.perPage = perPage;
    }

    public TuiPageGridResult build() {
        long totalCount;

        JPAQuery<?> totalQuery = jpaQueryFactory.from(table);
        filterQuery(totalQuery);
        totalCount = totalQuery.fetchCount();

        // 데이터를 담을 배열
        List<Object> records = new ArrayList<>();
        // 쿼리 실행
        List<Tuple> tuples = getTuples();
        // 데이터 추출
        for (Tuple tuple : tuples) {
            Map<String, Object> data = new HashMap<>();
            for (Map.Entry<String, Expression<?>> entry : dataPath.getDataPaths().entrySet()) {
                data.put(entry.getKey(), tuple.get(entry.getValue()));
            }
            records.add(data);
        }

        return new TuiPageGridData(new TuiPage(page, (int) totalCount), records);
    }

    private List<Tuple> getTuples() {
        // offset 계산
        int offset = (page - 1) * perPage;
        // 컬럼 배열 추출
        List<Expression<?>> colList = new ArrayList<>(dataPath.getPathSet());
        Expression<?>[] colArr = colList.toArray(new Expression<?>[0]);

        JPAQuery<Tuple> jpaQuery = jpaQueryFactory.select(colArr).from(table);
        buildQuery(jpaQuery);
        jpaQuery.limit(perPage).offset(offset);

        // todo JPAQuery 반환해서 컨트롤러에서 dto 직업 입력하고 fetch 시도할 것
        return jpaQuery.fetch();
    }

    public void buildQuery(JPAQuery<Tuple> jpaQuery) {
        filterQuery(jpaQuery);
        sortQuery(jpaQuery);
    }
    private void filterQuery(JPAQuery<?> jpaQuery) {
        if( where != null )
            where.where(jpaQuery);

        for (Map.Entry<Expression<?>, String> entry : searchPath.getFilterMap().entrySet())
            buildFilter(jpaQuery, entry.getKey(), entry.getValue(), false);
    }
    private void sortQuery(JPAQuery<Tuple> jpaQuery) {
        if( orderBy != null )
            orderBy.orderBy(jpaQuery);

        for (Map.Entry<Expression<?>, String> entry : searchPath.getSortMap().entrySet())
            buildSort(jpaQuery, entry.getKey(), entry.getValue());
    }

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

    private void buildSort(JPAQuery<?> jpaQuery, Expression<?> exp, String direct) {
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
    private void buildSortOther(JPAQuery<?> jpaQuery, Expression<?> exp, String direct) {
        throw new RuntimeException("미구현된 항목입니다. " + exp.getClass().getName());
    }




}
