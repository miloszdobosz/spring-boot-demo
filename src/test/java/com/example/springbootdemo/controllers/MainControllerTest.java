package com.example.springbootdemo.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest
class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

//    String accessToken = """
//                {
//                "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ii1LSTNROW5OUjdiUm9meG1lWm9YcWJIWkdldyIsImtpZCI6Ii1LSTNROW5OUjdiUm9meG1lWm9YcWJIWkdldyJ9.eyJhdWQiOiJodHRwczovL21hbmFnZW1lbnQuY29yZS53aW5kb3dzLm5ldC8iLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80ZDk3ZGYwMC1lNTUzLTRmMzUtODFjOC1hMWJmNTMwMWQ5ZjkvIiwiaWF0IjoxNjg4MTM0NDI0LCJuYmYiOjE2ODgxMzQ0MjQsImV4cCI6MTY4ODEzOTA2NiwiYWNyIjoiMSIsImFpbyI6IkFZUUFlLzhUQUFBQXk0V2xsNmx1SU8wZkhDaml1T3c0SUpTMWloR2k2UGM4MnZXZW00VGN3RHlkWVlLaXRPcU1sODhWUy9DMjZZWWR1SFkraHdSMTI0TWRoRndZanV0ZkdFdFdIVmhpUVBsRWJwZ0czZk9ReklKZXZ5d1FFTE5YR24xcmFVQkY3OUtYQWVnbHdocE1hOUlXWDVmNlVBMmZZN2FRd0ZMWEJEVHJNVVdEZjh0b29jOD0iLCJhbHRzZWNpZCI6IjE6bGl2ZS5jb206MDAwM0JGRkQ2NERFMjM3MSIsImFtciI6WyJwd2QiLCJtZmEiXSwiYXBwaWQiOiJiNjc3YzI5MC1jZjRiLTRhOGUtYTYwZS05MWJhNjUwYTRhYmUiLCJhcHBpZGFjciI6IjAiLCJlbWFpbCI6Im1pbG9zei5qYWt1Yi5kb2Jvc3pAZ21haWwuY29tIiwiZmFtaWx5X25hbWUiOiJEb2Jvc3oiLCJnaXZlbl9uYW1lIjoiTWlsb3N6IiwiZ3JvdXBzIjpbIjdkNDI5M2FjLWU0ZDUtNDEwOC1hZmUyLTY0OGQxZWQ4MWQzNiJdLCJpZHAiOiJsaXZlLmNvbSIsImlwYWRkciI6IjJhMDA6ZjQxOjU4Zjc6ZmE0ZDo1NjVmOmQ5OTA6NzJhNzpmNjQ1IiwibmFtZSI6Ik1pbG9zeiBEb2Jvc3oiLCJvaWQiOiIyYmMwOGViYS1kOWJlLTRiNDUtODlhZS1hYzY4ZmM3YmM0ODEiLCJwdWlkIjoiMTAwMzIwMDJCMUQ1QzU0MSIsInJoIjoiMC5BVTRBQU4tWFRWUGxOVS1CeUtHX1V3SFotVVpJZjNrQXV0ZFB1a1Bhd2ZqMk1CT0RBTkEuIiwic2NwIjoidXNlcl9pbXBlcnNvbmF0aW9uIiwic3ViIjoiQU1VSW9fTVZ2NHlsZDJKZ2ppUS1RVmZRNXMxRnVOZWRtNDFjX3hISEtsYyIsInRpZCI6IjRkOTdkZjAwLWU1NTMtNGYzNS04MWM4LWExYmY1MzAxZDlmOSIsInVuaXF1ZV9uYW1lIjoibGl2ZS5jb20jbWlsb3N6Lmpha3ViLmRvYm9zekBnbWFpbC5jb20iLCJ1dGkiOiJMbnhJb0oybXlrZWtpdEdkeUNkTUFBIiwidmVyIjoiMS4wIiwid2lkcyI6WyI2MmU5MDM5NC02OWY1LTQyMzctOTE5MC0wMTIxNzcxNDVlMTAiLCJiNzlmYmY0ZC0zZWY5LTQ2ODktODE0My03NmIxOTRlODU1MDkiXSwieG1zX3RjZHQiOjE2ODY4MzA0NzZ9.cGfLJdL3uO6pxbIp-W2BQywqPk5LelGWohk60yq9dKDb3uoIK2mR0iuzdIab4j-puKgqWtqW0QgvMQhm4uoRf498XK2fcn_3FS7yUT-ERfkMTjBPAnWGazfk4dleEN_tKrp3nJgzE7Dy6d4uswHcF5U3ZE5ZNgP-55nQ1aJrc7VfIDcSMlEfM-2u3EUWBU6_NlAK_sBezo8faL9pv-ksPN9qPFzj8oZKJ_FphRsrU8wtLElwiushC7_jFMfLpoaUhMh8QC6m_qM0nDzV1M1nwrcjVBqxIVZVy3euhP8VXBOhC_PfG3MK0i1sxh4S-fRw7TaJ2vB5r92XuT3-oPLnCw",
//                    "expiresOn": "2023-06-30 15:31:06.000000",
//                    "subscription": "10992cd5-0b8c-4f16-9cc9-4dcb64081b43",
//                    "tenant": "4d97df00-e553-4f35-81c8-a1bf5301d9f9",
//                    "tokenType": "Bearer"
//                    }
//            """;

    @BeforeEach
    void reset() throws Exception {
        this.mockMvc.perform(delete("/"));
    }

    @Test
    void shouldGetHome() throws Exception {
        this.mockMvc.perform(
                        get("/")
                )
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