package utils;

public class DuplicateKeyException extends RuntimeException {
  public DuplicateKeyException(Throwable t) {
    super(t);
  }
}