package com.hyunbenny.bookroom.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record JoinRequest(
        @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$", message = "아이디는 5~20자 이내의 영어와 숫자만 가능합니다.")
        String userId,
        @Pattern(regexp = "^[가-힣]{2,6}", message = "이름은 2~6자 이내의 한글만 가능합니다.")
        String name,
        @Pattern(regexp = "^[a-zA-Z가-힣0-9]{2,20}$", message = "닉네임은 2~20자 이내의 한글, 영어와 숫자만 가능합니다.")
        String nickname,
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "비밀번호는 최소 8자, 한개의 소문자와 한개의 대문자, 한개의 숫자와 한개의 특수 문자를 포함해야 합니다.")
        String password,
        @Email(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "이메일 형식과 맞지 않습니다.")
        String email,
        @Pattern(regexp = "^01[0179][0-9]{7,8}$", message = "핸드폰 번호의 양식과 맞지 않습니다.")
        String phone
) {
    public static JoinRequest of(String userId, String name, String nickname, String password, String email, String phone) {
        return new JoinRequest(userId, name, nickname, password, email, phone);
    }

    @Override
    public String toString() {
        return "JoinRequest{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
