package pl.anastazjaglowska.jobportal.controller;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import pl.anastazjaglowska.jobportal.entity.JobSeekerProfile;
import pl.anastazjaglowska.jobportal.entity.Users;
import pl.anastazjaglowska.jobportal.entity.UsersTest;
import pl.anastazjaglowska.jobportal.repository.UsersRepository;
import pl.anastazjaglowska.jobportal.services.JobSeekerProfileService;

import java.util.Optional;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.security.test.context.support.WithMockUser;


@WebMvcTest(controllers = JobSeekerProfileController.class)
//@AutoConfigureMockMvc(addFilters = false)
//@MockBean({UsersServiceImpl.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc(addFilters = false)

class JobSeekerProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private JobSeekerProfileService jobSeekerProfileService;

    @Test
    @Order(1)
    @WithMockUser(username = "test@test.com")
    void testJobSeekerProfile_whenUserIsFound_returnCorrectModel() throws Exception {
        Users user = new Users();
        user.setUserId(1);
        user.setEmail("test@test.com");

        JobSeekerProfile profile = new JobSeekerProfile();
        profile.setUserAccountId(1);
        profile.setSkills(new ArrayList<>());

        Mockito.when(usersRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        Mockito.when(jobSeekerProfileService.getOne(1))
                .thenReturn(Optional.of(profile));

        mockMvc.perform(get("/job-seeker-profile/"))
                .andExpect(status().isOk())
                .andExpect(view().name("job-seeker-profile"))
                .andExpect(model().attributeExists("skills"))
                .andExpect(model().attributeExists("profile"));
    }

    @Test
    @Order(2)
    @WithMockUser(username = "test@test.com")
    void testAddNew_whenValidDataProvided_addNewJobSeekerInformation() throws Exception {
        //arrange
        Users user = new Users();
        user.setUserId(1);
        user.setEmail("test@test.com");

        JobSeekerProfile profile = new JobSeekerProfile();
        profile.setUserAccountId(1);
        profile.setSkills(new ArrayList<>());

        Mockito.when(usersRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        Mockito.when(jobSeekerProfileService
                .addNew(Mockito.any(JobSeekerProfile.class))).thenReturn(profile);

        MockMultipartFile image = new MockMultipartFile(
                "image",
                "photo.jpg",
                "image/jpeg",
                "fake-image-content".getBytes()
        );

        MockMultipartFile pdf = new MockMultipartFile(
                "pdf",
                "cv.pdf",
                "application/pdf",
                "fake-image-content".getBytes()
        );

        MockMultipartFile dummyForm = new MockMultipartFile(
                "jobSeekerProfile", "", "application/json", "{}".getBytes()
        );

        //act and assert

        mockMvc.perform(multipart("/job-seeker-profile/addNew")
                .file(image)
                .file(pdf)
                .param("skills[0].name", "Java")
                .flashAttr("jobSeekerProfile", profile))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard/"));





    }
}

//
//@GetMapping("/")
//public String JobSeekerProfile(Model model) {
//    JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    List<Skills> skills = new ArrayList<>();
//
//    if(!(authentication instanceof AnonymousAuthenticationToken)) {
//        Users user = usersRepository.findByEmail(authentication.getName()).orElseThrow(() ->
//                new UsernameNotFoundException("User not found"));
//        Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
//        if(seekerProfile.isPresent()) {
//            jobSeekerProfile = seekerProfile.get();
//            if(jobSeekerProfile.getSkills().isEmpty()){
//                skills.add(new Skills());
//                jobSeekerProfile.setSkills(skills);
//            }
//        }
//        model.addAttribute("skills", skills);
//        model.addAttribute("profile", jobSeekerProfile);
//    }
//
//    return "job-seeker-profile";
//
//}
