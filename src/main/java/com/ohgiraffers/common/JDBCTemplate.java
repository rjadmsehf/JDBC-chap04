package com.ohgiraffers.common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCTemplate {




        public static Connection getConnection() {
            Connection con = null;

            Properties prop = new Properties();

            try {
                prop.load(new FileReader("src/main/java/com/ohgiraffers/config/connection-info.properties"));

                String driver = prop.getProperty("driver");             // 드라이버 불러오기 변수지정
                String url = prop.getProperty("url");

                Class.forName(driver);

                con = DriverManager.getConnection(url,prop);            // 연결

            } catch (IOException e) {
                throw new RuntimeException(e);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return  con;
        }

        public static void close(ResultSet rest) {
            try {
                if(rest !=null && !rest.isClosed()){

                    rest.close();

                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        public static void close(Statement stmt) {
            try {
                if(stmt !=null && !stmt.isClosed()){

                    stmt.close();

                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        public static void close(Connection con) {
            try {
                if(con !=null && !con.isClosed()){

                    con.close();

                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


