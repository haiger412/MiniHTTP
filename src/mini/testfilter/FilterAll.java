package mini.testfilter;

import mini.http.MiniFilter;
import mini.http.MiniFilterChain;
import mini.http.MiniRequest;
import mini.http.MiniResponse;

public class FilterAll implements MiniFilter {

	@Override
	public void Filter(MiniFilterChain Chain,MiniRequest request, MiniResponse response) {
		System.out.println("ȫ·������ǰ");
		Chain.doFilter();
		System.out.println("ȫ·�����غ�");
	}

}
