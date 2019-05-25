package cn.ecit.store.web.servlet;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ecit.store.domain.Category;
import cn.ecit.store.service.CategoryService;
import cn.ecit.store.service.serviceImpl.CategoryServiceImpl;
import cn.ecit.store.utils.JedisUtils;
import cn.ecit.store.web.base.BaseServlet;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public String findAllCats(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	response.setContentType("application/json;charset=UTF-8");	//服务端以JSON格式将数据返回给客户端
    	String jsonStr="";	
    	Jedis jedis = JedisUtils.getJedis();	//调用Jedis工具类，获得链接
    	jsonStr = jedis.get("callCats");	//获得键为callCats的键值
    	if(null == jsonStr || "".equals(jsonStr)) {	//判断Redis中是否有分类数据，没有就将数据存入，有则响应给客户端
    		System.out.println("缓存中没有数据。");
    		//查询所有分类
    		CategoryService categoryService = new CategoryServiceImpl();
    		List<Category> list = categoryService.findAllCats();	//获得所有分类数据
    		//将集合中的所有分类信息转换成json格式的字符串数据
    		jsonStr = JSONArray.fromObject(list).toString();
    		jedis.set("allCats", jsonStr);	//添加键和对应的JSON格式的分类字符串数据
    	} else {
    		System.out.println("缓存中有数据");
    	}
    	//将字符串响应到客户端
    	response.getWriter().println(jsonStr);;
    	JedisUtils.closeJedis(jedis);
    	return null;
    }

}
