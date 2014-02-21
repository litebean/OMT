package com.litetech.omt.ui.comp;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;


import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.SkyBluer;
import com.litetech.omt.client.LicenseClient;
import com.litetech.omt.license.AbstractLicense;
import com.litetech.omt.license.MyLicense;
import com.litetech.omt.ui.comp.MainMenu.MenuListener;

public class MainFrame extends JFrame {

	private static final Icon HEADER_ICON = new ImageIcon(MenuListener.class.getResource("/img/track-your-order-images-v1.jpg"));
	private static final Icon FOOTER_ICON = new ImageIcon(MenuListener.class.getResource("/img/footer-2.jpg"));
	
	
	public MainFrame(){
		super();
		if(hasValidLicense()){
			AppContext.getInstance().init(getToolkit());
			ComponentRegistry.instance().setMainFrame(this);
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(AppContext.getInstance().getScreenDimension());
			setTitle("Order Management Tool");
			buildPanels();
			setVisible(true);
		}
	}
	
	
	private boolean hasValidLicense(){
		boolean valid = false;
		try {
			MyLicense license = LicenseClient.validateLicense();
			long val = license.getExpiration().getTime() - new Date().getTime();
            val /= (1000 * 60 * 60 * 24);
            
            if(val < 15){
            	JOptionPane.showMessageDialog(null, "Your license is about to expire in "+val+" days, please renew");
            }
			valid = true;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return valid;
	}
	private void buildPanels(){
		add(buildHeader(), BorderLayout.NORTH);
		
		JPanel displayPanel = buildMenuDisplayPanel();
		add(displayPanel, BorderLayout.EAST);
		ComponentRegistry.instance().setDisplayPanel(displayPanel);
		
		add(buildMainMenu(displayPanel), BorderLayout.WEST);
		add(buildFooter(), BorderLayout.SOUTH);
	}
	
	
	private JPanel buildHeader(){
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(Color.WHITE);
		
//		headerPanel.setOpaque(false);
	/*	JLabel clientLabel = new JLabel("");
		clientLabel.setIcon(CLIENT_ICON);
		headerPanel.add(clientLabel);
		clientLabel.setPreferredSize(new Dimension(200, 75));*/
		
		//headerPanel.setOpaque(false);
		JLabel headerLabel = new JLabel("");
		headerLabel.setIcon(HEADER_ICON);
		headerPanel.add(headerLabel);
		//headerPanel.setPreferredSize(new Dimension(1, 75));
		
		return headerPanel;
	}
	
	private JPanel buildFooter(){
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(Color.WHITE);
		headerPanel.setPreferredSize(new Dimension(800, 75));
//		headerPanel.setOpaque(false);
	/*	JLabel clientLabel = new JLabel("");
		clientLabel.setIcon(CLIENT_ICON);
		headerPanel.add(clientLabel);
		clientLabel.setPreferredSize(new Dimension(200, 75));*/
		
		//headerPanel.setOpaque(false);
		JLabel headerLabel = new JLabel("");
		headerLabel.setIcon(FOOTER_ICON);
		headerPanel.add(headerLabel);
		//headerLabel.setPreferredSize(new Dimension(800, 75));
		
		return headerPanel;
	}
	
	
	private JPanel buildMainMenu(JPanel displayPanel){
		JPanel mainMenuPanel  = new JPanel();
		//mainMenuPanel.setBackground(Color.GRAY);
		mainMenuPanel.setOpaque(false);
		mainMenuPanel.setPreferredSize(AppContext.getInstance().getMenuDimension());
		
		mainMenuPanel.add(new MainMenu(displayPanel));
		return mainMenuPanel;				
	}
	
	
	private JPanel buildMenuDisplayPanel(){
		JPanel menuDisplayPanel  = new JPanel(new BorderLayout());
		//menuDisplayPanel.setOpaque(false);
		menuDisplayPanel.setBackground(Color.WHITE);
		//menuDisplayPanel.setOpaque(false);
		//860
		menuDisplayPanel.setPreferredSize(AppContext.getInstance().getMainAreaDimension());
		
		JTabbedPane tabbedPane = new OMTTabbedPane();
		tabbedPane.setTabPlacement(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		tabbedPane.setOpaque(true);
		tabbedPane.setBackground(Color.WHITE);
		
		menuDisplayPanel.add(tabbedPane);
		/*JTabbedPane tabbedPane = new JTabSample().constructTab();
		tabbedPane.setTabPlacement(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		
		menuDisplayPanel.add(tabbedPane);*/
		return menuDisplayPanel;
	}
	
	
	public static void main(String[] args) {
		
		try {
			  PlasticLookAndFeel laf = new Plastic3DLookAndFeel();
		      //PlasticLookAndFeel.setCurrentTheme(new ExperienceRoyale());
			  PlasticLookAndFeel.setCurrentTheme(new SkyBluer());
			  
			  
		      UIManager.setLookAndFeel(laf);
		 } catch (javax.swing.UnsupportedLookAndFeelException ex) {
		      ex.printStackTrace();
		 }
		    
		
		MainFrame frame = new MainFrame();

	}
	
	
}
