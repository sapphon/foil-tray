package org.sapphon.foiltray;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FoilTrayApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void contextLoads() {
    }

    @Test
    public void defaultMotionsExist() throws Exception {
        mockMvc.perform(get("/api/v1/motions")).andExpect(status().isOk()).andExpect(content().json("{" +
                "\"animationMotions\":[{\"id\":1,\"name\":\"Idle\"},{\"id\":2,\"name\":\"Walk\"},{\"id\":3,\"name\":\"Jump\"},{\"id\":4,\"name\":\"Get Item\"},{\"id\":5,\"name\":\"Use Item\"},{\"id\":6,\"name\":\"Talk\"},{\"id\":7,\"name\":\"Hit\"},{\"id\":8,\"name\":\"Shoot\"}]" +
                "}"));
    }

}
