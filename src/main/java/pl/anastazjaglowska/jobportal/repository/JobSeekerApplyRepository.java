package pl.anastazjaglowska.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.anastazjaglowska.jobportal.entity.JobPostActivity;
import pl.anastazjaglowska.jobportal.entity.JobSeekerApply;
import pl.anastazjaglowska.jobportal.entity.JobSeekerProfile;

import java.util.List;


@Repository
public interface JobSeekerApplyRepository extends JpaRepository<JobSeekerApply, Integer> {

    List<JobSeekerApply> findByUserId(JobSeekerProfile userId);

    List<JobSeekerApply> findByJob(JobPostActivity job);
}
