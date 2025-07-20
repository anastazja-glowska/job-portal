package pl.anastazjaglowska.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.anastazjaglowska.jobportal.entity.JobSeekerProfile;

public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile, Integer> {
}
