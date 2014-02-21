import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.litetech.omt.constant.OrderStatusEnum;
import com.litetech.omt.dao.OrderDAO;
import com.litetech.omt.vo.OrderVO;


public class SpringHibernateUtil {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("SpringDAO.xml");
		OrderDAO orderDAO = (OrderDAO)applicationContext.getBean("orderDAO");
		
		OrderVO orderVO = new OrderVO(false);
		orderVO.setCreationDate(new Date());
		orderVO.setCustomerId(1005);
		orderVO.setLastModifiedDate(new Date());
		orderVO.setOrderAmount(10000000);
		orderVO.setOrderDate(new Date());
		orderVO.setOrderDesc("After Recovery coding.. ");
		orderVO.setOrderStatus(OrderStatusEnum.PENDING);
		orderVO.setTaxId(1002);
		
		orderDAO.persistOrder(orderVO);
		
		
		OrderVO loadedOrderVO = orderDAO.retrieveOrder(orderVO.getId());
		System.out.println("Order Id "+loadedOrderVO.getId());
		System.out.println("Order Desc "+loadedOrderVO.getOrderDesc() + " order amt "+loadedOrderVO.getOrderAmount());
	}
	
}
