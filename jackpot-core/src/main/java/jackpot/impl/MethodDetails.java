package jackpot.impl;

import com.google.common.base.Preconditions;
import jackpot.annotation.Query;
import jackpot.mapper.Mapper;
import jackpot.mapper.Mappers;

import java.lang.reflect.Method;
import java.sql.ResultSet;

class MethodDetails {
    private final Method method;
    private final Query query;

    private final String name;
    private final String sql;
    private final Mapper resultMapper;

    private MethodDetails(Method method) {
        Query query = method.getAnnotation(Query.class);
        this.resultMapper = getMap(method, query);
        this.method = method;
        this.query = query;

        this.sql = this.query.sql();
        this.name = method.getName();
    }

    private static Mapper getMap(Method method, Query query) {
        Class<?> to = method.getReturnType();
        Class from;
        switch (query.queryType()) {
            case READ:
                from = ResultSet.class;
                break;
            case WRITE:
                from = Integer.TYPE;
                break;
            default:
                throw new UnsupportedOperationException(
                        String.format(
                                "Invalid query type %s for method %s",
                                query.queryType(),
                                method.getName()));
        }
        return Preconditions.checkNotNull(
                Mappers.map(from, to),
                "Cannot map results from %s to %s.",
                from.getSimpleName(),
                to.getSimpleName());
    }

    public static MethodDetails of(Method method) {
        return new MethodDetails(method);
    }

    public String getName() {
        return name;
    }

    public String getSql() {
        return sql;
    }

    public Mapper getResultMapper() {
        return resultMapper;
    }

    public boolean isQuery() {
        return query.queryType() == Query.QueryType.READ;
    }
}
