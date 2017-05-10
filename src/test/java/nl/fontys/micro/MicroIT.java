package nl.fontys.micro;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.jayway.jsonpath.JsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Micro.class)
@WebAppConfiguration
public class MicroIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void creates_account() throws Exception {
        mockMvc.perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"John Doe\", \"password\": \"test123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"username\": \"John Doe\"}"));
    }

    @Test
    public void updates_account() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"John Doe\", \"password\": \"test123\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        Integer id = JsonPath.read(contentAsString, "$.id");

        mockMvc.perform(put("/account/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"Jane Doe\", \"password\": \"test456\", \"currentPassword\": \"test123\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"username\": \"Jane Doe\"}"));
    }
}