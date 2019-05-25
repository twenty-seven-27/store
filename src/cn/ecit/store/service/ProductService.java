package cn.ecit.store.service;

import java.sql.SQLException;
import java.util.List;

import cn.ecit.store.domain.Product;
import cn.ecit.store.utils.PageModel;

public interface ProductService {
	
	List<Product> findnewProducts() throws SQLException;
	
	List<Product> findHotProducts() throws SQLException;
	
	Product findProductByPid(String pid) throws SQLException;

	PageModel findProductsWithCidAndPage(String cid, int cueNum)throws SQLException;
	
	
	
}
