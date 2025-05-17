package org.example;

import com.opencsv.CSVReader;
import io.javalin.Javalin;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
        }).start("0.0.0.0", 8080);


        app.get("/api/containers", ctx -> {
            String requestedDate = ctx.queryParam("date");

            List<ContainerData> containers = new ArrayList<>();

            try (CSVReader reader = new CSVReader(new FileReader("altglas_simulation_angepasst.csv"))) {
                String[] row;
                boolean isFirst = true;

                while ((row = reader.readNext()) != null) {
                    if (isFirst) {
                        isFirst = false;
                        continue;
                    }

                    String date = row[3];

                    if (requestedDate == null || requestedDate.equals(date)) {
                        double fill = Double.parseDouble(row[5]);
                        containers.add(new ContainerData(
                                row[0], row[1], row[2], date, row[4], fill
                        ));
                    }
                }
            } catch (Exception e) {
                ctx.status(500).result("Error: " + e.getMessage());
                return;
            }

            ctx.json(containers);
        });


    }
}