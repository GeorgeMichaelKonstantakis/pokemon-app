import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {

    private String filepath = "test_results.csv";

    public CsvWriter() {
    }

    public void toCsv(ResultsCsv resultsCsv) {

        File file = new File(filepath);
        try {
            FileWriter outputfile = new FileWriter(file, true);

            CSVWriter writer = new CSVWriter(outputfile);

            String[] data = {resultsCsv.getTestName(), String.valueOf(resultsCsv.getTestPassed()), resultsCsv.getTestResultMessage(), resultsCsv.getTimestamp()};
            writer.writeNext(data);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
