package br.appAuth.appAuthentication.repository;

import br.appAuth.appAuthentication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<User, UUID>{

}
