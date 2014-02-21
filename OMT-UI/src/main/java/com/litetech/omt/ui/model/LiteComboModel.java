package com.litetech.omt.ui.model;

public class LiteComboModel {
	
	private long id;
	private String name;
	private Object relativeObj;
	
	public LiteComboModel(long id){
		this.id = id;
	}
	public LiteComboModel(long id, String name){
		this.id = id;
		this.name = name;
	}
	
	public LiteComboModel(long id, String name, Object relativeObj){
		this.id = id;
		this.name = name;
		this.relativeObj = relativeObj;
	}
	
	
	
	
	public Object getRelativeObj() {
		return relativeObj;
	}
	public void setRelativeObj(Object relativeObj) {
		this.relativeObj = relativeObj;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0 != null){
			LiteComboModel object2 = (LiteComboModel)arg0;
			return object2.getId() == id;
		}
		return false;
		
	}
	
	
	@Override
	public String toString(){
		return this.name;
	}
	
	
}
