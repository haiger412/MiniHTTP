package mini.testservice;

import java.util.List;

import mini.http.MiniRequest;
import mini.http.MiniResponse;
import mini.http.action.HttpService;
public class Login implements HttpService {
	@Override
	public void service(MiniRequest request, MiniResponse response) {
		System.out.println(request.getParameter("username"));
		System.out.println(request.getParameter("password"));
		System.out.println(request.getParameter("password123"));
		List<String> list=request.getParameters().get("interest");
		System.out.println(list);
		response.forward("form.html");
	}
	

}
