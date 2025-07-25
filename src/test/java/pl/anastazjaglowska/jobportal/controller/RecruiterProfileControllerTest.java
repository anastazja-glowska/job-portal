package pl.anastazjaglowska.jobportal.controller;


import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.anastazjaglowska.jobportal.entity.RecruiterProfile;
import pl.anastazjaglowska.jobportal.entity.Users;
import pl.anastazjaglowska.jobportal.repository.RecruiterProfileRepository;
import pl.anastazjaglowska.jobportal.repository.UsersRepository;
import pl.anastazjaglowska.jobportal.services.RecruiterProfileService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RecruiterProfileController.class)
//@AutoConfigureMockMvc(addFilters = false)
//@MockBean({UsersServiceImpl.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc(addFilters = false)
public class RecruiterProfileControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private RecruiterProfileService recruiterProfileService;

    @Test
    @Order(1)
    @WithMockUser(username = "test@test.com")
    void testRecruiterProfile_whenRecruiterIdIsFound_returnRecruiterProfileView() throws Exception {
        Users user = new Users();
        user.setUserId(1);
        user.setEmail("test@test.com");

        RecruiterProfile recruiterProfile = new RecruiterProfile();
        recruiterProfile.setUserAccountId(1);

        Mockito.when(usersRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        Mockito.when(recruiterProfileService.getOne(1))
                .thenReturn(Optional.of(recruiterProfile));


        mockMvc.perform(MockMvcRequestBuilders.get("/recruiter-profile/"))
                .andExpect(view().name("recruiter_profile"))
                .andExpect(model().attributeExists("profile"));

    }

    @Test
    @Order(2)
    @WithMockUser(username = "test@test.com")
    void testAddNew_whenValidCredentialsProvided_createRecruiterProfile() throws Exception {
        Users user = new Users();
        user.setEmail("test@test.com");
        user.setUserId(1);

        RecruiterProfile recruiterProfile = new RecruiterProfile();
        recruiterProfile.setUserAccountId(1);


        Mockito.when(usersRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        Mockito.when(recruiterProfileService.addNew(any(RecruiterProfile.class)))
                .thenReturn(recruiterProfile);

        MockMultipartFile image = new MockMultipartFile(
                "image",
                "photo.jpg",
                "image/jpeg",
                "fake-image-content".getBytes());

        MockMultipartFile dummyForm = new MockMultipartFile(
                "recruiterProfile", "", "application/json", "{}".getBytes()
        );

        mockMvc.perform(multipart("/recruiter-profile/addNew")
                .file(image)
                .flashAttr("recruiterProfile", recruiterProfile))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard/"));



    }



}
