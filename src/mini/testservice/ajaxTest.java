package mini.testservice;

import java.util.ArrayList;
import java.util.List;

import mini.http.MiniHttpRequest;
import mini.http.MiniRequest;
import mini.http.MiniResponse;
import mini.http.action.HttpService;

public class ajaxTest implements HttpService{

	@Override
	public void service(MiniRequest request, MiniResponse response) {
		List <String> list=new ArrayList<String>();
		list.add("zhangsan");
		list.add("ÀîËÄ");
		response.setContentType("application/json");
		response.ResponseAjax(list);
		
	}
 

}
