package jackpot.testutilities;

import org.junit.Assert;

public class AssertUtil {
    private AssertUtil() { /* nope */ }

    public static void stringContains(String needle, String haystack, String msg) {
        Assert.assertTrue(msg, haystack.contains(needle));
    }

    public static void stringContains(String needle, String haystack) {
        Assert.assertTrue(haystack.contains(needle));
    }
}
