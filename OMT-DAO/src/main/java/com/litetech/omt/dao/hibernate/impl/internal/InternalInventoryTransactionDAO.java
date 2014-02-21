package com.litetech.omt.dao.hibernate.impl.internal;

import java.util.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.litetech.omt.dao.model.inventory.InventoryTransaction;

public class InternalInventoryTransactionDAO {

	private SessionFactory sessionFactory;
	
	public InternalInventoryTransactionDAO(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	
	public long addNewTransaction(long productId, long unitId, double transQty,
			double availQty, long availQtyUnitId, String transDesc){
		Session session = sessionFactory.getCurrentSession();
		InventoryTransaction inventoryTransactionDO = 
				constructTransactionDO(productId, unitId, transQty, 
						availQty, availQtyUnitId, transDesc);
		session.save(inventoryTransactionDO);
		return inventoryTransactionDO.getId();
	}
	
	
	private InventoryTransaction constructTransactionDO(long productId, 
			long unitId, double transQty, double availQty, long availQtyUnitId, String transDesc){
		Date currentDate = new Date();
		
		InventoryTransaction inventoryTransactionDO = new InventoryTransaction();
		inventoryTransactionDO.setProductId(productId);
		inventoryTransactionDO.setUnitId(unitId);
		inventoryTransactionDO.setQuantityAdded(transQty);
		inventoryTransactionDO.setAvlQuantity(availQty);
		inventoryTransactionDO.setAvlQuantityUnitId(availQtyUnitId);
		inventoryTransactionDO.setTransDesc(transDesc);
		inventoryTransactionDO.setCreationDate(currentDate);
		inventoryTransactionDO.setLastModifiedDate(currentDate);
		
		return inventoryTransactionDO;
	}
}
