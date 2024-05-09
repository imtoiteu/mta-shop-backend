package vn.fs.api;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import vn.fs.config.JwtUtils;
import vn.fs.dto.JwtResponse;
import vn.fs.dto.LoginRequest;
import vn.fs.dto.MessageResponse;
import vn.fs.dto.SignupRequest;
import vn.fs.entity.AppRole;
import vn.fs.entity.Cart;
import vn.fs.entity.User;
//import vn.fs.objectstorageservice.MinioService;
import vn.fs.repository.AppRoleDAO;
import vn.fs.repository.CartDAO;
import vn.fs.repository.UserDAO;
import vn.fs.business.SendMailBusiness;
import vn.fs.business.UserBusiness;
import vn.fs.business.implement.UserDetailsImpl;

import java.time.format.DateTimeFormatter;


@CrossOrigin("*")
@RestController
@RequestMapping("api/auth")
public class UserService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDAO userRepository;

	@Autowired
	CartDAO cartRepository;

	@Autowired
	AppRoleDAO roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	SendMailBusiness sendMailService;

	@Autowired
	UserBusiness userService;

//	@Autowired
//	private MinioService minioService;

//	@Value("${minio.bucket.name}")
//	private String bucketName;


//	@PostMapping("/createOrUpdate")
//	public ResponseEntity<User> createOrUpdateUserWithImage(@RequestParam("file") MultipartFile file,
//															@RequestParam("name") String name,
//															@RequestParam("email") String email,
//															@RequestParam("password") String password,
//															@RequestParam("address") String address,
//															@RequestParam("phone") String phone,
//															@RequestParam("gender") Boolean gender,
//															@RequestParam("registerDate") String registerDate,
//															@RequestParam("status") Boolean status
//	) {
//		if (userRepository.existsByEmail(email)) {
//			System.out.println("1");
//			return ResponseEntity.notFound().build();
//		}
//		String fileN = file.getOriginalFilename();
//		if(fileN == null){
//			fileN = "https://res.cloudinary.com/dwrcr5anf/image/upload/v1683206351/profileImage_ug8g1i.png";
//		}
//		try {
//			String fileName = minioService.uploadFile(bucketName, fileN,
//					file.getInputStream(), file.getContentType());
////			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
////					.path("/download/")
////					.path(fileName)
////					.toUriString();
//			String fileDownloadUri = "http://127.0.0.1:9000/mtashop/" + fileName;
//
//			// Tạo hoặc cập nhật user trong cơ sở dữ liệu
//			User user = new User();
//			user.setEmail(email);
//			user.setName(name);
//			user.setPassword(passwordEncoder.encode(password));
//			user.setAddress(address);
//			user.setPhone(phone);
//			user.setGender(gender);
//
//			// Tạo DateTimeFormatter với locale US để phân tích cú pháp tháng viết tắt bằng chữ
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)", Locale.ENGLISH);
//			// Chuyển đổi chuỗi thành LocalDate
//			LocalDate localDate = LocalDate.parse(registerDate, formatter);
//			// Hiển thị ngày đã phân tích
//			user.setRegisterDate(localDate);
//			user.setStatus(status);
//			// Set các trường khác cho user
//			user.setImage(fileDownloadUri); // URL của file trong MinIO
//
//			Set<AppRole> roles = new HashSet<>();
//			roles.add(new AppRole(1, null));
//			user.setRoles(roles);
//			user.setToken(jwtUtils.doGenerateToken(email));
//			User u = userRepository.save(user);
//			Cart c = new Cart(0L, 0.0, u.getAddress(), u.getPhone(), u);
//			cartRepository.save(c);
//
//			return ResponseEntity.ok(user);
//		} catch (Exception e) {
//			// Xử lý lỗi
//			System.out.println("2");
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		}
//	}


//	@PutMapping("/update/{id}")
//	public ResponseEntity<User> updateUserWithImage(@PathVariable("id") Long id,
//													@RequestParam("file") MultipartFile file,
//													@RequestParam("name") String name,
//													@RequestParam("email") String email,
//													@RequestParam("password") String password,
//													@RequestParam("address") String address,
//													@RequestParam("phone") String phone,
//													@RequestParam("gender") Boolean gender,
//													@RequestParam("registerDate") String registerDate,
//													@RequestParam("status") Boolean status
//	) {
//
//		if (!userRepository.existsById(id)) {
//			return ResponseEntity.notFound().build();
//		}
//
//		try {
//			String fileN = file.getOriginalFilename();
//			if(fileN == null){
//				fileN = "https://res.cloudinary.com/dwrcr5anf/image/upload/v1683206351/profileImage_ug8g1i.png";
//			}
//			String fileName = minioService.uploadFile(bucketName, fileN,
//					file.getInputStream(), file.getContentType());
//			String fileDownloadUri = "http://127.0.0.1:9000/mtashop/" + fileName;
//
//			User user = new User();
//			user.setUserId(id);
//			user.setEmail(email);
//			user.setName(name);
//			user.setPassword(passwordEncoder.encode(password));
//			user.setAddress(address);
//			user.setPhone(phone);
//			user.setGender(gender);
//
//
//			String[] parts = registerDate.split(",");
//			StringBuilder sb = new StringBuilder();
//			for (int i = 0; i < parts.length; i++) {
//				if (parts[i].length() == 1) {
//					sb.append("0"); // Thêm số 0 vào trước số có một chữ số
//				}
//				sb.append(parts[i]);
//				if (i < parts.length - 1) {
//					sb.append(",");
//				}
//			}
//
//			// Chuẩn hóa định dạng của chuỗi ngày tháng
//			String standardizedDateStr = sb.toString();
//
//			// Chuyển đổi chuỗi đã chuẩn hóa thành LocalDate
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy,MM,dd");
//			LocalDate localDate = LocalDate.parse(standardizedDateStr, formatter);
//
//			// Hiển thị ngày đã phân tích
//			user.setRegisterDate(localDate);
//			user.setStatus(status);
//			// Set các trường khác cho user
//			user.setImage(fileDownloadUri); // URL của file trong MinIO
//
//			Set<AppRole> roles = new HashSet<>();
//			roles.add(new AppRole(1, null));
//			user.setRoles(roles);
//			user.setToken(jwtUtils.doGenerateToken(email));
//			User u = userRepository.save(user);
//
//			return ResponseEntity.ok(userRepository.save(user));
//		} catch (Exception e) {
//			// Xử lý lỗi
//			System.out.println("2");
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		}
//	}

	@GetMapping
	public ResponseEntity<List<User>> getAll() {
//		return ResponseEntity.ok(userRepository.findByStatusTrue());
		List<User> activeUsers = userService.getAllActiveUsers();
	    // Trả về danh sách người dùng trong ResponseEntity
	    return ResponseEntity.ok(activeUsers);
	}

	@GetMapping("{id}")
	public ResponseEntity<User> getOne(@PathVariable("id") Long id) {
		if (!userRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(userRepository.findById(id).get());
//		// Gọi service method để lấy thông tin của người dùng
//	    User user = userService.getUserById(id);
//	    // Kiểm tra xem người dùng có tồn tại không
//	    if (user != null) {
//	        return ResponseEntity.ok(user);
//	    } else {
//	        return ResponseEntity.notFound().build();
//	    }
	}

	@GetMapping("email/{email}")
	public ResponseEntity<User> getOneByEmail(@PathVariable("email") String email) {
		if (userRepository.existsByEmail(email)) {
			return ResponseEntity.ok(userRepository.findByEmail(email).get());
		}
		return ResponseEntity.notFound().build();
//		User user = userService.getUserByEmail(email);
//	    // Kiểm tra xem người dùng có tồn tại không
//	    if (user != null) {
//	        return ResponseEntity.ok(user);
//	    } else {
//	        return ResponseEntity.notFound().build();
//	    }
	}

	@PostMapping
	public ResponseEntity<User> post(@RequestBody User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			return ResponseEntity.notFound().build();
		}
		if (userRepository.existsById(user.getUserId())) {
			return ResponseEntity.badRequest().build();
		}

		Set<AppRole> roles = new HashSet<>();
		roles.add(new AppRole(1, null));

		user.setRoles(roles);

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setToken(jwtUtils.doGenerateToken(user.getEmail()));
		User u = userRepository.save(user);
		Cart c = new Cart(0L, 0.0, u.getAddress(), u.getPhone(), u);
		cartRepository.save(c);
		return ResponseEntity.ok(u);
//		return userService.createUser(user);
	}

	@PutMapping("{id}")
	public ResponseEntity<User> put(@PathVariable("id") Long id, @RequestBody User user) {
		if (!userRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		if (!id.equals(user.getUserId())) {
			return ResponseEntity.badRequest().build();
		}

		User temp = userRepository.findById(id).get();

		if (!user.getPassword().equals(temp.getPassword())) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}

		Set<AppRole> roles = new HashSet<>();
		roles.add(new AppRole(1, null));

		user.setRoles(roles);
		return ResponseEntity.ok(userRepository.save(user));
//		return userService.updateUser(id, user);
	}

	@PutMapping("admin/{id}")
	public ResponseEntity<User> putAdmin(@PathVariable("id") Long id, @RequestBody User user) {
		if (!userRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		if (!id.equals(user.getUserId())) {
			return ResponseEntity.badRequest().build();
		}
		Set<AppRole> roles = new HashSet<>();
		roles.add(new AppRole(2, null));

		user.setRoles(roles);
		return ResponseEntity.ok(userRepository.save(user));
//		return userService.updateUserAsAdmin(id, user);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		if (!userRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		User u = userRepository.findById(id).get();
		u.setStatus(false);
		userRepository.save(u);
		return ResponseEntity.ok().build();
		
//		boolean deleted = userService.deleteById(id);
//	    if (!deleted) {
//	        return ResponseEntity.notFound().build();
//	    }
//	    return ResponseEntity.ok().build();
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getName(),
				userDetails.getEmail(), userDetails.getPassword(), userDetails.getPhone(), userDetails.getAddress(),
				userDetails.getGender(), userDetails.getStatus(), userDetails.getImage(), userDetails.getRegisterDate(),
				roles));
		
//		return userService.authenticateUser(loginRequest);

	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupRequest) {

		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
		}

		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is alreadv in use!"));
		}

		// create new user account
		User user = new User(signupRequest.getName(), signupRequest.getEmail(),
				passwordEncoder.encode(signupRequest.getPassword()), signupRequest.getPhone(),
				signupRequest.getAddress(), signupRequest.getGender(), signupRequest.getStatus(),
				signupRequest.getImage(), signupRequest.getRegisterDate(),
				jwtUtils.doGenerateToken(signupRequest.getEmail()));
		Set<AppRole> roles = new HashSet<>();
		roles.add(new AppRole(1, null));

		user.setRoles(roles);
		userRepository.save(user);
		Cart c = new Cart(0L, 0.0, user.getAddress(), user.getPhone(), user);
		cartRepository.save(c);
		return ResponseEntity.ok(new MessageResponse("Đăng kí thành công"));
//		return userService.registerUser(signupRequest);

	}

	@GetMapping("/logout")
	public ResponseEntity<Void> logout() {
		return ResponseEntity.ok().build();
//		return userService.logout();
	}

	@PostMapping("send-mail-forgot-password-token")
	public ResponseEntity<String> sendToken(@RequestBody String email) {

		if (!userRepository.existsByEmail(email)) {
			return ResponseEntity.notFound().build();
		}
		User user = userRepository.findByEmail(email).get();
		String token = user.getToken();
		sendMaiToken(email, token, "Reset mật khẩu");
		return ResponseEntity.ok().build();
//		if (!userRepository.existsByEmail(email)) {
//	        return ResponseEntity.notFound().build();
//	    }
//	    User user = userRepository.findByEmail(email).get();
//	    String token = user.getToken();
//	    userService.sendToken(email, token);
//	    return ResponseEntity.ok().build();

	}

	public void sendMaiToken(String email, String token, String title) {
		String body = "\r\n" + "    <h2>Hãy nhấp vào link để thay đổi mật khẩu của bạn</h2>\r\n"
				+ "    <a href=\"http://localhost:8080/forgot-password/" + token + "\">Đổi mật khẩu</a>";
		sendMailService.queue(email, title, body);
	}

}
