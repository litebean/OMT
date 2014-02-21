package com.litetech.omt.poc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class PrgoressBarPOC extends JPanel{

	JProgressBar pbar;

	  static int min = 0;

	  static int max = 100;

	  public PrgoressBarPOC() {
	    pbar = new JProgressBar();
	    pbar.setMinimum(min);
	    pbar.setMaximum(max);
	    add(pbar);
	    
	    JFrame frame = new JFrame("Progress Bar Example");
	    frame.setPreferredSize(new Dimension(300, 50));
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    frame.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

	    
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setContentPane(this);
	    frame.pack();
	    frame.setResizable(false);
	    frame.setVisible(true);
	    
	    for (int i = min; i <= max; i++) {
	      final int percent = i;
	      try {
	        SwingUtilities.invokeLater(new Runnable() {
	          public void run() {
	            updateBar(percent);
	          }
	        });
	        Thread.sleep(100);
	      } catch (InterruptedException e) {
	      }
	    }
	    
	    frame.dispose();
	  }

	  public void updateBar(int newValue) {
	    pbar.setValue(newValue);
	  }

	  public static void main(String args[]) {
	    new PrgoressBarPOC();
	  }
	}



