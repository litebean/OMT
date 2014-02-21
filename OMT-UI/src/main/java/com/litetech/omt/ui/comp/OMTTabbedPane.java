package com.litetech.omt.ui.comp;


import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.jgoodies.looks.plastic.theme.ExperienceRoyale;
import com.jgoodies.looks.plastic.theme.SkyBluer;

public class OMTTabbedPane extends JTabbedPane{

	public OMTTabbedPane(){
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				//System.out.println("After released");
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				unselectPrevSelectedTab();
				setCurrentTab(((PanelTabComp)getSelectedComponent()).getMyPanelTab());				
			}
		});
	}
	
	private JPanel pnlTab;
	
	
	
	private void setCurrentTab(Component arg0){
		if(arg0 != null){
			this.pnlTab = (JPanel)arg0;
			this.pnlTab.setBackground(Color.GREEN);
			//pnlTab.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
		}
	}
	
	
	
	public void resetSelectedComponent(Component arg0) {
		//if(!arg0.equals(getSelectedComponent())){
			unselectPrevSelectedTab();
			super.setSelectedComponent(arg0);
			setCurrentTab(((PanelTabComp)arg0).getMyPanelTab());
		//}
	}
	
		
	private void unselectPrevSelectedTab(){
		if(pnlTab != null){
			pnlTab.setBackground(Color.WHITE);
			//this.pnlTab.setOpaque(false);
						
		}
	}	
	
	@Override
	public void setTabComponentAt(int arg0, Component arg1) {
		unselectPrevSelectedTab();
		super.setTabComponentAt(arg0, arg1);
		setCurrentTab(arg1);	
	}
	
	
	
	@Override
	public void remove(Component arg0) {
		Component[] components = getComponents();
		Component currentComp = getSelectedComponent(); 
		super.remove(arg0);
		if(currentComp.equals(arg0)){
			PanelTabComp nextAvailableComp = findNextComp(components, (PanelTabComp)arg0);
			if(nextAvailableComp != null){
				setCurrentTab(nextAvailableComp.getMyPanelTab());
			}
		}else{
			setCurrentTab(((PanelTabComp)currentComp).getMyPanelTab());
		}
	}
	
	
	private PanelTabComp[] getPanelTabs(Component[] components){
		List<PanelTabComp> panelTabCompList = new ArrayList<PanelTabComp>();
	
		for(Component component : components){
			if(component instanceof PanelTabComp){
				panelTabCompList.add((PanelTabComp)component);
			}			
		}
		
		return panelTabCompList.toArray(new PanelTabComp[panelTabCompList.size()]);
	}
	
	private PanelTabComp findNextComp(Component[] components, PanelTabComp currentComp){
		PanelTabComp nextComponent = null;
		int matchedIndex = -1;
		if(components.length > 2){
			PanelTabComp[] panelTabs = getPanelTabs(components);
			for(int index = 0; index < panelTabs.length; index++){
				PanelTabComp panelTab = panelTabs[index];
				if(panelTab.equals(currentComp)){
					matchedIndex = index;
					break;
				}
			}
			
			if(matchedIndex != -1){
				if(panelTabs.length > (matchedIndex+1)){
					nextComponent = panelTabs[matchedIndex+1]; 
				}else if((matchedIndex-1) < panelTabs.length){
					nextComponent = panelTabs[matchedIndex-1];
				}
			}
		}
		return nextComponent;
	}
	
}
