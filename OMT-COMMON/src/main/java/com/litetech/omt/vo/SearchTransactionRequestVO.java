package com.litetech.omt.vo;

import java.util.Date;

import com.litetech.omt.constant.PaymentStatusEnum;
import com.litetech.omt.constant.TransactionTypeEnum;

public class SearchTransactionRequestVO {
	private long transactionId;
	private long customerId;
	private TransactionTypeEnum transactionTypeEnum;
	private long bankId;
	private long paymentModeId;
	private PaymentStatusEnum statusEnum;
	private String descLike;
	private Double transAmoutGrThan;
	private Date transDateGrThan;
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public TransactionTypeEnum getTransactionTypeEnum() {
		return transactionTypeEnum;
	}
	public void setTransactionTypeEnum(TransactionTypeEnum transactionTypeEnum) {
		this.transactionTypeEnum = transactionTypeEnum;
	}
	public long getBankId() {
		return bankId;
	}
	public void setBankId(long bankId) {
		this.bankId = bankId;
	}
	public long getPaymentModeId() {
		return paymentModeId;
	}
	public void setPaymentModeId(long paymentModeId) {
		this.paymentModeId = paymentModeId;
	}
	public PaymentStatusEnum getStatusEnum() {
		return statusEnum;
	}
	public void setStatusEnum(PaymentStatusEnum statusEnum) {
		this.statusEnum = statusEnum;
	}
	public String getDescLike() {
		return descLike;
	}
	public void setDescLike(String descLike) {
		this.descLike = descLike;
	}
	public Double getTransAmoutGrThan() {
		return transAmoutGrThan;
	}
	public void setTransAmoutGrThan(Double transAmoutGrThan) {
		this.transAmoutGrThan = transAmoutGrThan;
	}
	public Date getTransDateGrThan() {
		return transDateGrThan;
	}
	public void setTransDateGrThan(Date transDateGrThan) {
		this.transDateGrThan = transDateGrThan;
	}
	
	
	
}
