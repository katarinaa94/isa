package rs.ac.uns.ftn.informatika.spring.security.service;

import java.util.List;

import rs.ac.uns.ftn.informatika.spring.security.model.Authority;

public interface AuthorityService {
	List<Authority> findById(Long id);
	List<Authority> findByname(String name);
}
