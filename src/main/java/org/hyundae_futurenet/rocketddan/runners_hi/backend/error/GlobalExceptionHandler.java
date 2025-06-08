package org.hyundae_futurenet.rocketddan.runners_hi.backend.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {

		String msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		return ResponseEntity.badRequest().body("❌ " + msg);
	}
}
