package datetime;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xzhao9
 * @since 2022-07-14 10:47
 **/
public class FormatTest {

    /**
     * JDK-SimpleDateFormat
     * 速度慢
     * 线程不安全，所以不能设为static
     * 对于2022-06-31，会转换为2022-07-01,除非setLenient（false)
     */
    @Test
    public void SimpleDateFormatTest(){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        System.out.println(dateFormat.format(new Date()));
        try{
            System.out.println(dateFormat.parse("2015-09-01 12:00:08"));
        }catch (ParseException e){
            e.printStackTrace(); // 要转换的时间字符串：格式错误
        }
    }

    /**
     * joda.time DateTimeFormat
     * 线程安全
     * 对于2022-06-31，会报错: Value 31 for dayOfMonth must be in the range [1,30]
     */
    @Test
    public void DateTimeFormatTest(){
        final DateTimeFormatter DATE_TIME_FORMATTER= DateTimeFormat.forPattern("yyyyMMdd");
        System.out.println(DATE_TIME_FORMATTER.print(new DateTime()));
        System.out.println(DATE_TIME_FORMATTER.parseDateTime("20220914").getDayOfWeek());   //得到星期几的数字（int）
        // 得到：星期三
        final DateTimeFormatter GET_DAY_OF_WEEK_STRING=DateTimeFormat.forPattern("EEEE");
        System.out.println(GET_DAY_OF_WEEK_STRING.print(DATE_TIME_FORMATTER.parseDateTime("20220914")));
    }
}
