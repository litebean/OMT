package com.litetech.omt.report;

import java.util.List;

import com.litetech.omt.vo.CustomerVO;
import com.litetech.omt.vo.IncomeExpenseVO;
import com.litetech.omt.vo.InvoiceVO;
import com.litetech.omt.vo.OrderVO;
import com.litetech.omt.vo.OrgVO;
import com.litetech.omt.vo.PaymentTransactionVO;
import com.litetech.omt.vo.ProfitLossSearchRequest;

public interface ReportService {

	
	public void printInvoice(OrgVO orgVO, CustomerVO customerVO,
			OrderVO orderVO, InvoiceVO invoiceVO, String[] reportModes);
	
	
	public void printProfitLossReport(ProfitLossSearchRequest searchRequest,
			OrgVO orgVO, IncomeExpenseVO incomeExpenseVO,
			List<PaymentTransactionVO> paymentTransactionVOs);
}
