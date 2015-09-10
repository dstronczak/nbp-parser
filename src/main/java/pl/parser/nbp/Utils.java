package pl.parser.nbp;

import org.joda.time.DateTime;

import java.util.*;

public class Utils {

    public static int getYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

//    public static List<Date> getDaysBetweenDates(Date startdate, Date enddate)
//    {
//        List<Date> dates = new ArrayList<Date>();
//        Calendar calendar = new GregorianCalendar();
//        calendar.setTime(startdate);
//
//        while (calendar.getTime().before(enddate))
//        {
//            Date result = calendar.getTime();
//            dates.add(result);
//            calendar.add(Calendar.DATE, 1);
//        }
//        return dates;
//    }

    public static List<Date> getDaysBetweenDates(Date startDate, Date endDate) {

        DateTime start = new DateTime(startDate);
        DateTime end = new DateTime(endDate);

        List<Date> ret = new ArrayList<Date>();
        DateTime tmp = start;
        while(tmp.isBefore(end) || tmp.equals(end)) {
            ret.add(tmp.toDate());
            tmp = tmp.plusDays(1);
        }
        return ret;
    }

    public static float getStdDev(List <Float> data)
    {
        return (float) Math.sqrt(getVariance(data));
    }

    private static float getVariance(List<Float> data) {

            float mean = getMean(data);
            float temp = 0;
            for(Float a :data)
                temp += (mean-a)*(mean-a);
            return temp/data.size();

    }

    public static float getMean(List<Float> data) {
        float sum = 0.0f;
        for(float a : data)
            sum += a;
        return sum/data.size();
    }

}
