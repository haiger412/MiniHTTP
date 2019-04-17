package mini.testfilter;

import mini.http.MiniFilter;
import mini.http.MiniFilterChain;
import mini.http.MiniRequest;
import mini.http.MiniResponse;

public class Filter3 implements MiniFilter{

	@Override
	public void Filter(MiniFilterChain Chain,MiniRequest request, MiniResponse response) {
		System.out.println("执行过滤器3前");
		Chain.doFilter();
		System.out.println("执行过滤器3后");
	}

}
