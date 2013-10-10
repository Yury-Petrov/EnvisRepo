package com.envisprototype.controller.processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import processing.core.PApplet;

import android.util.Log;

import com.envisprototype.model.processing.Coordinates;
import com.envisprototype.view.processing.EnvisPApplet;
import com.envisprototype.view.processing.SensorSet;



public class CoordinateFiller {
	EnvisPApplet epApplet;
	BufferedReader mapBReader, sensorsBReader;
	String line, sensorLine;
	
	public CoordinateFiller(EnvisPApplet epApplet){
		this.epApplet = epApplet;
	}
	
	public ArrayList<SensorSet> prepareSensorsCoordinates(String sensorFileName){
		ArrayList<SensorSet> envisSensors = new ArrayList<SensorSet>();
		try {
			sensorsBReader = new BufferedReader(new FileReader(new 
			        File(epApplet.getFilesDir()+File.separator+sensorFileName)));
			sensorLine = sensorsBReader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			sensorLine = null;
			e.printStackTrace();
		}
		if(sensorLine != null){
			// parsing sensors
			  if(sensorLine != null){
				  PApplet. println(line);
			    {
			    	String[][] xValues =PApplet. matchAll(sensorLine, "x:\\d+");
			    	String[][] yValues = PApplet.matchAll(sensorLine, "y:\\d+");
			    	String[][] zValues = PApplet.matchAll(sensorLine, "z:\\d+");
			    	//String[][] idValues = PApplet.matchAll(sensorLine, "id:\\d+"); //!!!! must be changed properly
			      
			      for(int j = 0; j < xValues.length; j++){
//			         String[][] xValues2 = matchAll(xValues[j][0],"\\d+");
//			         String[][] yValues2 = matchAll(yValues[j][0],"\\d+");
			    	// String idString = PApplet.matchAll(idValues[j][0],"\\d+")[0][0];
			         String xString = PApplet.matchAll(xValues[j][0],"\\d+")[0][0];
			         String yString = PApplet.matchAll(yValues[j][0],"\\d+")[0][0];
			         String zString = PApplet.matchAll(zValues[j][0],"\\d+")[0][0];
			         envisSensors.add(new SensorSet(epApplet, "dummy",
			    			  Float.parseFloat(xString),
			    			  Float.parseFloat(yString),
			    			  Float.parseFloat(zString)));
			         PApplet.println("zzzzz " + zString);
			         envisSensors.get(j).printCoors();
			      }
			    }
			  }
		}
		return envisSensors;
	}

	public void prepareMapCoordinates(String mapFileName){
		ArrayList<Float> coorX = new ArrayList<Float>();
		ArrayList<Float> coorY = new ArrayList<Float>();
	  try{
	    mapBReader = new BufferedReader(new FileReader(new 
                File(epApplet.getFilesDir()+File.separator+mapFileName)));
	    line = mapBReader.readLine();
	    Log.i("for db", "total line when read is  = " + line);
	    if(line != null){
			  PApplet.println(line);
			  String tempEl = new String();
			  Log.i("for db", "total line when read is  = " + line);
		    {
		    	StringTokenizer filecontents = new StringTokenizer(line, "||");
		    	String xCoor =  (String) filecontents.nextElement();
		    	String yCoor =  (String) filecontents.nextElement();

		    	StringTokenizer X = new StringTokenizer(xCoor, ",");
		    	System.out.println("scoor = " + xCoor);
		    	while (X.hasMoreElements()) {
		    		tempEl = (String) X.nextElement();
		    	System.out.println(tempEl);
		    	coorX.add(Float.parseFloat(tempEl));
		    	}


		    	StringTokenizer Y = new StringTokenizer(yCoor, ",");
		    	while (Y.hasMoreElements()) {
		    		tempEl = (String) Y.nextElement();
		    	System.out.println(tempEl);
		    	coorY.add(Float.parseFloat(tempEl));
		    	}
		    	
		    }
		  }
		  epApplet.getEnvisMap().setVisCoors(new Coordinates());
		  epApplet.getEnvisMap().setRealCoors(new Coordinates());
		  for(int i = 0; i < coorX.size(); i++){
			  epApplet.getEnvisMap().getVisCoors().getCoorX().add(coorX.get(i));
			  epApplet.getEnvisMap().getVisCoors().getCoorY().add(coorY.get(i));
			  epApplet.getEnvisMap().getRealCoors().getCoorX().add(coorX.get(i));
			  epApplet.getEnvisMap().getRealCoors().getCoorY().add(coorY.get(i));
		  }
	  }catch(IOException e){
	    line = null;
	    e.printStackTrace();
	  }
	  finally{
		  try {
			mapBReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	  //epApplet.getEnvisMap().printCoors();
	}
}
