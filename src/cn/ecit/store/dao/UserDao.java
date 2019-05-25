package cn.ecit.store.dao;

import java.sql.SQLException;

import cn.ecit.store.domain.User;

public interface UserDao {

	void userRegist(User user01) throws SQLException;

	public User findUserByUserName(String um) throws SQLException;

	User userActive(String code) throws SQLException;

	void updateUser(User user) throws SQLException;

	User userLogin(User user01) throws SQLException; 

}
