package com.hyunbenny.bookroom.repository;

import com.hyunbenny.bookroom.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {

    @Query(value = "select * from user_account where user_id = :userId", nativeQuery = true)
    Optional<UserAccount> findByIdWithoutDeletedAtCondition(@Param("userId") String userId);
}
