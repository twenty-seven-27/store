package cn.ecit.store.service;

import java.sql.SQLException;
import java.util.List;

import cn.ecit.store.domain.Category;

public interface CategoryService {


	List<Category> findAllCats() throws SQLException;
	
	void saveCat(Category category) throws SQLException;

}
