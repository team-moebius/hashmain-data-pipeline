package com.moebius.backend.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

public class Verifier {
	private static int VERIFICATION_CODE_LENGTH = 6;

	public static String generateCode() {
		return RandomStringUtils.randomNumeric(VERIFICATION_CODE_LENGTH);
	}

	public static <T> void checkNullFields(T object) throws NullPointerException {
		Stream.of(object.getClass().getDeclaredFields())
			.filter(field -> field.getDeclaredAnnotation(Nullable.class) == null)
			.forEach(field -> {
				field.setAccessible(true);

				try {
					if (Objects.isNull(field.get(object))) {
						throw new NullPointerException(object.toString() + " is null");
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} finally {
					field.setAccessible(false);
				}
			});
	}
}
