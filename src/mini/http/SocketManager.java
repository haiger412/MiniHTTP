package mini.http;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import mini.http.action.SimpleHttpServiceImpl;
import mini.http.init.HttpContextFactory;
import mini.http.util.RequestUtil;
import mini.http.util.ResponseUtil;

public class SocketManager extends Thread{
	private Socket client;
	private OutputStream out;
	private InputStream in;
	private MiniHttpRequest request;
	private MiniResponse response;
	private HttpContext httpcontext;
	private ThreadLocal threadlocal;
	private Map<String,Object>threadlocalmap;
	public SocketManager(Socket client) {
		this.client=client;
	}
	public void run(){
		try {
			init();
			service();
			destory();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(!client.isClosed())
					client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void init(){
		try {
			this.client.setSoTimeout(Config.HTTP_TIME_OUT);
			this.out=client.getOutputStream();
			this.in=client.getInputStream();
			this.threadlocalmap=new HashMap<String, Object>();
			this.threadlocal=new ThreadLocal();
			this.httpcontext=HttpContextFactory.getHttpContext();
			bindThreadLocal();
			this.request=new MiniHttpRequest(threadlocal);
			this.response=new MiniResponse(threadlocal);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void bindThreadLocal(){
		this.threadlocalmap.put("inputstream",this.in);
		this.threadlocalmap.put("outputstream",this.out);
		this.threadlocalmap.put("httpcontext", this.httpcontext);
		threadlocal.set(this.threadlocalmap);
	}
	/**
	 * 根据请求。处理响应。
	 */
	private void service(){
		new SimpleHttpServiceImpl().service(request, response);
	}
	/**
	 * 销毁请求。
	 */
	private void destory(){
		this.threadlocal=null;
		this.threadlocalmap=null;
		try {
			this.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
