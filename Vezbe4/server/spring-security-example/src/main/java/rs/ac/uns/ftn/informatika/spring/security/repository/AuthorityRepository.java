package rs.ac.uns.ftn.informatika.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.spring.security.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	Authority findByName(String name);
}
