package com.example.LinkShortener;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import java.sql.SQLException;

public class HttpServer {

    public static final String symbols= "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static Javalin app;

    public static void main(String[] args) throws SQLException {
        DataBase.start("localhost",
                "5432",
                "linksdatabase",
                "rafo",
                "qwerty"
        );

        //Connection creation
        app = Javalin.create(config -> {
            config.addStaticFiles("src/main/resources/public", Location.EXTERNAL);
        }).start(8000);

        app.post("/*", ctx-> {
            System.out.println("asas");
        });

        app.post("/your-short-link", ctx -> {
            String link = ctx.formParam("link"); //Got link from label
            String shortLink;

            shortLink = tryToGetFromDB(link);
            if(shortLink == null) {
                shortLink = shortenLink();
            }

            ctx.result(("<html>\n" +
                    "    <head>\n" +
                    "        <title>Link shortener</title>\n" +
                    "    </head>\n" +
                    "    <body>\n" +
                    "        <h1>Thank you, you can copy your link:</h1>\n" +
                    "        <input type=\"text\" value=localhost:8000/SHORT_LINK id=\"myInput\">\n" +
                    "        <h3>Powered by Rafayel Shahnazaryan</h3>\n" +
                    "    </body>\n" +
                    "</html>").replaceAll("SHORT_LINK", shortLink)).contentType("html");
        });
    }

    private static String tryToGetFromDB(String link) throws SQLException {
        String shortLink = DataBase.getShortenLinkFor(link);
        return null;
    }

    private static String shortenLink() {
        int length = (int) (Math.random()*10)+5;
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
