package test.sample.example.inheritance;

import org.junit.Assert;
import org.junit.Test;

import sample.example.inheritance.A;
import sample.example.inheritance.B;

public class TestInheritance {

	@Test
	public void testInheritance() {
		A a = new A();
		Assert.assertEquals("Inside Non-Static A", a.print2());
		Assert.assertEquals("Inside Static A", a.print());
		
		A b = new B();
		Assert.assertEquals("Inside Non-Static B", b.print2());
		Assert.assertEquals("Inside Static A", b.print());

		B b2 = new B();
		Assert.assertEquals("Inside Non-Static B", b2.print2());
		Assert.assertEquals("Inside Static B", b2.print());
	}
}
