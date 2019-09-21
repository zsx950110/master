package person.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class RegexTest {

    //java正则主要有Pattern类和Matcher类，Pattern类接受正则表达式，返回一个pattern对象，
    //matcher类对字符串进行匹配和解释，通过Pattern的matcher方法获得Matcher对象
    public static void main(String[] args) {
        //测试常用，纯数字
        boolean b = true;
        if (b){
            String string = "F:\\workspace\\myProject\\src\\main\\java\\person\\test\\MainTest.java";
            System.out.println("==========trim:"+string.trim());
            Pattern pattern  = Pattern.compile("[^\\/]*[\\/]+]");
            Matcher matcher = pattern.matcher(string);

            System.out.println("=====替换之后======"+matcher.replaceAll("test"));

            String date = "2019-09-12";
           String regex = "^[1-9]\\d{3}-((0?[1-9])|([1][0-2]))-((0?[1-9])|([12]\\d)|(3[01]))$";
           //Pattern pattern = Pattern.compile(regex);
           //Matcher matcher0  =pattern.matcher(date);
           String regex1  ="-";
           Pattern pattern1  =Pattern.compile(regex1);
           Matcher matcher1 = pattern1.matcher(date);
           if (Pattern.matches(regex,date)){
               System.out.println(matcher1.replaceAll("/"));
           }

            return;
        }

      String content="sfdsfw23#……&%ezsx_office@163.com";
      //java中\\d就表示\d，\\反斜杠就是两个，要表示普通反斜杠则为\\\\
      String pattern = "\\w+@\\w+\\..+";
      boolean ismatcher = Pattern.matches(pattern,content);
        System.out.println("========匹配结果============"+ismatcher);
        //匹配组
     String pattern1 = "(\\w*)(\\W*)([^\\d]*)(\\d*)";
     Pattern pattern2 = Pattern.compile(pattern1);
     Matcher matcher = pattern2.matcher(content);
     if (matcher.find()){
         System.out.println("匹配第四个子表达式的值"+matcher.group(4));
         System.out.println("匹配第四组的开始索引值"+matcher.start(4));
     }
        System.out.println("==========Match 的索引方法=========");
        String content1 = "9dus39dus3";
        String patternStr = "(9dus3)*";
        //忽略大小写
        Pattern pattern3 = Pattern.compile(patternStr,Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = pattern3.matcher(content1);
        if (matcher1.find()){
            System.out.println("匹配字符的开始索引,从0开始算起的："+matcher1.start());
            System.out.println("最后匹配字符的索引偏移量："+matcher1.end());
            System.out.println(matcher1.group(0));
            System.out.println("====匹配");
        }
        System.out.println("==============Match的研究方法=============");
        System.out.println("find:部分匹配，从当前位置开始匹配，找到一个匹配的子串，将移动下次匹配的位置," +
                "find会受到matches或者lookAt等其他方法的影响，如果通过matcher对象先调用了其他的匹配方法，那么find会从他们匹配之后的索引位置开始匹配:"+matcher1.find());
        System.out.println("lookAt部分匹配，总是从第一个字符进行匹配,匹配成功了不再继续匹配，匹配失败了,也不继续匹配。:"+matcher1.lookingAt());
        System.out.println("matches:整个匹配，只有整个字符序列完全匹配成功，才返回True，否则返回False:"+matcher1.matches());
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
    public static void testThread(){



    }

}
