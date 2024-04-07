package br.com.tiviati.sct.config.security;

import br.com.tiviati.sct.service.AuthApiService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static br.com.tiviati.sct.commons.Constants.API_KEY_HEADER_NAME;
import static br.com.tiviati.sct.commons.Constants.KEY_LOADED_SUCCESSFUL;
import static br.com.tiviati.sct.commons.Constants.KEY_LOADED_UNSUCCESSFUL;
import static br.com.tiviati.sct.commons.Constants.TOKEN_EMPTY;
import static br.com.tiviati.sct.commons.Constants.UNAUTHORIZED_JSON_PAYLOAD;
import static br.com.tiviati.sct.commons.Constants.UNAUTHORIZED_PAYLOAD;
import static br.com.tiviati.sct.commons.Constants.UNAUTHORIZED_PAYLOAD_REASON;

@Component
public class AuthFilter extends GenericFilterBean {
    @Autowired
    private AuthApiService authApiService;

    private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest requestToHttpServlet = (HttpServletRequest) request;
        final Optional<String> token = Optional.ofNullable(requestToHttpServlet.getHeader(API_KEY_HEADER_NAME));
        if (token.isEmpty()) {
            LOG.debug(TOKEN_EMPTY);
            /*
              O spring security vai lidar com isso, proibindo os respectivos acessos
              nao autorizados.
            */
            chain.doFilter(request, response);
            return;
        }

        if (authApiService.isValidApiKey(token.get())) {
            LOG.debug(KEY_LOADED_SUCCESSFUL);
            final UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(token.get(), token.get(), Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);
        } else {
            LOG.debug(KEY_LOADED_UNSUCCESSFUL);
            final HttpServletResponse responseToHttpServlet = (HttpServletResponse) response;
            responseToHttpServlet.reset();
            responseToHttpServlet.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            responseToHttpServlet.addHeader(UNAUTHORIZED_PAYLOAD, UNAUTHORIZED_PAYLOAD_REASON);
            response.setContentLength(UNAUTHORIZED_JSON_PAYLOAD.length());
            response.getWriter().write(UNAUTHORIZED_JSON_PAYLOAD);
        }
    }
}
