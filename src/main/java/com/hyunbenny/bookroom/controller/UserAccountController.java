package com.hyunbenny.bookroom.controller;

import com.hyunbenny.bookroom.dto.UserAccountDto;
import com.hyunbenny.bookroom.dto.request.JoinRequest;
import com.hyunbenny.bookroom.exception.UserAlreadyExistException;
import com.hyunbenny.bookroom.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    @GetMapping("/join")
    public String joinForm(JoinRequest joinRequest, Model model) {
        model.addAttribute("joinRequest", joinRequest);
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
            } catch (UserAlreadyExistException ex) {
                model.addAttribute("joinRequest", joinRequest);
                return "joinForm";
            }
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "ex", required = false) String exception,
                            Model model) {
        log.info("error : {}, exception : {}", error, exception);

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "loginForm";
    }

}
