# Jackpot - A Casual Library For Databases and Stuff

This is a totally incomplete library. Don't use it yet.

## Usage:

This is what I'd assume usage looks like once I'm done:

```java

public class Main {
    public static void main(String... args) {
        Interpreter interpreter = new Interpreter(...);
        FooFace dbInterpreter = interpreter.generateConnection(FooFace.class);
        dbInterpreter.getAllFoos();
    }

    interface FooFace {
        @Query(
                sql = "select * from foo",
                queryType = QueryType.READ
        )
        List<Foo> getAllFoos();
    }
}
```
