package com.invento.authhandler.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.invento.authhandler.dto.AuthorityDto;
import com.invento.authhandler.exception.AuthorityNotFoundException;
import com.invento.authhandler.exception.InvalidRequestException;
import com.invento.authhandler.mapper.AuthMapper;
import com.invento.authhandler.model.Authority;
import com.invento.authhandler.repo.AuthorityRepo;
import com.invento.authhandler.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	private AuthorityRepo authRepo;
	
	private AuthMapper authMapper;
	
	private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	public AuthServiceImpl(AuthorityRepo authRepo, AuthMapper authMapper) {
		
		this.authRepo = authRepo;
		this.authMapper = authMapper;
	}
	
	/**
	 * Get list of Authority details objects
	 *  
	 * @return List of AuthorityDto
	 * 
	 */
	public List<AuthorityDto> getAuthorities() {

		List<AuthorityDto> authorityDto = new ArrayList<>();
		List<Authority> authorities = authRepo.findAll();
		if(!CollectionUtils.isEmpty(authorities)) {
			authorityDto = authMapper.authoritiesToDtos(authorities);
		}
		return authorityDto;
	}
	
	/**
	 * Add new authority objects
	 * 
	 * @param authorityDtos: Authority objects to be stored
	 * 
	 * throws InvalidRequestException if authorityDtos is null/empty
	 * 
	 */
	public void addAuthorites(List<AuthorityDto> authorityDtos) {
		
		if(CollectionUtils.isEmpty(authorityDtos)) {
			throw new InvalidRequestException("Authority dtos are null/empty");
		}
		List<Authority> authorities = authMapper.dtosToAuthorityList(authorityDtos);
		authRepo.saveAll(authorities);
		log.info("Authorities created succesfully.");
	}

	/**
	 * Get list of Authority objects for adding authority name in the 
	 * authorization object corresponding to the groupIds
	 * 
	 * @return List of Authority objects
	 * 
	 */
	@Override
	public List<Authority> getAuthorityList() {
		
		return authRepo.findAll();
	}
	

	/**
	 * Update existing authority object where id matches the provided id
	 * 
	 * @param id : unique identifier value for specific authority object
	 * 
	 * @param name : name to be updated for the authority
	 * 
	 * throws InvalidRequestException with status code 400 {BAD_REQUEST} and an error message
	 * if provided name is null or empty,
	 * 
	 * throws AuthorityNotFoundException with status code 404 {NOT_FOUND} and an error message
	 * if object is not found matching the provided id.
	 * 
	 */
	@Override
	public void updateAuthority(int id, String name) {
		
		if(Strings.isEmpty(name)) {
			throw new InvalidRequestException(
				"Value: " + name + " for parameter name is invalid.");
		}
		Authority authority = authRepo.findById(id).orElseThrow(
				() -> new AuthorityNotFoundException("Authority not found with id " + id));
		authority.setName(name);
		authRepo.save(authority);
		log.info("Authority with id {} updated succesfully with name {}.", id, name);
	}

	/**
	 * Delete authority object matching the provided id
	 * 
	 * @param id : unique identifier value for specific product object
	 * 
	 * throws AuthorityNotFoundException with status code 404 {NOT_FOUND} and an error message
	 * if object is not found matching the provided id.
	 */
	@Override
	public void deleteAuthority(int id) {
		
		Authority authority = authRepo.findById(id).orElseThrow(
			() -> new AuthorityNotFoundException("Authority not found with id " + id));
		authRepo.delete(authority);
		log.info("Authority with id {} deleted succesfully.", id);
	}
}
