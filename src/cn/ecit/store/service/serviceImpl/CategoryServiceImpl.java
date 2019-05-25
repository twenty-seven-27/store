package cn.ecit.store.service.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import cn.ecit.store.dao.CategoryDao;
import cn.ecit.store.dao.daoImpl.CategoryDaoImpl;
import cn.ecit.store.domain.Category;
import cn.ecit.store.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {
	
	CategoryDao CategoryDao = new CategoryDaoImpl();

	@Override
	public List<Category> findAllCats() throws SQLException {
		return CategoryDao.findAllCats();
	}

	@Override
	public void saveCat(Category category) throws SQLException {
		CategoryDao.saveCat(category);
	}

}
