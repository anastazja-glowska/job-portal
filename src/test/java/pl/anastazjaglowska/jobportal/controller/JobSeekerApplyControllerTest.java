package pl.anastazjaglowska.jobportal.controller;


import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.anastazjaglowska.jobportal.entity.JobCompany;
import pl.anastazjaglowska.jobportal.entity.JobLocation;
import pl.anastazjaglowska.jobportal.entity.JobPostActivity;
import pl.anastazjaglowska.jobportal.entity.JobSeekerProfile;
import pl.anastazjaglowska.jobportal.services.JobPostActivityService;
import pl.anastazjaglowska.jobportal.services.UsersService;

import static io.restassured.RestAssured.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = JobSeekerApplyController.class)
//@AutoConfigureMockMvc(addFilters = false)
//@MockBean({UsersServiceImpl.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc(addFilters = false)
public class JobSeekerApplyControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    JobPostActivityService jobPostActivityService;

    @MockBean
    UsersService usersService;


    @Test
    @Order(1)
    @WithMockUser(username = "test@test.com")
    void testDisplay_whenJobPostActivityIsFound_returnCorrectView() throws Exception {
        JobCompany jobCompany = new JobCompany();
        jobCompany.setId(1);
        jobCompany.setLogo("");
        jobCompany.setName("abc");

        JobLocation jobLocation = new JobLocation();
        jobLocation.setCity("Warsaw");
        jobLocation.setState("New York");
        jobLocation.setCountry("Usa");

        JobPostActivity jobPostActivity = new JobPostActivity();
        jobPostActivity.setJobPostId(1);
        jobPostActivity.setJobLocationId(jobLocation);
        jobPostActivity.setJobCompanyId(jobCompany);
        JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();






        Mockito.when(jobPostActivityService.getOne(1)).thenReturn(jobPostActivity);
        Mockito.when(usersService.getCurrentUserProfile()).thenReturn(jobSeekerProfile);

        mockMvc.perform(MockMvcRequestBuilders.get("/job-details-apply/{id}", 1))
                .andExpect(view().name("job-details"))
                .andExpect(model().attributeExists("jobDetails"))
                .andExpect(model().attributeExists("user"));

    }

}
