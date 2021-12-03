package com.annotation.reflection;

import java.lang.reflect.Field;

public class Main {

	@SuppressWarnings({"deprecation"})
	public static void main(String[] args) throws Exception {
		Person p1 = new Person("Bob", "Beijing", 20);
		Person p2 = new Person("", "Shanghai", 20);
		Person p3  = new Person("Alice", "Shanghai", 199);

		Person p4= (Person)(Class.forName("com.annotation.reflection.Person").newInstance());

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
