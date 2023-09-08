package com.hyunbenny.bookroom.controller;

import com.hyunbenny.bookroom.auth.CustomUserDetails;
import com.hyunbenny.bookroom.dto.UserAccountDto;
import com.hyunbenny.bookroom.dto.request.JoinRequest;
import com.hyunbenny.bookroom.dto.request.UserDeleteRequest;
import com.hyunbenny.bookroom.dto.request.UserInfoModifyRequest;
import com.hyunbenny.bookroom.dto.request.UserPasswordModifyRequest;
import com.hyunbenny.bookroom.dto.response.UserInfoResponse;
import com.hyunbenny.bookroom.exception.InvalidPasswordException;
import com.hyunbenny.bookroom.exception.UserAlreadyExistException;
import com.hyunbenny.bookroom.exception.UserNotFoundException;
import com.hyunbenny.bookroom.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    @GetMapping("/join")
    public String joinForm(JoinRequest joinRequest, Model model) {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(@Validated JoinRequest joinRequest, BindingResult bindingResult, Model model) {
        log.info(joinRequest.toString());

        if (bindingResult.hasErrors()) {
            log.info("bindingResult : {}", bindingResult.getAllErrors());
            model.addAttribute("joinRequest", joinRequest);
            return "joinForm";
        } else {
            try {
                userAccountService.join(UserAccountDto.of(joinRequest.userId(), joinRequest.name(), joinRequest.nickname(), joinRequest.password(), joinRequest.email(), joinRequest.phone()));
                model.addAttribute("message", "성공적으로 회원가입하였습니다.");
                model.addAttribute("url", "/login");
                return "message";
            } catch (UserAlreadyExistException ex) {
                bindingResult.reject("userId", "해당 아이디가 이미 존재합니다.");
                model.addAttribute("joinRequest", joinRequest);
                return "joinForm";
            }
        }
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "ex", required = false) String exception,
                            Model model) {
        log.info("error : {}, exception : {}", error, exception);

        if (error != null && error.equals("true")) {
            model.addAttribute("message", "아이디 혹은 비밀번호가 올바르지 않습니다.");
            model.addAttribute("url", "redirect:/login");
            return "message";
        }

        return "loginForm";
    }

    @GetMapping("/user/info")
    public String modifyForm(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {

        try {
            UserAccountDto userInfo = userAccountService.getUserInfo(customUserDetails.getUsername());
            model.addAttribute("userInfo", UserInfoResponse.of(userInfo));
        } catch (UserNotFoundException ex) {
            model.addAttribute("message", "잘못된 요청입니다. 해당 회원이 존재하지 않습니다.");
            model.addAttribute("url", "redirect:/");
            return "message";
        }

        return "modifyForm";
    }

    @PostMapping("/user/info")
    public String modify(@Validated UserInfoModifyRequest request,
                         @AuthenticationPrincipal CustomUserDetails customUserDetails,
                         Model model) {
        if (!request.userId().equals(customUserDetails.getUsername())) {
            model.addAttribute("message", "잘못된 요청입니다.");
            model.addAttribute("url", "redirect:/logout");
        }else{
            try {
                UserAccountDto userAccountDto = UserAccountDto.of(request.userId(), request.name(), request.nickname(), null, request.email(), request.phone());
                userAccountService.modifyUserInfo(userAccountDto);
                model.addAttribute("message", "성공적으로 수정하였습니다.");
                model.addAttribute("url", "/user/info");
            } catch (UserNotFoundException ex) {
                model.addAttribute("message", "잘못된 요청입니다. 해당 회원이 존재하지 않습니다.");
                model.addAttribute("url", "redirect:/logout");
            }
        }

        return "message";
    }

    @GetMapping("/user/password")
    public String modifyPasswordForm(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {

        try {
            UserAccountDto userInfo = userAccountService.getUserInfo(customUserDetails.getUsername());
            model.addAttribute("userId", userInfo.userId());
        } catch (UserNotFoundException e) {
            model.addAttribute("message", "잘못된 요청입니다. 해당 회원이 존재하지 않습니다.");
            model.addAttribute("url", "redirect:/logout");
            return "message";
        }

        return "userPasswordModifyForm";
    }

    @PostMapping("/user/password")
    public String modifyPassword(UserPasswordModifyRequest request,
                             @AuthenticationPrincipal CustomUserDetails customUserDetails,
                             Model model) {

        if (!request.userId().equals(customUserDetails.getUsername())) {
            model.addAttribute("message", "잘못된 요청입니다.");
        } else {
            try {
                userAccountService.modifyUserPassword(customUserDetails.getUsername(), request.password(), request.newPassword());
                model.addAttribute("message", "비밀번호를 성공적으로 변경하였습니다. 다시 로그인해주세요.");
                model.addAttribute("url", "redirect:/logout");
            } catch (UserNotFoundException ex) {
                model.addAttribute("message", "잘못된 요청입니다. 해당 회원이 존재하지 않습니다.");
                model.addAttribute("url", "redirect:/logout");
            } catch (InvalidPasswordException ipe) {
                model.addAttribute("message", "잘못된 요청입니다. 비밀번호가 올바르지 않습니다.");
                model.addAttribute("url", "/user/password");
            }
        }
        return "message";
    }

    @GetMapping("/user/delete")
    public String deleteForm(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {

        try {
            UserAccountDto userInfo = userAccountService.getUserInfo(customUserDetails.getUsername());
            log.info("userId: {}", userInfo.userId());
            model.addAttribute("userId", userInfo.userId());
            return "deleteForm";
        } catch (UserNotFoundException e) {
            model.addAttribute("message", "잘못된 요청입니다. 해당 회원이 존재하지 않습니다.");
            model.addAttribute("url", "redirect:/logout");
            return "message";
        }
    }

    @PostMapping("/user/delete")
    public String delete(UserDeleteRequest request,
                         @AuthenticationPrincipal CustomUserDetails customUserDetails,
                         Model model) {

        if (!request.userId().equals(customUserDetails.getUsername())) {
            model.addAttribute("message", "잘못된 요청입니다.");
            model.addAttribute("url", "redirect:/logout");
        } else {
            try {
                userAccountService.deleteUserAccount(request.userId(), request.password());
                model.addAttribute("message", "정상적으로 탈퇴되었습니다.");
                model.addAttribute("url", "redirect:/logout");
            } catch (UserNotFoundException e) {
                model.addAttribute("message", "잘못된 요청입니다. 해당 회원이 존재하지 않습니다.");
                model.addAttribute("url", "redirect:/logout");
            } catch (InvalidPasswordException ipe) {
                    model.addAttribute("message", "잘못된 요청입니다. 비밀번호가 올바르지 않습니다.");
                    model.addAttribute("url", "/user/delete");
                    return "message";
                }
        }
        return "message";
    }

}
