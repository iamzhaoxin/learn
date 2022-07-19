package datetime;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author xzhao9
 * @since 2022-07-14 10:15
 **/
public class DateTimeTest {

    /**
     * java自带类
     */
    @Test
    public void JDKDateTimeTest(){
        Date d=new Date();
        Calendar c=new GregorianCalendar();
        c.setTime(d);
        System.out.println(c.get(Calendar.YEAR));
        System.out.println(c.get(Calendar.MONTH));  // 月份：0表示1月
        System.out.println(c.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 使用joda的DateTime
     */
    @Test
    public void jodaDateTimeTest(){
        Date d=new Date();
        DateTime dt=new DateTime(d);
        System.out.println(dt.getYear());
        System.out.println(dt.getMonthOfYear());
        System.out.println(dt.getDayOfMonth());
        System.out.println(dt.getDayOfWeek());
    }

    /**
     * DateTime->Date
     * .withTimeAtStartOfDay()
     * .plusMonth(?)
     */
    @Test
    public void otherTest(){
        DateTime dt=new DateTime(2022,7,14,10,36,55);
        // DateTime -> Date
        Date d=dt.toDate();
        // 2022-07-14T00:00:00.000+08:00
        DateTime today=DateTime.now().withTimeAtStartOfDay();
        // move DateTime
        DateTime dateTime=today.plusDays(3).plusMonths(2);
        System.out.println(dateTime);

        // 计算时间间隔
        Days daysBetween = Days.daysBetween(dt, dateTime);
        System.out.println(daysBetween.getDays());
    }

}
