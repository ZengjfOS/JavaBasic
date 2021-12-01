# Maven Project

转为Maven工程，便于安装依赖库

## 参考文档

* [How to create a Java / Maven project that works in Visual Studio Code?](https://stackoverflow.com/questions/46671308/how-to-create-a-java-maven-project-that-works-in-visual-studio-code)
* [dagger2-hello-world](https://github.com/ravn/dagger2-hello-world/blob/master/src/main/java/demo/Main.java)

## path

* `C:\Users\zengjianfeng\.m2`
  * maven默认包下载路径
* `wrapper\dists\apache-maven-3.6.3-bin\1iopthnavndlasol9gbrbg6bf2`
  * vscode自动下载的maven路径，版本可能有差异
  * 将其bin目录加入环境变量，这样就可以通过命令创建maven工程

## 命令行创建项目

mvn archetype:generate -DgroupId=com.annotation.maven -DartifactId=maven -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false

```
[INFO] Scanning for projects...
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-clean-plugin/2.5/maven-clean-plugin-2.5.pom
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-clean-plugin/2.5/maven-clean-plugin-2.5.pom (3.9 kB at 281 B/s)
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-plugins/22/maven-plugins-22.pom
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-plugins/22/maven-plugins-22.pom (13 kB at 27 kB/s)
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-clean-plugin/2.5/maven-clean-plugin-2.5.jar
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-clean-plugin/2.5/maven-clean-plugin-2.5.jar (25 kB at 34 kB/s)
...省略
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/apache/3/apache-3.pom
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/apache/3/apache-3.pom (3.4 kB at 8.6 kB/s)
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/archetypes/maven-archetype-quickstart/1.0/maven-archetype-quickstart-1.0.jar
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/archetypes/maven-archetype-quickstart/1.0/maven-archetype-quickstart-1.0.jar (4.3 kB at 11 kB/s)
[INFO] ----------------------------------------------------------------------------
[INFO] Using following parameters for creating project from Old (1.x) Archetype: maven-archetype-quickstart:1.0
[INFO] ----------------------------------------------------------------------------
[INFO] Parameter: basedir, Value: D:\zengjf\github\JavaAnotation
[INFO] Parameter: package, Value: com.annotation.maven
[INFO] Parameter: groupId, Value: com.annotation.maven
[INFO] Parameter: artifactId, Value: maven
[INFO] Parameter: packageName, Value: com.annotation.maven
[INFO] Parameter: version, Value: 1.0-SNAPSHOT
[INFO] project created from Old (1.x) Archetype in dir: D:\zengjf\github\JavaAnotation\maven
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  09:45 min
[INFO] Finished at: 2021-11-30T14:57:45+08:00
[INFO] ------------------------------------------------------------------------
```

* 将pom.xml拷贝出来，以及将maven生成的源代码包拷贝到当前目录，注意包结构一致性
* 重启打开当前目录，当前目录就变成了maven工程了

## 添加依赖

在[Java PROJECTS]窗口中的[Maven Dependencies]右边有一个[+]号，可以用于自动化添加依赖库，按照提示输入检索、安装

## MAVEN 

在[MAVEN]窗口可以执行一些按钮指令，前提是配置了前面的maven的命令目录，按提示配置目录即可
