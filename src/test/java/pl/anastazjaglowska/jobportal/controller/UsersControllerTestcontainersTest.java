package pl.anastazjaglowska.jobportal.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.anastazjaglowska.jobportal.entity.Users;
import pl.anastazjaglowska.jobportal.entity.UsersTest;
import pl.anastazjaglowska.jobportal.entity.UsersType;
import pl.anastazjaglowska.jobportal.repository.UsersTypeRepository;
import pl.anastazjaglowska.jobportal.services.UsersService;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class UsersControllerTestcontainersTest {

    @ServiceConnection
    private static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsersTypeRepository usersTypeRepository;

    @Autowired
    private UsersService usersService;

    private Users users;
    private UsersType recruiter;

    static {
        mySQLContainer.start();
    }

    @BeforeEach
    void setup() {

        usersTypeRepository.deleteAll();


        recruiter = new UsersType();
        recruiter.setUserTypeName("Recruiter");
        recruiter = usersTypeRepository.save(recruiter);


        users = new Users();
        users.setEmail("test@test.com");
        users.setPassword("12345678");
        users.setActive(true);
        users.setRegistrationDate(java.sql.Date.valueOf(LocalDate.of(2025, 7, 23)));
        users.setUserTypeId(recruiter);
    }

    @Order(1)
    @Test
    void testContainerIsRunning() {
        assertTrue(mySQLContainer.isRunning(), "MySQL container is not running");
        assertTrue(mySQLContainer.isCreated(), "MySQL container is not created");
    }

    @Order(2)
    @Test
    void testRegister_whenValidDetailsProvided_returnRegisterForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("getAllTypes"))
                .andExpect(model().attributeExists("user"));
    }

//    @Order(3)
//    @Test
//    void testUserRegistration_whenValidDetailsProvided_returnsCorrectModel() throws Exception {
//        mockMvc.perform(post("/register/new")
//                        .param("email", users.getEmail())
//                        .param("password", users.getPassword())
//                        .param("isActive", String.valueOf(users.isActive()))
//                        .param("registerDate", users.getRegistrationDate().toString())
//                        .param("usersType.id", String.valueOf(recruiter.getUserTypeId()))
//                )
//                .andExpect(view().name("register"))
//                .andExpect(model().attributeExists("getAllTypes"))
//                .andExpect(model().attributeExists("user"));
//    }
}



//@PostMapping("/register/new")
//public String userRegistration(@Valid Users users, Model model) {
//
//    Optional<Users> optionalUsers = usersService.getUserByEmail(users.getEmail());
//
//    if(optionalUsers.isPresent()) {
//        model.addAttribute("error",
//                "Email already existed, try to login or register with other email.");
//        List<UsersType> usersTypes = usersTypeService.getAll();
//        model.addAttribute("getAllTypes", usersTypes);
//        model.addAttribute("user",new Users());
//        return "register";
//    }

//
//@GetMapping("/register")
//public String register(Model model) {
//    List<UsersType> usersTypes = usersTypeService.getAll();
//    model.addAttribute("getAllTypes", usersTypes);
//    model.addAttribute("user",new Users());
//    return "register";
//}
