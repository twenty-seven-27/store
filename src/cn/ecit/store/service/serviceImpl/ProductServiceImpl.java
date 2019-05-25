package cn.ecit.store.service.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import cn.ecit.store.dao.ProductDao;
import cn.ecit.store.dao.daoImpl.ProductDaoImpl;
import cn.ecit.store.domain.Product;
import cn.ecit.store.service.ProductService;
import cn.ecit.store.utils.PageModel;

public class ProductServiceImpl implements ProductService{

	ProductDao productDao = new ProductDaoImpl();
	
	@Override
	public List<Product> findnewProducts() throws SQLException {
		return productDao.findNewProducts();
	}

	@Override
	public List<Product> findHotProducts() throws SQLException {
		return productDao.findHotProuducts();
	}

	@Override
	public Product findProductByPid(String pid) throws SQLException {
		return productDao.findProductByPid(pid);
	}

	@Override
	public PageModel findProductsWithCidAndPage(String cid, int curNum) throws SQLException {
		//1,创建PageModel对象，目的：携带分页参数 select count(*) from product where cid=?
		int totalRecords = productDao.findTotalNum(cid);
		PageModel pageModel = new PageModel(curNum, totalRecords, 12);
		//2，联合集合 select * from product where cid=1 limit (当前页-1)*5， 5；
		List<Product> list = productDao.findProductsWithCidAndPage(cid, pageModel.getStartIndex(), pageModel.getPageSize());
		pageModel.setList(list);
		//3，关联url
		pageModel.setUrl("ProductServlet?method=findProductsWithCidAndPage&cid="+cid);
		
		return pageModel;
	}

	
}

