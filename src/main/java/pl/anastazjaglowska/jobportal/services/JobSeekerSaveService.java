package pl.anastazjaglowska.jobportal.services;

import org.springframework.stereotype.Service;
import pl.anastazjaglowska.jobportal.entity.JobPostActivity;
import pl.anastazjaglowska.jobportal.entity.JobSeekerProfile;
import pl.anastazjaglowska.jobportal.entity.JobSeekerSave;
import pl.anastazjaglowska.jobportal.repository.JobSeekerSaveRepository;

import java.util.List;

@Service
public class JobSeekerSaveService {

    private final JobSeekerSaveRepository jobSeekerSaveRepository;

    public JobSeekerSaveService(JobSeekerSaveRepository jobSeekerSaveRepository) {
        this.jobSeekerSaveRepository = jobSeekerSaveRepository;
    }

    public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId) {
        return jobSeekerSaveRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerSave> getJobCandidates(JobPostActivity job){
        return jobSeekerSaveRepository.findByJob(job);
    }
}
