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
//		 
//		 Individual adam = new Individual(sim,sim.grid.pointArray[3][2]);
//		 
//		 Individual eve = new Individual(sim,sim.grid.pointArray[2][1]);
//		 eve.addPointPath("down");
//		 eve.addPointPath("right");
//		 
//		 float pathEve = eve.getCostPath();
//		 float pathAdam =adam.getCostPath();
//		 System.out.print("\n\neve cost path: " + pathEve + " "+ eve.getLengthPath() +" and adam's cost path: " + pathAdam+ " "+adam.getLengthPath()+ "\n\n");
//		 System.out.print("\n\n eve's comfort: " +  eve.getComfort()+ " adam's comfort: " + adam.getComfort() +"\n");
//		 boolean test = sim.checkBestIndividual(adam);
//		 System.out.print("\n\nIs bestIndividual null: \n " + test);
//		 sim.setBestIndividual(adam);
//		 test=sim.checkBestIndividual(eve);
//		 System.out.print("\n\nIs eve better than adam: \n " + test +" eve's comfort: " +  eve.getComfort()+ " adam's comfort: " + adam.getComfort() +"\n");
//		 sim.setBestIndividual(eve);
//		 test = sim.checkBestIndividual(adam);
//		 System.out.print("\n\nIs adam better than eve: \n " + test);
		
//		 sim.popGenesis();
//		 
//		System.out.print("\n\nEventPec after sexy time \n " + pec.miniToString());
//		System.out.print("\n\nIndivList after sexy time\n " + sim.individualListToString());
//		
//		AbsEvent event;
//		
//		if(!pec.isNull()) {
//			event=sim.getNextEvent();
//			event.simulateEvent();
//		}
//		
//		System.out.print("\n\nEventPec after first event\n " + pec.miniToString());
//		System.out.print("\n\nIndivList after first event\n " + sim.individualListToString());
//		
//		
//		if(!pec.isNull()) {
//			event=sim.getNextEvent();
//			event.simulateEvent();
//			
//		}	
//		System.out.print("\n\nEventPec after second event\n " + pec.miniToString());
//		System.out.print("\n\nIndivList after second event\n " + sim.individualListToString());
//		System.out.print("\n\nIs pec empty" + pec.isNull());

		
		
		 

		 
		 
		 //sim.simulate();
		 
//		 while(! pec.isNull()) {
//				event=this.getNextEvent();
//				event.simulateEvent();
//
//			}
		 
		 
//		 sim.popGenesis();
//		 
//		 
//		 //Individual adam = new Individual(sim,sim.grid.initialPoint);
//		 
//		 //Se quiserem dar set
//		 
//			System.out.print("\n\nEventPec after sexy time \n " + pec.miniToString());
//			AbsEvent event=sim.getNextEvent();
//			if(!pec.isNull()) {
//				event.simulateEvent();
//			}
//			
//			System.out.print("\n\nEventPec after first event\n " + pec.miniToString());
//			event=sim.getNextEvent();
//			if(!pec.isNull()) {
//				event.simulateEvent();
//			}
//			
//		 
//			System.out.print("\n\nEventPec after second event \n " + pec.miniToString());
//		 
//
//		 System.out.print("\n\nIndivList after popGenesis\n " + sim.individualListToString());
	 
	}
}
