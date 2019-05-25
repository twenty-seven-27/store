package cn.ecit.store.dao.daoImpl;

import java.sql.SQLException;

import java.util.List;
import java.util.Map;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


import cn.ecit.store.dao.OrderDao;
import cn.ecit.store.domain.Order;
import cn.ecit.store.domain.OrderItem;
import cn.ecit.store.domain.Product;
import cn.ecit.store.domain.User;
import cn.ecit.store.utils.JDBCUtils;

public class OrderDaoImpl implements OrderDao {

	public void saveOrder(Order order) throws SQLException {
		String sql = "insert into orders values(?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] params = {order.getOid(), order.getOrderTime(), order.getTotal(), order.getState(), 
				order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid()};
		QueryRunner queryRunner = new QueryRunner();
		queryRunner.update(JDBCUtils.getConnection(), sql, params);
	}

	public void saveOrderItem(OrderItem orderItem) throws SQLException {
		String sql = "insert into orderitem values(?, ?, ?, ?, ?)";
		Object[] params = {orderItem.getItemid(),    orderItem.getQuantity(), 
				orderItem.getTotal(), orderItem.getProduct().getPid(), orderItem.getOrder().getOid()};
		QueryRunner queryRunner = new QueryRunner();
		queryRunner.update(JDBCUtils.getConnection(), sql, params);
				
	}

	public int findTotalRecordsByUid(User user) throws Exception {
		String sql = "select count(*) from orders where uid=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long)queryRunner.query(sql, new ScalarHandler(), user.getUid());
		
		return num.intValue();
	}

	public List<Order> findOrdersByUidWithPage(User user, int startIndex, int pageSize) throws Exception {
		String sql = "select * from orders where uid=? order by ordertime desc limit ?, ?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		List<Order> list = queryRunner.query(sql, new BeanListHandler<Order>(Order.class), user.getUid(), startIndex, pageSize);
		
		for(Order order : list) {
			sql = "select * from orderitem o, product p where o.pid=p.pid and oid=?";
			List<Map<String, Object>> list01 = queryRunner.query(sql, new MapListHandler(), order.getOid());
			
			for(Map<String, Object> map : list01) {
				Product product = new Product();
				OrderItem orderItem = new OrderItem();
				
				try {
					BeanUtils.populate(product, map);
					BeanUtils.populate(orderItem, map);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				orderItem.setProduct(product);
				order.getList().add(orderItem);
			}
		}
		return list;
	}

	public Order findOrderByOid(String oid) throws Exception {
		String sql = "select * from orders where oid=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		Order order = queryRunner.query(sql, new BeanHandler<Order>(Order.class), oid);
		
		sql = "select * from orderitem o, product p where o.pid=p.pid and oid=?";
		List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler(), oid);
		
		for(Map<String, Object> map : list) {
			Product product = new Product();
			OrderItem orderItem = new OrderItem();
			try {
				BeanUtils.populate(product, map);
				BeanUtils.populate(orderItem, map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			orderItem.setProduct(product);
			order.getList().add(orderItem);
		}
		return order;
	}

	public void updateOrder(Order order) throws Exception {
		
		String sql = "update orders set ordertime =?, total=?, state=?, address=?, name=?, telephone=? where oid=?";
		Object[] params = {order.getOrderTime(), order.getTotal(), order.getState(), 
				order.getAddress(), order.getName(), order.getOid()};
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		queryRunner.update(sql, params);
	}

	public List<Order> findAllOrdersWithState(String state) throws Exception {
		String sql = "select * from orders where state=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRunner.query(sql, new BeanListHandler<Order>(Order.class), state);
	}

	public List<Order> findAllOrders() throws Exception {
		String sql = "select * from orders";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		
		return queryRunner.query(sql, new BeanListHandler<Order>(Order.class));
	}

}
