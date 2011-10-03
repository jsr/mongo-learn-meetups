package utils;

import com.google.gson.*;
import java.lang.reflect.Type;

public class PagedResultGsonAdapter implements JsonSerializer<PagedResult> {
  @Override
  public JsonElement serialize(PagedResult result, Type type, JsonSerializationContext context) {
    JsonObject json = new JsonObject();
    json.addProperty("total", result.total);
    json.addProperty("page", result.page);
    json.addProperty("pages", result.pages);
    json.add("models", context.serialize(result.models));
    return json;
  }
}