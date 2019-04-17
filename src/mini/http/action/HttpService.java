package mini.http.action;

import mini.http.MiniRequest;
import mini.http.MiniResponse;

public interface HttpService {
	public void service(MiniRequest request, MiniResponse response);
}
