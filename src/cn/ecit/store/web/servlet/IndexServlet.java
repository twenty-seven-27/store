package cn.ecit.store.web.servlet;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ecit.store.domain.Category;
import cn.ecit.store.domain.Product;
import cn.ecit.store.service.CategoryService;
import cn.ecit.store.service.ProductService;
import cn.ecit.store.service.serviceImpl.CategoryServiceImpl;
import cn.ecit.store.service.serviceImpl.ProductServiceImpl;
import cn.ecit.store.web.base.BaseServlet;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends BaseServlet {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//查询全部分类
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> list = categoryService.findAllCats();
		//放入request 域对象
		request.setAttribute("allCats", list);
		
		ProductService productService = new  ProductServiceImpl();
		//获取最新的9件商品
		List<Product> list2 = productService.findnewProducts();
		//获取最热的9件商品
		List<Product> list3 = productService.findHotProducts();
		
		//将最新商品放入request 
		request.setAttribute("news", list2);
		//将最热的商品放入request
		request.setAttribute("hots", list3);
		
		
		//转发到/jsp/index.jsp
		return "/jsp/index.jsp";
	}
}
