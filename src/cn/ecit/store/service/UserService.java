package cn.ecit.store.service;

import java.sql.SQLException;


import cn.ecit.store.domain.User;

public interface UserService {

	void userRegist(User user01) throws SQLException;

	User findUserByUserName(String um) throws Exception;

	User userActive(String code) throws SQLException;

	void updateUser(User user) throws SQLException;

	User userLogin(User user01) throws SQLException;

}
