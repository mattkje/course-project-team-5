package no.ntnu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

  @Autowired
  private MockMvc mvc;


  @Test
  void indexWhenNotAuthorized() throws Exception {
    this.mvc.perform(get("/"))
        .andExpect(status().isOk());
  }

  @WithMockUser(authorities="USER")
  @Test
  void indexWhenAuthorized() throws Exception {
    this.mvc.perform(get("/"))
        .andExpect(status().isOk());
  }

}