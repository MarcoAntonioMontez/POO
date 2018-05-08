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
		 sim.popGenesis();
		 
		 System.out.print("\n\nEventPec after sexy time \n " + pec.miniToString());
		 System.out.print("\n\nIndivList after epidemic\n " + sim.individualListToString());
		 
		 Individual daviv = new Individual(sim,sim.grid.pointArray[4][0]); 
//		 
//		 Death death = new Death(0.7f,daviv);
//		 Death death1 = new Death(0.0f,daviv);
//		 
//		 System.out.print("\n\ndeath equals  " +(death.getClass()==death1.getClass()));
		 
		 
		 
		 
//		 System.out.print("\n\nSTART OF REPRODUCTION\n\n \n");
		 
//		 Individual I_1 = new Individual(daviv);
//		 I_1.addPointPath("left");
//		 System.out.print("\n\nI_1 " + I_1.toString());

//		 
//		 pec = sim.getEventPec();
//		 System.out.print("\n\nPec " + pec.toString());
//
//		 Individual I_2 = new Individual(I_1);
//		 I_2.addPointPath("left");
//		 System.out.print("\n\nI_2 " + I_2.toString());
//		 sim.addIndividual(I_2);
//		 
//		 Individual I_3 = new Individual(I_2);
//		 I_3.addPointPath("down");
//		 System.out.print("\n\nI_3 " + I_3.toString());
//		 sim.addIndividual(I_3);
//		 
//		 Individual I_4 = new Individual(I_3);
//		 I_4.addPointPath("down");
//		 System.out.print("\n\nI_4 " + I_4.toString());
//		 sim.addIndividual(I_4);
//		 
//		 Individual I_5 = new Individual(I_4);
//		 I_5.addPointPath("down");
//		 System.out.print("\n\nI_5 " + I_5.toString());
//		 sim.addIndividual(I_5);
//		 
//		 Individual I_6 = new Individual(I_5);
//		 I_6.addPointPath("right");
//		 System.out.print("\n\nI_6 " + I_6.toString());
//		 sim.addIndividual(I_6);
//		 
//		 Individual I_7 = new Individual(I_6);
//		 I_7.addPointPath("right");
//		 System.out.print("\n\nI_6 " + I_7.toString());
//		 sim.addIndividual(I_7);
//		 
//		 AbsEvent move_1=new Move(0.0f,I_1);
//		 pec.add(move_1);
//		 pec.add(new Move(1.0f,I_2));
//		 pec.add(new Move(2.0f,I_3));
//		 
//
//		 System.out.print("\n\nIndivList before epidemic \n " + sim.individualListToString());
//		 System.out.print("\n\nEventPec before epidemic \n " + pec.miniToString());
//		 sim.epidemic();
//		 System.out.print("\n\nIndivList after epidemic\n " + sim.individualListToString());
//		 System.out.print("\n\nEventPec after epidemic \n " + pec.miniToString());
//		 
//		 Reproduction reproduction=new Reproduction(0.0f, I_7);
//		 reproduction.simulateEvent();
//		 System.out.print("\n\nEventPec after sexy time \n " + pec.miniToString());
//		 System.out.print("\n\nIndivList after epidemic\n " + sim.individualListToString());
		 
////////////////////////////////////////
//		 AbsEvent death= new Death(1.0f,daviv);
//		 System.out.print("\n\nDeath " + death.toString());
//		 pec.add(death);
//
//		 //Daviv vai para o ponto á esquerda
//		 daviv.addPointPath("left");
//		 System.out.print("\n\nPai 	"+daviv.toString());
//		 
//		 AbsEvent move= new Move(0,daviv);
//		 System.out.print("\n\nMove " + move.toString());
//		 
//		 move.simulateEvent();
//		 System.out.print("\n\nMove " + move.toString());
//		 System.out.print("\n\nPec " + pec.toString());
//		 
//		 AbsEvent currEvent = sim.getNextEvent();
//		 currEvent.simulateEvent();
//		 System.out.print("\n\nPec " + pec.toString());

		}	
}
