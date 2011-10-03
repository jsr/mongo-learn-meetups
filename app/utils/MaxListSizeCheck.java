package utils;

import java.util.*;
import net.sf.oval.Validator;
import net.sf.oval.context.OValContext;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;

@SuppressWarnings("serial")
public class MaxListSizeCheck extends AbstractAnnotationCheck<MaxListSize> {

  private int maxSize;

  @Override
  public void configure(MaxListSize annotation) {
    this.maxSize = annotation.value();
    setMessage(annotation.message());
  }

  public boolean isSatisfied(Object o, Object list, OValContext context, Validator validator) {
    requireMessageVariablesRecreation();
    return list == null || ((AbstractCollection)list).size() <= this.maxSize;
  }

  @Override
  public Map<String, String> createMessageVariables() {
    Map<String, String> messageVariables = new HashMap<String, String>();
    messageVariables.put("maxSize", Integer.toString(maxSize));
    return messageVariables;
  }
}