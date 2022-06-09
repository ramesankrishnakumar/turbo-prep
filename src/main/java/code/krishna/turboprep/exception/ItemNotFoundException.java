package code.krishna.turboprep.exception;

public class ItemNotFoundException extends BaseException {
	
	private static final long serialVersionUID = -2138151492428663593L;
	
	public ItemNotFoundException(String message) {
		super(message);
	}

	@Override
	public int getErrorCode() {
		return 404;
	}
}
