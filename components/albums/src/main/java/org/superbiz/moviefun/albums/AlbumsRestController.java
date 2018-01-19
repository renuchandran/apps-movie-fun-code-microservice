package org.superbiz.moviefun.albums;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums-rest")
public class AlbumsRestController {
    private AlbumsRepository albumsRepository;
    public AlbumsRestController(AlbumsRepository albumsRepository) {
        this.albumsRepository = albumsRepository;
    }
    @PostMapping
    public void addMovie(@RequestBody Album album){
        albumsRepository.addAlbum(album);
    }

    @GetMapping
    public List<Album> getAlbums(){
        return albumsRepository.getAlbums();
    }

    @GetMapping("/{albumId}")
    public Album find(@PathVariable int albumId){
        System.out.println("GOD Rest Id"+albumId);
        return albumsRepository.find(albumId);
    }
}
