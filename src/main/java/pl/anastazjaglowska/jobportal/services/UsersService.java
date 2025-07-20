package pl.anastazjaglowska.jobportal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.anastazjaglowska.jobportal.entity.JobSeekerProfile;
import pl.anastazjaglowska.jobportal.entity.RecruiterProfile;
import pl.anastazjaglowska.jobportal.entity.Users;
import pl.anastazjaglowska.jobportal.repository.JobSeekerProfileRepository;
import pl.anastazjaglowska.jobportal.repository.RecruiterProfileRepository;
import pl.anastazjaglowska.jobportal.repository.UsersRepository;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, JobSeekerProfileRepository jobSeekerProfileRepository,
                        RecruiterProfileRepository recruiterProfileRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users addNew(Users users) {
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUser = usersRepository.save(users);
        int userTypeId = users.getUserTypeId().getUserTypeId();
        if(userTypeId ==1 ) {
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        } else {
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }

        return savedUser;
    }

    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }


}
