package com.example.hibernate.movie.step_0;

import com.example.hibernate.BasicIT;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class MovieControllerIT extends BasicIT {

    @Test
    public void should_return_all_movies_with_country_code() throws Exception {

        insertFakeMovies(2);

        mvc.perform(
                        MockMvcRequestBuilders.get("/movies")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}