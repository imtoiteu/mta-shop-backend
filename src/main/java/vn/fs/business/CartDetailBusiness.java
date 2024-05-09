package vn.fs.business;

import java.util.List;

import vn.fs.entity.CartDetail;

public interface CartDetailBusiness {

	List<CartDetail> getByCartId(Long id);
	
	CartDetail getOne(Long id);
	
	CartDetail post(CartDetail detail);
	
	CartDetail put(CartDetail detail);
	
	CartDetail deleteById(Long id);
}
