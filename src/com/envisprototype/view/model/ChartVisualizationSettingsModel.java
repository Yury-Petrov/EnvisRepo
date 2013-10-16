package com.envisprototype.view.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChartVisualizationSettingsModel {

	List<String> SensorIDs;
	List<String> SetIDs;
	Calendar from;
	Calendar to;
	boolean realtimeornot;
	
	
	
	
	
	public Calendar getFrom() {
		return from;
	}

	public void setFrom(Calendar from) {
		this.from = from;
	}

	public Calendar getTo() {
		return to;
	}

	public void setTo(Calendar to) {
		this.to = to;
	}

	public boolean isRealtimeornot() {
		return realtimeornot;
	}

	public void setRealtimeornot(boolean realtimeornot) {
		this.realtimeornot = realtimeornot;
	}

	public List<String> getSensorIDs() {
		return SensorIDs;
	}

	public void setSensorIDs(List<String> sensorIDs) {
		SensorIDs = sensorIDs;
	}

	public List<String> getSetIDs() {
		return SetIDs;
	}

	public void setSetIDs(List<String> setIDs) {
		SetIDs = setIDs;
	}

	
	public void addSensorID(String id)
	{
		this.SensorIDs.add(id);
	}
	public void removeSensorID(String id)
	{
		this.SensorIDs.remove(id);
	}
	
	public void addSetID(String id)
	{
		this.SetIDs.add(id);
	}
	public void removeSetID(String id)
	{
		this.SetIDs.remove(id);
	}
	
	public boolean isSensorPresent(String id)
	{
		for(int i=0;i<SensorIDs.size();i++){
			if(SensorIDs.get(i).equals(id))
				return true;
		}
		return false;
	}
	
	public boolean isSetPresent(String id)
	{
		for(int i=0;i<SetIDs.size();i++){
			if(SetIDs.get(i).equals(id))
				return true;
		}
		return false;
	}
	
	public ChartVisualizationSettingsModel() {
		super();
		SensorIDs = new ArrayList<String>();
		SetIDs = new ArrayList<String>();
	}

	private static ChartVisualizationSettingsModel singletonInstance;

	public static ChartVisualizationSettingsModel getSingletonInstance() {
		if(singletonInstance==null)
			singletonInstance=new ChartVisualizationSettingsModel();

		return singletonInstance;
	}
	
	
	
}
