package pl.anastazjaglowska.jobportal.controller;


import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.anastazjaglowska.jobportal.entity.*;
import pl.anastazjaglowska.jobportal.repository.JobPostActivityRepository;
import pl.anastazjaglowska.jobportal.repository.JobSeekerProfileRepository;
import pl.anastazjaglowska.jobportal.repository.UsersRepository;
import pl.anastazjaglowska.jobportal.repository.UsersTypeRepository;
import pl.anastazjaglowska.jobportal.services.JobPostActivityService;
import pl.anastazjaglowska.jobportal.services.JobSeekerApplyService;
import pl.anastazjaglowska.jobportal.services.JobSeekerSaveService;
import pl.anastazjaglowska.jobportal.services.UsersService;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc(addFilters = false)
public class JobPostActivityControllerTest {


    @ServiceConnection
    private static MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:8");


    static {
        mySQLContainer.start();
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JobPostActivityService jobPostActivityService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private JobPostActivityRepository jobPostActivityRepository;

    @Autowired
    private JobSeekerApplyService jobSeekerApplyService;

    @Autowired
    private JobSeekerSaveService jobSeekerSaveService;

    @Autowired
    private UsersTypeRepository usersTypeRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JobSeekerProfileRepository jobSeekerProfileRepository;

    @Autowired
    private EntityManager entityManager;



    @BeforeEach
    void setUp() {

        UsersType jobSeekerType = new UsersType();
        jobSeekerType.setUserTypeName("JobSeeker");
        jobSeekerType = usersTypeRepository.save(jobSeekerType);

        Users testUser = new Users();
        testUser.setEmail("test@seeker.com");
        testUser.setPassword("12345678");
        testUser.setActive(true);
        testUser.setRegistrationDate(new Date());
        testUser.setUserTypeId(jobSeekerType);
        usersRepository.save(testUser);

        JobSeekerProfile profile = new JobSeekerProfile();
//        profile.setUserAccountId(testUser.getUserId());
        profile.setUserId(testUser);
        profile.setFirstName("Jan");
        profile.setLastName("Kowalski");
        profile.setCity("Warsaw");
        profile.setState("Mazowieckie");
        profile.setCountry("Poland");
        profile.setWorkAuthorization("PL");
        profile.setEmploymentType("Full-Time");
        profile.setResume("resume_test.pdf");
        profile.setProfilePhoto("photo.jpg");
        profile.setSkills(new ArrayList<>());
        jobSeekerProfileRepository.save(profile);


        JobCompany company = new JobCompany();
        company.setName("TechCorp");
        company.setLogo("abc");
        entityManager.persist(company);
        company = entityManager.merge(company);

        JobLocation location = new JobLocation();
        location.setCity("Warszawa");
        location.setCountry("Polska");
        entityManager.persist(location);
        location = entityManager.merge(location);

        JobPostActivity job = new JobPostActivity();
        job.setJobTitle("Junior Java Developer");
        job.setDescriptionOfJob("We are looking for young Java Developer.");
        job.setJobType("Full-Time");
        job.setSalary("9000 PLN");
        job.setRemote("Remote-Only");
        job.setPostedDate(new Date());
        job.setPostedById(testUser);
        job.setJobCompanyId(company);
        job.setJobLocationId(location);
        jobPostActivityRepository.save(job);

    }

    @Order(1)
    @Test
    @DisplayName("The test MYSQL container is created and is running")
    void testContainerIsRunning() {
        assertTrue(mySQLContainer.isCreated(), "My sql container is not created");
        assertTrue(mySQLContainer.isRunning(), "My sql container is not running");
    }

    @Test
    @Order(2)
    @WithMockUser(username = "test@test.com")
    void testSearchJobs_whenValidCredentialsProvided_returnsCorrectUserProfile() throws Exception {
        mockMvc.perform(get("/dashboard/")
                .param("job", "Java")
                .param("fullTime", "Full-Time"))
                .andExpect(view().name("dashboard"))
                .andExpect(model().attributeExists("jobPost"));
    }
}
