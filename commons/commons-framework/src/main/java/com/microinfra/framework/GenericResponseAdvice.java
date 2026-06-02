package com.microinfra.framework;

import static com.microinfra.commons.bean.GlobalConstants.INTERNAL_SERVICE_API_URL_PATTERN;
import static com.microinfra.commons.bean.GlobalConstants.SERVICE_UNAVAILABLE_PROMPTS;

import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.microinfra.commons.bean.Result;
import com.microinfra.commons.lang.BizException;
import com.microinfra.commons.lang.SysException;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 响应拦截，对常见异常类型进行统一处理、包装请求结果及释放资源等
 * </p>
 *
 * @author albert pi
 * @since 1.0.0
 */

@Slf4j
@ControllerAdvice
public class GenericResponseAdvice implements ResponseBodyAdvice<Object> {

	private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<Object> handleMissingRequestParameterException(MissingServletRequestParameterException ex) {
		log.error("", ex);
		return new ResponseEntity<>(Result.error(HttpStatus.BAD_REQUEST.value(), ex.getParameterName() + "参数不能为空"), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MissingPathVariableException.class)
	public ResponseEntity<Object> handleMissingPathVariableException(MissingPathVariableException ex) {
		log.error("", ex);
		return new ResponseEntity<>(Result.error(HttpStatus.BAD_REQUEST.value(), ex.getVariableName() + "参数不能为空"), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Validation框架校验参数错误
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(BindException.class)
	public ResponseEntity<Object> handleException(BindException ex) {
		StringBuilder msg = new StringBuilder();

		BindingResult result = ex.getBindingResult();
		if (result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();

			for (ObjectError objError : errors) {
				if (objError instanceof FieldError) {
					FieldError fieldError = (FieldError) objError;
					msg.append(fieldError.getDefaultMessage() + ", ");
				}
			}
			msg.setLength(msg.length() - 2);
		}

		log.error(msg.toString(), ex);

		return new ResponseEntity<>(Result.error(HttpStatus.BAD_REQUEST.value(), msg.toString()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BizException.class)
	public ResponseEntity<Object> handleServiceException(BizException e) {
		log.error("", e);
		return new ResponseEntity<>(Result.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(SysException.class)
	public ResponseEntity<Object> handleSysException(SysException e) {
		log.error("", e);
		return new ResponseEntity<>(
				Result.error(HttpStatus.SERVICE_UNAVAILABLE.value(), !ObjectUtils.isEmpty(e.getMessage()) ? e.getMessage() : SERVICE_UNAVAILABLE_PROMPTS),
				HttpStatus.SERVICE_UNAVAILABLE);
	}

	/**
	 * 处理未知异常
	 * 
	 * @param
	 * @return
	 */
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<Object> handleException(Throwable t) {
		log.error("", t);

		String err = SERVICE_UNAVAILABLE_PROMPTS;
		if (t.getCause() != null) {
			if (!ObjectUtils.isEmpty(t.getCause().getMessage())) {
				err = t.getCause().getMessage();
			}
		} else if (!ObjectUtils.isEmpty(t.getMessage())) {
			err = t.getMessage();
		}

		if (t.getCause() instanceof BizException) {
			return new ResponseEntity<>(Result.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), err), HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (t.getCause() instanceof SysException) {
			return new ResponseEntity<>(Result.error(HttpStatus.SERVICE_UNAVAILABLE.value(), err), HttpStatus.SERVICE_UNAVAILABLE);
		} else {
			return new ResponseEntity<>(Result.error(HttpStatus.SERVICE_UNAVAILABLE.value(), err), HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter arg1, MediaType arg2, Class<? extends HttpMessageConverter<?>> arg3, ServerHttpRequest arg4,
			ServerHttpResponse arg5) {
		UserContextHolder.clean();

		if (body instanceof Result) {
			return body;
		}

		if (ANT_PATH_MATCHER.match(INTERNAL_SERVICE_API_URL_PATTERN, arg4.getURI().getPath())) {
			return body;
		}

		return Result.success(body);
	}

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

}
