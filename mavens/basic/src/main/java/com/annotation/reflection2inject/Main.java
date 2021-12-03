package com.annotation.reflection2inject;

import java.lang.reflect.Field;
import java.util.Vector;

public class Main {

	@SuppressWarnings({"rawtypes", "unchecked", "unused"})
	public static void main(String[] args) throws Exception {
		// 并不会加载Person类
		Person person = null;

		Class clazz = Class.forName("com.annotation.reflection2inject.Person");

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

							/**
							 * 这条语句会导致类加载器中的classes增加，进而出问题，动态加载Inject.class导致
							 */
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
