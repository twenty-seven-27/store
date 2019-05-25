package cn.ecit.store.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cn.ecit.store.domain.User;

/**
 * Servlet Filter implementation class PriviledgeFilter
 */
public class PriviledgeFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public PriviledgeFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 将ServletRequest转成HttpServletRequest，才能获取session。
		HttpServletRequest myRequest = (HttpServletRequest) request;
		// 判断当前的session中是否存在已经登录的用户
		User user = (User) myRequest.getSession().getAttribute("loginUser");

		if (null != user) {
			// 如果存在，放行
			chain.doFilter(request, response);
		} else {
			// 如果不存在，提示信息
			myRequest.setAttribute("msg", "请登录之后再访问");
			// 转到提示页面
			myRequest.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
