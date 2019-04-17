package mini.testservice;

import java.util.Date;

import mini.http.HttpSession;
import mini.http.MiniHttpRequest;
import mini.http.MiniRequest;
import mini.http.MiniResponse;
import mini.http.action.HttpService;

public class LoginService implements HttpService{

	@Override
	public void service(MiniRequest request, MiniResponse response) {
		HttpSession ss=request.getSession();
		ss.getAttribute("user");
		if(ss.getAttribute("user")==null){
			ss.setAttribute("user", "我已经登录了,登录时间为:"+new Date());
			System.out.println("用户未登录");
		}else{
			System.out.println("用户信息:"+ss.getAttribute("user"));
		}
		response.forward("index.html"); 
	}

	 

}
