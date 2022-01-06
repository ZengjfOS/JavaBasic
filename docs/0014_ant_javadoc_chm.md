# ant javadoc chm

ant生成java文档，并将java文档转成chm

不能直接用javadoc生成的文档，必须用ant生成的文档，可能文档格式上存在一丢丢的差异导致的

## ant build.xml

* 源代码要用UTF-8编码文件保存

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project name="stack_overflow">

    <property environment="SystemVariable" />
    <property name="chmDir" value="${SystemVariable.ANT_HOME}/lib" />

    <target name="javadoc">
        <javadoc packagenames="com.dns.*" sourcepath="." 
            destdir="javadoc" version="true" windowtitle="DNS Filter API" encoding="UTF-8" docencoding="UTF-8" charset="UTF-8">
        <group title="util packages" packages="com.dns.*"/>
        </javadoc>
        <echo message="java doc has been generated!" />
    </target>

    <target name="makechm">
        <java jar="${chmDir}/MyCHMWriter.jar" fork="true" dir="${chmDir}">
            <arg value="${basedir}/javadoc" />
            <arg value="javadoc.chm" />
            <arg value="v0.0.1" />
        </java>
    </target>
</project>
```

## ant

* docs\refers\0014_apache-ant-1.10.5.zip
  * bin目录配置到环境变量
* 跳转到目录：mavens/annotation/src/main/java
  * ant javadoc
  * ant makechm

## EasyCHM

* 使用ant生成javadoc
* 使用EasyCHM加载ant生成的文档目录
  * [0014_EasyCHM.zip](refers/0014_EasyCHM.zip)
* 编译生成chm文件
  * 注意这里的输出chm文件会在javadoc目录的父目录中；
  * 如下log中的`TOC cannot be resolved`问题无关紧要，chm还是会生成；
    ```
    正在输出目录文件...
          D:\zengjf\github\JavaAnotation\mavens\annotation\src\main\java\javadoc\TOC-Created-By-Easy-CHM.HHC
          输出完毕.
    正在输出索引文件...
          D:\zengjf\github\JavaAnotation\mavens\annotation\src\main\java\javadoc\Index-Created-By-Easy-CHM.HHK
    正在处理Alias和Map文件...
          输出完毕.
    正在输出工程文件...
          D:\zengjf\github\JavaAnotation\mavens\annotation\src\main\java\javadoc\javadoc.HHP
          输出完毕.
    
    工程名称:
          D:\zengjf\github\JavaAnotation\mavens\annotation\src\main\java\javadoc\javadoc.HHP
    CHM文件名:
          D:\zengjf\github\JavaAnotation\mavens\annotation\src\main\java\javadoc.CHM
    
    **********************************************************************
    Microsoft HTML Help Compiler 4.74.8702
    
    Compiling d:\zengjf\github\JavaAnotation\mavens\annotation\src\main\java\javadoc.CHM
    
    allclasses-frame.html
    allclasses-noframe.html
    com\dns\DnsItem.html
    com\dns\package-frame.html
    com\dns\package-summary.html
    com\dns\package-tree.html
    com\dns\PaxNetWorkControlManager.DnsItem.html
    com\dns\PaxNetWorkControlManager.html
    constant-values.html
    deprecated-list.html
    help-doc.html
    index.html
    index-all.html
    overview-tree.html
    script.js
    stylesheet.css
    
    TOC-Created-By-Easy-CHM.HHC
    
    Index-Created-By-Easy-CHM.HHK
    C:\Users\ZENGJI~1\AppData\Local\Temp\Easy-CHM-HtmlHelp-Maker.HTML
    c:\Users\ZENGJI~1\AppData\Local\Temp\Easy-CHM-HtmlHelp-Maker.GIF
    HHC5013: Error: URL reference in the TOC cannot be resolved: "Easy-CHM-HtmlHelp-Maker.HTML".
    HHC5013: Error: URL reference in the TOC cannot be resolved: "Easy-CHM-HtmlHelp-Maker.HTML".
    The following files were not compiled:
    **********************************************************************
    编译结束.
    ```
