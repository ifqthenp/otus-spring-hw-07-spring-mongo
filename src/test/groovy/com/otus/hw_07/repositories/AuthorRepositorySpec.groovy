package com.otus.hw_07.repositories

import com.otus.hw_07.domain.Author
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.MongoTemplate
import spock.lang.Specification

@DataMongoTest
class AuthorRepositorySpec extends Specification {

    @Autowired
    AuthorRepository authorRepo

    @Autowired
    MongoTemplate template

    def setup() {
        def authors = [
            new Author('1', 'Leo', 'Tolstoy'),
            new Author('2', 'Leonardo', 'Fibonacci'),
            new Author('3', 'Lewis', 'Carrol')
        ]
        authors.each { template.save(it) }
    }

    def "can find authors by their full name, case insensitive"() {
        given:
        def name = 'leo'

        when:
        def authors = authorRepo.findAuthorByFirstNameContainingIgnoreCase(name)

        then:
        authors.size() == 2
        authors.get(0).firstName == 'Leo'

    }

    def cleanup() {
        template = null
        authorRepo = null
    }
}
