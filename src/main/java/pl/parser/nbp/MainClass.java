package pl.parser.nbp;

import com.sun.tools.javah.Util;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class MainClass {

    //TODO:
    // watki
    // refactoring
    // pomijanie sobót i niedziel
    // floats to double

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
                Result result = currencyCalculator.calculate();
                System.out.println(result);
                exitWithCode(ExitCode.SUCCESS);

            }
            catch(IllegalArgumentException e){
                printCurrencyError();
                exitWithCode(ExitCode.USER_ERROR);
            } catch (ParseException e) {
                printDateError();
                exitWithCode(ExitCode.USER_ERROR);
            } catch (CurrencyDataException e) {
                printProgramError();
                exitWithCode(ExitCode.PROGRAM_ERROR);
            }

        } else {
            printUsage();
            exitWithCode(ExitCode.USER_ERROR);
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

    private static void printProgramError() {
        System.out.println("Unexpected error while calculating the data");
    }

    private static void printUsage() {
        System.out.println("Usage: java pl.parser.nbp.MainClass <CURRENCY> <START_DATE> <END_DATE>");
    }


    private static void exitWithCode(ExitCode code){
        System.exit(code.getExitCode());
    }
}
