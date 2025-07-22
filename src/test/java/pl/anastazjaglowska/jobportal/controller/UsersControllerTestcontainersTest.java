package pl.anastazjaglowska.jobportal.controller;
//
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.testcontainers.containers.MySQLContainer;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import pl.anastazjaglowska.jobportal.entity.Users;
//import pl.anastazjaglowska.jobportal.entity.UsersType;
//import pl.anastazjaglowska.jobportal.repository.UsersTypeRepository;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Testcontainers
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@AutoConfigureMockMvc
//public class UsersControllerTestcontainersTest {
//
//    @ServiceConnection
//    private static MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:8");
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    UsersTypeRepository usersTypeRepository;
//
//
//    static {
//        mySQLContainer.start();
//    }
//
//    @BeforeAll
//    void setup() {
//        UsersType recruiter = new UsersType(1, "Recruiter", new Users());
//        UsersType jobSeeker = new UsersType(1, "Recruiter", new Users());
//        usersTypeRepository.findAll();
//    }
//
//
//    @Order(1)
//    @Test
//    void testContainerIsRunning() {
//        assertTrue(mySQLContainer.isRunning(), "Mysql container is not running");
//        assertTrue(mySQLContainer.isCreated(), "Mysql container is not created");
//    }
//
//    @Test
//    @Order(2)
//    void testRegister_whenValidDetailsProvided_returnRegisterForm(){
//
//    }
//
//
//
//
//
//
//
//
//
//}


//
//@GetMapping("/register")
//public String register(Model model) {
//    List<UsersType> usersTypes = usersTypeService.getAll();
//    model.addAttribute("getAllTypes", usersTypes);
//    model.addAttribute("user",new Users());
//    return "register";
//}
