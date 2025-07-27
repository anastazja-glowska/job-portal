package pl.anastazjaglowska.jobportal.entity;

import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UsersTest {

    @Autowired
    TestEntityManager testEntityManager;

    Users users;
    UsersType usersType;

    @BeforeEach
    void setUp() {
        users = new Users();
        usersType = new UsersType();
        usersType.setUserTypeName("Recruiter");
        testEntityManager.persist(usersType);

        users.setEmail("test@test.com");
        users.setPassword("12345678");
        users.setRegistrationDate(new Date());
        users.setActive(true);
        users.setUserTypeId(usersType);


    }

    @Test
    void testUser_whenValidCredentialsProvided_returnSavedUser() {
        testEntityManager.persistAndFlush(users);

        Assertions.assertEquals("test@test.com", users.getEmail());
        Assertions.assertEquals("Recruiter", users.getUserTypeId().getUserTypeName());

    }

    @Test
    void testUser_whenPasswordIsEmpty_throwsExceptions() {
        users.setPassword("");

        Assertions.assertThrows(ConstraintViolationException.class,  () -> testEntityManager
                .persistAndFlush(users));
    }
}
