package rockstars.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import rockstars.api.models.IArtist;
import rockstars.api.services.IArtistService;
import rockstars.api.services.ServiceException;
import rockstars.core.models.Artist;

@RestController
@RequestMapping("/rockstars")
public class ArtistController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ArtistController.class);
    private final IArtistService artistService;

    @Autowired
    public ArtistController(IArtistService artistService) {
        this.artistService = artistService;
    }

    @PostMapping("artist")
    @ResponseStatus(HttpStatus.CREATED)
    public IArtist postArtist(@RequestBody Artist artist) throws ServiceException {
        return artistService.add(artist);
    }

    @GetMapping("artists")
    public List<IArtist> getArtist() {
        return artistService.getAll();
    }

    @GetMapping("artist/{id}")
    public ResponseEntity<IArtist> getArtist(@PathVariable Long id) {
        Optional<IArtist> artist = artistService.get(id);
        if (artist.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(artist.get());
    }

    @GetMapping("artist/name/{name}")
    public ResponseEntity<IArtist> getArtistByName(@PathVariable String name) {
        List<IArtist> artist = artistService.getByName(name);
        if (artist.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(artist.get(0));
    }


    @DeleteMapping("artist/{id}")
    public Long deleteArtist(@PathVariable Long id) throws Exception {
        artistService.delete(id);
        return id;
    }

    @PutMapping("artist")
    public IArtist putArtist(@RequestBody Artist artist) throws Exception {
        return artistService.update(artist);
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
