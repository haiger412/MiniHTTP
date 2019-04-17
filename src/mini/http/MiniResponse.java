package mini.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mini.http.util.JSONUtil;
import mini.http.util.ResponseUtil;

public class MiniResponse {
	private ThreadLocal socketsession;//本地线程变量
	private String responseLine;//响应行。
	private Map<String,Object>thradlocal;
	private String httpverson="HTTP/1.1";
	private List<MiniCookie> cookielist;
	private Object []responseContent=new Object[2];//响应的资源 responseContent[0]为类型。responseContent[1]为资源。
	private HashMap<String,String> responseheader=new HashMap<String,String>();
	private OutputStream out;
	public MiniResponse(ThreadLocal socketsession){
		this.socketsession=socketsession;
		this.thradlocal=(Map<String, Object>) socketsession.get();
		this.init();
	}
	private void init(){
		this.out=(OutputStream)this.thradlocal.get("outputstream");
		this.responseLine="HTTP/1.1 200 OK";
		this.writedefaultheader();
		
	}
	private void writedefaultheader(){
		this.setHeader("Server", "Haiger MiniHttpServer/1.0");
		this.setHeader("Accept-Ranges", "bytes");
		this.setHeader("Date",new SimpleDateFormat("EEE,d MMM yyyy HH:mm:ss 'GMT'").format(new Date()));
	}
	public OutputStream getOutputStream(){
		return this.out;
	}
	public void setHeader(String key,String value){
		this.responseheader.put(key, value);
	}
	public void setResponseLine(String line){
		this.responseLine=line;
	}
	public void setContentType(String value){
		this.setHeader("Content-Type", value);
	}
	public void responseStaticFile(File file){
		try {
			this.setHeader("Content-Length", file.length()+"");
			this.setHeader("Content-Type", Config.getMIME(file.getName().split("\\.")[1])+";charset="+Config.charset);
			this.writeResponseHeader();
			this.writeResponseContent(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void writeResponseHeader_Firstline(StringBuffer sb){
		sb.append(responseLine+"\r\n");
	}
	private void writeResponseHeader_HeadEntry(StringBuffer sb){
		Set<String> set=responseheader.keySet();
		for(String key : set){
			sb.append(key+": "+responseheader.get(key)+"\r\n");
		}
	}
	private void writeResponseHeader_Cookie(StringBuffer sb){
		if(cookielist!=null){
			for(MiniCookie cookie:cookielist){
				if(cookie==null)continue;
				sb.append("Set-Cookie: "+cookie.getName()+"="+cookie.getValue());
				if(cookie.getPath()!=null){
					sb.append(";Path="+cookie.getPath());
				}
				if(cookie.getExpires()!=null){
					sb.append(";Expires="+cookie.getExpires());
				}
				if(cookie.getMaxAge()!=null){
					sb.append(";maxAe="+cookie.getMaxAge());
				}
				sb.append("\r\n");
			}
		}
	}
	private void writeResponseHeader_EmptyLine(StringBuffer sb){
		sb.append("\r\n");
	}
	public void writeResponseHeader() {
		StringBuffer sb=new StringBuffer();
		this.writeResponseHeader_Firstline(sb);
		this.writeResponseHeader_HeadEntry(sb);
		this.writeResponseHeader_Cookie(sb);
		this.writeResponseHeader_EmptyLine(sb);
	//	System.out.println("响应:  "+sb.toString());
		try {
			out.write(sb.toString().getBytes(Config.charset));
			sb=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void writeResponseContent(byte b[]){
		try {
			out.write(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void writeResponseContent(File file){
		ResponseUtil.wirteByteNotclose(file, out);
	}
	public void writeResponseContent(InputStream in){
		ResponseUtil.wirteByteNotclose(in, out);
	}
	public void writeResponseContent(String outstring){
		try {
			this.writeResponseContent(outstring.getBytes(Config.charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void setResponseStatus(int code){
		switch (code) {
			case 200:responseLine=httpverson+" "+code+" OK";break;
		    case 302:responseLine=httpverson+" "+code+" Found";break;
			case 404:responseLine=httpverson+" "+code+" Not Found";break;
		    case 500:responseLine=httpverson+" "+code+" XXX";break;
			default:break;
		}
	}
	public void ResponseAjax(String ajaxString){
		byte[] b;
		try {
			b = ajaxString.getBytes(Config.charset);
			this.setHeader("Content-Length",b.length+"");
			this.writeResponseHeader();
			this.writeResponseContent(b);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void ResponseAjax(Object obj){
		String str=JSONUtil.toJSON(obj);
		if(str==null)str="";
			this.ResponseAjax(str);
	}
	/**
	 * 重定向。
	 * @param pagename
	 */
	public void rederect(String pagename){
		this.setResponseStatus(302);
		this.setHeader("Location",pagename);
		this.writeResponseHeader();
	}
	/**
	 * 转发
	 * @param pagename
	 */
	public void forward(String pagename){
		this.setResponseStatus(200);
		File file=new File(pagename);
		if(file.exists()){
			this.responseStaticFile(file);
		}else{
			this.response404();
		}

	}
	public void response404(){
		this.setResponseStatus(404);
		this.responseStaticFile(new File("404.html"));
	}
	public void SetCookie(MiniCookie cookie){
		if(this.cookielist==null){
			this.cookielist=new ArrayList();
		}
		this.cookielist.add(cookie);
	}
}
