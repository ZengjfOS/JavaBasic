package com.annotation.inject;

import java.lang.reflect.Field;
import java.util.Vector;

public class Mainv2 {

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static void main(String[] args) throws Exception {
		Person person = new Person();

		System.out.println(person);

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
								Object value = field.get(person);

								if (value instanceof Integer){
									field.set(person, inject.value());
									System.out.println("value: " + value + " -> " + inject.value());
								}
							}
						}
					}
				}
			} catch (Exception e) {
				System.out.println("获取Annotation增加了Inject.class类，会抛出异常");
				e.printStackTrace();
			}
		}

		System.out.println(person);
	}
}
