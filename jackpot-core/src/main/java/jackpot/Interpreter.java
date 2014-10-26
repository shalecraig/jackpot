package jackpot;

import com.google.common.base.Preconditions;
import com.google.common.reflect.Reflection;
import jackpot.impl.JackpotInvocationHandler;

import java.sql.Connection;

public class Interpreter {
    private final Connection conn;

    // TODO: builder instead
    public Interpreter(Connection conn) {
        this.conn = conn;
    }

    @SuppressWarnings("unchecked")
    public <T> T generate(Class<T> interfaceClass) {
        Preconditions.checkNotNull(interfaceClass, "Cannot use a null interfaceClass.");
        return Reflection.newProxy(
                interfaceClass,
                new JackpotInvocationHandler(conn, interfaceClass)
        );
    }
}
