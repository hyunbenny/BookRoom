package com.hyunbenny.bookroom.repository;

import com.hyunbenny.bookroom.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
}
