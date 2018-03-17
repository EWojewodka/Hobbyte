/**
 * 
 */
package com.webrest.hobbyte.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Emil Wojewódka
 *
 * @since 17 mar 2018
 */
public class StringUtilsTest {

	@Test
	public void testOfValidEmails() throws IOException {
		try (InputStream is = getResource("valid-emails.txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
			String line;
			while ((line = br.readLine()) != null) {
				boolean isEmail = StringUtils.isEmail(line);
				if (!isEmail)
					Assert.assertTrue("Email (" + line + ") should be valid. ", isEmail);
			}
		}
	}
	
	@Test
	public void testOfInvalidEmails() throws IOException {
		try (InputStream is = getResource("invalid-emails.txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
			String line;
			while ((line = br.readLine()) != null) {
				boolean isEmail = StringUtils.isEmail(line);
				if (isEmail)
					Assert.assertFalse("Email (" + line + ") should be invalid. ", isEmail);
			}
		}
	}

	private InputStream getResource(String name) {
		return StringUtilsTest.class.getClassLoader().getResourceAsStream(name);
	}

}
