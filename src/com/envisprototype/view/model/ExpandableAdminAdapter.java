package com.envisprototype.view.model;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.envisprototype.R;
import com.envisprototype.controller.AdminExpandableItemPickedListener;

public class ExpandableAdminAdapter extends ExpandableListAdapter{

	public ExpandableAdminAdapter(Context context, List<String> listDataHeader,
			HashMap<String, List<String>> listDataChild) {
		super(context, listDataHeader, listDataChild);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getChildView(int groupPos, int childPos, boolean isLastChild,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// Getting one sensor with a layout
		final String childText = (String) getChild(groupPos, childPos);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.childrow, null);
		}

		//TextView sensorLabel = (TextView) convertView.findViewById(R.id.text1);
		Button sensorIB = (Button) convertView.findViewById(R.id.sensorImageBtn);
		sensorIB.setText(childText);
		System.out.println(childText + " " + convertView.getContext().getPackageName());
		// CHANGE TO IMAGE BUTTON
//		sensorIB.setImageResource(convertView.getResources().
//				getIdentifier("com.example.envisprototypetake2:drawable/" + childText,null, null));
		
		if (childText != null && sensorIB != null){
			//sensorLabel.setText(childText);
			//sensorLabel.setOnClickListener(new AdminExpandableItemPickedListener(childText));
			sensorIB.setOnClickListener(new AdminExpandableItemPickedListener(childText));
			// !!!! Add changing icons here
		}
		return convertView;
	}
	
	public View getGroupView(int groupPos, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// Getting one type of sensors with a layout
		String headerTitle = (String) getGroup(groupPos);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.grouprow, null);
		}

		TextView textListHeader = (TextView) convertView.findViewById(R.id.text1);
		ImageView image=(ImageView)convertView.findViewById(R.id.image);
		if (headerTitle != null){
			textListHeader.setText(headerTitle);
			if(headerTitle.equals("Sets"))
				image.setImageResource(convertView.getResources().
			getIdentifier("com.envisprototype:drawable/set",null,null));
			else
				image.setImageResource(convertView.getResources().
						getIdentifier("com.envisprototype:drawable/drawmapnew",null,null));
			
		}
		textListHeader.setTypeface(null, Typeface.BOLD);
		textListHeader.setText(headerTitle);

		return convertView;
	}

}
