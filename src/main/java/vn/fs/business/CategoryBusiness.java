package vn.fs.business;

import java.util.List;

import vn.fs.entity.Category;

public interface CategoryBusiness {

	List<Category> getAll(int offset, int limit);
	
	Category getById(Long id);
	
	Category post(Category category);
	
	Category put(Category category);
	
	boolean deleteById(Long id);
	
}
