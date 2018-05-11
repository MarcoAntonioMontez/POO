package test;

import javax.xml.parsers.*; // SAX and DOM parsers

import org.xml.sax.*; // Generic API for SAX
import org.xml.sax.helpers.*;
import java.io.File;

import board.*;
import simulation.*;

/**
 * @author nº 78508 Marco Montez, nº 79021 Tomás Cordovil, nº 78181 João Alves.
 */

/**
 * Class Main. Used to read the XML file, fill the InitObject and run the 
 * simulation.
 *  
 */

public class Main extends DefaultHandler{
	static String fileName;
	static InitObject initObject = new InitObject();
	static Edge edge;
	static Point initialPoint;
	static Point finalPoint;
	
	
	boolean is_grid=false;
	boolean is_simulation=false;
	boolean is_zone=false;
	

	/**
	 * Method to print out a string when starting to read the XML.
	 */
	public void startDocument(){
		System.out.println("Beginning the parsing of"+ fileName);
	}
	

	/**
	 * Method to print out a string when finishing reading the XML.
	 */
	public void endDocument(){
		System.out.println("Parsing concluded");
	}
	
	/**
	 * Method used to fill the initObject with the information read from
	 * the XML file.
	 * @param url
	 * @param name
	 * @param tag
	 * @param atts
	 */
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

	/**
	 * Method that ends an element.
	 * @param url
	 * @param localName
	 * @param qName
	 */
	
	public void endElement(String uri, String localName, String qName)
            throws SAXException {
		
		 if(qName.equals("zone"))  { is_zone= false;  }
    }
	

	/**
	 * Method that sets the attributes of an edge on a special cost zone
	 * to the values read from the XML.
	 * @param ch
	 * @param start
	 * @param length
	 */

	public void characters(char[]ch,int start,int length){
		if(is_zone) {
			edge.setCost(Integer.parseInt(new String(ch,start,length)));		

		}
		
	} 

	/**
	 * Main method that builds the SAX parser, parses the XML and runs the simulation
	 * @param argv
	 */
	public static void main(String[] argv) throws Exception {
		
		fileName = argv[0];

		 // builds the SAX parser
		 SAXParserFactory fact = SAXParserFactory.newInstance();
		 SAXParser saxParser = fact.newSAXParser();

		 // parse the XML document with this handler
		 DefaultHandler handler = new Main();
		 saxParser.parse(new File(fileName), handler);
		 	 
		 Simulation sim = new Simulation(initObject);
		 
		 sim.simulate();
	}
}
