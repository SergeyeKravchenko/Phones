package com.example;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PhonesApplicationTests {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();
    }


    @Test
    public void accessLoginThenOk() throws Exception {
        mockMvc.perform(get("/phones/login"))
                .andExpect(status().isOk());
    }

    @Test
    public void accessRegistrationThenOk() throws Exception {
        mockMvc.perform(get("/phones/registration"))
                .andExpect(status().isOk());
    }

    @Test
    public void accessSecuredPageBookUnauthenticatedThenRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/phones/book"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    public void accessSecuredPageErrorUnauthenticatedThenRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/phones/error"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    public void accessSecuredPageNewfieldUnauthenticatedThenRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/phones/newfield"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    public void accessSecuredPageSuccessUnauthenticatedThenRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/phones/success"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    public void accessSecuredUpdatefieldUnauthenticatedThenRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/phones/updatefield"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

}
