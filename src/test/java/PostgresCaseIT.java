import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;

import java.sql.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by maksim on 19.04.17.
 */
public class PostgresCaseIT {

    @Rule
    public GenericContainer postgres = new GenericContainer("postgres:9.5.4").withExposedPorts(5432);

    @Test
    public void test() throws ClassNotFoundException, SQLException, InterruptedException {
        Thread.currentThread().sleep(5000);
        Integer mappedPort = postgres.getMappedPort(5432);
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:" + mappedPort + "/postgres","postgres", "postgres")) {
            Statement statement = connection.createStatement();
            statement.execute("create table test_table (id integer)");
            statement.execute("insert into test_table (id) values (1)");
            statement.execute("insert into test_table (id) values (2)");
            ResultSet resultSet = statement.executeQuery("select sum(id) as summ from test_table");
            resultSet.next();
            int summ = resultSet.getInt("summ");
            assertEquals(3, summ);
        }
        System.out.println("Test complete!");
    }

}
