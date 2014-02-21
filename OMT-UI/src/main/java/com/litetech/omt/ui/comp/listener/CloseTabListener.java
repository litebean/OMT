package com.litetech.omt.ui.comp.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class CloseTabListener  implements ActionListener{
	private JPanel parentContainer;
	private Component component;
	
	public CloseTabListener(JPanel parentContainer, Component component){
		this.parentContainer = parentContainer;
		this.component = component;
	}
	
	public void actionPerformed(ActionEvent event) {
		JTabbedPane tabbedPane = (JTabbedPane)parentContainer.getComponent(0);
		tabbedPane.remove(component);
	}
}
