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
		 
		 int initPop=3;
		 int k=1;
		 Simulation sim = new Simulation(initObject,initPop,k);
		 //System.out.print(sim.grid.toString());
		 
		 
		 //Proximos Passos!!
		 
		 //1- Testar a inserção ordenada e remocao dos absEvents na Pec! pec.add(), pec.removeFirst().
		 
		 //2- Implementar os methods simulate Event dos 3 eventos (Death.move,reprod)
		 //O death e reprod afetam a lista de indiv da Sim
		 
		 //The journey of daviv and davivSon!
		 //Daviv is born on point (5,1) 
		 Individual daviv = new Individual(sim,sim.grid.pointArray[4][0]); 
		 System.out.print("\n\nPai 	"+daviv.toString());

		 //Daviv vai para o ponto á esquerda
		 daviv.addPointPath("left");
		 System.out.print("\n\nPai 	"+daviv.toString());
		 
		 AbsEvent move= new Move(0,daviv);
		 System.out.print("\n\nMove " + move.toString());
		 
		 EventPec pec = sim.getEventPec();
		 System.out.print("\n\nPec " + pec.toString());
		 
		 move.simulateEvent();
		 System.out.print("\n\nMove " + move.toString());
		 System.out.print("\n\nPec " + pec.toString());
		 
		 AbsEvent currEvent = sim.getNextEvent();
		 currEvent.simulateEvent();
		 System.out.print("\n\nPec " + pec.toString());
		// -----------------------------------------------------------
		 AbsEvent reproduction= new Reproduction(0,daviv);
		 
		 AbsEvent move1= new Move(0,daviv);
		 move1.simulateEvent();
		 
		 AbsEvent move2= new Move(0,daviv);
		 move2.simulateEvent();
		 
		 AbsEvent move3= new Move(0,daviv);
		 move3.simulateEvent();
		 
		 AbsEvent move4= new Move(0,daviv);
		 move4.simulateEvent();
		 
		 AbsEvent move5= new Move(0,daviv);
		 move5.simulateEvent();
		 
		 AbsEvent move6= new Move(0,daviv);
		 move6.simulateEvent();
		 
		 
		 
		 reproduction.simulateEvent();
		 
		 System.out.print("\n\nTime 	"+ reproduction.getNextTime());
		 System.out.print("\n\nPec " + pec.toString());
		 
		}	
}
