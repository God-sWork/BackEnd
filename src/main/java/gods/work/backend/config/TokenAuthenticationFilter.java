package gods.work.backend.config;

import gods.work.backend.config.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 유효한 엑세스 토큰일 때, 인증 정보 설정
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_BEARER = "Bearer ";
    public static final String ACCESS_TOKEN_COOKIE_NAME = "access_token";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 쿠키에서 토큰을 읽어옴
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ACCESS_TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token != null && tokenProvider.verifyToken(token)) {
            // 유효한 토큰일 때, 인증 정보 설정
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

//        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
//        String token = getAccessToken(authorizationHeader);
//        if (tokenProvider.verifyToken(token)){
//            // 유효한 토큰일 때, 인증 정보 설정
//            Authentication authentication = tokenProvider.getAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }

        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_BEARER)) {
            return authorizationHeader.substring(TOKEN_BEARER.length());
        }
        return null;
    }
}
