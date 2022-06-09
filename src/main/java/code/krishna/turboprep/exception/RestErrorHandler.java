package code.krishna.turboprep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestErrorHandler {
	@ExceptionHandler(value = ItemNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Response<Object> itemNotFoundException(ItemNotFoundException ex) {
		return createResponseForError(ex);
	}
	
	private Response<Object> createResponseForError(BaseException ex) {
		Response<Object> rsp = new Response<>();
		rsp.setError(rsp.new Error(ex.getErrorCode(), ex.getMessage()));
		return rsp;
	}

}
