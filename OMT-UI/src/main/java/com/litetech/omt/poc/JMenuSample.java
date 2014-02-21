package com.litetech.omt.poc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class JMenuSample {

	public JFrame constructBaseTemplate(){
		JFrame frame = new JFrame("Order Management Tool");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 800);
		return frame;
	}
		
	
	
	
	private JPanel buildComponent(){
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.yellow);
		leftPanel.setPreferredSize(new Dimension(75, 50));
		
		leftPanel.add(constuctMenu());
		
		return leftPanel;
	}
	
	
	
	public JMenuBar constuctMenu(){
		
		JMenu orderMenu = new JMenu("Order");
		orderMenu.setBackground(Color.BLUE);
		orderMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		orderMenu.setSize(1000, 1000);
		
		
		
		
		JMenu invoiceMenu = new JMenu("Invoice");
		invoiceMenu.setBackground(Color.BLUE);
		String[] orderOptions = new String[]{"Create New Order", "Search Order"};
		
		for (int i = 0; i < orderOptions.length; i++) {
			JMenuItem menuItem = new JMenuItem(orderOptions[i]);
			orderMenu.add(menuItem);
		}
		
		
		JMenuBar menuBar = new JMenuBar();
		LayoutManager grid = new GridLayout(0,1,0,10);
		menuBar.setLayout(grid);
		menuBar.setOpaque(false);
		
		menuBar.add(orderMenu);
		menuBar.add(invoiceMenu);
		
		return menuBar;	
		
	}
	
	
	public void buildComp(){
		JFrame frame = constructBaseTemplate();
		frame.add(buildComponent(), BorderLayout.WEST);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		JMenuSample menuSample = new JMenuSample();
		menuSample.buildComp();
	}
	
	
	
	
	
	
	
	
}
