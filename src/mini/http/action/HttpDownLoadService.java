package mini.http.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import mini.http.Config;
import mini.http.MiniHttpRequest;
import mini.http.MiniRequest;
import mini.http.MiniResponse;

public class HttpDownLoadService implements HttpService{
	@Override
	public void service(MiniRequest request, MiniResponse response) {
		/*String []strs=request.getParameters().get("filename");
		File file=new File("download/"+strs[0]);
		response.setResponseLine("HTTP/1.1 200 OK");
		response.setHeader("Content-Type", "application/octet-stream;charset="+Config.charset);
		response.setHeader("Content-Length", file.length()+"");
		response.setHeader("Content-Disposition", "attachment;filename="+strs[0]);
		response.writeResponseHeader();
		response.writeResponseContent(file);*/
		
	}
 
}
