package mao;

import java.util.TreeMap;
import java.util.function.Function;

/**
 * Project name(项目名称)：JDK15_TreeMap
 * Package(包名): mao
 * Class(类名): Test2
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2023/11/13
 * Time(创建时间)： 20:08
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class Test2
{
    public static void main(String[] args)
    {
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.computeIfAbsent("1", new Function<String, String>()
        {
            @Override
            public String apply(String s)
            {
                System.out.println("被调用");
                return "v" + s;
            }
        });
        System.out.println(treeMap.get("1"));
        treeMap.computeIfAbsent("1", new Function<String, String>()
        {
            @Override
            public String apply(String s)
            {
                System.out.println("被调用");
                return "v2" + s;
            }
        });
        System.out.println(treeMap.get("1"));

    }
}
