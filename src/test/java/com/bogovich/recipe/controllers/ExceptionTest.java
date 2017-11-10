package com.bogovich.recipe.controllers;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class ExceptionTest {
    static void testGet(MockMvc mockMvc, String url, ResultMatcher status) throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status)
                .andExpect(view().name("errorPage"))
                .andExpect(model().attributeExists("exception", "errorCode", "errorTitle"));
    }
}
