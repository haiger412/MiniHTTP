package mini.http;

import java.util.LinkedList;

public  interface  HttpContext {
	public static String URLCLASSTABLE="url_"+System.currentTimeMillis()+"_class";
	public  void  setAttribute(String key,Object value);
	public  Object getAttribute(String key);
}
