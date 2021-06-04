package com.example.LinkShortener;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.stream.Stream;

public class HttpServer {

    public static final String symbols= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static Javalin app;
    public static DataBase db;

    public static void main(String[] args) throws SQLException {
        //DB connection-start
        db = new DataBase();
        db.start("localhost",
                "5432",
                "linksdatabase",
                "rafo",
                "qwerty"
        );

        //Connection creation
        app = Javalin.create(config -> {
            config.addStaticFiles("src/main/resources/public", Location.EXTERNAL);
        }).start(8000);

        // Short link page
        app.post("/your-short-link", ctx -> {
            String link = ctx.formParam("link"); //Got link from label
            String shortLink;

            shortLink = db.tryToGetFromDB(link);

            if(shortLink == null) {
                shortLink = generateShortLink();
                db.registerInDb(link, shortLink);
            }
            ctx.result(getFileContent("src/main/resources/public/shorten_link_page.html")
                    .replaceAll("SHORT_LINK", shortLink))
                    .contentType("html");
        });

    }

    private static String getFileContent(String s) {
        Path path = Paths.get("src/main/resources/public/shorten_link_page.html");
        StringBuilder sb = new StringBuilder();
        try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
            stream.forEach(sb::append);
        } catch (Exception ex) {
            // Handle exception
        }
        return sb.toString();
    }

    private static String generateShortLink() {
        int length = (int) (Math.random()*5)+5;
        StringBuilder shortLink = new StringBuilder();
        for(int i = 0; i < length; i++) {
            int index = (int) (Math.random()*62);
            shortLink.append(symbols.charAt(index));
        }
        checkIfNotExists(shortLink);
        return shortLink.toString();
    }

    private static void checkIfNotExists(StringBuilder shortLink) {

    }

}
