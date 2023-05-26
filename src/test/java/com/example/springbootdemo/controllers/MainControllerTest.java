package com.example.springbootdemo.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void reset() throws Exception {
        this.mockMvc.perform(delete("/"));
    }

    @Test
    void shouldGetHome() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void shouldDeleteHome() throws Exception {
        this.mockMvc.perform(delete("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetCategory() throws Exception {
        this.mockMvc.perform(get("/category")
                        .param("id", "0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetCategory() throws Exception {
        String name = "Tested category name";

        this.mockMvc.perform(post("/category")
                .param("name", name));

        this.mockMvc.perform(get("/category")
                        .param("id", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(0))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void shouldPostCategory() throws Exception {
        String name = "Tested category name";
        this.mockMvc.perform(post("/category")
                        .param("name", name))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(0))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void shouldNotPutCategory() throws Exception {
        String name = "New tested category name";
        this.mockMvc.perform(put("/category")
                        .param("id", "0")
                        .param("name", name))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldPutCategory() throws Exception {
        String name = "New tested category name";

        this.mockMvc.perform(post("/category")
                .param("name", name));

        this.mockMvc.perform(put("/category")
                        .param("id", "0")
                        .param("name", name))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(0))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void shouldNotDeleteCategory() throws Exception {
        this.mockMvc.perform(delete("/category")
                        .param("id", "0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        String name = "Tested category name";

        this.mockMvc.perform(post("/category")
                .param("name", name));

        this.mockMvc.perform(delete("/category")
                        .param("id", "0"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void shouldNotGetItem() throws Exception {
        this.mockMvc.perform(get("/item")
                        .param("category_id", "0")
                        .param("id", "0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetItem() throws Exception {
        String name = "Tested item name";

        this.mockMvc.perform(post("/category")
                .param("name", ""));
        this.mockMvc.perform(post("/item")
                .param("category_id", "0")
                .param("name", name));

        this.mockMvc.perform(get("/item")
                        .param("category_id", "0")
                        .param("id", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category_id").value(0))
                .andExpect(jsonPath("$.item_id").value(0))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void shouldPostItem() throws Exception {
        String name = "Tested item name";

        this.mockMvc.perform(post("/category")
                .param("name", ""));

        this.mockMvc.perform(post("/item")
                        .param("category_id", "0")
                        .param("name", name))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category_id").value(0))
                .andExpect(jsonPath("$.item_id").value(0))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void shouldNotPutItem() throws Exception {
        String name = "New tested item name";

        this.mockMvc.perform(post("/category")
                .param("name", ""));

        this.mockMvc.perform(put("/item")
                        .param("category_id", "0")
                        .param("id", "0")
                        .param("name", name))
                .andDo(print())
                .andExpect(status().is(454));
    }

    @Test
    void shouldPutItem() throws Exception {
        String name = "New tested item name";

        this.mockMvc.perform(post("/category")
                .param("name", ""));
        this.mockMvc.perform(post("/item")
                .param("category_id", "0")
                .param("name", ""));

        this.mockMvc.perform(put("/item")
                        .param("category_id", "0")
                        .param("id", "0")
                        .param("name", name))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category_id").value(0))
                .andExpect(jsonPath("$.item_id").value(0))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void shouldNotDeleteItem() throws Exception {
        this.mockMvc.perform(post("/category")
                .param("name", ""));

        this.mockMvc.perform(delete("/item")
                        .param("category_id", "0")
                        .param("id", "0"))
                .andDo(print())
                .andExpect(status().is(454));
    }

    @Test
    void shouldDeleteItem() throws Exception {
        String name = "Tested item name";

        this.mockMvc.perform(post("/category")
                .param("name", name));
        this.mockMvc.perform(post("/item")
                .param("category_id", "0")
                .param("name", ""));

        this.mockMvc.perform(delete("/item")
                        .param("category_id", "0")
                        .param("id", "0"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}