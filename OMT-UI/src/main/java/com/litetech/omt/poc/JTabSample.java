package com.litetech.omt.poc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class JTabSample {

	
	public JFrame constructBaseTemplate(){
		JFrame frame = new JFrame("Order Management Tool");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 800);
		return frame;
	}
		
	
	private Component buildComponent(){
		JPanel baseComp  = new JPanel(new GridLayout(1, 1));
		//baseComp.setOpaque(false);
		baseComp.setBackground(Color.GREEN);
		baseComp.setPreferredSize(new Dimension(1000, 500));
		
		baseComp.add(constructTab());
		
		return baseComp;
	}
	
	
	public JTabbedPane constructTab(){
		JTabbedPane tabbedPane = new JTabbedPane();
			
		tabbedPane.addTab("Tab1", new JLabel("This is tab 1"));
		tabbedPane.addTab("Tab2", new JLabel("This is tab 2"));
		
		
		return tabbedPane;
	}
	
	public void buildComp(){
		JFrame frame = constructBaseTemplate();
		frame.add(buildComponent(), BorderLayout.NORTH);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		JTabSample tabSample = new JTabSample();
		tabSample.buildComp();
	}
	
	
	 /** Returns an ImageIcon, or null if the path was invalid. */
	/*  
	protected static ImageIcon createImageIcon(String path) {
	    java.net.URL imgURL = TabbedPaneDemo.class.getResource(path);
	    if (imgURL != null) {
	      return new ImageIcon(imgURL);
	    } else {
	      System.err.println("Couldn't find file: " + path);
	      return null;
	    }
	  }
	  */
}
