package com.hyunbenny.bookroom.repository;

import com.hyunbenny.bookroom.entity.UserAccount;
import com.hyunbenny.bookroom.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserAccountRepositoryTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    private UserAccountRepository userAccountRepository;

    @DisplayName("UserAccount 저장")
    @Test
    void insert() {
        UserAccount userAccountEntity = createUserAccountEntity();
        UserAccount savedUserAccount = userAccountRepository.save(userAccountEntity);
        System.out.println(savedUserAccount);
    }

    @DisplayName("UserAccount 조회")
    @Test
    void getById() {
        UserAccount userAccountEntity = createUserAccountEntity();
        userAccountRepository.save(userAccountEntity);

        UserAccount findUserAccount = userAccountRepository.findById("testUserId").get();
        System.out.println(findUserAccount);
    }

    @DisplayName("UserAccount 조회 - 탈퇴회원(deleted_at이 null이 아닌 회원)은 조회되지 않는다.")
    @Test
    void getByIdWithDeletedUser() {
        UserAccount userAccountEntity = createUserAccountEntity();
        UserAccount savedUser = userAccountRepository.save(userAccountEntity);
        savedUser.deleteUser();

        em.flush();
        em.clear();

        assertThat(userAccountRepository.findById("testUserId").isEmpty()).isTrue();
    }


    private UserAccount createUserAccountEntity() {
        return UserAccount.of("testUserId", "testUsername", "testUserNickname", "encodedPassword", "testUser@email.com", "01012345678");
    }

    private UserAccount createDeletedUserAccountEntity() {
        UserAccount userAccount = UserAccount.of("testUserId", "testUsername", "testUserNickname", "encodedPassword", "testUser@email.com", "01012345678");
        userAccount.deleteUser();
        return userAccount;
    }

}