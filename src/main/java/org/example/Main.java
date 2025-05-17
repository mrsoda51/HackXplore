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
                    if (fill > 90.0) {
                        ContainerData data = new ContainerData(
                                row[0], // containerId
                                row[1], // district
                                row[2], // address
                                row[3], // date
                                row[4], // time
                                fill
                        );
                        overflows.add(data);
                    }
                }
            } catch (Exception e) {
                ctx.status(500).result("Error reading CSV: " + e.getMessage());
                return;
            }

            ctx.json(overflows);
        });
    }
}