package se.mbi.be2.trava.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.mbi.be2.trava.api.model.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

}
