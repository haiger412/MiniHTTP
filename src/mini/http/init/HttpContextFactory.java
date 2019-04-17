package mini.http.init;


import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;
import mini.http.HttpContext;
/**
 * 初始化context对象。
 * @author Administrator
 *
 */
public class HttpContextFactory {
	private static HttpContext httpcontext;
	private static void initHttpContext(){
		Properties pro=new Properties();
		String pack=HttpContextFactory.class.getPackage().getName();
		pack=pack.replaceAll("\\.", "/")+"/init.properties";
		InputStream in=HttpContextFactory.class.getClassLoader().getResourceAsStream(pack);
		try {
			pro.load(in);
			String packclass=(String) pro.get("Context");
			@SuppressWarnings("unchecked")
			Class<HttpContext> cls=(Class<HttpContext>) Class.forName(packclass);
			Method m=cls.getMethod("newInstance");
			HttpContext context=(HttpContext) m.invoke(cls);
			httpcontext=context;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static{
		initHttpContext();
	}
	
	 public static HttpContext getHttpContext(){
		return httpcontext;
	 }
	 
}
