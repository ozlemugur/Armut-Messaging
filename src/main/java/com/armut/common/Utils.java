package com.armut.common;

import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class Utils {

	public String generateAlphaNumericString() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 32;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		return generatedString;
	}

	public String hashPassword(String password) {
		String md5Hex = DigestUtils.md5Hex(password).toUpperCase();
		return md5Hex;
	}

	public String generateToken() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}
