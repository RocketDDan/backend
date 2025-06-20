package org.hyundae_futurenet.rocketddan.runners_hi.backend.error;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.error.auth.TokenExpiredException;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(-1) // 우선순위 높게
public class CustomExceptionResolver implements HandlerExceptionResolver {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
		Object handler, Exception ex) {

		try {
			if (ex instanceof TokenExpiredException tokenEx) {
				response.setStatus(tokenEx.getErrorCode().getStatus().value());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				Map<String, Object> body = new HashMap<>();
				body.put("error", tokenEx.getErrorCode().getMessage());
				body.put("code", tokenEx.getErrorCode().name());
				body.put("detail", tokenEx.getErrorCode().getDetail());

				response.getWriter().write(objectMapper.writeValueAsString(body));
				return new ModelAndView();
			}
		} catch (IOException e) {
			// 로그 출력만
			e.printStackTrace();
		}

		return null; // 다른 ExceptionResolver에 위임
	}
}