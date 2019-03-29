package fr.yncrea.cir3.othello.exception.othello;

public class OthelloCreateGameException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OthelloCreateGameException() {
		super();
	}

	public OthelloCreateGameException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public OthelloCreateGameException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public OthelloCreateGameException(String arg0) {
		super(arg0);
	}

	public OthelloCreateGameException(Throwable arg0) {
		super(arg0);
	}
}
