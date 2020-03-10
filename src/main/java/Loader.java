import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Loader {
    private static String fileCSVPath = "src\\main\\resources\\csv\\movementList.csv";
    private static String dateFormat = "dd.MM.yy";
    private static LinkedHashMap<String, Double> expenseTransactions = new LinkedHashMap<>();

    public static void main(String[] args) {
        ArrayList<PaymentTransaction> transactions = readAndParseCSVFile(fileCSVPath);
        int incomingSum = 0;
        int expenseSum = 0;
        for(PaymentTransaction pT : transactions){
            incomingSum += pT.getIncoming();
            expenseSum += pT.getExpense();
        }
        System.out.println("Incoming transactions summary: " + incomingSum);
        System.out.println("Expense transactions summary: " + expenseSum + "\n");

        System.out.println("Group expense" + "\t\t-\t\t" + "Amount");
        expenseTransactions.forEach((key, value) -> System.out.println(key + " - " + value));


    }

    private static ArrayList<PaymentTransaction> readAndParseCSVFile(String file){
        ArrayList<PaymentTransaction> transactions = new ArrayList<PaymentTransaction>();
        FileReader fReader = null;
        try {
            fReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Try again.");
        }

        BufferedReader bReader = new BufferedReader(fReader);
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        CSVReader csvReader = null;

        try {
            bReader.readLine();
            while(bReader.ready()){
                csvReader = new CSVReaderBuilder(bReader)
                        .withCSVParser(parser)
                        .build();
                String[] buff = csvReader.readNext();
                if(buff.length != 8){
                    System.out.println("Wrong line: ");
                    for(String str : buff){
                        System.out.print(str + " ");
                    }
                    continue;
                }
                double inc = 0.0;
                if(buff[7].matches(",")){
                    String withoutQuotes = buff[7].replace(',', '.');
                    BigDecimal dec = new BigDecimal(withoutQuotes);
                    inc = dec.doubleValue();
                }
                else{
                    inc = Double.parseDouble(buff[7].replace(',', '.'));
                }
                transactions.add(new PaymentTransaction(
                        buff[1],
                        (new SimpleDateFormat(dateFormat).parse(buff[3])),
                        Integer.parseInt(buff[6]),
                        inc
                ));
                if(buff[6].equals("0")){
                    String first = buff[5].replace("548673++++++1028", "").trim();
                    String result = first.substring(0, first.indexOf("   "));
                    if(expenseTransactions.containsKey(result)){
                        double value = expenseTransactions.get(result);
                        expenseTransactions.put(result, value + inc);
                    }
                    else {
                        expenseTransactions.put(result, inc);
                    }
                }
            }
            fReader.close();
            bReader.close();
            csvReader.close();
        } catch (IOException | ParseException | CsvValidationException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}
