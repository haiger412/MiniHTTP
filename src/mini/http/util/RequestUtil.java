package mini.http.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import mini.http.Config;

public class RequestUtil {
	private HashMap<String,String> headerMap;
	private String requestsrc;
	private String requestURI;
	private String HttpVerson;
	private String requestmethod;
	private String urlparam=null;
	private HashMap<String,List<String>> parametersmap;
	public HashMap<String, String> getHeaderMap() {
		return headerMap;
	}
	public String getRequestsrc() {
		return requestsrc;
	}
	public String getRequestURI() {
		return requestURI;
	}
	public String getHttpVerson() {
		return HttpVerson;
	}
	public String getRequestmethod() {
		return requestmethod;
	}
	public RequestUtil(InputStream in){
		requestsrc=parseRequest(in);
		headerMap=getRequest(requestsrc);
		parseFirstLine();
		parseParameters(in);
	}	
	private String parseRequest(InputStream in){
		int []b=new int[4];
		StringBuffer sb=new StringBuffer();
		while(true){
				for(int i=0;i<b.length;i++){b[i]=-1;}
				try {
					if((b[0]=in.read())==13 && (b[1]=in.read())==10 && (b[2]=in.read())==13  && (b[3]=in.read())==10){
						break;
					}
				} catch (IOException e) {
				
					e.printStackTrace();
				}
			for(int i=0;i<b.length;i++){
					if(b[i]!=-1){
						sb.append((char)b[i]);
					}
			}
		}
		return sb.toString();
	}
	private HashMap<String,String> getRequest(String header){
		HashMap<String,String> headerMap=new HashMap();
		StringTokenizer stk=new StringTokenizer(header,"\r\n");
		String requestline=stk.nextToken();//ÇëÇóÐÐ
		headerMap.put("requestline", requestline);
		while(stk.hasMoreTokens()){
			String line=stk.nextToken();
			int ind=line.indexOf(":");
			if(ind>-1){
				String hkey=line.substring(0, ind).trim();
				String value=line.substring(ind+1, line.length()).trim();
				headerMap.put(hkey.toLowerCase(), value);
			}
		}
		return headerMap;
	}
	private void parseFirstLine(){
		String requestline=headerMap.get("requestline");
		StringTokenizer stk=new StringTokenizer(requestline);
		requestmethod=stk.nextToken().toLowerCase();
		requestURI=stk.nextToken();
		HttpVerson=stk.nextToken();
		StringTokenizer stk2=new StringTokenizer(requestURI,"?");
		requestURI=stk2.nextToken();
		if(stk2.hasMoreTokens()){
			urlparam=stk2.nextToken();
		}
	}
	private void parseParameters(InputStream in){
		//name=zhangsan&txt=12amp&;4
		//Content-Length: 43
		//Content-Type: multipart/form-data; boundary=----WebKitFormBoundarypPJwvC2r1WVoPKaY
		if(requestmethod.equals("get")){//if using get method,the param must be in urlparam 
			if(urlparam==null)return;
		}else if(requestmethod.equals("post")){//if using post method
			String contengtype=headerMap.get("content-type");
			if(contengtype!=null && contengtype.indexOf("multipart/form-data")!=-1){//upload file
				return;
			}else{
				int bytes=Integer.parseInt(headerMap.get("content-length"));
				if(bytes==0)return;
				byte params[]=new byte[bytes];
				try {
					in.read(params);
					urlparam=new String(params, Config.charset);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		int index;
		String keyvalue;
		parametersmap=new HashMap<String,List<String>>();
		while((index=urlparam.indexOf("&"))!=-1){//name=zhangsan&txt=12amp&;4
			 if(urlparam.charAt(index+1)!=';'){
				 keyvalue=urlparam.substring(0,index);//name=zhangsan
				 String []kv=keyvalue.split("\\=");
				 if(kv.length==1){
					 parametersmap.put(kv[0], null);
				 }else{
					 Object obj=parametersmap.get(kv[0]);
					 if(obj==null){
						 List li=new ArrayList<String>();
						 li.add(kv[1]);
						 parametersmap.put(kv[0], li);
					 }else{
						 ((ArrayList)obj).add(kv[1]);
					 }
				 }
				 urlparam=urlparam.substring(index+1, urlparam.length());
			}
			 
		}
		 String []kv=urlparam.split("\\=");
		 if(kv.length==1){
			 parametersmap.put(kv[0], null);
		 }else{
			 Object obj=parametersmap.get(kv[0]);
			 if(obj==null){
				 List li=new ArrayList<String>();
				 li.add(kv[1]);
				 parametersmap.put(kv[0], li);
			 }else{
				 ((ArrayList)obj).add(kv[1]);
			 }
		 }
		
	}
	public HashMap<String,List<String>>  getParameters(){
		return parametersmap;
	}
	
}
