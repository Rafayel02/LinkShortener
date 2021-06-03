package com.example.LinkShortener;
import java.sql.*;
import java.util.Arrays;

public class DataBase {

    private static Connection connection;

    public static void start(String host, String port, String database, String user, String password) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:postgresql://")
                .append(host)
                .append(":")
                .append(port)
                .append("/")
                .append(database);
        String url = sb.toString();
        connection = DriverManager.getConnection(url, user, password);
    }

    public static String getShortenLinkFor(String link) throws SQLException{
        createTableIfNotExists();
        String sql = "Select short_link from LinksTable where link = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, link);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }

        return null;
    }

    private static void createTableIfNotExists() throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("create table if not exists ")
                .append("LinksTable")
                .append("(")
                .append("link")
                .append(" text")
                .append(", ")
                .append("short_link")
                .append(" text")
                .append(")");
        Statement statement = connection.createStatement();
        statement.execute(sql.toString());
    }

}
