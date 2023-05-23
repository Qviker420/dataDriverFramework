package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySqlJDCButil {

    public static Connection getConnection() throws SQLException {
        Connection conn = null;

        try (FileInputStream f = new FileInputStream("C:\\Users\\chali\\TBC\\dataDriverFramework-homework\\DDT_Framework_HomeWork\\src\\main\\resources\\db.properties")) {

            Properties pros = new Properties();
            pros.load(f);

            String url = pros.getProperty("url");
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");

            conn = DriverManager.getConnection(url, user, password);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}
