package se.mbi.be2.trava.api.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.mbi.be2.trava.api.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
