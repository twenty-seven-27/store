package cn.ecit.store.web.servlet;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.ecit.store.domain.Product;
import cn.ecit.store.service.ProductService;
import cn.ecit.store.service.serviceImpl.ProductServiceImpl;
import cn.ecit.store.utils.PageModel;
import cn.ecit.store.utils.UUIDUtils;
import cn.ecit.store.web.base.BaseServlet;
/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public String findProductByPid(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	//服务端获取代pid
    	String pid = request.getParameter("pid");
    	//更具pid查询对应的商品
    	ProductService productService = new  ProductServiceImpl();
    	Product product = productService.findProductByPid(pid);
    	//将商品放入request内
    	request.setAttribute("pro", product);
    	
    	String ranStr = UUIDUtils.getId();
    	//在session中存放一份随机字符串
    	request.getSession().setAttribute("ranStr", ranStr);
    	System.out.println(ranStr);
    	
    	//转发到/product_info.jsp
    	return "/jsp/product_info.jsp";
    }
    
    	public String findProductsWithCidAndPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
    		//接受当前页面
    		int curNum = Integer.parseInt(request.getParameter("num"));
        	//接受分类cid
    		String cid = request.getParameter("cid");
        	//调用业务层，查询当前分类下的当前页数据的功能，返回PageModel对象
    		ProductService productService = new ProductServiceImpl();
    		PageModel pageModel = productService.findProductsWithCidAndPage(cid, curNum);
        	//将pageModel对象存入request
    		request.setAttribute("page", pageModel);
        	//转发到 /jsp/product_list.jsp下
    		return "/jsp/product_list.jsp";
    	}
    }
