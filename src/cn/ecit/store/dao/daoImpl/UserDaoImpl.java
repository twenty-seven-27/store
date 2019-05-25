package cn.ecit.store.dao.daoImpl;

import java.sql.SQLException;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;


import cn.ecit.store.dao.UserDao;
import cn.ecit.store.domain.User;
import cn.ecit.store.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public void userRegist(User user01) throws SQLException{
		String sql = "insert into user values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Object[] params = {user01.getUid(), user01.getUsername(),  user01.getPassword(), user01.getName(), user01.getEmail(), 
				user01.getTelephone(), user01.getBirthday(), user01.getSex(), user01.getState(), user01.getCode()};
		QueryRunner qRunner = new QueryRunner(JDBCUtils.getDataSource());
		qRunner.update(sql, params);
	}

	@Override
	public User findUserByUserName(String um) throws SQLException{
		String sql = "select * from user where username=?";
		QueryRunner qRunner = new QueryRunner(JDBCUtils.getDataSource());
		return qRunner.query(sql, new BeanHandler<User>(User.class), um);
	}

	@Override
	public User userActive(String code) throws SQLException {
		String sql = "select * from user where code=?";
		QueryRunner qRunner = new QueryRunner(JDBCUtils.getDataSource());
		return qRunner.query(sql, new BeanHandler<User>(User.class), code);
	}

	@Override
	public void updateUser(User user) throws SQLException {
		String sql = "update user set username=?, password=?, name=?, email=?, telephone=?, "
				+ "birthday=?, sex=?, state=?, code=?  where uid=? ";
		Object[] params = {user.getUsername(), user.getPassword(), user.getName(), user.getEmail(), 
				user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode(), user.getUid()};
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		queryRunner.update(sql, params);
	}

	@Override
	public User userLogin(User user01) throws SQLException {
		String sql = "select * from user where username=? and password=? and state=1";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRunner.query(sql, new BeanHandler<User>(User.class), user01.getUsername(), user01.getPassword());
	}

}
