package com.memo.backend.service.auth;

import com.memo.backend.domain.Authority.Authority;
import com.memo.backend.domain.Authority.AuthorityRepository;
import com.memo.backend.domain.Authority.MemberAuth;
import com.memo.backend.domain.jwt.RefreshToken;
import com.memo.backend.domain.jwt.RefreshTokenRepository;
import com.memo.backend.domain.member.Member;
import com.memo.backend.domain.member.MemberRepository;
import com.memo.backend.dto.jwt.TokenDTO;
import com.memo.backend.dto.jwt.TokenReqDTO;
import com.memo.backend.dto.login.LoginReqDTO;
import com.memo.backend.dto.member.MemberReqDTO;
import com.memo.backend.dto.member.MemberRespDTO;
import com.memo.backend.exceptionhandler.AuthorityExceptionType;
import com.memo.backend.exceptionhandler.BizException;
import com.memo.backend.exceptionhandler.JwtExceptionType;
import com.memo.backend.exceptionhandler.MemberExceptionType;
import com.memo.backend.jwt.CustomEmailPasswordAuthToken;
import com.memo.backend.jwt.TokenProvider;
import com.memo.backend.service.member.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomUserDetailsService customUserDetailsService;



    @Transactional
    public MemberRespDTO signup(MemberReqDTO memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new BizException(MemberExceptionType.DUPLICATE_USER);
        }

        // DB ?????? ROLE_USER??? ????????? ???????????? ????????????.
        Authority authority = authorityRepository
                .findByAuthorityName(MemberAuth.ROLE_USER).orElseThrow(()->new BizException(AuthorityExceptionType.NOT_FOUND_AUTHORITY));

        Set<Authority> set = new HashSet<>();
        set.add(authority);


        Member member = memberRequestDto.toMember(passwordEncoder,set);
        log.debug("member = {}",member);
        return MemberRespDTO.of(memberRepository.save(member));
    }

    @Transactional
    public TokenDTO login(LoginReqDTO loginReqDTO) {
        CustomEmailPasswordAuthToken customEmailPasswordAuthToken = new CustomEmailPasswordAuthToken(loginReqDTO.getEmail(),loginReqDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(customEmailPasswordAuthToken);
        String email = authenticate.getName();
        Member member = customUserDetailsService.getMember(email);

        String accessToken = tokenProvider.createAccessToken(email, member.getAuthorities());
        String refreshToken = tokenProvider.createRefreshToken(email, member.getAuthorities());

        //refresh Token ??????
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .key(email)
                        .value(refreshToken)
                        .build()
        );

        return tokenProvider.createTokenDTO(accessToken,refreshToken);

    }

    @Transactional
    public TokenDTO reissue(TokenReqDTO tokenRequestDto) {
        /*
        *  accessToken ??? JWT Filter ?????? ???????????? ???
        * */
        String originAccessToken = tokenRequestDto.getAccessToken();
        String originRefreshToken = tokenRequestDto.getRefreshToken();

        // refreshToken ??????
        int refreshTokenFlag = tokenProvider.validateToken(originRefreshToken);

        log.debug("refreshTokenFlag = {}", refreshTokenFlag);

        //refreshToken ???????????? ????????? ?????? ????????? ????????????.
        if (refreshTokenFlag == -1) {
            throw new BizException(JwtExceptionType.BAD_TOKEN); // ????????? ???????????? ??????
        } else if (refreshTokenFlag == 2) {
            throw new BizException(JwtExceptionType.REFRESH_TOKEN_EXPIRED); // ???????????? ?????? ??????
        }

        // 2. Access Token ?????? Member Email ????????????
        Authentication authentication = tokenProvider.getAuthentication(originAccessToken);

        log.debug("Authentication = {}",authentication);

        // 3. ??????????????? Member Email ??? ???????????? Refresh Token ??? ?????????
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new BizException(MemberExceptionType.LOGOUT_MEMBER)); // ?????? ????????? ?????????


        // 4. Refresh Token ??????????????? ??????
        if (!refreshToken.getValue().equals(originRefreshToken)) {
            throw new BizException(JwtExceptionType.BAD_TOKEN); // ????????? ???????????? ????????????.
        }

        // 5. ????????? ?????? ??????
        String email = tokenProvider.getMemberEmailByToken(originAccessToken);
        Member member = customUserDetailsService.getMember(email);

        String newAccessToken = tokenProvider.createAccessToken(email, member.getAuthorities());
        String newRefreshToken = tokenProvider.createRefreshToken(email, member.getAuthorities());
        TokenDTO tokenDto = tokenProvider.createTokenDTO(newAccessToken, newRefreshToken);

        log.debug("refresh Origin = {}",originRefreshToken);
        log.debug("refresh New = {} ",newRefreshToken);
        // 6. ????????? ?????? ???????????? (dirtyChecking?????? ????????????)
        refreshToken.updateValue(newRefreshToken);

        // ?????? ??????
        return tokenDto;
    }
}
