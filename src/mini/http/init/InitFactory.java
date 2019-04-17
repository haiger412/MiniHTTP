package mini.http.init;

import java.util.Hashtable;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

import mini.http.HttpContext;
import mini.http.MiniFilter;
import mini.http.action.HttpService;
import mini.http.util.XMLloader;

public class InitFactory {
	private static Object[][]serviceurlclasslist;
	private static Object[][]filterurlclasslist;
	/**
	 * 初始化。
	 * @param context
	 */
	public static void init(HttpContext context) {
		XMLloader loader=new XMLloader();
		try {
			  loadUrlClass(loader,context);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private static void loadUrlClass(XMLloader loader,HttpContext context) 
		throws ClassNotFoundException{
		Element root=loader.loadXML("web.xml");
		//加载过滤器
		loadFilter(root);
		//加载service
		loadService(root);
	}
	public static void loadFilter(Element root) throws ClassNotFoundException{
		List<Element> listele=root.elements("filter");
		filterurlclasslist=new Object[listele.size()][3];
		int i=0;
		for(Element ele: listele){
			Attribute _url=ele.attribute("url");
			Attribute _class=ele.attribute("class");
			Class<MiniFilter>filter=(Class<MiniFilter>) Class.forName(_class.getValue());
			filterurlclasslist[i][0]=_url.getValue();
			//存入一个filter的 正则
			String rs=_url.getValue();
			rs=rs.replaceAll("/", "\\\\/");
			rs=rs.replaceAll("\\*", "\\\\w{1,}");
			rs=rs.replaceAll("\\.", "[.]");
			rs="^"+rs+"$";
			filterurlclasslist[i][1]=rs;
			filterurlclasslist[i][2]=filter;
			i++;
		}
	}
	public static void loadService(Element root) throws ClassNotFoundException{
		List<Element> listele=root.elements("service");
		serviceurlclasslist=new Object[listele.size()][2];
		int i=0;
		for(Element ele: listele){
			Attribute _name=ele.attribute("name");
			Attribute _class=ele.attribute("class");
			Class<HttpService> httpserviceclass= (Class<HttpService>) Class.forName(_class.getValue());
			serviceurlclasslist[i][0]=_name.getValue();
			serviceurlclasslist[i][1]=httpserviceclass;
			i++;
		}
	}
	public static Object[][] getFilterurlclasslist() {
		return filterurlclasslist;
	}
	public static Object[][] getServiceurlclasslist() {
		return serviceurlclasslist;
	}
	
 
}
