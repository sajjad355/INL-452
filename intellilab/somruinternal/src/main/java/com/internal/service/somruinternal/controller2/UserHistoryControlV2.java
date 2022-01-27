package com.internal.service.somruinternal.controller2;

import com.internal.service.somruinternal.model2.UserHistoryV2;
import com.internal.service.somruinternal.repository2.UserHistoryRepositoryV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

@RestController
@RequestMapping("/userhistoryv2")
public class UserHistoryControlV2 {

  @Autowired
  UserHistoryRepositoryV2 userHistoryRepo;

  private final static Logger LOGGER = LoggerFactory.getLogger(UserHistoryControlV2.class);

  /**
   * Get a paginated subset of all user histories between now and a given number of days ago
   * @param days A number of days to traverse
   * @param page A requested page of user histories
   * @return A list of UserHistoryV2 instances
   */
  @GetMapping("/all/{days}/{page}")
  public List<UserHistoryV2> getAllUserHistories(
    @PathVariable(value = "days") int days,
    @PathVariable(value = "page") int page
  ) {
	  PageRequest pageable = PageRequest.of(((page <= 1) ? 0 : page -1), 10, Sort.by("id").descending());
	  LOGGER.info("getAllUserHistories");
  //  return userHistoryRepo.loadAllUserHistory(days, new PageRequest(((page <= 1) ? 0 : page -1), 10));
    return userHistoryRepo.loadAllUserHistory(days, pageable);

  }

  /**
   * Get a paginated subset of a given user's history between now and a given number of days ago
   * @param user A user
   * @param days A number of days to traverse
   * @param page A requested page of user histories
   * @return A list of UserHistoryV2 instances
   */
  @GetMapping("/{user}/{days}/{page}")
  public List<UserHistoryV2> getSpecificUserHistory(
    @PathVariable(value = "user") String user,
    @PathVariable(value = "days") int days,
    @PathVariable(value = "page") int page
  ) {
	PageRequest pageable = PageRequest.of(((page <= 1) ? 0 : page -1) , 10, Sort.by("id").descending());
    LOGGER.info("getSpecificUserHistory");
    return userHistoryRepo.loadSpecificUserHistory(user, days,pageable);
  }
}
