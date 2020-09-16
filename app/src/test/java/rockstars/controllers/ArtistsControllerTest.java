package rockstars.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ArtistsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getArtist() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/rockstars/artist/66").accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string(equalTo("{\"Id\":66,\"Name\":\"Black Tide\"}")));
    }

    @Test
    void getArtistByName() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/rockstars/artist/name/Blur")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string(equalTo("{\"Id\":71,\"Name\":\"Blur\"}")));
    }

    @Test
    void getArtistByUnknownName() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/rockstars/artist/name/BlurXXX")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
    }

    @Test
    void getArtistNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/rockstars/artist/66666").accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
    }

    @Test
    void getArtists() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/rockstars/artists").accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void postArtist() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/rockstars/artist")
                        .content("{\"Name\":\"Mark test-" + Math.random() + "\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void postBadArtist() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/rockstars/artist")
                        .content("{\"Name\": \"AC/DC\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void putArtist() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/rockstars/artist")
                        .content("{\"Id\": 67,\"Name\": \"Mark test\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void putArtistNotFund() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/rockstars/artist")
                        .content("{\"Id\": 6777777,\"Name\": \"Mark test\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void deleteArtist() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/rockstars/artist/68"))
                        .andExpect(status().isOk());
    }

    @Test
    void deleteArtistNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/rockstars/artist/6777777"))
                        .andExpect(status().isBadRequest());
    }
}
