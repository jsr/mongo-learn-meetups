package ext;

import java.util.*;
import play.templates.JavaExtensions;

public class EnumerableExtensions extends JavaExtensions {
 
  public static String join(Iterable iterable, String glue) {
    StringBuilder sb = new StringBuilder();
    for(Object o : iterable) {
      sb.append(o.toString());
      sb.append(glue);
    }
    if (sb.length() > 0) {
      sb.delete(sb.length() - 1, 1);
    }
    return sb.toString();
  } 
}