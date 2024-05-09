package vn.fs.business;

import vn.fs.entity.Cart;

//@Service
public interface CartBusiness {
	Cart getCartUser(String email);
	
	Cart putCartUser(String email, Cart cart);
}
