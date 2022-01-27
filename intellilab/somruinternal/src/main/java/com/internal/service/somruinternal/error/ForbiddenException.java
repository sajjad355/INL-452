package com.internal.service.somruinternal.error;

public class ForbiddenException extends RuntimeException {
  public ForbiddenException() {
    super();
  }

  public ForbiddenException(String message, Throwable cause) {
    super(message, cause);
  }

  public ForbiddenException(String message) {
    super(message);
  }

  public ForbiddenException(Throwable cause) {
    super(cause);
  }
}
