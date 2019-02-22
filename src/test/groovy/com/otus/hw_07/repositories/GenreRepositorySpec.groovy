package com.otus.hw_07.repositories

import com.otus.hw_07.domain.Genre
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate
import spock.lang.Specification
import spock.lang.Subject

@DataMongoTest
class GenreRepositorySpec extends Specification {

    @Subject
    @Autowired
    GenreRepository genreRepo

    @Autowired
    MongoTemplate template

    def setup() {
        def genres = [
            new Genre('1', 'Fantasy'),
            new Genre('2', 'Science-Fiction'),
            new Genre('3', 'Popular Science'),
        ]
        genres.each { template.save(it) }
    }

    def "can find genre by name, case insensitive"() {
        given:
        def genreName = 'scienc'

        when:
        def genresResult = genreRepo.findGenresByName(genreName)

        then:
        genresResult.size() == 2
        genresResult.each {
            assert it.genreName ==~ /(?i).*$genreName.*/
        }
    }

    def cleanup() {
        template = null
        genreRepo = null
    }
}
