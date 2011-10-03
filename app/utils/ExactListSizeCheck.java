package utils;

import java.util.*;
import net.sf.oval.Validator;
import net.sf.oval.context.OValContext;
import net.sf.oval.configuration.annotation.AbstractAnnotationCheck;

@SuppressWarnings("serial")
public class ExactListSizeCheck extends AbstractAnnotationCheck<ExactListSize> {

  private int exactSize;

  @Override
  public void configure(ExactListSize annotation) {
    this.exactSize = annotation.value();
    setMessage(annotation.message());
  }

  public boolean isSatisfied(Object o, Object list, OValContext context, Validator validator) {
    requireMessageVariablesRecreation();
    return list == null || ((AbstractCollection)list).size() == this.exactSize;
  }

  @Override
  public Map<String, String> createMessageVariables() {
    Map<String, String> messageVariables = new HashMap<String, String>();
    messageVariables.put("exactSize", Integer.toString(exactSize));
    return messageVariables;
  }
}