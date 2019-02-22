package com.otus.hw_07.repositories;

import com.otus.hw_07.domain.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {

    @Query("{'genreName': { $regex: ?0, $options: 'i' }}")
    List<Genre> findGenresByName(String genre);

}
