import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ClientLog {

    private int price[];
    private String products[];
    private ArrayList<String> logs = new ArrayList<>();

    ClientLog(int price[], String products[]) {
        this.price = price;
        this.products = products;
    }

    public void log(int productNumber, int productCount) {
        logs.add(productNumber + 1 + "," + productCount);
    }

    public void exportAsCSV(File csvFile) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile.getName(), true))) {
            for (String log : logs) {
                String logArrayCsv[] = log.split(",");
                writer.writeNext(logArrayCsv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
