package code.krishna.turboprep.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(Include.NON_NULL)
@Getter @Setter @NoArgsConstructor
public class Response<T> {
	
	T value;
	Error error;
	
	@Getter @Setter @AllArgsConstructor 
	class Error {
		private int code;
		private String message;
	}
}
