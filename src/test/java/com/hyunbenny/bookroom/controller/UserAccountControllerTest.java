package com.hyunbenny.bookroom.controller;

import com.hyunbenny.bookroom.auth.CustomLoginFailureHandler;
import com.hyunbenny.bookroom.config.SecurityConfig;
import com.hyunbenny.bookroom.dto.UserAccountDto;
import com.hyunbenny.bookroom.dto.request.JoinRequest;
import com.hyunbenny.bookroom.exception.UserAlreadyExistException;
import com.hyunbenny.bookroom.service.UserAccountService;
import com.hyunbenny.bookroom.util.FormDataEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Import({SecurityConfig.class, FormDataEncoder.class, CustomLoginFailureHandler.class})
@WebMvcTest(UserAccountController.class)
class UserAccountControllerTest {

    private final MockMvc mvc;
    private final FormDataEncoder formDataEncoder;

    @MockBean
    private UserAccountService userAccountService;

    public UserAccountControllerTest(@Autowired MockMvc mvc, @Autowired FormDataEncoder formDataEncoder) {
        this.mvc = mvc;
        this.formDataEncoder = formDataEncoder;
    }

    @DisplayName("[GET] join: 회원가입 페이지를 호춣한다.")
    @Test
    void when_requestJoinWithHttpMethodGet_then_returnJoinPage() throws Exception {
        // given

        // when & then
        mvc.perform(get("/join"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("joinForm"))
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("[POST] join: 회원가입 요청을 정상 처리 후 로그인 페이지로 이동한다.")
    @Test
    void given_JoinRequest_when_requestJoin_then_saveUserAccountAndReturnLoginPage() throws Exception {
        // given
        JoinRequest joinRequest = createJoinRequest();

        // when & then
        mvc.perform(post("/join")
                        .content(formDataEncoder.encode(joinRequest))
                )
                .andExpect(view().name("message"))
                .andExpect(model().attribute("message", "성공적으로 회원가입하였습니다."))
                .andExpect(model().attribute("url", "/login"));
    }

    @DisplayName("[POST] join: 회원가입 요청시, 이미 존재하는 아이디로 요청하면 다시 회원가입 페이지로 이동한다.")
    @Test
    void given_JoinRequestWithExistUserId_when_requestJoin_then_returnException() throws Exception {
        // given
        JoinRequest joinRequest = createJoinRequest();
        doThrow(new UserAlreadyExistException())
                .when(userAccountService).join(any(UserAccountDto.class));

        // when & then
        mvc.perform(post("/join")
                        .content(formDataEncoder.encode(joinRequest))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("joinForm"));
    }

    private JoinRequest createJoinRequest() {
        return JoinRequest.of("testUserId", "testUserName", "testUserNickname", "password", "testUser@email.com", "01012345678");
    }
}