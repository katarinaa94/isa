package rs.ac.uns.ftn.informatika.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.informatika.spring.security.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername( String username );
}

