package com.litetech.omt.util.comparator;

import java.util.Comparator;

import com.litetech.omt.vo.ProductUnitVO;

public class ProductUnitComparator implements Comparator<ProductUnitVO> {

	@Override
	public int compare(ProductUnitVO arg0, ProductUnitVO arg1) {
		return (int)(arg0.getId() - arg1.getId());
	}

}
