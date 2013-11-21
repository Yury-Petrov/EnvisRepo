package com.envisprototype.view.processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import processing.core.PApplet;
import processing.core.PConstants;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

import com.envisprototype.R;
import com.envisprototype.controller.processing.eventListeners.RotateScopeListener;
import com.envisprototype.controller.processing.eventListeners.ZoomListener;
import com.envisprototype.model.maps.MapListModel;
import com.envisprototype.model.processing.Coordinates;
//import com.envisprototype.controller.ModelSaver;

public abstract class EnvisPApplet extends PApplet{

	static final int BACKGROUND_COLOR = 0xff000000; 
	public static final int STROKE_COLOR = 0xffffffff;
	static final int STROKE_WEIGHT = 1;
	public static int DEF_BTN_X;
	int MAX_WIDTH, MIN_WIDTH = 0;
	EnvisButton zoom, rotateScope;
	private Axis axis;
	public PositionDisplay currentClick;
	HashMap<String,SensorSet> envisSensors;
	Map envisMap;
	String flag;
	static ArrayList<BarGraphSet> barGraphSetList;
	static ArrayList<SphereGraphSet> sphereGraphList;
	public static String curDate;

	ArrayList<String> setIdFromAndroid;
	Bundle extras;
	Iterator<String> setIterator;
	private boolean ifAttractor = false;
	private boolean ifBars = true; // true - bars, false - spheres

	public void setup(){
		// default setup which will be reused
		// to specify size of ui elements for different screens
		ellipseMode(PConstants.CORNERS);
		fill(217,200,33);
		MAX_WIDTH =  width-width/7;
		DEF_BTN_X = width-width/8;
		background(BACKGROUND_COLOR);
		stroke(STROKE_COLOR);
		// smooth is not available on most devices, but sometimes makes
		// the ui prettier
		smooth();
		envisSensors = new HashMap<String,SensorSet>();
		// // current click is showing coordinates of the last finger tap
		currentClick = new PositionDisplay(this, "");
		currentClick.setPlace(width/15, height/30);
		rotateScope = new EnvisButton(this, "");
		rotateScope.setPlace(width/25, height-height/10);
		rotateScope.setSize(width-width/5, 3*height/55);
		rotateScope.addEventListener(new RotateScopeListener());
		envisMap = new Map(this);
		barGraphSetList = new ArrayList<BarGraphSet>();
		sphereGraphList = new ArrayList<SphereGraphSet>();
		// getting a map id from model.
		extras = getIntent().getExtras();
		if(extras != null){
			if(extras.containsKey(getString(R.string.map_id_extra))){
				String mapId = extras.getString(getString(R.string.map_id_extra));
				envisMap.setMapId(mapId);
				if(MapListModel.getSingletonInstance().findMapById(mapId)!= null){
					Coordinates coors = MapListModel.getSingletonInstance().findMapById(mapId).getRealCoordinates();
					float mapZ = MapListModel.getSingletonInstance().findMapById(mapId).getzCoordinate();
					Log.i("coors",coors.toString());
					envisMap.setAllCoors(coors); 
					envisMap.setCOOR_Z((int)mapZ);
				}
			}
			if(extras.containsKey(getString(R.string.flags))){
				flag = extras.getString(getString(R.string.flags));
				if(flag.equals(getString(R.string.plot_flag_extra))){
					// plotting several sets at the same time
					if(extras.containsKey(getString(R.string.sets_id_extra))){
						setIdFromAndroid = extras.getStringArrayList(getString(R.string.sets_id_extra));
						for(String setId: setIdFromAndroid){
							// SetInterface setFromModel =  SetListModel.getSingletonInstance().findSetById(setId);
							SensorSet setToShow = new SensorSet(this, setId);
							setToShow.setIfSensor(false);
							envisSensors.put(setId, setToShow);
						}
					}
					if(extras.containsKey(getString(R.string.sensor_id_extra))){
						setIdFromAndroid = extras.getStringArrayList(getString(R.string.sensor_id_extra));
						for(String sensorId: setIdFromAndroid){
							//SensorInterface setFromModel =  SensorListModel.getSingletonInstance().findSensorById(setId);
							EnvisSensor sensorToShow = new EnvisSensor(this, sensorId);
							sensorToShow.setIfSensor(true);
							envisSensors.put(sensorId, sensorToShow);
						}
					}
				}
			}
			if(extras.containsKey(getString(R.string.sensors_to_vis_extra))){
				setIdFromAndroid = extras.getStringArrayList(getString(R.string.sensors_to_vis_extra));
				for(String setId: setIdFromAndroid){
					//		  SetInterface setFromModel =  SetListModel.getSingletonInstance().findSetById(setId);
					SensorSet setToShow = new SensorSet(this, setId);
					envisSensors.put(setId, setToShow);

				}
			}
		}

		// HERE SENSORS MUST BE ADDED SIMILARLY TO MAP (EXTRAS)
		//envisSensors = filler.prepareSensorsCoordinates("sensors.txt");
		// zoom works only in 3D
		zoom = new EnvisButton(this, "");
		zoom.addEventListener(new ZoomListener(envisMap));  
		//drawing a zoom bar
		zoom.setPlace(width/100, width/100);
		zoom.setSize(width/50, height-height/20);
		axis = new Axis(this);
	}

	public void draw(){
		clear();
	}
	@Override
	public void mouseDragged(){
		if(envisMap.isIfCentered()){
			zoom.fireEvent();
		}
	}


	public void threeDDrawPreset(boolean ifWithSensors){
		/*
		 * Things to draw:
		 * - rotateScope border ���������
		 * - zoomBar
		 * - 3D map ���������
		 * - sets ���������
		 * - axis ���������
		 * - a bunch of buttons to choose vis type (bars or spheres)
		 * - bars or spheres
		 */
		stroke(STROKE_COLOR);
		currentClick.drawMe();
		pushMatrix();
		rotateScope.drawRect();
		translate(width/2,height/2);
		rotateScope.fireEvent();
		envisMap.drawMe();
		//scale(envisMap.getZoomValue());
		//	println("coor in threedvissss = " + envisMap.getRealCoors().getCoorX().toString());
		if(ifWithSensors){
			setIterator = envisSensors.keySet().iterator();
			while(setIterator.hasNext()){
				SensorSet temp = envisSensors.get(setIterator.next());
				temp.drawMe();
			}		
			if(!ifAttractor){
				if(ifBars){
					for(BarGraphSet barToShow: barGraphSetList){
						barToShow.drawMe();
						//barToShow.fireDragEvent();

					}
				}
				else{
					for (SphereGraphSet sphere: sphereGraphList) {
						sphere.drawMe();
					}
				}
			}
			else{
				
			}

		}
		popMatrix();
		axis.drawMe();
		pushMatrix();
		line(width/2,0,width/2,height);
		line(0,height/2,MAX_WIDTH,height/2);
		line(width/2,0,width/2,height);
		//rectMode(PApplet.CORNERS);
		fill(54,255);
		rect(MAX_WIDTH, 0, width-MAX_WIDTH-1, height-1);
		popMatrix();
		if(ifWithSensors){
			zoom.drawRect();
		}
	}

	public Axis getAxis() {
		return axis;
	}
	public void setAxis(Axis axis) {
		this.axis = axis;
	}
	public EnvisButton getZoom() {
		return zoom;
	}
	public void setZoom(EnvisButton zoom) {
		this.zoom = zoom;
	}
	public HashMap<String,SensorSet> getEnvisSensors() {
		return envisSensors;
	}
	public void setEnvisSensors(HashMap<String,SensorSet> envisSensors) {
		this.envisSensors = envisSensors;
	}
	public int getMAX_WIDTH() {
		return MAX_WIDTH;
	}
	public Map getEnvisMap() {
		return envisMap;
	}
	public void setEnvisMap(Map envisMap) {
		this.envisMap = envisMap;
	}

	public ArrayList<String> getSetIdFromAndroid(){
		return setIdFromAndroid;
	}

	public static  ArrayList<BarGraphSet> getBarGraphSetList() {
		return barGraphSetList;
	}

	public static  void setBarGraphSetList(ArrayList<BarGraphSet> barGraphSetList) {
		EnvisPApplet.barGraphSetList = barGraphSetList;
	}
	
	public boolean isIfBars() {
		return ifBars;
	}

	public void setIfBars(boolean ifBars) {
		this.ifBars = ifBars;
	}



	public EnvisButton getRotateScope() {
		return rotateScope;
	}

	public void setRotateScope(EnvisButton rotateScope) {
		this.rotateScope = rotateScope;
	}

	public static ArrayList<SphereGraphSet> getSphereGraphList() {
		return sphereGraphList;
	}

	public static  void setSphereGraphList(ArrayList<SphereGraphSet> sphereGraphList) {
		EnvisPApplet.sphereGraphList = sphereGraphList;
	}

	public int sketchWidth() {
		Display display = getWindowManager().getDefaultDisplay();
		return display.getWidth(); }
	public int sketchHeight() {
		Display display = getWindowManager().getDefaultDisplay();
		return display.getHeight(); }
	public String sketchRenderer() { return P3D; }
}
