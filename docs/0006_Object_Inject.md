# Object Inject

注解数据注入

## 注意

注解类Inject默认并没有被JVM加载，导致直接通过查找类注入引发动态加载，进而导致异常

```
java.util.ConcurrentModificationException
        at java.base/java.util.Vector$Itr.checkForComodification(Vector.java:1321)
        at java.base/java.util.Vector$Itr.next(Vector.java:1277)
        at com.annotation.inject.Main.main(Main.java:24)
```

## src

* [Main.java](/src/com/annotation/inject/Main.java)
  * 没有拷贝Vector会导致for循环异常
* [Mainv2.java](/src/com/annotation/inject/Mainv2.java)
  * 解决JVM动态加载Inject.class导致for循环异常，主要是拷贝Vector
