package com.invento.authhandler.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invento.authhandler.dto.AuthorityDto;
import com.invento.authhandler.dto.ResponseDto;
import com.invento.authhandler.service.AuthService;

@RestController
@RequestMapping(path="/auth", produces= {MediaType.APPLICATION_JSON_VALUE})
public class AuthController {
	
	private AuthService authService;
	
	AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	/**
	 * {@code GET /login} : Authenticate user with azure credentials
	 * 
	 * @return ResponseEntity object with status code 200 {OK} and a success message,
	 *         or with status code 401 {Unauthorized} and no body.
	 */
	@GetMapping("/login")
	public ResponseEntity<ResponseDto> index(Authentication user) {
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Authentication Successful"));
	}
	
	/**
	 * {@code GET /getAuthorities} : Get list of all authorities
	 * 
	 *    
	 * @return ResponseEntity object with status code 200 {OK} and body with list of AuthorityDto objects,
	 *         or with status code 404 {NOT FOUND} and body with and empty list.
	 */
	@GetMapping("/getAuthorities")
	public ResponseEntity<List<AuthorityDto>> getAuthorities() {
		
		List<AuthorityDto> authorities =  authService.getAuthorities();
		return !CollectionUtils.isEmpty(authorities)
				? ResponseEntity.status(HttpStatus.OK).body(authorities)
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
	}
	
	/**
	 * {@code POST /addAuthority} : Add new authority
	 * 
	 * @param authorityDtos : Request payload containing authority details to be stored
	 * 
	 * @return ResponseEntity object of type ResponseDto with status 201 {Created} and a success message,
	 *         or throws InvalidRequestException with status 400 {Bad Request} and an error message.
	 */
	@PostMapping("/addAuthority")
	public ResponseEntity<ResponseDto> addAuthority(@RequestBody List<AuthorityDto> authorityDtos) {
		
		authService.addAuthorites(authorityDtos);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("Authorities created successfully"));	
	}
	
	/**
	 * {@code PUT /updateAuthority} : Update an existing authority object with matching id
	 * 
	 * @param id : unique identifier value for specific authority object
	 * 
	 * @param name : name to be updated for the authority
	 * 
	 * @return ResponseEntity object of type ResponseDto with status 200 {OK} and a success message,
	 *         or throws InvalidRequestException with status code 400 {BAD_REQUEST} and an error message
	 *         if provided name is null or empty,
	 *         or throws AuthorityNotFoundException with status code 404 {NOT_FOUND} and an error message
	 *         if object is not found matching the provided id.
	 */
	@PutMapping("/updateAuthority")
	public ResponseEntity<ResponseDto> updateAuthority(@RequestParam int id, @RequestParam String name) {
		
		authService.updateAuthority(id, name);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Authority updated successfully"));
	}
	
	/**
	 * {@Code DELETE /deleteAuthority} : Delete an authority object
	 * 
	 * @param id :  unique identifier value for specific authority object
	 * 
	 * @return ResponseEntity object of type String with status 200 {OK} and a success message,
	 *         or throws AuthorityNotFoundException with status code 404 {NOT FOUND} and an error message
	 *         if object is not found matching the provided id.
	 */
	@DeleteMapping("/deleteAuthority")
	public ResponseEntity<ResponseDto> deleteAuthority(@RequestParam int id) {
		
		authService.deleteAuthority(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Authority deleted successfully"));
	}
	
	@GetMapping("/logout")
	public ResponseEntity<ResponseDto> logout(Authentication user) {
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("Logout successful."));
	}
}
