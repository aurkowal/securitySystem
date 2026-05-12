package pl.coderslab.securitysystem.JMS;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void allUsers_shouldBeAccessibleWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/all-users"))
                .andExpect(status().isOk())
                .andExpect(content().string("Dostęp do wszystkich użytkowników."));
    }


    @Test
    @WithMockUser
    void restricted_shouldRequireAuthentication() throws Exception {
        mockMvc.perform(get("/restrictedLogin"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void anonymousRestrictedAccessTest() throws Exception {
        mockMvc.perform(get("/restrictedLogin"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void restrictedAdmin_shouldAllowAdmin() throws Exception {
        mockMvc.perform(get("/restrictedAdmin"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void restrictedAdmin_shouldDenyUser() throws Exception {
        mockMvc.perform(get("/restrictedAdmin"))
                .andExpect(status().isForbidden());
    }

}
