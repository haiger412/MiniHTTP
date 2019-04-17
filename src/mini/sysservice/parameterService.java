package mini.sysservice;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import mini.http.Config;
import mini.http.MiniFilter;
import mini.http.MiniFilterChain;
import mini.http.MiniRequest;
import mini.http.MiniResponse;

public class parameterService implements MiniFilter{

	@Override
	public void Filter(MiniFilterChain Chain, MiniRequest request,
			MiniResponse response) {
		HashMap<String,List<String>> paramsmap;
			if("get".equals(request.getReqeustMethod()) || "post".equals(request.getReqeustMethod())){
				paramsmap=request.getParameters();
				if(paramsmap!=null){
					Set<Entry<String, List<String>>> set=paramsmap.entrySet();
					for(Entry<String, List<String>> paramlist:set){
						List<String> valueslist=paramlist.getValue();
						if(valueslist!=null){
							 for(int i=0;i<valueslist.size();i++){
								 try {
									 valueslist.set(i, java.net.URLDecoder.decode(valueslist.get(i),Config.charset));
								} catch (UnsupportedEncodingException e) {
									e.printStackTrace();
								}
							 }
						}
					}
				}
			}
		Chain.doFilter();
		
	}

}
