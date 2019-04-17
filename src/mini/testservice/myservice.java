package mini.testservice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import mini.http.HttpSession;
import mini.http.MiniCookie;
import mini.http.MiniHttpRequest;
import mini.http.MiniRequest;
import mini.http.MiniResponse;
import mini.http.action.HttpService;
public class myservice implements HttpService{
	@Override
	public void service(MiniRequest request, MiniResponse response) { 
		
		System.out.println(request.hasCookie("sessionid"));
		System.out.println(request.hasCookie("user"));
		response.forward("action.html"); 
		//response.rederect("action.html"); 
		
	}

	 
}
