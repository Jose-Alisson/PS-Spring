package br.com.sapucaians.filter;

import br.com.sapucaians.detail.AccountDetail;
import br.com.sapucaians.jwt.TokenService;
import br.com.sapucaians.repository.AccountRepository;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AccountFilter extends OncePerRequestFilter {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AccountRepository repository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = "";
		var auth_ = request.getHeader("Authorization");
		
		/*Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
        	 while (headerNames.hasMoreElements()) {
                 String headerName = headerNames.nextElement();
                 String headerValue = request.getHeader(headerName);
                 System.out.println(headerName + ": " + headerValue);
             }
        }*/

		try {
			if (auth_ != null) {
				
				token = auth_.replaceAll("Bearer ", "");
				var subject = tokenService.getSubject(token);
				
				var auth = new AccountDetail(repository.findByEmail(subject));
	
				var authentication = new UsernamePasswordAuthenticationToken(auth.getUsername(), null,
						auth.getAuthorities());
	
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				filterChain.doFilter(request, response);
				return;
			}
		} catch (TokenExpiredException e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Erro: " + e.getMessage());
			return;
		}
		
		filterChain.doFilter(request, response);
	}
}
