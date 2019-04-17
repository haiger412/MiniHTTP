package mini.http.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JSONUtil {
	private static String replace(String str){
		return str.replace("\\", "\\\\").replace("\"", "\\\"");
	}
	public static String toJSON(Object obj){
		StringBuffer sb=new StringBuffer();
		String rl="";
	    if(obj instanceof Map){
	    	Map map=(Map) obj;
	    	Set s=	map.keySet();
	    	Iterator i= s.iterator();
	    	if(!i.hasNext())return rl;
	    	 rl+="{";
	         int c=0;
	    	String key; 
	    	Object value; 
	    	while(i.hasNext()){
	    		  c++;
	    		  key=i.next().toString(); 
	    		  value=map.get(key); 
	    		  rl+="\"";
	    		  rl+=key;
	    		  rl+="\"";
	    		  rl+=":";
	    		  if(value instanceof Map){//��map
	    			  rl+= toJSON((Map)value);
	    		  }else if(value instanceof List){//��list
	    			  rl+= toJSON((List)value);
	    		  }else if(!jiaShuangYinHao(value)){//����������
	    			  rl+= value;
	    		  }else {						 //���ַ��������ڻ����������͡�
	    			  rl+="\"";
	    			  rl+=replace(String.valueOf (value));
	    			  rl+="\"";
	    		  }
	    		  rl+=",";
	    		
	    	}
	    	if(c>0){//ȥ�����һ������
	    		rl=rl.substring(0, rl.length()-1);
	    	}
	    	   rl+="}";
	    	   sb.append(rl);
	    	   rl=null;
	    	    return sb.toString();
	    }else if(obj instanceof List){
	    	int c2=0;
	    	List list=(List)obj;// ["","65","45","456","456",]
	    	rl+="[";
	    	for(int j=0;j<list.size();j++){
	    		c2++;
	    		Object value2=list.get(j);
	    		if(value2 instanceof Map){//��map
	  			    rl+= toJSON((Map)value2);
			    }else  if(value2 instanceof List){//��list
			    	rl+= toJSON((List)value2);
			   }else  if(!jiaShuangYinHao(value2)){//�����ͣ������͡�
	  			    rl+=value2;
		  	   }else {						      //���ַ��������ڻ����������͡�
	 			    rl+="\"";
		  			rl+=replace(String.valueOf (value2));
		  			rl+="\"";
			   }
	    	rl+=",";
	    	}
	    	if(c2>0){//ȥ�����һ������
	    		rl=rl.substring(0, rl.length()-1);
	    	}
	    	rl+="]";
	    	sb.append(rl);
	    	rl=null;
	    	return sb.toString();
	    	
	    }
	    else return null;	
	   }
	//Ҫ��Ҫ��˫���š� �������������ֺ��ַ������������ڶ���
	private static Boolean jiaShuangYinHao(Object obj){
		 if(obj instanceof Integer){
		    	return false;
		    }else  if(obj instanceof Float){
		    	return false;
		    }else  if(obj instanceof Long){
		    	return false;
		    }else{//��ת���ַ�����
		    	String ss=obj.toString();
		    	try{
		    		Integer.valueOf(ss);
		    		return false;
		    	}catch (Exception e) {}
		    	try{
		    		 Float.valueOf(ss);
		    		 return false;
		    	}catch (Exception e) {}
		    	try{
		    		 Double.valueOf(ss);
		    		 return false;
		    	}catch (Exception e) {}
		    	try{
		    		 Long.valueOf(ss);
		    		 return false;
		    	}catch (Exception e) {}
		    
		     }
		return true;
	}

}
