package com.annotation.inject;

import java.lang.reflect.Field;
import java.util.Vector;

public class Main {

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
			Vector<Class> classes =  (Vector<Class>) f.get(classLoader);

			try {
				for(Class cls : classes){
					System.out.println(cls);

					if (cls == Person.class) {
						for (Field field : cls.getFields()) {
							System.out.println(field);
							
							/**
							 * 1. 这条语句会导致类加载器中的classes增加，进而出问题，好像是动态加载导致的
							 * 2. 所以不能这么直接处理，不知道那些注入库是怎么处理的
							 */
							Inject inject = field.getAnnotation(Inject.class);
						}
					}
				}
			} catch (Exception e) {
				System.out.println("获取Annotation增加了Inject.class类，会抛出异常");
				e.printStackTrace();
			}
		}

		for (Field field : person.getClass().getFields()) {
			Inject inject = field.getAnnotation(Inject.class);
			if (inject != null) {
				Object value = field.get(person);

				if (value instanceof Integer){
					field.set(person, inject.value());
					System.out.println("value: " + value + " -> " + inject.value());
				}
			}
		}

		System.out.println(person);

		{
			Field f;
			f = ClassLoader.class.getDeclaredField("classes");
			f.setAccessible(true);
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			// ClassLoader classLoader = ClassLoader.getSystemClassLoader();
			// ClassLoader classLoader = ClassLoader.getSystemClassLoader().getParent();
			Vector<Class> classes =  (Vector<Class>) f.get(classLoader);

			// 查看当前JVM中的所有的类
			try {
				for(Class cls : classes){
					System.out.println(cls);

					if (cls == Person.class) {
						for (Field field : cls.getFields()) {
							System.out.println(field);
							
							/**
							 * 1. 这条语句会导致类加载器中的classes增加，进而出问题，好像是动态加载导致的
							 * 2. 所以不能这么直接处理，不知道那些注入库是怎么处理的
							 */
							Inject inject = field.getAnnotation(Inject.class);
							if (inject != null) {
								Object value = field.get(person);

								if (value instanceof Integer){
									field.set(person, inject.value() + 5);
									System.out.println("value: " + value + " -> " + (inject.value() + 5));
								}
							}
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Inject.class类在上面被加载了，不会抛出异常");
				e.printStackTrace();
			}
		}

		System.out.println(person);
	}
}
