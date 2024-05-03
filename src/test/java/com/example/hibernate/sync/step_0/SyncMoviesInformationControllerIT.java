package com.example.hibernate.sync.step_0;

import com.example.hibernate.BasicIT;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.example.hibernate.external.client.MovieInformationClient.getFakeMoviesInformation;

public class SyncMoviesInformationControllerIT extends BasicIT {
    @Test
    public void launch_sync_movies_information() throws Exception {

        var movieNumber = 1000;

        Mockito.doReturn(getFakeMoviesInformation(movieNumber))
                .when(movieInformationClient).getMoviesInformations();


        mvc.perform(
                        MockMvcRequestBuilders.post("/sync")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }
}