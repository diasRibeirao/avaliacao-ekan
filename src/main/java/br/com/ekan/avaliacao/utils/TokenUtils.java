package br.com.ekan.avaliacao.utils;

import br.com.ekan.avaliacao.model.security.UserDetailsImpl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Component
public class TokenUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @Value("${ekan.security.token.secret}")
    private String tokenSecret;

    @Value("${ekan.security.token.issuer}")
    private String tokenIssuer;

    public String gerarToken(final Authentication authentication, final Instant dataExpiracao) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

            return JWT.create()
                    .withIssuer(tokenIssuer)
                    .withSubject((userPrincipal.getUsername()))
                    .withExpiresAt(Date.from(dataExpiracao))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro durante a geração do token", exception);
        }
    }

    public Instant calcularDataExpiracao() {
        return Instant.now().truncatedTo(ChronoUnit.SECONDS).plusSeconds(60 * 60 * 4);
    }

    public String formatarDataExpiracao(Instant dataExpiracao) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(dataExpiracao, ZoneOffset.UTC);
        return dateTime.format(formatter);
    }

    public String validateToken(final String token) {
        try {
            final var anAlgorithm = Algorithm.HMAC256(tokenSecret);
            final var verifier = JWT.require(anAlgorithm)
                    .withIssuer(tokenIssuer)
                    .build();
            final var decodedToken = verifier.verify(token);
            return decodedToken.getSubject();
        } catch (Exception e) {
            return "";
        }
    }



}
