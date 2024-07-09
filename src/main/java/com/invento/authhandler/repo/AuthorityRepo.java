package com.invento.authhandler.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invento.authhandler.model.Authority;

@Repository
public interface AuthorityRepo extends JpaRepository<Authority, Integer>{

}
