package com.example.LinkShortener;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.example.LinkShortener.Link;

import java.io.Serializable;
import java.sql.*;
import java.util.Arrays;

public class DataBase {

    private Connection connection;
    private SessionFactory sessionFactory = HibernateHelper.getSessionFactory();
    private Session session = sessionFactory.openSession();

    public void start(String host, String port, String database, String user, String password) throws SQLException {
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

    public String tryToGetFromDB(String link) {
        Link ll= null;
        String shortName = null;
        try {
            Query<Link> query =
                    session.createQuery("select l from Link l where l.originalName = :originalName", Link.class)
                            .setParameter("originalName", link);
            ll = query.getSingleResult();
            shortName = ll.getShortName();
        } catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(shortName);
        return shortName;
    }

    public void insertToDb() {
        try {
            Link link = new Link();
            link.setOriginalName("original");
            link.setShortName("short");

            Serializable linkId = session.save(link);
            link.setID((int) linkId);
            System.out.println(linkId);
        } catch(Exception e) {

        }
    }


    public void registerInDb(String link, String shortLink) {
        try {
            Link l = new Link();
            l.setOriginalName(link);
            l.setShortName(shortLink);

            Serializable linkId = session.save(l);
            l.setID((int) linkId);
            System.out.println(linkId);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
