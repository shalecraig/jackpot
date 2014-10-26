package jackpot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Query {
    String sql();

    // TODO: auto-detect this instead?
    QueryType queryType() default QueryType.READ;

    enum QueryType {
        READ, WRITE
    }
}
