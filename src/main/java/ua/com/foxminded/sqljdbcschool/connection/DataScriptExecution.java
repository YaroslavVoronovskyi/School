package ua.com.foxminded.sqljdbcschool.connection;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

public class DataScriptExecution {
    private static final String DEFAULT_FORMAT = "UTF-8";
    private static final String DELIMETER = ";";
    
    
    public void scriptRunner(String sqlScript) throws ClassNotFoundException, SQLException, IOException {
        try (Connection connection = DataSource.getConnection()) {
            connection.setAutoCommit(false);
            ScriptRunner runner = new ScriptRunner(connection);
            runner.setAutoCommit(false);
            runner.setStopOnError(true);
            runner.setSendFullScript(false);
            // runner.setLogWriter(null);
            Resources.setCharset(Charset.forName(DEFAULT_FORMAT));
            runner.setDelimiter(DELIMETER);
            runner.setFullLineDelimiter(false);
            runner.runScript(Resources.getResourceAsReader(sqlScript));
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw new SQLException("SQLException exception in scriptRunner(String sqlScript)", exception);
            } finally {
                try {
                    connection.close();
                } catch (SQLException exception) {
                    throw new SQLException("SQLException exception in scriptRunner(String sqlScript)", exception);
                }
            }
        }
    }
}
