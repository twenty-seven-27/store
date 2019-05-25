package cn.ecit.store.dao;

import java.sql.SQLException;
import java.util.List;

import cn.ecit.store.domain.Product;

public interface ProductDao {

	Product findProductByPid(String pid) throws SQLException;

	List<Product> findNewProducts() throws SQLException;

	List<Product> findHotProuducts() throws SQLException;

	int findTotalNum(String cid) throws SQLException;

	List<Product> findProductsWithCidAndPage(String cid, int startIndex, int pageSize) throws SQLException;

}
