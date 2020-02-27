package person.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class batchImportSql {
    public static void main(String[] args) {
      String str = "2019/08/07 19:51:00";

String pa11 ="yyyy/MM/dd HH:mm:ss";
String pa12 ="yyyy/MM/dd hh:mm:ss";

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pa12);
        Date date0 = new Date();
        str = simpleDateFormat.format(date0);
            try {
                Date date = simpleDateFormat.parse(str);
                System.out.println("时间为："+date.toString()+"--模式为："+pa12+"---测试字符串："+str);
            } catch (ParseException e) {
                e.printStackTrace();
            }



    }
}
