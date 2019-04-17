package mini.http;

public interface HttpSession {
	public String getId();
	public void setAttribute(String key,Object attribute);
	public Object getAttribute(String key);
	public void removeAttribute(String key);
	public void invalidate();
}
