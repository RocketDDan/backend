package org.hyundae_futurenet.rocketddan.runners_hi.backend.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleCustomException(CustomException e) {

		ErrorCode errorCode = e.getErrorCode();
		HttpStatusCode status = errorCode.getStatus();
		return ResponseEntity.status(status).body("⚠️ " + errorCode.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {

		String msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		return ResponseEntity.badRequest().body("❌ " + msg);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {

		return ResponseEntity.badRequest().body("⚠️ " + ex.getMessage());
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<?> handleIllegalStateException(IllegalStateException ex) {

		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("🚫 " + ex.getMessage());
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleCustomException(CustomException ex) {

		return ResponseEntity.status(ex.getErrorCode().getStatus()).body("⚠️ " + ex.getMessage());
	}
}
