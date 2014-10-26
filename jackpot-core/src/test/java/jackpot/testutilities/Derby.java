package jackpot.testutilities;

import org.apache.derby.jdbc.EmbeddedDriver;
import org.junit.Assert;

import java.io.File;
import java.sql.Connection;
import java.util.Properties;
import java.util.UUID;

public class Derby {

    private Derby() {
    }

    /**
     * Utility method to instantiate a clean, in-memory jdbc connection based on derby
     */
    public static Connection getConnection() {
        EmbeddedDriver driver = new EmbeddedDriver();
        try {
            File tempFile = File.createTempFile(UUID.randomUUID().toString(), null);
            String derbyDBPath = tempFile.getAbsolutePath();
            Assert.assertTrue(String.format("Generating a temp file path for derby's in-memory db failed. Location '%s' created but not deleted.", derbyDBPath), tempFile.delete());
            return driver.connect(String.format("jdbc:derby:%s;create=true", derbyDBPath), new Properties());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
