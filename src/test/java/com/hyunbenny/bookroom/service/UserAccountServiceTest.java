package com.hyunbenny.bookroom.service;

import com.hyunbenny.bookroom.dto.UserAccountDto;
import com.hyunbenny.bookroom.entity.UserAccount;
import com.hyunbenny.bookroom.exception.InvalidPasswordException;
import com.hyunbenny.bookroom.exception.UserAlreadyExistException;
import com.hyunbenny.bookroom.exception.UserNotFoundException;
import com.hyunbenny.bookroom.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@DisplayName("회원 비지니스 로직")
@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {

    @InjectMocks
    private UserAccountService userAccountService;

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원가입 성공")
    @Test
    void given_userAccountDto_when_requestJoin_then_saveUserAccountEntity() {
        // given
        UserAccountDto userAccountDto = createUserAccountDto();
        UserAccount userAccountEntity = createUserAccountEntity();
        // when
        when(userAccountRepository.findById("testUserId")).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(userAccountRepository.save(any())).thenReturn(userAccountEntity);


        // then
        assertDoesNotThrow(() -> userAccountService.join(userAccountDto));
        System.out.println(userAccountEntity);
    }

    @DisplayName("회원가입 실패: 가입을 시도하는 회원의 아이디가 이미 존재하면 예외를 반환한다.")
    @Test
    void given_alreadyExistUserId_when_requestJoin_then_returnException() {
        // given
        UserAccountDto userAccountDto = createUserAccountDto();
        given(userAccountRepository.findById(any())).willReturn(Optional.of(createUserAccountEntity()));

        // when & then
        UserAlreadyExistException exception = assertThrows(UserAlreadyExistException.class, () -> userAccountService.join(userAccountDto));
        assertThat(exception.getMessage()).isEqualTo("해당 회원이 이미 존재합니다.");
    }

    @DisplayName("회원정보 조회 성공")
    @Test
    void given_userId_when_requestUserInfo_then_returnUserAccountDto() {
        // given
        String userId = "testUserId";
        given(userAccountRepository.findById(userId)).willReturn(Optional.of(createUserAccountEntity()));

        // when
        UserAccountDto user = userAccountService.getUserInfo(userId);

        // then
        assertThat(user.userId()).isEqualTo(userId);
        assertThat(user.name()).isEqualTo("testUsername");
        assertThat(user.nickname()).isEqualTo("testUserNickname");
    }

    @DisplayName("회원정보 조회 실패: 요청한 회원이 존재하지 않으면 예외를 반환한다")
    @Test
    void given_NotExistUserId_when_requestUserInfo_then_returnException() {
        // given
        String userId = "testUserId";
        given(userAccountRepository.findById(any())).willReturn(Optional.empty());

        // when, then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userAccountService.getUserInfo(userId));
        assertThat(exception.getMessage()).isEqualTo("해당 회원이 존재하지 않습니다.");
    }

    @DisplayName("회원 정보 수정 성공")
    @Test
    void given_UserAccountDtoFromUserAccountModifyRequest_when_requestModifyUserAccountInfo_then_modifyUserAccountInfo() {
        // given
        UserAccountDto modifyUserAccountDto = UserAccountDto.of(
                "testUserId",
                "modifiedUsername",
                "modifiedNickname",
                null,
                "modified@email.com",
                "01098765432"
        );
        given(userAccountRepository.findById(any())).willReturn(Optional.of(createUserAccountEntity()));

        // when
        UserAccountDto modifiedUserAccount = userAccountService.modifyUserInfo(modifyUserAccountDto);

        // then
        assertThat(modifiedUserAccount.userId()).isEqualTo(modifyUserAccountDto.userId());
        assertThat(modifiedUserAccount.name()).isEqualTo(modifyUserAccountDto.name());
        assertThat(modifiedUserAccount.email()).isEqualTo(modifyUserAccountDto.email());
        assertThat(modifiedUserAccount.phone()).isEqualTo(modifyUserAccountDto.phone());
        assertNotNull(modifiedUserAccount.modifiedAt());

        System.out.println(modifiedUserAccount);
    }

    @DisplayName("회원 정보 수정 실패: 존재하지 않는 회원정보로 수정 요청을 하면 예외를 반환한다.")
    @Test
    void given_NotExistUserId_when_requestModifyUserAccountInfo_then_returnException() {
        // given
        given(userAccountRepository.findById(any())).willReturn(Optional.empty());

        // when
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userAccountService.modifyUserInfo(createUserAccountDto()));
        assertThat(exception.getMessage()).isEqualTo("해당 회원이 존재하지 않습니다.");
    }

    @DisplayName("회원 비밀번호 변경 성공")
    @Test
    void given_passwords_when_requestModifyPassword_then_modifyPassword() {
        // given
        String userId = "testUserId";
        String oldPassword = "password";
        String newPassword = "newPassword";

        UserAccount userAccountEntity = createUserAccountEntity();

        given(userAccountRepository.findById(any())).willReturn(Optional.of(userAccountEntity));
        given(passwordEncoder.matches(any(), any())).willReturn(true);
        given(passwordEncoder.encode(newPassword)).willReturn("encodedNewPassword");

        // when, then
        assertDoesNotThrow(() -> userAccountService.modifyUserPassword(userId, oldPassword, newPassword));
        assertThat(userAccountEntity.getPassword()).isEqualTo("encodedNewPassword");
    }

    @DisplayName("회원 비밀번호 변경 실패: 존재하지 않는 회원정보로 수정 요청을 하면 예외를 반환한다")
    @Test
    void given_NotExistUserId_when_requestModifyPassword_then_returnException() {
        // given
        String userId = "testUserId";
        String oldPassword = "password";
        String newPassword = "newPassword";
        given(userAccountRepository.findById(any())).willReturn(Optional.empty());

        // when, then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userAccountService.modifyUserPassword(userId, oldPassword, newPassword));
        assertThat(exception.getMessage()).isEqualTo("해당 회원이 존재하지 않습니다.");
    }

    @DisplayName("회원 비밀번호 변경 실패: 기존 비밀번호가 일치하지 않는 경우 예외를 반환한다.")
    @Test
    void given_wrongOldPassword_when_requestModifyPassword_then_returnException() {
        // given
        String userId = "testUserId";
        String oldPassword = "password";
        String newPassword = "newPassword";

        given(userAccountRepository.findById(any())).willReturn(Optional.of(createUserAccountEntity()));
        given(passwordEncoder.matches(any(), any())).willReturn(false);

        // when, then
        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class, () -> userAccountService.modifyUserPassword(userId, oldPassword, newPassword));
        assertThat(exception.getMessage()).isEqualTo("비밀번호가 올바르지 않습니다.");
    }

    @DisplayName("회원 탈퇴 성공")
    @Test
    void given_userId_when_requestDeleteUser_then_modifyDeletedAtOfUserAccount() {
        // given
        String userId = "testUserId";
        String password = "password";
        UserAccount userAccountEntity = createUserAccountEntity();
        given(userAccountRepository.findById(any())).willReturn(Optional.of(userAccountEntity));
        given(passwordEncoder.matches(any(), any())).willReturn(true);

        // when then
        assertDoesNotThrow(() -> userAccountService.deleteUserAccount(userId, password));
        assertNotNull(userAccountEntity.getDeletedAt());
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of("testUserId", "testUsername", "testUserNickname", "password", "testUser@eamil.com", "01012345678");
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
