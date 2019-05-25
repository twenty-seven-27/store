package cn.ecit.store.dao.daoImpl;

import java.sql.SQLException;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;


import cn.ecit.store.dao.CategoryDao;
import cn.ecit.store.domain.Category;
import cn.ecit.store.utils.JDBCUtils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findAllCats() throws SQLException {
		
		String sql = "select * from category";
		QueryRunner qRunner = new QueryRunner(JDBCUtils.getDataSource());
		
		return qRunner.query(sql,  new BeanListHandler<Category>(Category.class));
	}

	@Override
	public void saveCat(Category c) throws SQLException {
		String sql = "insert into category values (?, ?)";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		queryRunner.query(sql, new BeanListHandler<Category>(Category.class));
	}

}
