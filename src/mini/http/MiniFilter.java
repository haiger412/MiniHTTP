package mini.http;

public interface MiniFilter {
	public void Filter(MiniFilterChain Chain,MiniRequest request, MiniResponse response);
}
