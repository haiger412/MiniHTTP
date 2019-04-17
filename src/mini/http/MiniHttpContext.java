package mini.http;

import java.util.HashMap;

import mini.http.action.HttpService;

public class MiniHttpContext implements HttpContext{
	private static MiniHttpContext context;
	private static HashMap<String,Object> attributes;
	private MiniHttpContext(){
	}
	public static MiniHttpContext newInstance(){
		if(context==null){
			synchronized(MiniHttpContext.class){
				if(context==null){
					attributes=new HashMap();
					context=new MiniHttpContext();
				}
			}
		}
		return context;
	} 
	public  void  setAttribute(String key,Object value){ 
		attributes.put(key, value);
	}
	public  Object getAttribute(String key){ 
		return attributes.get(key);
	}
	
}
