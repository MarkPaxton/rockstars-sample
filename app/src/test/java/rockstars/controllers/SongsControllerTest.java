package rockstars.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SongsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getSong() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/rockstars/song/191").accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string(endsWith(",\"Name\":\"There's No Other Way\",\"Id\":191,\"Year\":1991,\"Duration\":208276,\"Artist\":\"Blur\",\"ShortName\":null,\"Bpm\":110,\"Genre\":\"Alternative\",\"SpotifyId\":\"2Ly3gfkiVkbrtkWyhg5n2s\",\"Album\":\"Leisure\"}")));
    }

    @Test
    void getSongNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/rockstars/song/66666").accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
    }

    @Test
    void getSongs() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/rockstars/songs").accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    void getSongsBeforeYear() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/rockstars/songs/before/1960").accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    void postSong() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/rockstars/song")
                        .content("{\"Name\":\"Mark test-" + Math.random() + "\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void postSongThatExists() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/rockstars/song")
                        .content("{\"Id\":191,\"Name\":\"test\",\"Year\":1991,\"Duration\":208276,\"ShortName\":null,\"Bpm\":110,\"Genre\":\"Alternative\",\"SpotifyId\":\"2Ly3gfkiVkbrtkWyhgdd2s\",\"Album\":\"Leisure\",\"Artist\":\"Blur\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void putSong() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/rockstars/song")
                        .content("{\"Id\":67,\"Name\":\"test\",\"Year\":1991,\"Duration\":208276,\"ShortName\":null,\"Bpm\":110,\"Genre\":\"Alternative\",\"SpotifyId\":\"2Ly3gfkiVkbrtkWyhgdd2s\",\"Album\":\"Leisure\",\"Artist\":\"Blur\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void putSongNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/rockstars/song")
                        .content("{\"Id\": 6777777,\"Name\":\"test\",\"Year\":1991,\"Duration\":208276,\"ShortName\":null,\"Bpm\":110,\"Genre\":\"Alternative\",\"SpotifyId\":\"2Ly3gfkiVkbrtkWyhgdd2s\",\"Album\":\"Leisure\",\"Artist\":\"Blur\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
    }

    @Test
    void deleteSong() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/rockstars/song/110"))
                        .andExpect(status().isOk());
    }

    @Test
    void deleteSongNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/rockstars/song/6777777")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
    }
}
