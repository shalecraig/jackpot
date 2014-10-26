package jackpot;

import jackpot.annotation.Query;
import jackpot.testutilities.Derby;
import org.junit.Test;

import static jackpot.annotation.Query.QueryType;

public class InterpreterTest {
    @Test
    public void testCreateFace() {
        Interpreter i = new Interpreter(Derby.getConnection());
        FooFace fooFace = i.generate(FooFace.class);
        int x = fooFace.createTable();
        fooFace.getFoo();
        int numAdded = fooFace.bar();
        fooFace.getFoo();
    }

    interface FooFace {
        @Query(
                sql = "select * from foo",
                queryType = QueryType.READ
        )
        void getFoo();

        @Query(
                sql = " create table foo (" +
                        "   item_id Int Not Null," +
                        "   item_radius float Not Null," +
                        "   Primary Key (item_id)" +
                        "   )",
                queryType = QueryType.WRITE
        )
        int createTable();

        @Query(
                sql = "insert into foo values (12, 1.27)",
                queryType = QueryType.WRITE
        )
        int bar();
    }
}