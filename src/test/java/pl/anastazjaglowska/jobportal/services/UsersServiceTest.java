package pl.anastazjaglowska.jobportal.services;


import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.anastazjaglowska.jobportal.entity.Users;
import pl.anastazjaglowska.jobportal.entity.UsersTest;
import pl.anastazjaglowska.jobportal.entity.UsersType;
import pl.anastazjaglowska.jobportal.repository.JobSeekerProfileRepository;
import pl.anastazjaglowska.jobportal.repository.RecruiterProfileRepository;
import pl.anastazjaglowska.jobportal.repository.UsersRepository;
import pl.anastazjaglowska.jobportal.repository.UsersTypeRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsersServiceTest {


    @ServiceConnection
    private static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8");


    static {
        mySQLContainer.start();
    }

    private Users users;



    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    JobSeekerProfileRepository jobSeekerProfileRepository;

    @Autowired
    UsersTypeRepository usersTypeRepository;


    @BeforeAll
    void setupUserType() {
        if (usersTypeRepository.findAll().stream().noneMatch(ut -> "Recruiter".equals(ut.getUserTypeName()))) {
            UsersType usersType = new UsersType();
            usersType.setUserTypeName("Recruiter");
            usersTypeRepository.save(usersType);
        }
    }

    @BeforeEach
    void setUp() {

        UsersType recruiterType = usersTypeRepository.findAll().stream()
                .filter(ut -> "Recruiter".equals(ut.getUserTypeName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("UserType 'Recruiter' not found"));

        users = new Users();
        users.setEmail("test@test.com");
        users.setPassword("12345678");
        users.setUserTypeId(recruiterType);
    }

    @Autowired
    UsersService usersService;


    @Order(1)
    @Test
    void testContainerIsRunning() {
        assertTrue(mySQLContainer.isRunning(), "MySQL container is not running");
        assertTrue(mySQLContainer.isCreated(), "MySQL container is not created");
    }

    @Test
    @Order(2)
    void testAddNew_saveRequiredUserProfile() {
        Users savedUser = usersService.addNew(users);


        assertNotNull(savedUser);
        assertTrue(savedUser.isActive());

//        assertTrue(recruiterProfileRepository.existsById(1));




    }

}
