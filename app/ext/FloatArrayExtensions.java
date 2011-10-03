package ext;

import java.util.List;
import play.templates.JavaExtensions;

public class FloatArrayExtensions extends JavaExtensions {
 
  public static String forDisplay(List<Float> coordinates) {
    return coordinates.size() != 2 ? "" : String.valueOf(coordinates.get(0)) + "," + String.valueOf(coordinates.get(1));
  }
}