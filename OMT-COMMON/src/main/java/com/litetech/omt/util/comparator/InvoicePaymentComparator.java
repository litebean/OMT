package com.litetech.omt.util.comparator;

import java.util.Comparator;

import com.litetech.omt.vo.InvoicePaymentVO;

public class InvoicePaymentComparator implements Comparator<InvoicePaymentVO>{

	@Override
	public int compare(InvoicePaymentVO arg0, InvoicePaymentVO arg1) {
		return (int)(arg0.getId() - arg1.getId());
	}

}
