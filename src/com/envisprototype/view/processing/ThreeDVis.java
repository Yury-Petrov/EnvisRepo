package com.envisprototype.view.processing;

import java.util.ArrayList;
import java.util.Collections;

import com.envisprototype.controller.processing.eventListeners.FrontViewButtonListener;
import com.envisprototype.controller.processing.eventListeners.LeftSideViewButtonListener;
import com.envisprototype.controller.processing.eventListeners.RotateButtonListener;
import com.envisprototype.controller.processing.eventListeners.RotateScopeListener;
import com.envisprototype.controller.processing.eventListeners.TopViewButtnoListener;

public class ThreeDVis extends EnvisPApplet{
	
	EnvisButton frontViewButton, leftSideViewButton, topViewButton, rotateButton; 
	final boolean DRAW_WITH_SENSORS = true;


public void setup(){
	super.setup();
	// to visualise the map in 3D
	// by this time the map has already been copied from the model
    envisMap.setIf3D(true);
    envisMap.translateToMiddle();
    rotateButton = new EnvisButton(this, "Rotate");
    rotateButton.setPlace(DEF_BTN_X, height/30);
    rotateButton.addEventListener(new RotateButtonListener());
    frontViewButton = new EnvisButton(this, "Front View");
    frontViewButton.setPlace(DEF_BTN_X, 3*height/30);
    frontViewButton.addEventListener(new FrontViewButtonListener());
    leftSideViewButton = new EnvisButton(this, "Left Side");
    leftSideViewButton.setPlace(DEF_BTN_X, 5*height/30);
    leftSideViewButton.addEventListener(new LeftSideViewButtonListener());
    topViewButton = new EnvisButton(this, "Top View");
    topViewButton.setPlace(DEF_BTN_X, 7*height/30);
    topViewButton.addEventListener(new TopViewButtnoListener());
    RotateScopeListener.setIfTop(true);
    for(int i = 0; i < envisSensors.size(); i++){
    	envisSensors.get(i).translateSensorsForMap(envisMap);
    }
}
public void draw(){
	super.draw();
	threeDDrawPreset(DRAW_WITH_SENSORS); // true - drawing with sensors
	//rotateButton.drawMe();
	frontViewButton.drawMe();
	leftSideViewButton.drawMe();
	topViewButton.drawMe();
}

@Override
public void mouseDragged(){
	super.mouseDragged();
	rotateScope.fireEvent();
}

public void mouseReleased(){
	//rotateButton.fireEvent();
	frontViewButton.fireEvent();
	leftSideViewButton.fireEvent();
	topViewButton.fireEvent();
}

public float medianValue(ArrayList<Float> list){
  Collections.sort(list);
  return list.get(list.size()/2);
}
}


