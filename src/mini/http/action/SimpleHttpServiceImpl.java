package mini.http.action;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import mini.http.HttpSession;
import mini.http.MiniCookie;
import mini.http.MiniFilter;
import mini.http.MiniFilterChain;
import mini.http.MiniRequest;
import mini.http.MiniResponse;
import mini.http.SessionManager;
import mini.http.init.InitFactory;

final public class SimpleHttpServiceImpl implements HttpService,MiniFilterChain{
	private Map<String, Object> thradlocalmap;
	private MiniFilter []  Fileterlist; 
	private MiniRequest request;
	private MiniResponse response;
	private int current=0;
	private boolean executeservice=false;
	private int count=0;
	@Override
	public void service(MiniRequest request, MiniResponse response){
		this.request=request;
		this.response=response;
		ThreadLocal t=(ThreadLocal) request.getThreadLocal();
		thradlocalmap=(Map<String, Object>)t.get();
		checkCookieId(request, response);
		try {
			this.initFilterChain(request.getRequestURI());
		} catch (Exception e) {	} 
		this.doFilter();
		
	}
	/**
	 * ��ʼ��������������������uri���ɹ�����������
	 * @param reqUri
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void initFilterChain(String reqUri) throws InstantiationException, IllegalAccessException{
		Object filterlist[][]=InitFactory.getFilterurlclasslist();
		MiniFilter [] temp_filterlist=new MiniFilter[filterlist.length];
		int tempi=0;
		for(int i=0;i<filterlist.length;i++){
			String orgurl=(String) filterlist[i][0];
			String pattern=(String) filterlist[i][1];
			if("/*".equals(orgurl) ){//�������С�
				Class<MiniFilter>filter=(Class<MiniFilter>) filterlist[i][2];
				temp_filterlist[tempi++]=filter.newInstance();
			}else if(Pattern.matches(pattern, reqUri)){
				Class<MiniFilter>filter=(Class<MiniFilter>) filterlist[i][2];
				temp_filterlist[tempi++]=filter.newInstance();
			}
		}
		Fileterlist=new MiniFilter[tempi];
		for(int i=0;i<tempi;i++){
			Fileterlist[i]=temp_filterlist[i];
		}
	}
	/**
	 * ��һ����Ҫ�ֶ����á��Ժ�͸����û��Ƿ���þ�����
	 */
	@Override
	public void doFilter() {
		if(current!=0)count++;
		if(current<Fileterlist.length){
			Fileterlist[current++].Filter(this,request,response);	
		}
		if(executeservice==false){//һ�������������ִֻ��һ�Ρ�
			executeservice=true;
			if(Fileterlist.length==0){//û���ù�����������urlִ�ж�Ӧservice����̬��Դ����С�
				responseResource(0);
			}else{
				if(count==Fileterlist.length){//�����˹�����������urlִ�ж�Ӧservice����̬��Դ����С�
					responseResource(1);
				}else{//�����˹�����������urlִ�ж�Ӧservice����̬��Դ����С�
					responseResource(2);
				}
			}
		}	
		 
	}
	/**
	 * ��Ӧ��Դ��
	 */
	private void responseResource(int cmd){
		String uri=request.getRequestURI();
		if(cmd==0){//����url�Ҳ�����������ֱ�Ӳ���service��
			invokeService(uri);
		}else if(cmd==1){//����url�ҵ������������ҹ������Ѿ����С�ֱ�Ӳ���service��
			invokeService(uri);
		}else if(cmd==2){//����url�ҵ�������������������һ�������������С����ܵ���service��
			
		}
		
	}
	/**
	 * �����û���ϵͳ�Զ���servcie��
	 */
	private void invokeService(String uri){
		Object urllist[][]=InitFactory.getServiceurlclasslist();
		HttpService httpservice=null;
		for(int i=0;i<urllist.length;i++){
			String name=(String) urllist[i][0];
			if(uri.equals("/"+name)){
				try {
					Class<HttpService> httpserviceclass=(Class<HttpService>) urllist[i][1];
					httpservice=httpserviceclass.newInstance();
				} catch (Exception e) {e.printStackTrace();} 
				break;
			}
		}
		if(httpservice!=null){
			httpservice.service(request, response);
		}else{
			responseStaticRes(uri);
		}
	}
	/**
	 * ���ؾ�̬��Դ
	 */
	private void responseStaticRes(String uri) {
		 if(uri.equals("/")){
				File file=new File("index.html");
				responseStaticFile(file,response);
			}else{
				 if(uri.endsWith(".ico")){
					File file=new File("favicon.icon");
					responseStaticFile(file,response);
				}else if(uri.indexOf(".")!=-1){
					uri="."+uri;
					File file=new File(uri);
					responseStaticFile(file,response);
				}else{//  /xx/xxx/xxx
					responseStaticFile(null, response);
				}
			}
	}
	private void responseStaticFile(File file,MiniResponse response){
		if(file!=null && file.exists()){
			response.setResponseLine("HTTP/1.1 200 OK");
			response.responseStaticFile(file);
		}else{//404
			response.setResponseLine("HTTP/1.1 404 Not Found");
			response.responseStaticFile(new File("404.html"));
		}
	}
	/**
	 * ���鲢����cookieid��
	 * @param request
	 * @param response
	 */
	private void checkCookieId(MiniRequest request,MiniResponse response){
		String findsessionid=null;
		List<MiniCookie> cookieslist=request.getCookies();
		if(cookieslist!=null){
			for(MiniCookie cook:cookieslist){
				if("sessionid".equals(cook.getName())){
					findsessionid=cook.getValue();
					 break;
				}
			}
		}
		if(findsessionid==null){//���û���ҵ����򴴽�һ��cookie���͸���������档����Ϊsession��sessionid
			String uuid=SessionManager.getUUID();
			MiniCookie cookie=new MiniCookie("sessionid", uuid);
			response.SetCookie(cookie);
		}
		HttpSession session=SessionManager.getSession(findsessionid);//��������ͬ��
		if(session==null){
			session=SessionManager.createBrowserSession(findsessionid);//�����������ڲ���Ҫͬ����
		}
		addSessionToThreadLocal(session);
	}
	//��session�󶨵������̱߳������Ա���request���л�ȡ��
	private void addSessionToThreadLocal(HttpSession session){
		thradlocalmap.put("session", session);
	}
	
	
}
