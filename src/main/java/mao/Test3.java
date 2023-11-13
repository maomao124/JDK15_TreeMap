package mao;

import java.util.TreeMap;
import java.util.function.BiFunction;

/**
 * Project name(项目名称)：JDK15_TreeMap
 * Package(包名): mao
 * Class(类名): Test3
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2023/11/13
 * Time(创建时间)： 20:11
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class Test3
{
    public static void main(String[] args)
    {
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.computeIfPresent("1", new BiFunction<String, String, String>()
        {
            @Override
            public String apply(String s, String s2)
            {
                System.out.println("被调用");
                System.out.println(s);
                System.out.println(s2);
                return s;
            }
        });
        System.out.println(treeMap.get("1"));
        treeMap.put("1", "v1");
        treeMap.computeIfPresent("1", new BiFunction<String, String, String>()
        {
            @Override
            public String apply(String s, String s2)
            {
                System.out.println("被调用2");
                System.out.println(s);
                System.out.println(s2);
                return s + "-" + s2;
            }
        });
        System.out.println(treeMap.get("1"));
    }
}
