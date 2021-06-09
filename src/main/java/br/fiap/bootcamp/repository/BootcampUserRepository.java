package br.fiap.bootcamp.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.fiap.bootcamp.entity.User;

@Repository
public interface BootcampUserRepository extends JpaRepository<User, Integer> {
	
	@Query("SELECT 1 FROM BOOTCAMP_USERS WHERE USERNAME = :username AND USER_PASSWORD = :password")
	public User findUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}
