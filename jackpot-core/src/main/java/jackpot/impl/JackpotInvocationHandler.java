package jackpot.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import jackpot.annotation.Query;
import jackpot.mapper.Mapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JackpotInvocationHandler implements InvocationHandler {
    private final Connection conn;
    private final Class<?> invokedInterface;
    private final Map<Method, MethodDetails> methodDetailsMap;

    public JackpotInvocationHandler(Connection conn, Class<?> invokedInterface) {
        this.conn = conn;
        this.invokedInterface = checkInterface(invokedInterface);
        methodDetailsMap = methodDetailsFor(invokedInterface);
    }

    // TODO: cache all this config-type stuff.
    private static List<Method> feasibleMethods(Class<?> face) {
        Method[] methods = face.getMethods();
        ArrayList<Method> result = Lists.newArrayListWithCapacity(methods.length);
        for (Method m : methods) {
            if (m.isAnnotationPresent(Query.class)) {
                result.add(m);
            }
        }
        return result;
    }

    private Map<Method, MethodDetails> methodDetailsFor(Class<?> invokedInterface) {
        List<Method> methods = feasibleMethods(invokedInterface);
        Map<Method, MethodDetails> result = Maps.newHashMapWithExpectedSize(methods.size());
        for (Method m : methods) {
            result.put(m, MethodDetails.of(m));
        }
        return result;
    }

    private Class<?> checkInterface(Class<?> invokedInterface) {
        Preconditions.checkNotNull(invokedInterface, "No null interfaces");
        Preconditions.checkState(
                invokedInterface.isInterface(),
                "Class '%s' must be an interface, but it's not.",
                invokedInterface.getSimpleName());
        Preconditions.checkState(
                feasibleMethods(invokedInterface).size() > 0,
                "Class '%s' must have at least one method annotated with '%s'.",
                invokedInterface.getSimpleName(),
                Query.class.getSimpleName());
        Preconditions.checkState(
                invokedInterface.getInterfaces().length == 0,
                "Interface '%s' must not extend any interfaces. It extends '%s'.",
                invokedInterface.getSimpleName(),
                invokedInterface.getInterfaces().length);

        return invokedInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass().equals(Object.class)) {
            return method.invoke(this, args);
        }
        MethodDetails methodDetails = Preconditions.checkNotNull(
                methodDetailsMap.get(method),
                "Unsupported method %s called for class %s",
                method.getName(),
                proxy.getClass().getSimpleName());

        Statement statement = conn.createStatement();
        Mapper resultMapper = Preconditions.checkNotNull(methodDetails.getResultMapper());

        try {
            if (methodDetails.isQuery()) {
                return resultMapper.map(statement.executeQuery(methodDetails.getSql()));
            } else {
                return resultMapper.map(statement.executeUpdate(methodDetails.getSql()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
        }
    }
}
