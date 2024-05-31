package br.com.sapucaians.controller;

import br.com.sapucaians.jwt.TokenService;
import br.com.sapucaians.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private TokenService tokenService;
	
	/*@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Account account_){
		try {
			// automatic login
			if (account_.getTokenAccess() != null) {
				var subject = tokenService.getSubject(account_.getTokenAccess());
				var account = new AccountDetail(repository.findByEmail(subject));
				var authentication = new UsernamePasswordAuthenticationToken(account.getUsername(), null, account.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				//Map<String, Object> response = new HashMap<>();
				//response.put("tokenAccess", tokenService.generateToken(Auth.builder().email((String) authentication.getPrincipal()).typeRule(auth.getAuth().orElseThrow().getTypeRule()).build()));
				//response.put("auth", auth.getAuth().get());
				
				Account response = account.getAccount().get();

				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			
			// Login with E-mail and password
			if (repository.existByEmail(account_.getEmail()) > 0) {
				var authToken = new UsernamePasswordAuthenticationToken(account_.getEmail(), account_.getPassword());
				Authentication authen = authenticationManager.authenticate(authToken);

				var response = ((AccountDetail) authen.getPrincipal()).getAccount().get();
				response.setTokenAccess(tokenService.generateToken(response));
				
				//Map<String, Object> response = new HashMap<>();
				//response.put("tokenAccess",tokenService.generateToken(auth));
				//response.put("auth", auth);
				
				return new ResponseEntity<>(response, HttpStatus.OK);
				
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		} catch (TokenExpiredException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token JWT já expirado");
		}
	}*/
}
