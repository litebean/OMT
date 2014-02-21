package com.litetech.omt.ui.comp;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class ProgressBar extends JPanel{
	private int min = 0;
	private int max = 100;
	JProgressBar pbar;
	private boolean hideProgressBar; 
	private JFrame frame;
	
	public ProgressBar(int max){
		this.max = max;
		pbar = new JProgressBar(min, max);
		add(pbar);
		
		this.frame = new JFrame("Initializing OMT...");
	    frame.setPreferredSize(new Dimension(300, 50));
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    frame.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setContentPane(this);
	    frame.pack();
	    frame.setResizable(false);
	    frame.setVisible(true);    
	    
	    frame.addWindowListener(new java.awt.event.WindowAdapter() {
	        @Override
	        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	        	System.exit(1);
	        }
	    });
	}
	
	public void showProgress(){
		int progressRatio = min;
	    while(true) {
	    	if(hideProgressBar){
	    		frame.dispose();
	    	}
	    	 final int percent = progressRatio; 
	    	 try {
		        SwingUtilities.invokeLater(new Runnable() {
		          public void run() {
		            updateBar(percent);
		          }
		        });
		        Thread.sleep(100);
		      } catch (InterruptedException e){
		      }
		      
	    	  
		      progressRatio++;
		      if(progressRatio == max){
		    	  progressRatio = min;
		      }
	    }
	}
	
	public void updateBar(int newValue) {
	  pbar.setValue(newValue);
	}
	
	
	public static void main(String[] args) {
		new ProgressBar(100);
	}
	
	public void setHideProgressBar(boolean hide){
		this.hideProgressBar = hide;
	}
	
}
