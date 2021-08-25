package com.yy.lightbulbroom;

import com.yy.lightbulbroom.controller.UserController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class LightbulbroomApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Test
    void contextLoads() {
        assertThat(userController).isNotNull();
    }

    @Test
    void mainPageTest() throws Exception {
        this.mockMvc.perform(get("/room/allRooms"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("allRooms"))
                .andExpect(content().string(containsString("All rooms")));
    }

    @Test
    void creatingRoomTest() throws Exception {
        this.mockMvc
                .perform(post("/room/new")
                        .param("roomName", "TestName")
                        .param("country", String.valueOf(1L)))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

}
