package br.ufscar.dc.dsw.mural.dao;

import br.ufscar.dc.dsw.mural.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUserDAO extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    User save(User user);
}
