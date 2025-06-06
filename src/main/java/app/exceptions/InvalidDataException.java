package app.exceptions;

import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDataException extends RuntimeException {
  public InvalidDataException(String message, Object... args) {
    super(MessageFormatter.arrayFormat(message, args).getMessage());
  }
}