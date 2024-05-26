package br.com.sunshine.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/file")
public class FileController {

	private final String BASE_URL = "C:\\Users\\Jose Alisson\\Desktop\\resources";

	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {

		try {
			FileOutputStream fot = new FileOutputStream(new File(BASE_URL + "\\" + file.getOriginalFilename()));

			fot.write(file.getBytes());
			fot.close();

			Map<String, String> res = new HashMap<>();
			res.put("photoUrl", file.getOriginalFilename());
			
			return ResponseEntity.ok(res);

		} catch (IOException e) {
			e.printStackTrace();
		}

		 return ResponseEntity.badRequest().body(null);
	}

	@GetMapping("/download")
	public ResponseEntity<?> download(@RequestParam("filePath") String filePath) {
		try {
			FileInputStream fis = new FileInputStream(new File(BASE_URL + "\\" + filePath));
			try {
				return ResponseEntity.ok()
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath + "\"")
						.contentType(MediaType.APPLICATION_OCTET_STREAM).body(fis.readAllBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		}
		
		return ResponseEntity.notFound().build();
	}
}