package pl.parser.nbp;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class MainClass {

    //TODO:
    // watki
    // refactoring
    // pomijanie sobót i niedziel

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        if(args.length == 3){

            try {
                Currency currency = Currency.valueOf(args[0]);
                Date dateFrom = dateFormat.parse(args[1]);
                Date dateTo = dateFormat.parse(args[2]);


//                System.out.printf("curr:" + currency);
//                System.out.println("date1: " + dateFrom);
//                System.out.println("date2: " + dateTo);
                CurrencyCalculator currencyCalculator = new CurrencyCalculator(currency, dateFrom, dateTo);
                currencyCalculator.calculate();
            }
            catch(IllegalArgumentException e){
                printCurrencyError();
            } catch (ParseException e) {
                printDateError();
            }

        } else {
            printUsage();
        }

    }

    private static void printDateError() {
        System.out.println("Incorrect date format. Valid date format: " + dateFormat.toString());
    }

    private static void printCurrencyError() {
        System.out.println("Incorrect currency. Valid currencies: ");
        for(Currency currency : Currency.values()){
            System.out.print(currency);
        }

    }

    private static void printUsage() {
        System.out.println("Usage: java pl.parser.nbp.MainClass <CURRENCY> <START_DATE> <END_DATE>");
    }
}
