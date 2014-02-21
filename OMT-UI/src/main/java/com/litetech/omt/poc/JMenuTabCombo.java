package com.litetech.omt.poc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class JMenuTabCombo {

	
	
	public JFrame constructBaseTemplate(){
		JFrame frame = new JFrame("Order Management Tool");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 800);
		//frame.setLocationRelativeTo(null);
		return frame;
	}
	
	
	
	private Component buildComponent(JFrame frame){
		JPanel comp = new JPanel();
		frame.add(buildHeader(), BorderLayout.NORTH);
		frame.add(buildMainMenu(), BorderLayout.WEST);
		frame.add(buildTabbedMenu(), BorderLayout.EAST);
		return comp;
	}
	
	
	private JPanel buildHeader(){
		JPanel headerPanel = new JPanel();
		//headerPanel.setBackground(Color.MAGENTA);
		headerPanel.setOpaque(false);
		JLabel headerLabel = new JLabel("Kannia Rubbers");
		headerPanel.add(headerLabel);
		
		return headerPanel;
	}
	
	private JPanel buildMainMenu(){
		JPanel mainMenuPanel  = new JPanel();
		//mainMenuPanel.setBackground(Color.yellow);
		mainMenuPanel.setOpaque(false);
		mainMenuPanel.setPreferredSize(new Dimension(75, 50));
		
		mainMenuPanel.add(new JMenuSample().constuctMenu());
		return mainMenuPanel;				
	}
	
	
	
	
	
	private JPanel buildTabbedMenu(){

		JPanel baseComp  = new JPanel(new BorderLayout());
		//baseComp.setOpaque(false);
		//baseComp.setBackground(Color.GREEN);
		baseComp.setOpaque(false);
		baseComp.setPreferredSize(new Dimension(900, 500));
		
		JTabbedPane tabbedPane = new JTabSample().constructTab();
		tabbedPane.setTabPlacement(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		
		baseComp.add(tabbedPane);
		return baseComp;
	}
	
	public void buildComp(){
		JFrame frame = constructBaseTemplate();
		frame.add(buildComponent(frame));
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new JMenuTabCombo().buildComp();
	} 
}
