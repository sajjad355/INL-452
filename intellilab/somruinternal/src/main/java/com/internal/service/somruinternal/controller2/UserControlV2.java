package com.internal.service.somruinternal.controller2;

import com.internal.service.somruinternal.model2.UserRoleV2;
import com.internal.service.somruinternal.model2.UserV2;
import com.internal.service.somruinternal.repository2.UserRepositoryV2;
import com.internal.service.somruinternal.repository2.UserRoleRepositoryV2;
import com.internal.service.somruinternal.error.EntityNotFoundException;
import com.internal.service.somruinternal.model2.ClientCompanyV2;
import com.internal.service.somruinternal.model2.OperationV2;
import com.internal.service.somruinternal.utils.SearchUtil;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/userV2")
public class UserControlV2 extends ParentControl {

	private final static Logger LOGGER = LoggerFactory.getLogger(UserControlV2.class);

	final UserRepositoryV2 userRepository;
	final UserRoleRepositoryV2 userRoleRepository;

	@Autowired
	public UserControlV2(UserRepositoryV2 userRepository, UserRoleRepositoryV2 userRoleRepository) {
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
	}

	@GetMapping("/all")
	public List<UserV2> getAllUsers() {
		LOGGER.info("/userV2 received GET /all mapped to getAllUsers");
		List<UserV2> allUsers = userRepository.findAll();
		for (UserV2 user : allUsers) {
			Hibernate.initialize(user.getUserRoles());
		}
		return allUsers;
	}

	@GetMapping("/search/{key}")
	public List<UserV2> searchByNameOrEmail(@PathVariable(value = "key") String key) {
		LOGGER.info(String.format("/userV2 received GET /search/{key} mapped to searchByNameOrEmail with key %s", key));
		return userRepository.searchByNameOrEmail(SearchUtil.replaceIllegalSearchCharacters(key));
	}

	@GetMapping("/count")
	public int count() {
		LOGGER.info("/userV2 received GET /count mapped to count");
		return userRepository.findAll().size();
	}

	@PostMapping("/save")
	public UserV2 save(@Valid @RequestBody UserV2 user) {
		LOGGER.info(String.format("/save received request with user %s", user));
		// check if update scenario and get password and set it in object so as not to
		// overirde
		String userPassword = null;
		String action;
		String auditTrailRecord;
		if (user.getUserId() > 0) {
			UserV2 userLookup = userRepository.findByUserId(user.getUserId());
			if (userLookup == null) {
				throw new EntityNotFoundException(UserV2.class, "id", String.valueOf(user.getUserId()));
			}
			userPassword = userLookup.getPassword();
			if (userPassword != null)
				user.setPassword(userPassword);
			action = "Updated user";
			auditTrailRecord = getDiffs(user, userLookup);

		} else {
			action = "insert user";
			auditTrailRecord = user.toString();
		}
		UserV2 savedUser = userRepository.save(user);
		super.saveUserHistory(savedUser.getEditedBy(), "User", action, auditTrailRecord);
		return savedUser;
	}

	private String getDiffs(UserV2 lhs, UserV2 rhs) {
		if ((lhs == null) || (rhs == null))
			throw new RuntimeException("Error: Trying to compare two User objects and at least one is null");
		String diffs = lhs.diffCompare(rhs);
		LOGGER.info(String.format("UserV2 GET DIFFS RESULT: %s", diffs));
		return diffs;

	}

	@PostMapping("/resetPassword")
	public UserV2 resetPassword(@Valid @RequestBody UserV2 user) {
		LOGGER.info(String.format("/resetPassword received request with user %s", user));
		// check if update scenario and get password and set it in object so as not to
		// overirde
		String requestedPassword = user.getPassword();
		String action = "Reset Password";
		UserV2 userLookup = userRepository.findByUserId(user.getUserId());
		if (userLookup == null) {
			throw new EntityNotFoundException(UserV2.class, "id", String.valueOf(user.getUserId()));
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(requestedPassword);
		userLookup.setPassword(encodedPassword);
		UserV2 savedUser = userRepository.save(userLookup);
		super.saveUserHistory(savedUser.getEditedBy(), "User", action, savedUser.getName());
		return savedUser;
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<UserV2> get(@PathVariable(value = "id") Long userId) throws EntityNotFoundException {
		LOGGER.info(String.format("get received request with userId %d", userId));
		if (userId == null)
			throw new EntityNotFoundException(UserV2.class, "id", "");
		UserV2 user = userRepository.findByUserId(userId);
		if (user == null) {
			LOGGER.info("user not found");
			throw new EntityNotFoundException(UserV2.class, "id", userId.toString());
		}
		Set<UserRoleV2> setOfRoles = user.getUserRoles(); // force roles to load
		LOGGER.info(String.format("Retrieve %d roles: ", setOfRoles.size()));
		// force each roles operations to load
		setOfRoles.forEach((role) -> {
			Set<OperationV2> setOfOperations = role.getOperations();
			LOGGER.info(String.format("Retrieve %d linked operations for role", setOfOperations.size()));
		});
		return ResponseEntity.ok().body(user);
	}
}
