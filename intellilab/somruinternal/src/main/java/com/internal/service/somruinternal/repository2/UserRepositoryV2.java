package com.internal.service.somruinternal.repository2;

import com.internal.service.somruinternal.model2.UserV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepositoryV2 extends JpaRepository<UserV2, Long> {

    @Query(value = "SELECT user FROM UserV2 user ORDER BY user.userId DESC")
    List<UserV2> getAllUsers();

    @Query(value = "SELECT user FROM UserV2 user " + "WHERE user.name LIKE %:key% " + "OR user.email LIKE %:key% "
            + "ORDER BY user.userId DESC")
    List<UserV2> searchByNameOrEmail(@Param(value = "key") String key);

    public List<UserV2> findAllByUserIdIn(long[] userId);

    Optional<UserV2> findByEmail(String email);

    UserV2 findByUserId(long userId);
}
