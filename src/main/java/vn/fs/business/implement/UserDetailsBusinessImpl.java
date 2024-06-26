package vn.fs.business.implement;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.fs.entity.User;
import vn.fs.repository.UserDAO;

@Service
public class UserDetailsBusinessImpl implements UserDetailsService {

	@Autowired
	UserDAO userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("user not found with username" + username));

		return (UserDetails) UserDetailsImpl.build(user);
	}

}