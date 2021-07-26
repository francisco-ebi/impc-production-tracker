package org.gentar.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class SequenceResetter
{
    @Autowired
    private DataSource dataSource;

    /**
     * Resets the sequences of the given database tables. To be used to reset the database state after
     * tests that create new entities.
     */
    public void resetAutoIncrementColumns(String... tableNames) {
        try {
            String resetSqlTemplate = "ALTER TABLE %s ALTER COLUMN id RESTART WITH 1";
            Connection dbConnection = dataSource.getConnection();

            Statement statement = dbConnection.createStatement();
            for (String resetSqlArgument : tableNames) {
                String resetSql = String.format(resetSqlTemplate, resetSqlArgument);
                statement.execute(resetSql);
            }

            statement.close();
            dbConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates a sequence with the max id from the table + 1.
     * @param sequenceName Name of the sequence.
     * @param tableName Name of the table.
     */
    public void syncSequence(String sequenceName, String tableName)
    {
        try
        {
            String resetSqlTemplate = "ALTER SEQUENCE %s RESTART WITH %d";
            Connection dbConnection = dataSource.getConnection();

            String maxIdQueryTemplate = "select COALESCE(MAX(id),0) FROM %s";
            String maxIdQuery = String.format(maxIdQueryTemplate, tableName);
            int maxId = 0;

            Statement statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(maxIdQuery);
            while (rs.next())
            {
                maxId = rs.getInt(1);
            }
            String resetSql = String.format(resetSqlTemplate, sequenceName, maxId + 1);
            statement.execute(resetSql);
            // calling nextval is preventing hibernate to generate negative ids.
            statement.executeQuery(String.format("SELECT nextval('%s')", sequenceName));

            statement.close();
            dbConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
