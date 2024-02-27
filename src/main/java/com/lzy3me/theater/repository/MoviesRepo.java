package com.lzy3me.theater.repository;

import com.lzy3me.theater.model.Movies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MoviesRepo extends JpaRepository<Movies, UUID> {
}
