package org.example;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);

        app.get("/hello", ctx -> ctx.result("Hello from Javalin!"));
    }
}