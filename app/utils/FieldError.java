package utils;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import play.data.validation.Error;

public class FieldError {
  private Map<String,String> errors = new HashMap<String,String>();
  
  public FieldError(String... parameters) {
    for (int i = 0; i < parameters.length; i += 2) {
      errors.put(parameters[i], parameters[i+1]);
    }
  }
  public FieldError(List<Error> list) {
    for(Error error : list) {
      errors.put(error.getKey(), error.message());
    }
  }
  
  public Boolean has(String field) {
    return errors.containsKey(field);
  }
  
  public String get(String field) {
    return errors.get(field);
  }
  
  public Map<String,String> getErrors() {
    return errors;
  }
}