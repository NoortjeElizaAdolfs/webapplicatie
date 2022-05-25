package com.example.webapplicatie.endpointstest;

import com.example.webapplicatie.models.ERole;
import com.example.webapplicatie.models.Role;
import com.example.webapplicatie.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.Set;

import static com.example.webapplicatie.models.ERole.ROLE_USER;

@SpringBootTest
@AutoConfigureMockMvc
//@WithMockUser()
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void ShouldSigninReturnUserDetails() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setUsername("Annalies");
        user.setPassword("Wachtwoord!");

        String json = objectMapper.writeValueAsString(user);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/auth/signin").
                        contentType(MediaType.APPLICATION_JSON).content(json).characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Annalies"));
    }
}
