package com.thoughtworks.training.zhangtian.todoservice.security;

import com.google.common.net.HttpHeaders;
import com.thoughtworks.training.zhangtian.todoservice.model.User;
import com.thoughtworks.training.zhangtian.todoservice.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class TodoAuthFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;

    @Value("${private.password}")
    private String privatePassword;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.isEmpty(token)) {
            Claims body = Jwts.parser()
                    .setSigningKey(privatePassword.getBytes("UTF-8"))
                    .parseClaimsJws(token)
                    .getBody();


            int id = (int) body.get("id");
            String name = (String) body.get("name");
            User user = new User();
            user.setId(id);
            user.setName(name);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(user,
                            null,
                            Collections.emptyList())
            );
        }

        filterChain.doFilter(request, response);
    }

    private boolean validateToken(Claims body) {

        User user = new User();
        user.setName((String) body.get("name"));
        user.setPassword((String) body.get("password"));
        return userService.validate(user);
    }
}
