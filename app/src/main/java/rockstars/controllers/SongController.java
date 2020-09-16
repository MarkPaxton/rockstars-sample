package rockstars.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import rockstars.api.models.ISong;
import rockstars.api.services.ISongService;
import rockstars.api.services.ServiceException;
import rockstars.core.models.Song;

@RestController
@RequestMapping("/rockstars")
public class SongController {

    private final static Logger LOGGER = LoggerFactory.getLogger(SongController.class);
    private final ISongService songService;

    @Autowired
    public SongController(ISongService songService) {
        this.songService = songService;
    }

    @PostMapping("song")
    @ResponseStatus(HttpStatus.CREATED)
    public ISong postSong(@RequestBody Song song) throws ServiceException {
        return songService.add(song);
    }

    @GetMapping("songs")
    public List<ISong> getSong() {
        return songService.getAll();
    }

    @GetMapping("songs/before/{year}")
    public List<ISong> getSongsBeforeYear(@PathVariable Long year) {
        return songService.getBeforeYear(year);
    }

    @GetMapping("songs/{genre}")
    public List<ISong> getSongsByGenre(@PathVariable String genre) {
        return songService.getByGenre(genre);
    }

    @GetMapping("song/{id}")
    public ResponseEntity<ISong> getSong(@PathVariable Long id) {
        Optional<ISong> song = songService.get(id);
        if (song.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(song.get());
    }

    @DeleteMapping("song/{id}")
    public ResponseEntity<String> deleteSong(@PathVariable Long id) throws Exception {
        try {
            songService.delete(id);
            return ResponseEntity.ok(id.toString());
        } catch (ServiceException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("song")
    public ISong putSong(@RequestBody Song song) throws Exception {
        return songService.update(song);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ResponseEntity handleException(ServiceException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
