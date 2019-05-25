package cn.ecit.store.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import cn.ecit.store.domain.Cart;
import cn.ecit.store.domain.CartItem;
import cn.ecit.store.domain.Product;
import cn.ecit.store.domain.User;
import cn.ecit.store.service.ProductService;
import cn.ecit.store.service.serviceImpl.ProductServiceImpl;
import cn.ecit.store.web.base.BaseServlet;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet {

	public String addToCart(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//判断用户是否登入，如果没有登入，跳转到login.jsp页面，提示登入后才可以购物
		User user = (User)request.getSession().getAttribute("loginUser");
		
		if(null == user) {
			request.setAttribute("mag", "请登录后购买商品");
			return "/jsp/login.jsp";
		}
		
		//获取表单中的随机字符串
		String ranStr01 = request.getParameter("ranStr");
		
		//获取session中随机字符串
		String ranStr02 = (String)request.getSession().getAttribute("ranStr");
		System.out.println(ranStr01+"........01");
		System.out.println(ranStr02+"........02");
		
		if(!ranStr01.equals(ranStr02)) {
			request.setAttribute("msg", "不要重复提交数据");
			return "/jsp/info.jsp";
		}
		
		//如果登录，服务端获得到pid，数量，创建好CartItem
		String pid = request.getParameter("pid");
		int num = Integer.parseInt(request.getParameter("num"));
		ProductService productService = new ProductServiceImpl();
		Product product = productService.findProductByPid(pid);
		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setNum(num);

		//获取购物车：从session中获取，获取到直接使用
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		//如果获取不到，就新建一份，并在session中再绑定一份
		if(null == cart) {
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		//调用购物车上添加商品到购物车的功能
		cart.addCart(cartItem);

		//request.getSession().removeAttribute("ranStr");
		
		//重定向到购物车页面jsp/cart.jsp
		//return "jsp/cart.jsp"
		
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
		
	}
	
	//clearCart
	public String clearCart(HttpServletRequest request, HttpServletResponse response)throws Exception{
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		cart.clearCart();
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
	
	//delCartItem
	public String delCartItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取到被删除商品pid
		String pid = request.getParameter("pid");
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		cart.delCart(pid);
		response.sendRedirect(request.getContentType()+"/jsp/cart.jsp");
		return null;
	}
	
}
