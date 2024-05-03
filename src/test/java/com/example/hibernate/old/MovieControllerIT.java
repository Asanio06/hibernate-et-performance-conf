package com.example.hibernate.old;

import com.example.hibernate.BasicIT;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class MovieControllerIT extends BasicIT {

    @Test
    public void should_return_none_movies_when_countryCodes_are_not_specify() throws Exception {
        insertFakeMovies(2);

        mvc.perform(
                        MockMvcRequestBuilders.get("/movies")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));

    }

    @Test
    public void should_return_all_movies_with_country_code() throws Exception {

            insertFakeMovies(1000); // Baisser le nombre d'

        var response = mvc.perform(
                        MockMvcRequestBuilders.get("/movies")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();

    }


}


@ActiveProfiles("join_fetch")
class WithFetchJoin extends BasicIT {

    @Test
    public void should_return_all_movies_with_country_code() throws Exception {
        insertFakeMovies(10); // 280
//                    insertFakeMovies(50); // 320
//                    insertFakeMovies(100); // 350
//                    insertFakeMovies(500); // 630
//                    insertFakeMovies(1000); // 770
//                    insertFakeMovies(5000); // 1966
//        insertFakeMovies(10000); // 3425

        var response = mvc.perform(
                        MockMvcRequestBuilders.get("/movies")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();

    }
}

@ActiveProfiles("multiple_query")
class WithMultipleQuery extends BasicIT {
    @Test
    public void should_return_all_movies_with_country_code() throws Exception {
        insertFakeMovies(10); // 220 250
//                    insertFakeMovies(50); // 220 260
//                    insertFakeMovies(100); // 230 263
//                    insertFakeMovies(500); // 240 279
//                    insertFakeMovies(1000); // 260 (35MB) 297 (40MB)
//                    insertFakeMovies(5000); // 270
//        insertFakeMovies(10000); // 279

        var response = mvc.perform(
                        MockMvcRequestBuilders.get("/movies")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();

    }
}


@ActiveProfiles("multiple_query_with_read_only")
class WithReadOnly extends BasicIT {
    @Test
    public void should_return_all_movies_with_country_code() throws Exception {

        insertFakeMovies(1000); // 260 (35MB)


        var response = mvc.perform(
                        MockMvcRequestBuilders.get("/movies")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse();

    }
}



