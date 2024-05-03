package com.example.hibernate.old;

import com.example.hibernate.BasicIT;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.example.hibernate.external.client.MovieInformationClient.getFakeMoviesInformation;

public class SyncMoviesInformationControllerIT extends BasicIT {
    @Test
    public void launch_sync_movies_information() throws Exception {

        var movieNumber = 1000;

        Mockito.doReturn(getFakeMoviesInformation(movieNumber))
                .when(movieInformationClient).getMoviesInformations();


        var response = mvc.perform(
                        MockMvcRequestBuilders.post("/sync")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andReturn()
                .getResponse();
    }
}


@ActiveProfiles("batching")
@Sql("/db/init_db_for_insert.sql")
class WithBatchInsert extends BasicIT {
    @Test
    public void launch_sync_movies_information() throws Exception {
        var movieNumber = 1000;

        Mockito.doReturn(getFakeMoviesInformation(movieNumber))
                .when(movieInformationClient).getMoviesInformations();

        // 2,848 secondes
        var response = mvc.perform(
                        MockMvcRequestBuilders.post("/sync")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andReturn()
                .getResponse();
    }
}


@ActiveProfiles({"batching", "stateless_session"})
@Sql("/db/init_db_for_insert.sql")
class WithStatelessSession extends BasicIT {
    @Test
    public void launch_sync_movies_information() throws Exception {

        var movieNumber = 1000; // 1810 (75MB)
//        var movieNumber = 5000; // 1810 (75MB)
//        var movieNumber = 10000; // 1810 (75MB)
//        var movieNumber = 15000; // 1810 (75MB)
//        var movieNumber = 20000; // 1810 (75MB)

        Mockito.doReturn(getFakeMoviesInformation(movieNumber))
                .when(movieInformationClient).getMoviesInformations();

        // 2300
        var response = mvc.perform(
                        MockMvcRequestBuilders.post("/sync")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andReturn()
                .getResponse();
    }
}
