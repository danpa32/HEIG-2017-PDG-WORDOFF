/*
 * File: AuthInterceptor.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Rest.Inteceptor;

import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Repository.UserRepository;
import ch.heigvd.wordoff.server.Security.SecurityConst;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This interceptor is enabled for some request paths in the RestConfig class.
 *
 * It adds the authorized user to the request if the JWT token is authentic.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Find user associated with authentification

        boolean trusted = false;

        String token = request.getHeader(SecurityConst.AUTH_HEADER);
        if (token != null) {
            // parse the token.
            String login = Jwts.parser()
                    .setSigningKey(SecurityConst.TOKEN_SECRET.getBytes())
                    .parseClaimsJws(token.replace(SecurityConst.TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            if (login != null) {
                User user = userRepository.findByCredentialsLogin(login);
                if(user != null) {
                    request.setAttribute("player", user);
                    trusted = true;
                }
            }
        }

        if(!trusted) {
            response.sendError(401);
        }
        return trusted;
    }
}
