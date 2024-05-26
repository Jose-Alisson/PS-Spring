package br.com.sunshine.jwt;

import br.com.sunshine.exception.causable.ErrDateTransfer;
import br.com.sunshine.model.Account;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

	@Value("${chave.secreta.jwt.aplicacao}")
	private String chave;

	public String generateToken(Account account) {
		Date dateExpiration = Date.from(LocalDateTime.now().plusDays(3).toInstant(ZoneOffset.of("-03:00")));

		return JWT.create().withSubject(account.getEmail()).withExpiresAt(dateExpiration)
				.sign(Algorithm.HMAC256(chave));
	}
	
	public String getSubject(String token) {
		try {
			return JWT.require(Algorithm.HMAC256(chave)).build().verify(token).getSubject();
		} catch (TokenExpiredException ex){
			throw new ErrDateTransfer("Faça login novamente!!!", HttpStatus.UNAUTHORIZED);
		}
	}
}
