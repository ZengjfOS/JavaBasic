package com.annotation.autoloadclass;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

public class Main {

	@SuppressWarnings("rawtypes")
	public static Set<Class> findAllClassesUsingClassLoader(String packageName) {
		InputStream stream = ClassLoader.getSystemClassLoader()
		  .getResourceAsStream(packageName.replaceAll("[.]", "/"));

		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

		return reader.lines()
		  .filter(line -> line.endsWith(".class"))
		  .map(line -> getClass(line, packageName))
		  .collect(Collectors.toSet());
	}

	@SuppressWarnings("rawtypes")
	private static Class getClass(String className, String packageName) {
		System.out.println("className: " + className + ", packageName: " + packageName);

		try {
			return Class.forName(packageName + "."
			  + className.substring(0, className.lastIndexOf('.')));
		} catch (ClassNotFoundException e) {
			// handle the exception
		}

		return null;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static void main(String[] args) throws Exception {
		// 并不会加载Person类
		Person person = null;

		// 会自动加载包下所有的类
		// findAllClassesUsingClassLoader("com.annotation.autoloadclass");
		findAllClassesUsingClassLoader(Main.class.getPackageName());

		// 查看当前JVM中的所有的类
		{
			Field f;
			f = ClassLoader.class.getDeclaredField("classes");
			f.setAccessible(true);
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			// ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			// ClassLoader classLoader = ClassLoader.getSystemClassLoader().getParent();

			// 拷贝Vector，防止动态加载导致classes增加，导致for循环迭代出问题
			Vector<Class> classes = new Vector((Vector<Class>) f.get(classLoader));
			try {
				for(Class cls : classes){
					System.out.println(cls);

					if (cls == Person.class) {
						for (Field field : cls.getFields()) {
							System.out.println(field);

							Inject inject = field.getAnnotation(Inject.class);
							if (inject != null) {
								
								Class[] type = new Class[] { int.class };
								Object[] obj = new Object[] { inject.value() };
								person = (Person)(cls.getConstructor(type).newInstance(obj));
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("Person: " + person);
	}
}
