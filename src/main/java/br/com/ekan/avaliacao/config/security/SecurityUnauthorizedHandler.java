package br.com.ekan.avaliacao.config.security;

import br.com.ekan.avaliacao.controller.exceptions.StandardError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class SecurityUnauthorizedHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.error("Erro acesso não autorizado: {}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        StandardError erro = new StandardError(
                HttpStatus.UNAUTHORIZED.value(),
                "Acesso não autorizado",
                System.currentTimeMillis()
        );

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), erro);
    }
}
