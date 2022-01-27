/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.internal.service.somruinternal.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.InternalServerErrorException;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

  /**
   * handling Unauthorized Exception
   * @param request
   * @param ex the Exception
   * @return the ApiError object
   */
  @ExceptionHandler(UnauthorizedException.class)
  protected ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex,
                                                               WebRequest request) {
    ErrorMessage error = new ErrorMessage(UNAUTHORIZED, ex, request.getContextPath());
    return buildResponseEntity(error);
  }

  /**
   * handling Forbidden Exception
   * @param request
   * @param ex the Exception
   * @return the ApiError object
   */
  @ExceptionHandler(ForbiddenException.class)
  protected ResponseEntity<Object> handleForbiddenException(ForbiddenException ex,
                                                            WebRequest request) {
    ErrorMessage error = new ErrorMessage(FORBIDDEN, ex, request.getContextPath());
    return buildResponseEntity(error);
  }

	/**
	 * Handle MissingServletRequestParameterException. Triggered when a 'required'
	 * request parameter is missing.
	 *
	 * @param ex      MissingServletRequestParameterException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorMessage error = new ErrorMessage(BAD_REQUEST, ex, request.getContextPath());
		LOGGER.error(error.getTag(), ex.getCause());
		return buildResponseEntity(error);
	}

	/**
	 * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is
	 * invalid as well.
	 *
	 * @param ex      HttpMediaTypeNotSupportedException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorMessage error = new ErrorMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex, request.getContextPath());
		LOGGER.error(error.getTag(), ex.getCause());
		return buildResponseEntity(error);
	}

	/**
	 * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid
	 * validation.
	 *
	 * @param ex      the MethodArgumentNotValidException that is thrown when @Valid
	 *                validation fails
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorMessage error = new ErrorMessage(BAD_REQUEST, ex, request.getContextPath());
		LOGGER.error(error.getTag(), ex.getCause());
		return buildResponseEntity(error);
	}

	/**
	 * Handles javax.validation.ConstraintViolationException. Thrown when @Validated
	 * fails.
	 *
	 * @param ex the ConstraintViolationException
	 * @return the ApiError object
	 */
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(HttpServletRequest request,
			javax.validation.ConstraintViolationException ex) {
		String path = request.getRequestURI().substring(request.getContextPath().length());
		ErrorMessage error = new ErrorMessage(BAD_REQUEST, ex, path);
		LOGGER.error(error.getTag(), ex.getCause());
		return buildResponseEntity(error);
	}

	/**
	 * Handles EntityNotFoundException. Created to encapsulate errors with more
	 * detail than javax.persistence.EntityNotFoundException.
	 *
	 * @param ex the EntityNotFoundException
	 * @return the ApiError object
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(HttpServletRequest request, EntityNotFoundException ex) {
		String path = request.getRequestURI().substring(request.getContextPath().length());
		ErrorMessage error = new ErrorMessage(NOT_FOUND, ex, path);
		LOGGER.error(error.getTag(), ex.getCause());
		return buildResponseEntity(error);
	}

	/**
	 * Handle HttpMessageNotReadableException. Happens when request JSON is
	 * malformed.
	 *
	 * @param ex      HttpMessageNotReadableException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorMessage error = new ErrorMessage(BAD_REQUEST, ex, request.getContextPath());
		LOGGER.error(error.getTag(), ex.getCause());
		return buildResponseEntity(error);
	}

	/**
	 * Handle HttpMessageNotWritableException.
	 *
	 * @param ex      HttpMessageNotWritableException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex, request.getContextPath());
		LOGGER.error(error.getTag(), ex.getCause());
		return buildResponseEntity(error);
	}

	/**
	 * Handle NoHandlerFoundException.
	 *
	 * @param ex
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ErrorMessage error = new ErrorMessage(BAD_REQUEST, ex, request.getContextPath());
		LOGGER.error(error.getTag(), ex.getCause());
		return buildResponseEntity(error);
	}

	/**
	 * Handle javax.persistence.EntityNotFoundException
	 */
	@ExceptionHandler(javax.persistence.EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(HttpServletRequest request,
			javax.persistence.EntityNotFoundException ex) {
		String path = request.getRequestURI().substring(request.getContextPath().length());
		ErrorMessage error = new ErrorMessage(HttpStatus.NOT_FOUND, ex, path);
		LOGGER.error(error.getTag(), ex.getCause());
		return buildResponseEntity(error);
	}

	/**
	 * Handle DataIntegrityViolationException, inspects the cause for different DB
	 * causes.
	 *
	 * @param ex the DataIntegrityViolationException
	 * @return the ApiError object
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
			WebRequest request) {
		if (ex.getCause() instanceof ConstraintViolationException) {
			ErrorMessage error = new ErrorMessage(HttpStatus.CONFLICT, ex, request.getContextPath());
			LOGGER.error(error.getTag(), ex.getCause());
			return buildResponseEntity(error);
		}
		ErrorMessage error = new ErrorMessage(BAD_REQUEST, ex, request.getContextPath());
		LOGGER.error(error.getTag(), ex.getCause());
		return buildResponseEntity(error);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		ErrorMessage error = new ErrorMessage(BAD_REQUEST, ex, request.getContextPath());
		LOGGER.error(error.getTag(), ex.getCause());
		return buildResponseEntity(error);
	}

  /**
   * Handle Exception, handle generic Exception.class
   *
   * @param ex the Exception
   * @return the ApiError object
   */
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(HttpServletRequest request, Exception ex) {
    String path = request.getRequestURI().substring(request.getContextPath().length());
		ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex, path);
		LOGGER.error(error.getTag(), ex.getCause());
		return buildResponseEntity(error);
	}

	private ResponseEntity<Object> buildResponseEntity(ErrorMessage apiError) {
		return new ResponseEntity<>(apiError, (apiError.getStatus() != null) ? HttpStatus.resolve(apiError.getStatus())
				: HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
