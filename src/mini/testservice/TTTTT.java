package mini.testservice;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TTTTT extends Thread{
	public static void main(String[] args) throws Exception {
		//TTTTT t=new TTTTT();
		//t.init();
		/*
		mini.test.myservice
		mini.test.LoginService
		mini.test.ajaxTest
		*/
		
		
	}
	public void init(){
		
		//1先找过滤器  位置有限。正则匹配。
		//2 是否有星。有星则正常匹配。
		// /xx/xxx*		^\\/(\\w{1,}\\/){0,}\\w{1,}[*]$   ^\\/(aa\\/){0,}bbb\\w{1,}$
		 String uri="/a/b/c/*aaa";
		 String re=uri.replaceAll("/", "\\\\/");
		 re=re.replaceAll("\\*", "\\\\w{1,}");
		 re=re.replaceAll("\\.", "[.]");
		 re="^"+re+"$";
	   	System.out.println(Pattern.matches(re, "/a/b/c/aaa"));
	   	System.out.println(re);
	   	
		 ArrayList<String> list=new ArrayList<String>();
		 while(true){
			 int ind=uri.indexOf("/");
			 if(ind!=-1){
				 if(ind!=0){
					 String sss=uri.substring(0, ind);
					 list.add(sss);
				 }
				 uri=uri.substring(ind+1,uri.length());
			 }else{
				 list.add(uri);
				 break;
			 }
		 }
		// String []urllist=new String[list.size()];
		 String zhengze="^";
		 int dian=0;
		 for(int i=0;i<list.size();i++){
			String rs=list.get(i);
			 zhengze+="\\/";
			 dian=rs.indexOf("*");
			 System.out.println(dian);
		 }
		 zhengze+="$";
		 
		 String pattern1 = "^\\/(\\w{1,}\\/){0,}\\w{1,}[*]$";
		 String pattern2 = "^\\/(aa\\/){0,}bbb\\w{1,}$";
		 String filteruri="/aa/bbb*";
		
	     boolean isMatch = Pattern.matches(pattern2, uri);
	     
	}
}
