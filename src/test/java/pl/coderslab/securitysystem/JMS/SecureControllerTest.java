package pl.coderslab.securitysystem.JMS;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class SecureControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    void givenAuthRequest_onPrivateService_shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/secure")
                        .with(user("admin").roles("USER", "ADMIN")))
                .andExpect(status().isOk());
    }

    @Test
    void givenUnauthorizedRequest_onPrivateService_shouldReturn401() throws Exception {
        mockMvc.perform(get("/api/secure"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void whenUser_thenOk() throws Exception {
        mockMvc.perform(get("/api/secure"))
                .andExpect(status().isOk());
    }


}