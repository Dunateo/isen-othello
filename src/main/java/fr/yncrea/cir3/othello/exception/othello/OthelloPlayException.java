package fr.yncrea.cir3.othello.exception.othello;

public class OthelloPlayException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OthelloPlayException() {
		super();
	}

	public OthelloPlayException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public OthelloPlayException(String message, Throwable cause) {
		super(message, cause);
	}

	public OthelloPlayException(String message) {
		super(message);
	}

	public OthelloPlayException(Throwable cause) {
		super(cause);
	}
}
