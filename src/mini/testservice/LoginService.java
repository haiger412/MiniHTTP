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
			ss.setAttribute("user", "���Ѿ���¼��,��¼ʱ��Ϊ:"+new Date());
			System.out.println("�û�δ��¼");
		}else{
			System.out.println("�û���Ϣ:"+ss.getAttribute("user"));
		}
		response.forward("index.html"); 
	}

	 

}
