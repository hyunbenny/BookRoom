package com.hyunbenny.bookroom.auth;

import com.hyunbenny.bookroom.dto.UserAccountDto;
import com.hyunbenny.bookroom.entity.UserAccount;
import com.hyunbenny.bookroom.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("loadUserByUsername - userId: {}", userId);
//        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(() -> new UserNotFoundException()); // failureHandler에서 InternalAuthenticationServiceException으로 잡힌다
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("해당 회원이 존재하지 않습니다."));


        return new CustomUserDetails(UserAccountDto.from(userAccount));
    }
}
