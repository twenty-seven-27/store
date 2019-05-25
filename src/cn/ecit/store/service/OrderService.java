package cn.ecit.store.service;

import java.util.List;

import cn.ecit.store.domain.Order;
import cn.ecit.store.domain.User;
import cn.ecit.store.utils.PageModel;

public interface OrderService {
	
	void saveOrder(Order order);
	
	PageModel findOrdersByUidWithPage(User user, int curNum)throws Exception;
	
	Order findOrderByOid(String oid) throws Exception;
	
	void updateOrder(Order order) throws Exception;
	
	List<Order> findAllOrders() throws Exception;
	
	List<Order> findAllOrderWithState(String state) throws Exception;
	
	
}
