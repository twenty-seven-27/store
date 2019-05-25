package cn.ecit.store.service.serviceImpl;


import java.sql.SQLException;



import cn.ecit.store.dao.UserDao;
import cn.ecit.store.domain.User;
import cn.ecit.store.service.UserService;
import cn.ecit.store.utils.BeanFactory;
import cn.ecit.store.utils.MailUtils;

public class UserServiceImpl implements UserService {
	UserDao userDao = (UserDao)BeanFactory.createObject("UserDao");
	
	@Override
	public void userRegist(User user01) throws SQLException{
		//调用业务层注册功能
		userDao.userRegist(user01);
		try {
			//向用户邮箱发送一份激活邮件
			MailUtils.sendMail(user01.getEmail(), user01.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public User findUserByUserName(String um) throws Exception{
		
		return userDao.findUserByUserName(um);
	}

	@Override
	public User userActive(String code) throws SQLException {
		return userDao.userActive(code);
	}

	@Override
	public void updateUser(User user) throws SQLException {
		userDao.updateUser(user);
	}

	@Override
	public User userLogin(User user01) throws SQLException {
		return userDao.userLogin(user01);
	}

}
