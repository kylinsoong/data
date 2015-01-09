package org.apache.phoenix.datasource;

public class TestExtends {

	public static void main(String[] args) {

		new TestExtends().test();
	}
	
	private void test() {

		A a = new B();
		
		C c1 = new D();
		
		C c2 = new E();
	}

	class A {}
	
	class B extends A{}
	
	
	interface C {}
	
	class D implements C {}
	
	class E extends D {}

}
