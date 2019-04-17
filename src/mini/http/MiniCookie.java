package mini.http;

public class MiniCookie {
	private String name;
	private String value;
	private String Expires;
	private String maxAge;
	private String Path;
	private String Secure;
	private String httpOnly;
	public MiniCookie(){
		
	}
	public MiniCookie(String name,String value){
		this.name=name;
		this.value=value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getExpires() {
		return Expires;
	}
	public void setExpires(String expires) {
		Expires = expires;
	}
	public String getMaxAge() {
		return maxAge;
	}
	public void setMaxAge(String maxAge) {
		this.maxAge = maxAge;
	}
	public String getPath() {
		return Path;
	}
	public void setPath(String path) {
		Path = path;
	}
	public String getSecure() {
		return Secure;
	}
	public void setSecure(String secure) {
		Secure = secure;
	}
	public String getHttpOnly() {
		return httpOnly;
	}
	public void setHttpOnly(String httpOnly) {
		this.httpOnly = httpOnly;
	}
	
	
}
