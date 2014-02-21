package com.litetech.omt.ui.comp;

import java.util.Date;

import com.toedter.calendar.JDateChooser;

public class OMTCalendar extends JDateChooser{
	
	
	public OMTCalendar(Date date){
		super(date);
		super.setDateFormatString("dd-MM-yyyy");
		
	}
	
	
	
	
	
	
}
