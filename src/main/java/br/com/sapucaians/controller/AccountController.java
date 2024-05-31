package br.com.sapucaians.controller;

import br.com.sapucaians.dto.AccountDTO;
import br.com.sapucaians.jwt.TokenService;
import br.com.sapucaians.model.Account;
import br.com.sapucaians.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	@Autowired
	private AccountService service;

	@Autowired
	private TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam("email") String email, @RequestParam("password") String password) {
		Map<String, String> map = new HashMap<>();
		System.out.println(password);
		map.put("access", service.login(email, password));
		return ResponseEntity.ok(map);
	}

	@PostMapping("/create")
	public ResponseEntity<AccountDTO> save(@RequestBody @Valid Account account) {
		return ResponseEntity.ok(service.save(account));
	}

	@PutMapping("/update")
	public ResponseEntity<AccountDTO> update(@RequestBody @Valid AccountDTO account) {
		return ResponseEntity.ok(service.update(account));
	}
	
	@DeleteMapping("/{id}/delete")
	public ResponseEntity<String> delete(@PathVariable("id") Long id){
		service.deleteAccount(id);
		return ResponseEntity.ok("Successful");
	}

	@GetMapping("/byToken")
	public ResponseEntity<AccountDTO> getByToken(@RequestParam("token") String token){
		var email = tokenService.getSubject(token);
		return ResponseEntity.ok(service.getByEmail(email));
	}

	@GetMapping("/")
	public ResponseEntity<List<Account>> getAll(){
		return ResponseEntity.ok(service.getAll());
	}

	@GetMapping("/isExist")
	public ResponseEntity<?> isExist(@RequestParam("email") String email) {
		return ResponseEntity.ok(service.isExist(email));
	}

	/*
	* @GetMapping("verifyCode")
	public ResponseEntity<?> getCodeNumber(@RequestParam("number") String number,
			@RequestParam("provider") String provider) {

		int generatedCode = 10000 + new Random().nextInt(89999);
		MessagePs message = MessagePs.builder().to(number).message("Seu código de verificação é: " + generatedCode)
				.build();

		messageService.sendMessage(message, provider);

		Map<String, Object> response = new HashMap<>();
		response.put("token", tokenService.GenerateCodeVerify(generatedCode + ""));

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("verifyCode")
	public ResponseEntity<?> verifyCodeNumber(@RequestHeader("codeToken") String codeToken,
			@RequestParam("code") String code) {

		if (codeToken != null) {
			String token = codeToken.replaceAll("Bearer ", "");
			DecodedJWT decoded = tokenService.getCodeVerify(token);
			System.out.println("ésse é o codigo do token" + decoded.getClaim("code").asString());
			if (!(decoded.getClaim("code").asString().equals(code))) {
				return new ResponseEntity<>("Código Invalido", HttpStatus.UNAUTHORIZED);
			}
			return new ResponseEntity<>(code, HttpStatus.OK);
		}
		return new ResponseEntity<>("Token não present", HttpStatus.UNAUTHORIZED);
	}
	* */
}
