

import java.util.Date;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.litetech.omt.dao.model.core.Order;
import com.litetech.omt.dao.model.inventory.Product;
import com.litetech.omt.dao.model.inventory.ProductPrice;
import com.litetech.omt.dao.model.user.Address;
import com.litetech.omt.dao.model.user.Customer;

public class HibernateUtil {
	private static final SessionFactory sessionFactory;
	 
	static {
		try {
		//	 Create the SessionFactory from hibernate.cfg.xml
			sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (Throwable ex) {
		//	 Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	 
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	 
	
	
	public static void main(String[] args) {
		Session session = getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		/*Product product = new Product();
		product.setId(1);
		product.setProductName("Product Name");
		product.setProductDesc("product desc");
		product.setQuantity(5);
		product.setCreationDate(new Date());
		product.setLastModifiedDate(new Date());
		
		session.save(product);*/
		
		/*ProductPrice price = new ProductPrice(); 
		price.setProductId(product.getId());
		price.setStartDate(new Date());
		price.setPrice(250.00);
		price.setCreationDate(new Date());
		price.setLastModifiedDate(new Date());
		session.save(price);*/
		
		/*Product product = (Product)session.load(Product.class, 1005l);
		Set<ProductPrice> productPrices =  product.getProductPriceSet();
		for(ProductPrice price : productPrices){
			System.out.println("product id"+price.getProductId() );
			System.out.println("price id"+price.getPrice());
		}
		
		product.setProductName("updated product");
		product.setProductPriceSet(null);
		session.save(product);*/
		
	/*	Order order = new Order();
		order.setCustomerId(1l);
		order.setOrderAmount(1000.55);
		order.setOrderDate(new Date());
		order.setOrderStatus(1);
		order.setCreationDate(new Date());
		order.setLastModifiedDate(new Date());
		
		session.save(order);
		*/
		
		Address address1 = new Address();
		address1.setFirstLine("Test");
		address1.setCreationDate(new Date());
		address1.setFirstName("MERA");
		address1.setLastModifiedDate(new Date());
		address1.setMiddleName("SHIVA");
		address1.setLastName("BABA");
		address1.setPhone1("91-999999999");
		session.save(address1);
		
		Address address2 = new Address();
		address2.setFirstLine("Test 2");
		address2.setCreationDate(new Date());
		address2.setFirstName("MERA");
		address2.setLastModifiedDate(new Date());
		address2.setMiddleName("BRAHMA");
		address2.setLastName("BABA");
		address2.setPhone1("91-8888888888");
		session.save(address2);
		
	
		Customer customer = new Customer();
		customer.setAddress1(address1);
		customer.setAddress2(address2);
		customer.setCreationDate(new Date());
		customer.setCustomerName("BRAHMA KUMARIS");
		customer.setLastModifiedDate(new Date());
		session.save(customer);
		transaction.commit();
			
		Customer loadedCustomer = (Customer)session.load(Customer.class, customer.getId());
		System.out.println("customer 1" +loadedCustomer.getAddress1().getFirstName() + " " +loadedCustomer.getAddress1().getMiddleName() + " " +loadedCustomer.getAddress1().getLastName());
		System.out.println("customer 2" +loadedCustomer.getAddress2().getFirstName() + " " +loadedCustomer.getAddress2().getMiddleName() + " " +loadedCustomer.getAddress2().getLastName());
		
		
		session.close();
		
	}
}

