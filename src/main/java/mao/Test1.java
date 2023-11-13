package mao;

import java.util.TreeMap;

/**
 * Project name(项目名称)：JDK15_TreeMap
 * Package(包名): mao
 * Class(类名): Test1
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2023/11/12
 * Time(创建时间)： 17:45
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class Test1
{
    public static void main(String[] args)
    {
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.putIfAbsent("1", "v1");
        System.out.println(treeMap.get("1"));
        treeMap.put("1", "v2");
        System.out.println(treeMap.get("1"));
        treeMap.putIfAbsent("1", "v3");
        System.out.println(treeMap.get("1"));
    }
}
