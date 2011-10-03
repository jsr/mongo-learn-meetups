package utils;

import java.util.*;
import net.sf.oval.Validator;
import net.sf.oval.context.OValContext;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;

@SuppressWarnings("serial")
public class MaxListItemSizeCheck extends AbstractAnnotationCheck<MaxListItemSize> {

  private int maxSize;

  @Override
  public void configure(MaxListItemSize annotation) {
    this.maxSize = annotation.value();
    setMessage(annotation.message());
  }

  public boolean isSatisfied(Object o, Object list, OValContext context, Validator validator) {
    requireMessageVariablesRecreation();
    if (list == null) {
      return true;
    }
    
    for(Object s : (AbstractCollection)list) {
      if (s.toString().length() > maxSize) { return false; }
    }
    return true;
  }

  @Override
  public Map<String, String> createMessageVariables() {
    Map<String, String> messageVariables = new HashMap<String, String>();
    messageVariables.put("maxSize", Integer.toString(maxSize));
    return messageVariables;
  }
}