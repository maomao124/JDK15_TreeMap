

## TreeMap

### 概述

`TreeMap` 新引入了下面这些方法：

- `putIfAbsent()`
- `computeIfAbsent()`
- `computeIfPresent()`
- `compute()`
- `merge()`





### 使用

```java
/**
 * If the specified key is not already associated with a value (or is mapped
 * to {@code null}) associates it with the given value and returns
 * {@code null}, else returns the current value.
 *
 * @implSpec
 * The default implementation is equivalent to, for this {@code map}:
 *
 * <pre> {@code
 * V v = map.get(key);
 * if (v == null)
 *     v = map.put(key, value);
 *
 * return v;
 * }</pre>
 *
 * <p>The default implementation makes no guarantees about synchronization
 * or atomicity properties of this method. Any implementation providing
 * atomicity guarantees must override this method and document its
 * concurrency properties.
 *
 * @param key key with which the specified value is to be associated
 * @param value value to be associated with the specified key
 * @return the previous value associated with the specified key, or
 *         {@code null} if there was no mapping for the key.
 *         (A {@code null} return can also indicate that the map
 *         previously associated {@code null} with the key,
 *         if the implementation supports null values.)
 * @throws UnsupportedOperationException if the {@code put} operation
 *         is not supported by this map
 *         (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
 * @throws ClassCastException if the key or value is of an inappropriate
 *         type for this map
 *         (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
 * @throws NullPointerException if the specified key or value is null,
 *         and this map does not permit null keys or values
 *         (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
 * @throws IllegalArgumentException if some property of the specified key
 *         or value prevents it from being stored in this map
 *         (<a href="{@docRoot}/java.base/java/util/Collection.html#optional-restrictions">optional</a>)
 * @since 1.8
 */
default V putIfAbsent(K key, V value) {
    V v = get(key);
    if (v == null) {
        v = put(key, value);
    }

    return v;
}
```





```java
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
```



```sh
v1
v2
v2
```





```java
/**
 * {@inheritDoc}
 *
 * <p>This method will, on a best-effort basis, throw a
 * {@link ConcurrentModificationException} if it is detected that the
 * mapping function modifies this map during computation.
 *
 * @throws ConcurrentModificationException if it is detected that the
 * mapping function modified this map
 */
@Override
public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
    Objects.requireNonNull(mappingFunction);
    V newValue;
    Entry<K,V> t = root;
    if (t == null) {
        newValue = callMappingFunctionWithCheck(key, mappingFunction);
        if (newValue != null) {
            addEntryToEmptyMap(key, newValue);
            return newValue;
        } else {
            return null;
        }
    }
    int cmp;
    Entry<K,V> parent;
    // split comparator and comparable paths
    Comparator<? super K> cpr = comparator;
    if (cpr != null) {
        do {
            parent = t;
            cmp = cpr.compare(key, t.key);
            if (cmp < 0)
                t = t.left;
            else if (cmp > 0)
                t = t.right;
            else {
                if (t.value == null) {
                    t.value = callMappingFunctionWithCheck(key, mappingFunction);
                }
                return t.value;
            }
        } while (t != null);
    } else {
        Objects.requireNonNull(key);
        @SuppressWarnings("unchecked")
        Comparable<? super K> k = (Comparable<? super K>) key;
        do {
            parent = t;
            cmp = k.compareTo(t.key);
            if (cmp < 0)
                t = t.left;
            else if (cmp > 0)
                t = t.right;
            else {
                if (t.value == null) {
                    t.value = callMappingFunctionWithCheck(key, mappingFunction);
                }
                return t.value;
            }
        } while (t != null);
    }
    newValue = callMappingFunctionWithCheck(key, mappingFunction);
    if (newValue != null) {
        addEntry(key, newValue, parent, cmp < 0);
        return newValue;
    }
    return null;
}
```





```java
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
```





```sh
被调用
v1
v1
```







```java
/**
 * {@inheritDoc}
 *
 * <p>This method will, on a best-effort basis, throw a
 * {@link ConcurrentModificationException} if it is detected that the
 * remapping function modifies this map during computation.
 *
 * @throws ConcurrentModificationException if it is detected that the
 * remapping function modified this map
 */
@Override
public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
    Objects.requireNonNull(remappingFunction);
    Entry<K,V> oldEntry = getEntry(key);
    if (oldEntry != null && oldEntry.value != null) {
        return remapValue(oldEntry, key, remappingFunction);
    } else {
        return null;
    }
}
```





```java
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
```



```sh
null
被调用2
1
v1
1-v1
```





```java
/**
 * {@inheritDoc}
 *
 * <p>This method will, on a best-effort basis, throw a
 * {@link ConcurrentModificationException} if it is detected that the
 * remapping function modifies this map during computation.
 *
 * @throws ConcurrentModificationException if it is detected that the
 * remapping function modified this map
 */
@Override
public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
    Objects.requireNonNull(remappingFunction);
    V newValue;
    Entry<K,V> t = root;
    if (t == null) {
        newValue = callRemappingFunctionWithCheck(key, null, remappingFunction);
        if (newValue != null) {
            addEntryToEmptyMap(key, newValue);
            return newValue;
        } else {
            return null;
        }
    }
    int cmp;
    Entry<K,V> parent;
    // split comparator and comparable paths
    Comparator<? super K> cpr = comparator;
    if (cpr != null) {
        do {
            parent = t;
            cmp = cpr.compare(key, t.key);
            if (cmp < 0)
                t = t.left;
            else if (cmp > 0)
                t = t.right;
            else
                return remapValue(t, key, remappingFunction);
        } while (t != null);
    } else {
        Objects.requireNonNull(key);
        @SuppressWarnings("unchecked")
        Comparable<? super K> k = (Comparable<? super K>) key;
        do {
            parent = t;
            cmp = k.compareTo(t.key);
            if (cmp < 0)
                t = t.left;
            else if (cmp > 0)
                t = t.right;
            else
                return remapValue(t, key, remappingFunction);
        } while (t != null);
    }
    newValue = callRemappingFunctionWithCheck(key, null, remappingFunction);
    if (newValue != null) {
        addEntry(key, newValue, parent, cmp < 0);
        return newValue;
    }
    return null;
}
```





```java
package mao;

import java.util.TreeMap;
import java.util.function.BiFunction;

/**
 * Project name(项目名称)：JDK15_TreeMap
 * Package(包名): mao
 * Class(类名): Test4
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2023/11/13
 * Time(创建时间)： 20:16
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class Test4
{
    public static void main(String[] args)
    {
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.compute("1", new BiFunction<String, String, String>()
        {
            @Override
            public String apply(String s, String s2)
            {
                return s2;
            }
        });
        System.out.println(treeMap.get("1"));
        treeMap.put("1", "v3333");
        treeMap.compute("1", (s, s2) -> s2);
        System.out.println(treeMap.get("1"));
    }
}
```





```sh
null
v3333
```





```java
/**
 * {@inheritDoc}
 *
 * <p>This method will, on a best-effort basis, throw a
 * {@link ConcurrentModificationException} if it is detected that the
 * remapping function modifies this map during computation.
 *
 * @throws ConcurrentModificationException if it is detected that the
 * remapping function modified this map
 */
@Override
public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
    Objects.requireNonNull(remappingFunction);
    Objects.requireNonNull(value);
    Entry<K,V> t = root;
    if (t == null) {
        addEntryToEmptyMap(key, value);
        return value;
    }
    int cmp;
    Entry<K,V> parent;
    // split comparator and comparable paths
    Comparator<? super K> cpr = comparator;
    if (cpr != null) {
        do {
            parent = t;
            cmp = cpr.compare(key, t.key);
            if (cmp < 0)
                t = t.left;
            else if (cmp > 0)
                t = t.right;
            else return mergeValue(t, value, remappingFunction);
        } while (t != null);
    } else {
        Objects.requireNonNull(key);
        @SuppressWarnings("unchecked")
        Comparable<? super K> k = (Comparable<? super K>) key;
        do {
            parent = t;
            cmp = k.compareTo(t.key);
            if (cmp < 0)
                t = t.left;
            else if (cmp > 0)
                t = t.right;
            else return mergeValue(t, value, remappingFunction);
        } while (t != null);
    }
    addEntry(key, value, parent, cmp < 0);
    return value;
}
```





```java
package mao;

import java.util.TreeMap;
import java.util.function.BiFunction;

/**
 * Project name(项目名称)：JDK15_TreeMap
 * Package(包名): mao
 * Class(类名): Test5
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2023/11/13
 * Time(创建时间)： 20:19
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class Test5
{
    public static void main(String[] args)
    {
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.merge("1", "v1", new BiFunction<String, String, String>()
        {
            @Override
            public String apply(String s, String s2)
            {
                System.out.println("方法被调用");
                return s2;
            }
        });
        System.out.println(treeMap.get("1"));
        treeMap.merge("1", "v2", (s, s2) -> s2);
        System.out.println(treeMap.get("1"));
        treeMap.merge("1", "v2", (s, s2) -> "12");
        System.out.println(treeMap.get("1"));
        treeMap.merge("1", "v2", (s, s2) -> "22");
        System.out.println(treeMap.get("1"));
        treeMap.merge("1", "22", new BiFunction<String, String, String>()
        {
            @Override
            public String apply(String s, String s2)
            {
                System.out.println("方法被调用");
                return null;
            }
        });
        System.out.println(treeMap.get("1"));
        treeMap.merge("1", "22", new BiFunction<String, String, String>()
        {
            @Override
            public String apply(String s, String s2)
            {
                System.out.println("方法被调用");
                return null;
            }
        });
        System.out.println(treeMap.get("1"));
    }
}
```



```sh
v1
v2
12
22
方法被调用
null
22
```







