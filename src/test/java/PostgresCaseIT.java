import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by maksim on 19.04.17.
 */
public class PostgresCaseIT {

    @Test
    public void test() throws ClassNotFoundException, SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:8765/postgres","postgres", "postgres")) {
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
