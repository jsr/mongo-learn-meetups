package ext;

import java.util.Date;
import java.text.SimpleDateFormat;
import play.templates.JavaExtensions;

public class DateExtensions extends JavaExtensions {
 
  public static String forCalendarPicker(Date date) {
    return new SimpleDateFormat("MMMM dd yyyy").format(date);
  }

  public static String forTimePicker(Date date) {
    return new SimpleDateFormat("HH:mm").format(date);
  } 
}