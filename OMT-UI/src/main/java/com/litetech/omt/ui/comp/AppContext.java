package com.litetech.omt.ui.comp;

import java.awt.Dimension;
import java.awt.Toolkit;

public class AppContext {

	private  static AppContext appContext = new AppContext();
	
	private Dimension screenDimension;
	private Dimension menuDimension;
	private Dimension mainAreaDimension;
	private Dimension baseFormPanelDimension;
	private int workAreaWidth;
	private int workAreaHeight;
	
	public static AppContext getInstance(){
		return appContext;
	}
	
	
	
	public void init(Toolkit toolKit){
		this.screenDimension = toolKit.getScreenSize();
		this.menuDimension = new Dimension(150, 50);
		int remainingArea = (int)(screenDimension.getWidth() - menuDimension.getWidth()) - 10;
		this.mainAreaDimension = new Dimension(remainingArea, 50);
		int baseFormWidth = (int)this.mainAreaDimension.getWidth() - 20;
		int baseFormHeight = (int)screenDimension.getHeight()-100;
		System.out.println("height "+baseFormHeight);
		this.baseFormPanelDimension = new Dimension(baseFormWidth, baseFormHeight);
		this.workAreaWidth = (int)baseFormPanelDimension.getWidth() - 20;
		this.workAreaHeight = (int)baseFormPanelDimension.getHeight() - 20;
	}
	
	public Dimension getScreenDimension() {
		return screenDimension;
	}
	public void setScreenDimension(Dimension screenDimension) {
		this.screenDimension = screenDimension;
	}


	public Dimension getMenuDimension() {
		return menuDimension;
	}


	public void setMenuDimension(Dimension menuDimension) {
		this.menuDimension = menuDimension;
	}


	public Dimension getMainAreaDimension() {
		return mainAreaDimension;
	}


	public void setMainAreaDimension(Dimension mainAreaDimension) {
		this.mainAreaDimension = mainAreaDimension;
	}


	public int getWorkAreaWidth() {
		return workAreaWidth;
	}


	public void setWorkAreaWidth(int workAreaWidth) {
		this.workAreaWidth = workAreaWidth;
	}


	public Dimension getBaseFormPanelDimension() {
		return baseFormPanelDimension;
	}


	public void setBaseFormPanelDimension(Dimension baseFormPanelDimension) {
		this.baseFormPanelDimension = baseFormPanelDimension;
	}



	public int getWorkAreaHeight() {
		return workAreaHeight;
	}



	public void setWorkAreaHeight(int workAreaHeight) {
		this.workAreaHeight = workAreaHeight;
	}
	
	
	
}
