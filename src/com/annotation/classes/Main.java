package com.annotation.classes;

import java.lang.reflect.Field;
import java.util.Vector;

public class Main {

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static void main(String[] args) throws Exception {
		Person p1 = new Person("Bob", "Beijing", 20);
		Person p2 = null;
		Person p3  = null;
		Person p4  = null;

		p2= (Person)(Class.forName("com.annotation.classes.Person").newInstance());

		Field f;
        try {
            f = ClassLoader.class.getDeclaredField("classes");
            f.setAccessible(true);
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Vector<Class> classes =  (Vector<Class>) f.get(classLoader);

            for(Class cls : classes){
				System.out.println(cls);

				if (cls == Person.class) {
					p2 = (Person)(cls.newInstance());
					System.out.println("Person: " + p2);

					Class[] type = new Class[] { String.class, String.class, Integer.class };
					Object[] obj = new Object[] { "zengjf", "sz", 5};
					p3 = (Person)(cls.getConstructor(type).newInstance(obj));
					System.out.println("Person: " + p3);

					// Class[] type_6 = { String.class, String.class, int.class };
					type = new Class[] { String.class, String.class, int.class };
					obj = new Object[] { "zengjf", "fz", 1};
					p4 = (Person)(cls.getConstructor(type).newInstance(obj));
					System.out.println("Person: " + p4);
				}
            }
        } catch (Exception e) {
            e.printStackTrace();
		}

		for (Person p : new Person[] { p1, p2, p3, p4 }) {
			try {
				check(p);
				System.out.println("Person " + p + " checked ok.");
			} catch (IllegalArgumentException e) {
				System.out.println("Person " + p + " checked failed");
				e.printStackTrace();
			}

			System.out.println("------------------------------------");
		}
	}

	static void check(Person person) throws IllegalArgumentException, ReflectiveOperationException {
		for (Field field : person.getClass().getFields()) {
			Range range = field.getAnnotation(Range.class);
			if (range != null) {
				Object value = field.get(person);

				System.out.println("range.min: " + range.min() + ", range.max: " + range.max());
				if (value instanceof String) {
					System.out.println("value: " + value + ", length: " + ((String)value).length());
				} else if (value instanceof Integer){
					if ((Integer)value > range.min() && (Integer)value < range.max())
						System.out.println("value: " + value);
					else {
						throw new IllegalArgumentException("value out of range: " + field.getName());
					}
				}
			}
		}
	}
}
