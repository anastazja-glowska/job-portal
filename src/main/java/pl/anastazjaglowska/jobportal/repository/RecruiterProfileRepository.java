package pl.anastazjaglowska.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.anastazjaglowska.jobportal.entity.RecruiterProfile;

public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile, Integer> {
}
