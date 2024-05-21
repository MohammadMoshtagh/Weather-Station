package edu.sharif.webproject.security;

import edu.sharif.webproject.enduser.EndUserRepository;
import edu.sharif.webproject.enduser.api.ApiTokenEntity;
import edu.sharif.webproject.enduser.api.ApiTokenRepository;
import edu.sharif.webproject.enduser.api.ApiTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApiTokenAuthenticationFilter extends OncePerRequestFilter {
    private final ApiTokenService apiTokenService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String requestApiToken = request.getHeader("X-API-Key");
        if (requestApiToken == null) {
            filterChain.doFilter(request, response);
            return;
        }
        ApiTokenEntity apiToken = apiTokenService.getApiTokenEntityByApiToken(requestApiToken);
        Date expirationDate = apiToken.getExpirationDate();
        String username = apiToken.getEndUser().getUsername();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (expirationDate.after(new Date())) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}
