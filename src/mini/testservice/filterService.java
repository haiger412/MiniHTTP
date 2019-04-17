package mini.testservice;

import mini.http.MiniRequest;
import mini.http.MiniResponse;
import mini.http.action.HttpService;

public class filterService implements HttpService{
	@Override
	public void service(MiniRequest request, MiniResponse response) {
		 System.out.println("Ö´ÐÐµ½service");
		 response.rederect("filter.html");
	}

}
