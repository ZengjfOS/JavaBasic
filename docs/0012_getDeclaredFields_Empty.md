# getDeclaredFields Empty

MAC上无法反射到变量

## 参考文档

* [Differences in reflection between JDK 1.8 and JDK 16 [duplicate]](https://stackoverflow.com/questions/68413382/differences-in-reflection-between-jdk-1-8-and-jdk-16)
* [Get declared fields of java.lang.reflect.Fields in jdk12](https://stackoverflow.com/questions/56039341/get-declared-fields-of-java-lang-reflect-fields-in-jdk12)
* [vscode java launch.json args](https://stackoverflow.com/questions/52061958/vscode-java-launch-json-args/52341719)

## 无法获取类Field

```
ClassLoader.class.getDeclaredField("classes");
```
