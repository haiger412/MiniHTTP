package mini.http;

import java.util.Date;
public class SessionManager implements TimerSchedule{
	private static HttpSession httpsessions[]=null;
	static{
		if(httpsessions==null){
			synchronized(SessionManager.class){
				if(httpsessions==null){
					httpsessions=new HttpSession[Config.MAX_SESSION_NUM];
				}
			}
		}
	}
	public static String getUUID(){
		return System.currentTimeMillis()+"";
	}
	public static HttpSession getSession(String sessionid){
		if(sessionid==null)return null;
		 for(HttpSession ss:httpsessions){
			   if(ss!=null && sessionid.equals(ss.getId())){
				   return ss;
			   }
		   }
		return null;
		
	}
	@Override
	public void executeSchedule() {
		removeOvertimeSession();
	}
	//定时一分钟清理一次   失效的session。
	@Override
	public int getMinutes() {
		return 1;
	}
	public static HttpSession createBrowserSession(String findsessionid){
		synchronized(httpsessions){
			  long curent_t=new Date().getTime();
			  for(int i=0;i<httpsessions.length;i++){
				   if(httpsessions[i]==null){
					   MiniHttpSession ss=new MiniHttpSession();
					   ss.setId(findsessionid);
					   ss.setBirthtimes(curent_t);
					   httpsessions[i]=ss;
					   return  ss;
				   }
			   }
		}
		return null;
	}
	/**
	 * 移除超时的cookie。在定时回调函数中被调用。
	 */
	private static void removeOvertimeSession() {
		synchronized(httpsessions){
			long curent_t=new Date().getTime();
			  for(int i=0;i<httpsessions.length;i++){
				   if(httpsessions[i]!=null){
					   MiniHttpSession ss=(MiniHttpSession) httpsessions[i];
					   boolean remove=ss.getBirthtimes()+ss.getSettimeout()>=curent_t;
					   if(remove){
						   ss.invalidate();
						   httpsessions[i]=null;
					   }
				   }
			   }
		}
	}
	
	
}
