package code.krishna.turboprep.exception;

@SuppressWarnings("serial")
public abstract class BaseException extends RuntimeException {
	protected BaseException(String message) {
		super(message);
	}
	
	public abstract int getErrorCode();
}
