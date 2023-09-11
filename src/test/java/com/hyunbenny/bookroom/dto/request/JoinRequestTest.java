package com.hyunbenny.bookroom.dto.request;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JoinRequestTest {

    private static Validator validator;
    private static final String idMessage = "아이디는 5~20자 이내의 영어와 숫자만 가능합니다.";
    private static final String nameMessage = "이름은 2~6자 이내의 한글만 가능합니다.";
    private static final String nicknameMessage = "닉네임은 2~20자 이내의 한글, 영어와 숫자만 가능합니다.";
    private static final String passwordMessage = "비밀번호는 최소 8자, 한개의 소문자와 한개의 대문자, 한개의 숫자와 한개의 특수 문자를 포함해야 합니다.";
    private static final String emailMessage = "이메일 형식과 맞지 않습니다.";
    private static final String phoneMessage = "핸드폰 번호의 양식과 맞지 않습니다.";


    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * userId
     */

    @DisplayName("userId 정상 테스트")
    @Test
    void when_userIdIsOK() {
        // given
        String userId = "testUser90";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(0);
    }

    @DisplayName("userId가 경계값 테스트: 5글자는 통과해야 한다.")
    @Test
    void when_userIdeq5() {
        // given
        String userId = "abcde";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(0);
    }

    @DisplayName("userId가 경계값 테스트: 20글자는 통과해야 한다.")
    @Test
    void when_userIdeq20() {
        // given
        String userId = "1234567890123456789";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(0);
    }

    @DisplayName("userId가 \"\"인 경우 에러메세지를 출력한다.")
    @Test
    void when_userIdIsNull() {
        // given
        String userId = "";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 공백인 경우 에러메세지를 출력한다.")
    @Test
    void when_userIdIsEmpty() {
        // given
        String userId = " ";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId에 영어와 숫자가 아닌 문자가 들어가는 경우 에러메세지를 출력한다.")
    @Test
    void when_userIdHasToContainEnglishAndNumber() {
        // given
        String userId = "테스트유저아이디";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 5글자보다 작은 경우 에러메세지를 출력한다.")
    @Test
    void when_userIdlt5() {
        // given
        String userId = "abcd";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 20글자보다 큰 경우 에러메세지를 출력한다.")
    @Test
    void when_userIdgt20() {
        // given
        String userId = "abcdefghijklmnopqrstuvwxyz";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.(!)")
    @Test
    void when_userIdContainsSpecialCharacters1() {
        // given
        String userId = "helloWorld!";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.(@)")
    @Test
    void when_userIdContainsSpecialCharacters2() {
        // given
        String userId = "helloWorld@";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.(#)")
    @Test
    void when_userIdContainsSpecialCharacters3() {
        // given
        String userId = "helloWorld#";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.($)")
    @Test
    void when_userIdContainsSpecialCharacters4() {
        // given
        String userId = "helloWorld$";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.(%)")
    @Test
    void when_userIdContainsSpecialCharacters5() {
        // given
        String userId = "helloWorld%";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.(^)")
    @Test
    void when_userIdContainsSpecialCharacters6() {
        // given
        String userId = "helloWorld^";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.(&)")
    @Test
    void when_userIdContainsSpecialCharacters7() {
        // given
        String userId = "helloWorld&";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.(*)")
    @Test
    void when_userIdContainsSpecialCharacters8() {
        // given
        String userId = "helloWorld*";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.('(')")
    @Test
    void when_userIdContainsSpecialCharacters9() {
        // given
        String userId = "helloWorld(";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.(')')")
    @Test
    void when_userIdContainsSpecialCharacters10() {
        // given
        String userId = "helloWorld)";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.(-)")
    @Test
    void when_userIdContainsSpecialCharacters11() {
        // given
        String userId = "helloWorld-";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.(_)")
    @Test
    void when_userIdContainsSpecialCharacters12() {
        // given
        String userId = "helloWorld_";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.(+)")
    @Test
    void when_userIdContainsSpecialCharacters13() {
        // given
        String userId = "helloWorld+";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.(=)")
    @Test
    void when_userIdContainsSpecialCharacters14() {
        // given
        String userId = "helloWorld=";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.(<)")
    @Test
    void when_userIdContainsSpecialCharacters15() {
        // given
        String userId = "helloWorld<";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.(>)")
    @Test
    void when_userIdContainsSpecialCharacters16() {
        // given
        String userId = "helloWorld>";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.(?)")
    @Test
    void when_userIdContainsSpecialCharacters17() {
        // given
        String userId = "helloWorld?";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.(,)")
    @Test
    void when_userIdContainsSpecialCharacters18() {
        // given
        String userId = "helloWorld,";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.(.)")
    @Test
    void when_userIdContainsSpecialCharacters19() {
        // given
        String userId = "helloWorld.";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 특수문자가 포함된 경우 에러메세지를 출력한다.(/)")
    @Test
    void when_userIdContainsSpecialCharacters20() {
        // given
        String userId = "helloWorld/";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    @DisplayName("userId가 공백이 포함된 경우 에러메세지를 출력한다.")
    @Test
    void when_userIdContainsBlank() {
        // given
        String userId = "hello World";
        JoinRequest joinRequest = JoinRequest.of(
                userId,
                "김테스터",
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
            System.out.println(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(idMessage);
    }

    /**
     * name
     */
    @DisplayName("name 정상 테스트 - 한글")
    @Test
    void when_nameIsOK_KO() {
        // given
        String name = "테스터";
        JoinRequest joinRequest = JoinRequest.of(
                "userId",
                name,
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(0);
    }

    @DisplayName("name에 한글이 아닌 다른 문자가 포함되면 에러 메시지를 반환한다.")
    @Test
    void when_nameContainsNotKorean() {
        // given
        String name = "testname";
        JoinRequest joinRequest = JoinRequest.of(
                "userId",
                name,
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(nameMessage);
    }

    @DisplayName("userId가 경계값 테스트: 2글자는 통과해야 한다.")
    @Test
    void when_nameeq2() {
        // given
        String name = "김밥";
        JoinRequest joinRequest = JoinRequest.of(
                "userId",
                name,
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(0);
    }

    @DisplayName("userId가 경계값 테스트: 6글자는 통과해야 한다.")
    @Test
    void when_nameeq20() {
        // given
        String name = "김테스터임다";
        JoinRequest joinRequest = JoinRequest.of(
                "userId",
                name,
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(0);
    }

    @DisplayName("name이 2글자보다 작으면 에러 메시지를 반환한다.")
    @Test
    void when_nameLt2() {
        // given
        String name = "김";
        JoinRequest joinRequest = JoinRequest.of(
                "userId",
                name,
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(nameMessage);
    }

    @DisplayName("name이 6글자보다 크면 에러 메시지를 반환한다.")
    @Test
    void when_nameGt6() {
        // given
        String name = "김테스터입니다";
        JoinRequest joinRequest = JoinRequest.of(
                "userId",
                name,
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(nameMessage);
    }

    @DisplayName("name에 숫자가 포함되면 에러 메시지를 반환한다.")
    @Test
    void when_nameContainsNumber() {
        // given
        String name = "테스터1";
        JoinRequest joinRequest = JoinRequest.of(
                "userId",
                name,
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(nameMessage);
    }

    @DisplayName("name에 공백이 포함되면 에러 메시지를 반환한다.")
    @Test
    void when_nameContainsBlank() {
        // given
        String name = "김 테스터";
        JoinRequest joinRequest = JoinRequest.of(
                "userId",
                name,
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(nameMessage);
    }

    @DisplayName("name에 특수문자가 포함되면 에러 메시지를 반환한다.")
    @Test
    void when_nameContainsSpecialCharacters() {
        // given
        String name = "김테스터!@#$%";
        JoinRequest joinRequest = JoinRequest.of(
                "userId",
                name,
                "nickname",
                "Password12!@!@",
                "test@email.com",
                "01012345678"
        );

        // when
        Set<ConstraintViolation<JoinRequest>> violations = validator.validate(joinRequest);

        // then
        Iterator<ConstraintViolation<JoinRequest>> iterator = violations.iterator();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<JoinRequest> next = iterator.next();
            messages.add(next.getMessage());
        }

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0)).isEqualTo(nameMessage);
    }

}