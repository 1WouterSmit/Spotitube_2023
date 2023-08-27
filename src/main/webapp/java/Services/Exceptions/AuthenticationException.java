package Services.Exceptions;

public class AuthenticationException extends Exception {
	private String message;

	public AuthenticationException(String message) {
		super("");
		this.message = message;
	}

	public AuthenticationException() {
		super();
	}

	public String getMessage() {
		return message;
	}
}
