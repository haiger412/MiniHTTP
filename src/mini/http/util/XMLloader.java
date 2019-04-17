package mini.http.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLloader {
	public Element loadXML(InputStream in){
				SAXReader reader = new SAXReader();
				Document dom;
				Element root = null;
				try {
					dom = reader.read(in);
					root = dom.getRootElement();
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			
			return root;
	}
	public Element loadXML(File file){
		try {
			return loadXML(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	 }
	public Element loadXML(String filename){
		return  loadXML(new File(filename));
	 }
}
