package vn.fs.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.fs.entity.Favorite;
import vn.fs.entity.Product;
import vn.fs.entity.User;
import vn.fs.repository.FavoriteDAO;
import vn.fs.repository.ProductDAO;
import vn.fs.repository.UserDAO;
import vn.fs.business.FavoritesBusiness;

@CrossOrigin("*")
@RestController
@RequestMapping("api/favorites")
public class FavoritesService {

	@Autowired
	FavoriteDAO favoriteRepository;

	@Autowired
	UserDAO userRepository;

	@Autowired
	ProductDAO productRepository;
	
	@Autowired
	FavoritesBusiness favoritesBusiness;

	@GetMapping("email/{email}")
	public ResponseEntity<List<Favorite>> findByEmail(@PathVariable("email") String email) {
		if (userRepository.existsByEmail(email)) {
			return ResponseEntity.ok(favoriteRepository.findByUser(userRepository.findByEmail(email).get()));
		}
		return ResponseEntity.notFound().build();
//		List<Favorite> favorites = favoritesBusiness.findByEmail(email);
//	    if (!favorites.isEmpty()) {
//	        return ResponseEntity.ok(favorites);
//	    }
//	    return ResponseEntity.notFound().build();
	}

	@GetMapping("product/{id}")
	public ResponseEntity<Integer> findByProduct(@PathVariable("id") Long id) {
		if (productRepository.existsById(id)) {
			return ResponseEntity.ok(favoriteRepository.countByProduct(productRepository.getById(id)));
		}
		return ResponseEntity.notFound().build();
//
//		int count = favoritesBusiness.countByProductId(id);
//	    if (count > 0) {
//	        return ResponseEntity.ok(count);
//	    }
//	    return ResponseEntity.notFound().build();
	    
	}

	@GetMapping("{productId}/{email}")
	public ResponseEntity<Favorite> findByProductAndUser(@PathVariable("productId") Long productId,
			@PathVariable("email") String email) {
		if (userRepository.existsByEmail(email)) {
			if (productRepository.existsById(productId)) {
				Product product = productRepository.findById(productId).get();
				User user = userRepository.findByEmail(email).get();
				return ResponseEntity.ok(favoriteRepository.findByProductAndUser(product, user));
			}
		}
		return ResponseEntity.notFound().build();
//		Favorite favorite = favoritesBusiness.findByProductAndUser(productId, email);
//	    if (favorite != null) {
//	        return ResponseEntity.ok(favorite);
//	    }
//	    return ResponseEntity.notFound().build();
	}

	@PostMapping("email")
	public ResponseEntity<Favorite> post(@RequestBody Favorite favorite) {
		return ResponseEntity.ok(favoriteRepository.save(favorite));
//		Favorite savedFavorite = favoritesBusiness.saveFavorite(favorite);
//	    return ResponseEntity.ok(savedFavorite);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		if (favoriteRepository.existsById(id)) {
			favoriteRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
//		if (favoritesBusiness.deleteFavoriteById(id)) {
//	        return ResponseEntity.ok().build();
//	    }
//	    return ResponseEntity.notFound().build();
	}

}
