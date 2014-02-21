package com.litetech.omt.ui.model;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import com.litetech.omt.vo.CustomerVO;


public class SearchResultUserModel {
	
	public final static String[]  header = {"Customer Id", "Customer Name", "Tin No", "Contact Name", "City", "State", "Country", "Pincode", "Phone"};
	private DefaultTableModel defaultTableModel;
	private Object[][] rows;
	
	public SearchResultUserModel(List<CustomerVO> customerVOs){
		init(customerVOs);
		this.defaultTableModel = new DefaultTableModel(rows, header){
			 @Override
           public boolean isCellEditable ( int row, int column ){
				 return false;
           }
		};
	}
	
	
	
	
	public DefaultTableModel getDefaultTableModel() {
		return defaultTableModel;
	}
	
	public void setDefaultTableModel(DefaultTableModel defaultTableModel) {
		this.defaultTableModel = defaultTableModel;
	}
	
	public Object[][] getRows() {
		return rows;
	}

	public void setRows(Object[][] rows) {
		this.rows = rows;
	}
	
	public static String[] getHeader() {
		return header;
	}
	
	public Object getCell(int row, int col) {
		return rows[row][col];
	}
	
	private void init(List<CustomerVO> customerVOs){
		this.rows = new String[customerVOs.size()][9];
		
		int row = 0;
		for(CustomerVO customerVO : customerVOs){
			
			rows[row][0] = String.valueOf(customerVO.getId());
			rows[row][1] = customerVO.getName();
			rows[row][2] = customerVO.getTinNo();
			
			String contactName = customerVO.getPrimaryAddress().getFirstName();
			if(customerVO.getSecondaryAddress() != null ){
				String temp = customerVO.getSecondaryAddress().getFirstName();
				if(temp!= null && !temp.equals(contactName)){
					contactName += " | " + temp;
				}
			}
			rows[row][3] = contactName;
			
			String city = customerVO.getPrimaryAddress().getCity();
			if(customerVO.getSecondaryAddress() != null ){
				String temp = customerVO.getSecondaryAddress().getCity();
				if(temp!= null && !temp.equals(city)){
					city += " | " + temp;
				}
			}
			rows[row][4] = city;
			
			String state = customerVO.getPrimaryAddress().getState();
			if(customerVO.getSecondaryAddress() != null ){
				String temp = customerVO.getSecondaryAddress().getState();
				if(temp!= null && !temp.equals(state)){
					state += " | " + temp;
				}
			}
			rows[row][5] = state;
			
			
			String country = customerVO.getPrimaryAddress().getCountry();
			if(customerVO.getSecondaryAddress() != null ){
				String temp = customerVO.getSecondaryAddress().getCountry();
				if(temp!= null && !temp.equals(country)){
					country += " | " + temp;
				}
			}
			rows[row][6] = country;
			
			
			String pincode = customerVO.getPrimaryAddress().getPincode();
			if(customerVO.getSecondaryAddress() != null ){
				String temp = customerVO.getSecondaryAddress().getPincode();
				if(temp!= null && !temp.equals(pincode)){
					pincode += " | " + temp;
				}
			}
			rows[row][7] = pincode;
			
			
			String phone = customerVO.getPrimaryAddress().getPhone1(); 
			if(customerVO.getSecondaryAddress() != null ){
				String temp =  customerVO.getSecondaryAddress().getPhone1();
				if(temp!= null && !temp.equals(phone)){
					phone += " | " + temp;
				}
			}
			rows[row][8] = phone;
			
			row++;
		}
		
		
	}
}
