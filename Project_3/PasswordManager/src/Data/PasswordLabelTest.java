package Data;

import static org.junit.Assert.*;

import org.junit.Test;

public class PasswordLabelTest {
	@Test
	public void testGetters() {
		PasswordLabel testPassword = new PasswordLabel("Facebook", "password", false);
		assertTrue(testPassword.getLabel().equals("Facebook"));
		assertTrue(testPassword.getPassword().equals("password"));
		assertTrue(testPassword.isHidden() == false);
		testPassword.toggleHidden();
		assertTrue(testPassword.getPassword().equals("********"));
		assertTrue(testPassword.isHidden() == true);
	}
	
	@Test
	public void testSetter() {
		PasswordLabel testPassword = new PasswordLabel("Facebook", "password", true);
		testPassword.setPassword("newPassword");
		assertTrue(testPassword.getPassword().equals("newPassword"));
		assertTrue(testPassword.isHidden() == false);
	}
}
