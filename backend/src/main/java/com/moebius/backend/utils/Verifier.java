package com.moebius.backend.utils;

import com.moebius.backend.exception.ExceptionTypes;
import com.moebius.backend.exception.WrongDataException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

public class Verifier {
	private static int VERIFICATION_CODE_LENGTH = 6;

	public static String generateCode() {
		return RandomStringUtils.randomNumeric(VERIFICATION_CODE_LENGTH);
	}

	public static <T> void checkNullField(T object) throws WrongDataException {
		Stream.of(object.getClass().getDeclaredFields())
			.filter(field -> field.getDeclaredAnnotation(Nullable.class) == null)
			.forEach(field -> {
				field.setAccessible(true);

				try {
					if (Objects.isNull(field.get(object))) {
						throw new WrongDataException(ExceptionTypes.NULL_DATA.getMessage(object.toString()));
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} finally {
					field.setAccessible(false);
				}
			});
	}
}
