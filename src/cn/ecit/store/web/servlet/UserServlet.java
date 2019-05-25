package cn.ecit.store.web.servlet;

import java.io.IOException;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ecit.store.domain.User;
import cn.ecit.store.service.UserService;
import cn.ecit.store.service.serviceImpl.UserServiceImpl;
import cn.ecit.store.utils.BeanFactory;
import cn.ecit.store.utils.CookUtils;
import cn.ecit.store.utils.MyBeanUtils;
import cn.ecit.store.utils.UUIDUtils;
import cn.ecit.store.web.base.BaseServlet;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UserService userService = (UserService)BeanFactory.createObject("UserService");
	
	public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		return "/jsp/register.jsp";
	}
	
	public String userRegist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		//1, 接受用户数据
		User user01 = MyBeanUtils.populate(User.class, request.getParameterMap());
		//2, 部分数据是通过程序设定的：uid, state, code。
		user01.setUid(UUIDUtils.getId());
		user01.setState(0);
		user01.setCode(UUIDUtils.getCode());
		
//		//3, 调用业务层注册功能，向用户邮箱发送一份激活邮件
//		UserService userService = new UserServiceImpl();
//		userService.userRegist(user01);
//		//4, 向客户端提示：用户注册成功，请激活，转发到提示页面
//		request.setAttribute("msg", "用户注册成功，请激活");
		
		UserService userService = new UserServiceImpl();
		try {
			userService.userRegist(user01);
			//注册成功，向用户邮箱发送信息，跳转页面。
			//发送邮件
			request.setAttribute("msg", "注册成功，请查看邮件并激活邮箱！");
		} catch (Exception e) {
			//注册失败，跳转提示页面
			request.setAttribute("msg", "注册失败，请重新注册");
		}
		
		return "/jsp/info.jsp";
	}
	
	public String active(HttpServletRequest request, HttpServletResponse response)  throws SQLException {
		//服务端获取激活码，和数据库中的激活码进行比对，如果存在，激活成功。将用户状态改为1；转发到登入页面，提示：激活成功
		String code = request.getParameter("code");
		//调用业务层功能：更具激活码查询用户：select * from user where code=?
		User user = userService.userActive(code);
		//如果用户不为空，激活码正确，可以激活
		if(null != user ) {
			user.setState(1);
			user.setCode("");
			userService.updateUser(user);
			
			//转发登入页面，提示激活成功；提示：激活成功请登录
			request.setAttribute("msg", "激活成功, 请登录");
			return "/jsp/login.jsp"; 
			
		} else {
			//激活失败,跳转到info.jsp
			request.setAttribute("msg", "激活失败，请检查！");
			return "/jsp/info.jsp";
			
		}
	}
	
	public String userExists(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//接受用户名
		String um = request.getParameter("username");
		//调用业务层功能，根据用户名查询用户
		User user = userService.findUserByUserName(um);
		//完成响应
		if(null != user) {
			response.getWriter().print("11");
		}else {
			response.getWriter().print("00");
		}
		return null;
	}
	
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException {
		Cookie cookie = CookUtils.getCookieByName("remUser", request.getCookies());
		if(null != cookie) {
			request.setAttribute("remUser", cookie.getValue());
		}
		return "/jsp/login.jsp";
	}
	
	public String userLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//接受用户名和密码
		User user01 = MyBeanUtils.populate(User.class, request.getParameterMap());
		//调用业务层的登录功能
		User user02 = userService.userLogin(user01);
		
		if(null != user02) {
			//登录成功，向session中放入用户信息，重定向到首页
			request.getSession().setAttribute("loginUser", user02);
			
			//在登录的基础上，判断用户是否选中“自动登录”复选框
			String autoLogin = request.getParameter("autoLogin");
			if("yes".equals(autoLogin)) {
				//用户选了“自动登录”复选框
				Cookie cookie = new Cookie("autoLogin", user02.getUsername()+"#"+user02.getPassword());
				cookie.setPath("/store_v4");
				cookie.setMaxAge(23232323);
				response.addCookie(cookie);
			}
			//remUser
			String remUser = request.getParameter("remUser");
			if("yes".equals(remUser)) {
				//用户选中“记住我”复选框
				Cookie cookie = new Cookie("remUser", user02.getUsername());
				cookie.setPath("/store_v4");
				cookie.setMaxAge(12312312);
				response.addCookie(cookie);
			}
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		} else {
			//登录失败，向request放入提示信息，转发到登入页面，显示提示userLogin
			request.setAttribute("msg", "用户名与密码不匹配！");
			return "/jsp/login.jsp";
		}
		return null;
	}
	
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//用户退出，清空用户session
		request.getSession().invalidate();
		Cookie cookie = CookUtils.getCookieByName("autoLogin", request.getCookies());
		if(null != cookie) {
			cookie.setMaxAge(0);
			cookie.setPath("/store_v4");
			response.addCookie(cookie);
		}
		
		response.sendRedirect(request.getContextPath()+"/jsp/index.jsp");
		return null;
	}
	
	
	
	
}
