package ru.alishev.springcourse.FirstSecurityApp;

import org.checkerframework.checker.units.qual.A;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.alishev.springcourse.FirstSecurityApp.controllers.HelloController;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static  org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class FirstSecurityAppApplicationTests{
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private HelloController helloController;

	@Test
	public void accessDeniedTest() throws Exception{
		this.mockMvc.perform(get("/hello"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/auth/login"));
	}

	@Test
	@Ignore
	public void correctLogin() throws Exception{
		this.mockMvc.perform(formLogin().user("Lol").password("1242"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/auth/login"));

	}

	@Test
	public void badCredentials() throws Exception{
		this.mockMvc.perform(post("/auth/login").param("username", "Alfred"))
				.andDo(print())
				.andExpect(status().is4xxClientError());
	}







}
