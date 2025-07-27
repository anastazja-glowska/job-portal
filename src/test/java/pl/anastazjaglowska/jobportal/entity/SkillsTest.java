package pl.anastazjaglowska.jobportal.entity;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class SkillsTest {

    @Autowired
    TestEntityManager testEntityManager;

    private Skills skills;
    private JobSeekerProfile jobSeekerProfile;
    private Users users;

    @BeforeEach
    void setUp() {
        skills = new Skills();
        jobSeekerProfile = new JobSeekerProfile();
        users = new Users();

        users.setPassword("12345678");

        jobSeekerProfile.setUserId(users);

        skills.setName("Java");
        skills.setExperienceLevel("Intermediate");
        skills.setJobSeekerProfile(jobSeekerProfile);
        skills.setYearsOfExperience("2");



    }
    @Test
    void testSkills_returnsValidProvidedSkills() {
        testEntityManager.persistAndFlush(skills);

        Assertions.assertEquals("Java", skills.getName());
    }

}
