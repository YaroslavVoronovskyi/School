package ua.com.foxminded.sqljdbcschool.connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public class DataSource {
    private static final String DB_DRIVER = "DB_DRIVER";
    private static final String DB_URL = "DB_URL";
    private static final String DB_USER_NAME = "DB_USER_NAME";
    private static final String DB_PASSWORD = "DB_PASSWORD";
    
    private static BasicDataSource dataSource = new BasicDataSource();
    private static PropertiesDataLoader propertiesDataLoader = new PropertiesDataLoader();
    
    static {
        try {
            dataSource.setDriverClassName(propertiesDataLoader.getProperty(DB_DRIVER));
            dataSource.setUrl(propertiesDataLoader.getProperty(DB_URL));
            dataSource.setUsername(propertiesDataLoader.getProperty(DB_USER_NAME));
            dataSource.setPassword(propertiesDataLoader.getProperty(DB_PASSWORD));
            dataSource.setMinIdle(5);
            dataSource.setMaxIdle(10);
            dataSource.setMaxOpenPreparedStatements(100);
        } catch (IOException exception) {
             exception.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
    private DataSource() {
    }
}
