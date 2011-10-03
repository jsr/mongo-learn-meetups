package utils;

import java.util.List;
import play.modules.morphia.Model;

public class PagedResult<T> {
  public long total;
  public int page;
  public long pages;
  public List<T> models;
  
  public static PagedResult<Object> Empty = new PagedResult<Object>(0, 0, 0, null);
  
  public PagedResult(long total, int page, int perPage, List<T> models){
    this.total = total;
    this.page = page;
    this.pages = (long)Math.ceil(total / (float)perPage);
    this.models = models;
  }
}