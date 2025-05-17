package org.example;

import com.opencsv.CSVReader;
import io.javalin.Javalin;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);
        app.get("/api/overflows", ctx -> {
            String requestedDate = ctx.queryParam("date");  // e.g. "2024-02-15"

            List<ContainerData> overflows = new ArrayList<>();

            try (CSVReader reader = new CSVReader(new FileReader("altglas_simulation_angepasst.csv"))) {
                String[] row;
                boolean isFirst = true;

                while ((row = reader.readNext()) != null) {
                    if (isFirst) {
                        isFirst = false;
                        continue;
                    }

                    double fill = Double.parseDouble(row[5]);
                    String date = row[3];

                    // 🧠 Filter by fill level + optional date match
                    boolean match = fill > 90.0;
                    if (requestedDate != null) {
                        match = match && date.equals(requestedDate);
                    }

                    if (match) {
                        overflows.add(new ContainerData(
                                row[0], row[1], row[2], date, row[4], fill
                        ));
                    }
                }
            } catch (Exception e) {
                ctx.status(500).result("Error: " + e.getMessage());
                return;
            }

            ctx.json(overflows);
        });

    }
}