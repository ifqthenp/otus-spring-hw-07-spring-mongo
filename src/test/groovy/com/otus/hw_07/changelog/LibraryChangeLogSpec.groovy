package com.otus.hw_07.changelog

import spock.lang.Specification
import spock.lang.Subject

import java.nio.file.Path
import java.nio.file.Paths

class LibraryChangeLogSpec extends Specification {

    @Subject
    LibraryChangeLog libraryChangeLog

    void setup() {
        libraryChangeLog = new LibraryChangeLog()
        assert libraryChangeLog != null
    }

    def "can read file to a string"() {
        given:
        def pathToResource = "/test-authors.json"
        def path = getPath(pathToResource)

        when:
        def result = libraryChangeLog.readFileToString(path)

        then:
        result.length() > 0
        result.endsWith('"de Cervantes"}]')
    }

    /**
     * Gets a path to the resource file on the test classpath
     *
     * @param resource name of the file
     * @return the path to the resource file
     */
    private Path getPath(String resource) {
        return Paths.get(getClass().getResource(resource).toURI())
    }
}
