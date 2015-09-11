package pl.parser.nbp;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils {

    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static List<Date> getDaysBetweenDates(Date startDate, Date endDate) {

        DateTime start = new DateTime(startDate);
        DateTime end = new DateTime(endDate);

        List<Date> ret = new ArrayList<Date>();
        DateTime tmp = start;
        while (tmp.isBefore(end) || tmp.equals(end)) {
            ret.add(tmp.toDate());
            tmp = tmp.plusDays(1);
        }
        return ret;
    }

    public static double getStdDev(List<Double> data) {
        return Math.sqrt(getVariance(data));
    }

    public static double getMean(List<Double> data) {
        double sum = 0.0f;
        for (double a : data)
            sum += a;
        return sum / data.size();
    }

    private static double getVariance(List<Double> data) {

        double mean = getMean(data);
        double temp = 0;
        for (double a : data)
            temp += (mean - a) * (mean - a);
        return temp / data.size();
    }
}
