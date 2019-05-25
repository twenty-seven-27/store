package cn.ecit.store.dao.daoImpl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.ecit.store.dao.ProductDao;
import cn.ecit.store.domain.Product;
import cn.ecit.store.utils.JDBCUtils;

public class ProductDaoImpl implements ProductDao {

	@Override
	public Product findProductByPid(String pid) throws SQLException {
		String sql = "select * from product where pid=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRunner.query(sql, new BeanHandler<Product>(Product.class), pid);
	}

	@Override
	public List<Product> findNewProducts() throws SQLException {
		String sql = "select * from product where pflag=0 order by pdate desc limit 0, 9";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public List<Product> findHotProuducts() throws SQLException {
		String sql = "select * from product where pflag=0 and is_hot=1 order by pdate desc limit 0 , 9";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public int findTotalNum(String cid) throws SQLException {
		String sql = "select count(*) from product where cid=?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long)queryRunner.query(sql, new ScalarHandler(), cid);
		return num.intValue();
	}

	@Override
	public List<Product> findProductsWithCidAndPage(String cid, int startIndex, int pageSize) throws SQLException {
		String sql = "select * from product where cid=? limit ?,?";
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		return queryRunner.query(sql, new BeanListHandler<Product>(Product.class), cid, startIndex, pageSize);
	}

}
