package org.sapphon.foiltray;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public void testDefaultMotionsExist() throws Exception {
        mockMvc.perform(get("/api/v1/motions")).andExpect(status().isOk()).andExpect(content().json("{" +
                "\"animationMotions\":[{\"id\":1,\"name\":\"Idle\"},{\"id\":2,\"name\":\"Walk\"},{\"id\":3,\"name\":\"Jump\"},{\"id\":4,\"name\":\"Get Item\"},{\"id\":5,\"name\":\"Use Item\"},{\"id\":6,\"name\":\"Talk\"},{\"id\":7,\"name\":\"Hit\"},{\"id\":8,\"name\":\"Shoot\"}]" +
                "}"));
    }

    @Test
    public void testDefaultCharactersExist() throws Exception {
        mockMvc.perform(get("/api/v1/characters")).andExpect(status().isOk()).andExpect(content().json("[" +
                "{\"id\":9,\"name\":\"Player\"}," +
                "{\"id\":10,\"name\":\"Friend\"}," +
                "{\"id\":11,\"name\":\"Enemy\"}" +
                "]"));
    }

    @Test
    public void testDefaultRolesExist() throws Exception {
        mockMvc.perform(get("/api/v1/roles"))
                .andExpect(content().json("{\"roleNames\":[" +
                        "\"Character Artist\"," +
                        "\"Environment Artist\"," +
                        "\"Gameplay Designer\"," +
                        "\"Level Designer\"," +
                        "\"Sound Designer\"" +
                        "]}"))
                .andExpect(status().isOk());
    }
}
