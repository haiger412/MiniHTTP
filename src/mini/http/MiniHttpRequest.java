package mini.http;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import mini.http.util.RequestUtil;

public class MiniHttpRequest  implements MiniRequest{
	private String requestmethod;//请求方法
	private InputStream in;//socket获取的输入流。
	private ThreadLocal threadlocal;//本地线程变量
	private Map<String,Object>thradlocalmap;//线程变量里保存的东西。
	private HashMap<String,List<String>>parameters;
	private HashMap<String,String>headermap;
	private HttpContext httpcontext;
	private String requestURI;
	private String requestSrc;
	private List<MiniCookie> cookies;
	public MiniHttpRequest (ThreadLocal threadlocal){
		this.threadlocal=threadlocal;
		thradlocalmap=(Map<String, Object>) threadlocal.get();
		init();
	}
	private void init(){
		this.in=(InputStream)this.thradlocalmap.get("inputstream");
		this.httpcontext=(HttpContext) this.thradlocalmap.get("httpcontext");
		RequestUtil rqutil=new RequestUtil(in);
		this.headermap=rqutil.getHeaderMap();
		this.requestmethod=rqutil.getRequestmethod();
		this.requestURI=rqutil.getRequestURI();
		this.parameters=rqutil.getParameters();
		this.requestSrc=rqutil.getRequestsrc();
	}
	
	public HashMap<String,List<String>> getParameters(){
		return parameters;
		
	}
	public String getRequestURI() {
		return requestURI;
	}
	
	public String getReqeustMethod() {
		return requestmethod;
	}
	public InputStream getInputStream(){
		return this.in;
	}
	public HttpContext getContext(){
		return this.httpcontext;
	}
	public String getRequestSrc(){
		return this.requestSrc;
	}
	public HashMap<String, String> getHeaderMap(){
		return this.headermap;
	}
	public List<MiniCookie> getCookies(){
		String cookie=this.headermap.get("cookie");
		if(cookie!=null){
			cookies=new ArrayList();
			StringTokenizer cooktk=new StringTokenizer(cookie,";");
			while(cooktk.hasMoreTokens()){
				String kv=cooktk.nextToken();
				String []rs=kv.split("\\=");
				MiniCookie cok=new MiniCookie(rs[0].trim(),rs[1].trim());
				cookies.add(cok);
			}
		}
		return cookies;
	}
	public boolean hasCookie(String name){
		String rs=getCookieValue(name);
		return rs==null?false :true;
	}
	public String getCookieValue(String name){
		List<MiniCookie> cookielist=null;
		if(cookies==null)this.getCookies();
		else cookielist=cookies;
		if(cookielist==null || name==null)return null;
		String value=null;
		for(MiniCookie cookie:cookielist){
			if(name.equals(cookie.getName())){
				value=cookie.getValue();
				break;
			}
		}
		return value;
	}
	public HttpSession getSession() {
		Object session= thradlocalmap.get("session");
		return session==null ? new MiniHttpSession() :(HttpSession)session;
	}
	public ThreadLocal getThreadLocal(){
		return this.threadlocal;
	}
	@Override
	public String getParameter(String key) {
		if(parameters!=null){
			List<String> rs=parameters.get(key);
			if(rs!=null)return rs.get(0);
		}
		return null;
	}
	@Override
	public void setParameters(HashMap<String, List<String>> paramstermap) {
		this.parameters=paramstermap;
	}
	 
	 
}
