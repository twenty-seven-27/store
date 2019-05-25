package cn.ecit.store.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.ecit.store.domain.Cart;
import cn.ecit.store.domain.CartItem;
import cn.ecit.store.domain.Order;
import cn.ecit.store.domain.OrderItem;
import cn.ecit.store.domain.User;
import cn.ecit.store.service.OrderService;
import cn.ecit.store.service.serviceImpl.OrderServiceImpl;
import cn.ecit.store.utils.PageModel;
import cn.ecit.store.utils.UUIDUtils;
import cn.ecit.store.web.base.BaseServlet;


public class OrderServlet extends BaseServlet {
	
	OrderService orderService = new OrderServiceImpl();
	
	public String saveOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取用户session
		User user = (User)request.getSession().getAttribute("loginUser");
		//获取购物车
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		//创建订单对象
		Order order = new Order();
		//为订单对象赋值，oid ordertime state
		order.setOid(UUIDUtils.getId());
		order.setOrderTime(new Date());
		order.setState(1);
		//获取购物车中总的订单数
		order.setTotal(cart.getTotal());
		//为订单对象关联用户
		order.setUser(user);
		
		//遍历购物车中所有的购物项
		for(CartItem item : cart.getCartItems()) {
			//遍历的同时创建订单项
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtils.getId());
			
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getNum());
			orderItem.setTotal(item.getSubTotal());
			orderItem.setOrder(order);
			order.getList().add(orderItem);
		}
		
		orderService.saveOrder(order);
		
		//清空购物车
		cart.clearCart();
		//向request内放置一份order，便于详情页面显示订单信息
		request.setAttribute("order", order);
		return "/jsp/order_info.jsp";
		
	}
	
	public String findOrdersByUidWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取数据
		int curNum = Integer.parseInt(request.getParameter("num"));
		User user = (User)request.getSession().getAttribute("loginUser");
		//调用业务层的功能，返回PageModel对象（分页数，所有订单，url）
		PageModel pageModel = orderService.findOrdersByUidWithPage(user, curNum);
		//将PageModel对象放入reques，转发给order_list.jsp
		request.setAttribute("page", pageModel);
		return "/jsp/order_list.jsp";
	}
	
	public String findOrderByOid(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取客户端返回的oid，根据oid查询订单（订单携带所有当前订单上的所有订单项和商品）
		String oid = request.getParameter("oid");
		Order order = orderService.findOrderByOid(oid);
		//将订单放入request
		request.setAttribute("order", order);
		//转发到订单详情页面
		return "/jsp/order_info.jsp";
	}
	
	
}
