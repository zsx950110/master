package person.test;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.poi.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class RegexTest {

    //java正则主要有Pattern类和Matcher类，Pattern类接受正则表达式，返回一个pattern对象，
    //matcher类对字符串进行匹配和解释，通过Pattern的matcher方法获得Matcher对象
    public static void main(String[] args) {
        test1();

    }
    public static  void test(){

        String testStr = "there are Are many are aregs";
        //============忽略大小写============
        Pattern pattern = Pattern.compile("\\bare\\b",Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(testStr);
        int count=0;

        System.out.println("找到are一共"+count);
        //===================测试替换功能=============================
        StringBuffer stringBuffer = new StringBuffer();

        while (matcher.find()){
            count++;
            //将are替换为is，
            matcher.appendReplacement(stringBuffer,"is");
            System.out.println("appendReplacement替换后将上次替换和本次替换之后中间（包括替换的内容）的内容放到stringbuffer中："+stringBuffer.toString());
        }
        StringBuffer stringBuffer1 = new StringBuffer();
        matcher.appendTail(stringBuffer1);
        System.out.println("appendTail替换之后将最后一次匹配之后剩余的字符串放到buffer中："+stringBuffer1.toString());
        String stringAll =matcher.replaceAll("is");
        System.out.println("replaceAll将所有匹配的内容全部替换，并返回替换后的全部字符串内容"+stringAll);
        String stringFirst = matcher.replaceFirst("is");
        System.out.println("replaceFirst替换掉第一个匹配的内容,并返回替换后的全部字符串内容"+stringFirst);

    }
    public  static  void test1(){
        Long[] strings = new Long[3];
        strings[0]= 1L;
        final  Long[]strings1 = strings;
        strings1[1]=2L;
        System.out.println(strings[0]+strings[1]);


        List<Integer>  list1 = new ArrayList<Integer>();
        for (int i =0;i<6000;i++){
            list1.add(i);
        }
        List<Integer>  list2 = new ArrayList<Integer>();
        for (int i =0;i<4000;i++){
            list2.add(i);
        }
        long j = System.currentTimeMillis();
        list1.retainAll(list2);
long end = System.currentTimeMillis()-j;
        System.out.println(list1.size());
        System.out.println(end);
    }
}
