package com.me.bean;

public class B {
	private A a;
	
	public B() {
		System.out.println("һ��B�����Ѵ���");
	}
	
	public A getA() {
		return a;
	}
	public void setA(A a) {
		this.a = a;
	}

	@Override
	public String toString() {
		return "B [a=" + a + "]";
	}
	
	
	
}
