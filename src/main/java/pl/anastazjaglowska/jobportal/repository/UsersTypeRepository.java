package pl.anastazjaglowska.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.anastazjaglowska.jobportal.entity.UsersType;

public interface UsersTypeRepository extends JpaRepository<UsersType, Integer> {
}
