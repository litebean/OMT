package com.litetech.omt.vo;

public class IncomeExpenseVO {
	
	private double income;
	private double expense;
	
	
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public double getExpense() {
		return expense;
	}
	public void setExpense(double expense) {
		this.expense = expense;
	}
	public double getProfit() {
		return income - expense;
	}
	
	
	
	
	
	
}
