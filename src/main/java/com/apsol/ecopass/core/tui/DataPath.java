package com.apsol.ecopass.core.tui;


import com.querydsl.core.types.Expression;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@NoArgsConstructor
public class DataPath {

    private Map<String, Expression<?>> dataPaths = new HashMap<>();
    private Set<Expression<?>> pathSet = new HashSet<>();

    public void setDataPath(String name, Expression<?> path) {
        dataPaths.put(name, path);
        pathSet.add(path);

        if (dataPaths.size() != pathSet.size())
            throw new RuntimeException("중복된 Expression");
    }

}
