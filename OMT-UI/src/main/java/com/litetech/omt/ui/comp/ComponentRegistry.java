package com.litetech.omt.ui.comp;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ComponentRegistry {

	private static ComponentRegistry registry = new ComponentRegistry();
	
	public static ComponentRegistry instance(){
		 return registry;
	}
	
	private ComponentRegistry(){
		 
	}
	 
	private JPanel displayPanel = null; 
	private JFrame mainFrame = null; 

	public JFrame getMainFrame() {
		return mainFrame;
	}


	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	
	
	public JPanel getDisplayPanel() {
		return displayPanel;
	}

	public void setDisplayPanel(JPanel displayPanel) {
		this.displayPanel = displayPanel;
	}

	public void repaintComponents(){
		this.mainFrame.repaint();
	}
	 
	
	
	 
	
	
}
