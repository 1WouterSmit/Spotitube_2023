package Exceptions;

public class AuthorizationException extends Exception {
	private final String message;

	public AuthorizationException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
