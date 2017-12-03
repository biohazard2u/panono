package com.panono.exception;

public class InvalidTimestampException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public InvalidTimestampException(String message) {
		super(message);
	}

	public static void check(boolean condition, String message, Object... args) {
		if (!condition) {
			throw new InvalidTimestampException(String.format(message, args));
		}
	}
}
