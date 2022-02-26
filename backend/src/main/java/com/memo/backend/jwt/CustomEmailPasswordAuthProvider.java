package com.memo.backend.jwt;

import com.memo.backend.service.member.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
public class CustomEmailPasswordAuthProvider implements AuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;
    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails user = null;
        try {
            user = retrieveUser(authentication.getName());
        }catch (UsernameNotFoundException ex) {
            throw ex;
        }

        Object principalToReturn = user;
        CustomEmailPasswordAuthToken result = new CustomEmailPasswordAuthToken(principalToReturn
                ,authentication.getCredentials()
                ,this.authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(authentication.getDetails());
        return result;
    }

    protected final UserDetails retrieveUser(String username ) throws AuthenticationException {
        try {
            UserDetails loadedUser = customUserDetailsService.loadUserByUsername(username);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException(
                        "UserDetailsService returned null, which is an interface contract violation");
            }
            return loadedUser;
        }
        catch (UsernameNotFoundException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CustomEmailPasswordAuthToken.class);
    }
}
