package xmlPac;



import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import test.InitObject;

public class XMLReader extends DefaultHandler{
	String fileName;
	InitObject input = new InitObject();
	boolean is_simulation=false;
	
	public void startDocument(){
		System.out.println("Beginning the parsing of"+ fileName);
	}
	
	public void endDocument(){
		System.out.println("Parsing concluded");
	}
	
	public void startElement(String uri, String name,
		String tag, Attributes atts){
		System.out.print("Element <" + tag + "> \n");
		
//		if(tag.equals("simulation")) {
//			is_simulation=true;
//			System.out.print("Element <" + tag + "> \n");
//		}
	}
	
	public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if(qName.equals("simulation"))  { is_simulation=true;  }
    }
	
	public void characters(char[]ch,int start,int length){
		
		System.out.print(new String(ch,start,length));
//		if(isName) {
//			article.name=new String(ch,start,length);
//			System.out.print(new String(ch,start,length));
//		}
//		
	} 
	
	
	boolean isfinalinst=false;
	

}
