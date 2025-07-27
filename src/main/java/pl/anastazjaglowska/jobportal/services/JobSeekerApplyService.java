package pl.anastazjaglowska.jobportal.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.anastazjaglowska.jobportal.entity.JobPostActivity;
import pl.anastazjaglowska.jobportal.entity.JobSeekerApply;
import pl.anastazjaglowska.jobportal.entity.JobSeekerProfile;
import pl.anastazjaglowska.jobportal.repository.JobPostActivityRepository;
import pl.anastazjaglowska.jobportal.repository.JobSeekerApplyRepository;

import java.util.List;

@Service
public class JobSeekerApplyService {

    private final JobSeekerApplyRepository jobSeekerApplyRepository;

    @Autowired
    public JobSeekerApplyService(JobSeekerApplyRepository jobSeekerApplyRepository) {
        this.jobSeekerApplyRepository = jobSeekerApplyRepository;
    }

    public List<JobSeekerApply> getCandidatesJob(JobSeekerProfile userAccountId) {
        return jobSeekerApplyRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerApply> getJobCandidates(JobPostActivity job) {
        return jobSeekerApplyRepository.findByJob(job);
    }

}
