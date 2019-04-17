package mini.http.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ResponseUtil {
	public static void wirteByteNotclose(String filename,OutputStream out) {
		wirteByteNotclose(new File(filename), out);
	}
	public static void wirteByteNotclose(File file,OutputStream out) {
		try {
			wirteByteNotclose(new FileInputStream(file), out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void wirteByteNotclose(InputStream in,OutputStream out) {
		byte[]b=new byte[2048];
		int line;
		try {
			while((line=in.read(b))!=-1){
				out.write(b, 0, line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(in!=null)in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void wirteByteclose(File file,OutputStream out){
		wirteByteNotclose(file, out);
		try {
			if(out!=null)
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
