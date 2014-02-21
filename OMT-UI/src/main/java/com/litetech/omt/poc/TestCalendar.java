package com.litetech.omt.poc;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;


public class TestCalendar {

	
	public static void main(String[] args) {
        JFrame frame = new JFrame("JXPicker Example");
        JPanel panel = new JPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(400, 400, 250, 100);
        
        /*JCalendar calendar = new JCalendar();
       
        panel.add(calendar);
      */
        
        JDateChooser chooser = new JDateChooser();
        chooser.getDateEditor().addPropertyChangeListener(
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    if ("date".equals(e.getPropertyName())) {
                        System.out.println(e.getPropertyName()
                            + ": " + (Date) e.getNewValue());
                    }
                }
            });
        chooser.setDateFormatString("dd-mm-yyyy");
        
        panel.add(chooser);
        frame.getContentPane().add(panel);

        frame.setVisible(true);
    }

}
