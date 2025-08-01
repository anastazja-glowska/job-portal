package pl.anastazjaglowska.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.anastazjaglowska.jobportal.entity.Users;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByEmail(String email);
}
