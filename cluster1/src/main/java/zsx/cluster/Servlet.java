package zsx.cluster;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Servlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.timeWait();
    }
    private void timeWait(){
        try {

            System.out.println("开始阻塞");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("线程名称========="+Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
        Date date = sd.parse(sd.format(new Date()));
        System.out.println(date);
    }
}
