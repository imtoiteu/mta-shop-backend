package vn.fs.business;

import java.util.List;

import vn.fs.entity.Favorite;

public interface FavoritesBusiness {
	List<Favorite> findByEmail(String email);

	int countByProductId(Long productId);
	
	Favorite findByProductAndUser(Long productId, String email);
	
	Favorite saveFavorite(Favorite favorite);
	
	boolean deleteFavoriteById(Long id);
	
}
