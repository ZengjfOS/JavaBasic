package com.annotation.reflection2inject;

public class Person {

	@Inject(value = 10)
	public int age;

	public Person() {
		this.age = 5;
	}

	public Person(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return String.format("{Person: age=%d}", age);
	}
}
