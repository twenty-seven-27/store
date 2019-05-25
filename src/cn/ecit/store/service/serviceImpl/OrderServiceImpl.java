package cn.ecit.store.service.serviceImpl;

import java.util.List;


import cn.ecit.store.dao.OrderDao;
import cn.ecit.store.dao.daoImpl.OrderDaoImpl;
import cn.ecit.store.domain.Order;
import cn.ecit.store.domain.OrderItem;
import cn.ecit.store.domain.User;
import cn.ecit.store.service.OrderService;
import cn.ecit.store.utils.JDBCUtils;
import cn.ecit.store.utils.PageModel;

public class OrderServiceImpl implements OrderService {

	OrderDao orderDao = new OrderDaoImpl();
	
	public void saveOrder(Order order) {
		try {
			JDBCUtils.startTransaction();
			orderDao.saveOrder(order);
			for(OrderItem item : order.getList()) {
				orderDao.saveOrderItem(item);
			}
			JDBCUtils.commitAndClose();
		} catch (Exception e) {
			JDBCUtils.rollbackAndClose();
			e.printStackTrace();
		}
	}

	public PageModel findOrdersByUidWithPage(User user, int curNum) throws Exception {
		int totalRecords = orderDao.findTotalRecordsByUid(user);
		PageModel pageModel = new PageModel(curNum, totalRecords, 3);
		List<Order> list = orderDao.findOrdersByUidWithPage(user, pageModel.getStartIndex(), pageModel.getPageSize());
		pageModel.setList(list);
		pageModel.setUrl("OrderServlet?method=findOrdersByUidWithPage");
		return pageModel;
	}

	public Order findOrderByOid(String oid) throws Exception {
		return orderDao.findOrderByOid(oid);
	}

	public void updateOrder(Order order) throws Exception {
		orderDao.updateOrder(order);
	}

	public List<Order> findAllOrders() throws Exception {
		return orderDao.findAllOrders();
	}

	public List<Order> findAllOrderWithState(String state) throws Exception {
		return orderDao.findAllOrdersWithState(state);
	}

}
