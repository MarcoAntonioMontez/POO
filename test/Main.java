package test;

import javax.xml.parsers.*; // SAX and DOM parsers

import org.xml.sax.*; // Generic API for SAX
import org.xml.sax.helpers.*;
import java.io.File;

import board.*;
import event.*;
import simulation.*;
import pec.*;
import numberGen.*;

public class Main extends DefaultHandler{
	static String fileName;
	static InitObject initObject = new InitObject();
	static Edge edge;
	static Point initialPoint;
	static Point finalPoint;
	
	
	boolean is_grid=false;
	boolean is_simulation=false;
	boolean is_zone=false;
	
	public void startDocument(){
		System.out.println("Beginning the parsing of"+ fileName);
	}
	
	public void endDocument(){
		System.out.println("Parsing concluded");
	}
	
	public void startElement(String uri, String name,
		String tag, Attributes atts){
		
		if(tag.equals("simulation")) {
			      
			initObject.finalinst = Integer.parseInt(atts.getValue(0));
			initObject.initpop = Integer.parseInt(atts.getValue(1));
			initObject.maxpop = Integer.parseInt(atts.getValue(2));
			initObject.comfortsens = Integer.parseInt(atts.getValue(3));
			
		}
		else if(tag.equals("grid")) {
			
			initObject.colsnb = Integer.parseInt(atts.getValue(0));
			initObject.rowsnb = Integer.parseInt(atts.getValue(1));
			
		}
		else if(tag.equals("initialpoint")) {
			
			initObject.xinitial = Integer.parseInt(atts.getValue(0));
			initObject.yinitial = Integer.parseInt(atts.getValue(1));
		}
		else if(tag.equals("finalpoint")) {
			initObject.xfinal = Integer.parseInt(atts.getValue(0));
			initObject.yfinal = Integer.parseInt(atts.getValue(1));

		}else if(tag.equals("zone")) {
			is_zone=true;

			initialPoint = new Point(Integer.parseInt(atts.getValue(0)), Integer.parseInt(atts.getValue(1)));
			finalPoint = new Point(Integer.parseInt(atts.getValue(2)), Integer.parseInt(atts.getValue(3)));
			
			
			edge = new Edge(0, initialPoint, finalPoint);
			initObject.specialCostZones.add(edge);
		}
		else if(tag.equals("obstacle")) {
			initObject.obstacles.add(new Point(Integer.parseInt(atts.getValue(0)), Integer.parseInt(atts.getValue(1))));
		}
		else if(tag.equals("death")) {
			initObject.death = Integer.parseInt(atts.getValue(0));
		}
		else if(tag.equals("reproduction")) {
			initObject.reproduction = Integer.parseInt(atts.getValue(0));
		}
		else if(tag.equals("move")) {
			initObject.move = Integer.parseInt(atts.getValue(0));
		}
		
		else
		System.out.print("Element <" + tag + ">");
	}
	
	public void endElement(String uri, String localName, String qName)
            throws SAXException {
		
		 if(qName.equals("zone"))  { is_zone= false;  }
    }
	
	public void characters(char[]ch,int start,int length){
		if(is_zone) {
			edge.setCost(Integer.parseInt(new String(ch,start,length)));		

		}
		
	} 

	public static void main(String[] argv) throws Exception {
		
		fileName = argv[0];

		 // builds the SAX parser
		 SAXParserFactory fact = SAXParserFactory.newInstance();
		 SAXParser saxParser = fact.newSAXParser();

		 // parse the XML document with this handler
		 DefaultHandler handler = new Main();
		 saxParser.parse(new File(fileName), handler);
		 
		 //System.out.print(initObject.toString());
		 
		 System.out.print("\n\n");
		 
		 Simulation sim = new Simulation(initObject);
		 EventPec pec = sim.getEventPec();
		 
		 sim.simulate();

	 
	}
}
