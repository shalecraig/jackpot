package jackpot;

import jackpot.annotation.Query;
import jackpot.exception.InvalidInterfaceException;
import jackpot.testutilities.AssertUtil;
import org.junit.Test;

import static org.junit.Assert.fail;

public class InterpreterUnitTest {

    @Test
    public void noMethods() {
        Interpreter i = new Interpreter(null);
        try {
            i.generate(NoMethodInterface.class);
            fail();
        } catch (IllegalStateException e) {
            String message = e.getMessage();
            AssertUtil.stringContains(NoMethodInterface.class.getSimpleName(), message);
            AssertUtil.stringContains("at least one method", message);
        }
    }

    @Test(expected = NullPointerException.class)
    public void nullInterface() {
        Interpreter i = new Interpreter(null);
        i.generate(null);
    }

    @Test
    public void noClasses() {
        Interpreter i = new Interpreter(null);
        try {
            i.generate(AClass.class);
            fail();
        } catch (IllegalStateException e) {
            String message = e.getMessage().toLowerCase();
            AssertUtil.stringContains("class", message);
        }
    }

    @Test
    public void extendsInterfaces() {
        Interpreter i = new Interpreter(null);
        try {
            i.generate(ExtendsB.class);
            fail();
        } catch (IllegalStateException e) {
            AssertUtil.stringContains("must not extend", e.getMessage().toLowerCase());
        }
    }

    private interface ExtendsB extends B {
        @Query(sql = "select * from foo")
        void fooBar();
    }
    private interface B {
    }

    private interface NoMethodInterface {

    }
    private interface NoAnnotatedMethodInterface {
        void something();
    }

    private static class AClass {

    }
}