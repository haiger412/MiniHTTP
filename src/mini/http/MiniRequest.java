package mini.http;

import java.util.HashMap;
import java.util.List;

public interface MiniRequest {
	public String getRequestURI();
	public	String getRequestSrc();
	public String getReqeustMethod();
	public List<MiniCookie> getCookies();
	public HttpContext getContext();
	public HttpSession getSession();
	public Object getThreadLocal();
	public HashMap<String,List<String>> getParameters();
	public void  setParameters(HashMap<String,List<String>> paramstermap);
	public String getParameter(String key);
	public boolean hasCookie(String string);

}
