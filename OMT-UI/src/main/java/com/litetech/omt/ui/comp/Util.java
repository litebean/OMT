package com.litetech.omt.ui.comp;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.jgoodies.looks.common.RGBGrayFilter;
import com.jgoodies.looks.plastic.theme.SkyBluer;
import com.litetech.omt.ui.comp.MainMenu.MenuListener;

public class Util {

	private static final Icon CLOSE_TAB_ICON = new ImageIcon(MenuListener.class.getResource("/img/closeTabButton.png"));
	
	
	public static void buildCloseTab(final String command, final String title, JTabbedPane tabbedPane,
			JComponent c, final Icon icon, ActionListener onRemoveListner){
		tabbedPane.addTab(null, c);
		int pos = tabbedPane.indexOfComponent(c);
		
		 // Create a FlowLayout that will space things 5px apart
	    FlowLayout flowLyt = new FlowLayout(FlowLayout.CENTER, 5, 0);
	    
	    // Make a small JPanel with the layout and make it non-opaque
	    JPanel pnlTab = new JPanel(flowLyt);
	   // pnlTab.setOpaque(false);
	    pnlTab.setName(command);
	   // pnlTab.setBackground(Color.WHITE);
	    //pnlTab.setOpaque(true);
	    //pnlTab.setBackground(SkyBluer.BRIGHTEN_START);
	    
	    //    pnlTab.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	    //Add a JLabel with title and the left-side tab icon
	    JLabel lblTitle = new JLabel(title);
	    if(icon != null){
	    	lblTitle.setIcon(icon);
	    }
	    
	    //Create a JButton for the close tab button
	    JButton btnClose = new JButton();
	    btnClose.setOpaque(false);

	    // Configure icon and rollover icon for button
	    btnClose.setRolloverIcon(CLOSE_TAB_ICON);
	    btnClose.setRolloverEnabled(true);
	    btnClose.setIcon(RGBGrayFilter.getDisabledIcon(btnClose, CLOSE_TAB_ICON));

	    // Set border null so the button doesn't make the tab too big
	    btnClose.setBorder(null);

	    // Make sure the button can't get focus, otherwise it looks funny
	    btnClose.setFocusable(false);

	    // Put the panel together
	    pnlTab.add(lblTitle);
	    pnlTab.add(btnClose);
	    
	    // Add a thin border to keep the image below the top edge of the tab
	    // when the tab is selected
	    pnlTab.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
	    
	    // Now assign the component for the tab
	    tabbedPane.setTabComponentAt(pos, pnlTab);
	    btnClose.addActionListener(onRemoveListner);
	    // Optionally bring the new tab to the front
	    	  
	    //System.out.println("The component to be selected is "+c);
	    tabbedPane.setSelectedComponent(c);
	    if(c instanceof PanelTabComp){
	    	((PanelTabComp)c).setMyPanelTab(pnlTab);
	    }
	}
}
