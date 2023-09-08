package com.hyunbenny.bookroom.service;

import com.hyunbenny.bookroom.dto.UserAccountDto;
import com.hyunbenny.bookroom.entity.UserAccount;
import com.hyunbenny.bookroom.exception.InvalidPasswordException;
import com.hyunbenny.bookroom.exception.UserAlreadyExistException;
import com.hyunbenny.bookroom.exception.UserNotFoundException;
import com.hyunbenny.bookroom.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(UserAccountDto userAccountDto) throws UserAlreadyExistException{
        userAccountRepository.findByIdWithoutDeletedAtCondition(userAccountDto.userId()).ifPresent(e -> {
            throw new UserAlreadyExistException();
        });

        userAccountRepository.save(userAccountDto.toEntity(passwordEncoder.encode(userAccountDto.password())));
    }

    @Transactional(readOnly = true)
    public UserAccountDto getUserInfo(String userId) {
        UserAccount findUserAccount = userAccountRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

        return UserAccountDto.from(findUserAccount);
    }

    @Transactional
    public UserAccountDto modifyUserInfo(UserAccountDto userAccountDto) {
        UserAccount userAccount = userAccountRepository.findById(userAccountDto.userId()).orElseThrow(() -> new UserNotFoundException());
        userAccount.modifyUserInfo(userAccountDto);

        return UserAccountDto.from(userAccount);
    }

    @Transactional
    public void modifyUserPassword(String userId, String oldPassword, String newPassword) {
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

        if (passwordIsMatched(oldPassword, userAccount.getPassword())) {
            userAccount.modifyPassword(passwordEncoder.encode(newPassword));
        }else{
            throw new InvalidPasswordException();
        }
    }

    @Transactional
    public void deleteUserAccount(String userId, String password) {
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());
        if (passwordIsMatched(password, userAccount.getPassword())) {
            userAccount.deleteUser();
        } else {
            throw new InvalidPasswordException();
        }
    }

    private boolean passwordIsMatched(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
