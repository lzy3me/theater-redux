package com.lzy3me.theater.controller;

import com.lzy3me.theater.model.Movies;
import com.lzy3me.theater.repository.MoviesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping(path = "/movies")
public class MovieController {
    @Autowired
    private MoviesRepo m_repo;

    @PostMapping(path = "/create")
    public @ResponseBody Movies addMovie(@RequestBody Movies addBody) {
        return m_repo.save(addBody);
    }

    @GetMapping(path = "/read")
    public @ResponseBody Iterable<?> fetchMovies() {
        return m_repo.findAll();
    }

    @GetMapping(path = "/read/{id}")
    public @ResponseBody Object fetchMovie(@PathVariable UUID id) {
        return m_repo.findById(id);
    }

    @PutMapping(path = "/update/{id}")
    public @ResponseBody Movies updateMovie(@PathVariable UUID id, @RequestBody Movies updateBody) {
        return m_repo.findById(id).map(movie -> {
           movie.setM_name(updateBody.getM_name());
           movie.setM_desc(updateBody.getM_desc());
           movie.setM_release(updateBody.getM_release());
           movie.setM_length(updateBody.getM_length());
           movie.setM_tag(updateBody.getM_tag());
           movie.setM_starring(updateBody.getM_starring());

           return m_repo.save(movie);
        }).orElseGet(() -> {
            updateBody.setId(id);
            return m_repo.save(updateBody);
        });
    }

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody void deleteMovie(@PathVariable UUID id) {
        m_repo.deleteById(id);
    }
}
