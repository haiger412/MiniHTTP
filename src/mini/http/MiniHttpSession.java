package mini.http;

import java.util.HashMap;
import java.util.Hashtable;

public class MiniHttpSession implements HttpSession{
	private HashMap<String,Object> attributes=new HashMap();
	private String sessionid;
	private long birthtimes;
	private long settimeout=Config.SESSION_TIME_OUT*60*1000;//20分钟。单位毫秒。
	@Override
	public String getId() {
		return this.sessionid;
	}

	@Override
	public synchronized void setAttribute(String key, Object attribute){
		attributes.put(key, attribute);
	}

	@Override
	public Object getAttribute(String key) {
		return attributes.get(key);
	}

	@Override
	public synchronized void removeAttribute(String key) {
		attributes.remove(key);
	}

	@Override
	public void invalidate() {
		this.attributes=null;
		this.sessionid=null;
		//后续 可以触发事件。
		
	}
	public void setId(String sessionid) {
		this.sessionid = sessionid;
	}

	public long getBirthtimes() {
		return birthtimes;
	}

	public void setBirthtimes(long birthtimes) {
		this.birthtimes = birthtimes;
	}

	public long getSettimeout() {
		return settimeout;
	}

	public void setSettimeout(long settimeout) {
		this.settimeout = settimeout;
	}

	public HashMap<String, Object> getAttributes() {
		return attributes;
	}
	
	
	
	
	
}
