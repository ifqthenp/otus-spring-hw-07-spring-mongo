package com.otus.hw_07.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.otus.hw_07.domain.Author;
import com.otus.hw_07.domain.Book;
import com.otus.hw_07.domain.Genre;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Slf4j
@ChangeLog
public class LibraryChangeLog {

    private static final String DATABASE = "library";
    private static final String AUTHORS = "authors";
    private static final String GENRES = "genres";
    private static final String BOOKS = "books";
    private static final String AUTHORS_JSON_RESOURCE = "json/authors.json";
    private static final String GENRES_JSON_RESOURCE = "json/genres.json";
    private static final String BOOKS_JSON_RESOURCE = "json/books.json";

    private static CodecRegistry codecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
        fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    @ChangeSet(author = "user", id = "dropDb", order = "001", runAlways = true)
    public void dropDb(final MongoTemplate template) {
        template.getDb().drop();
        log.info("Dropped database {}", DATABASE);
    }

    @ChangeSet(author = "user", id = "createGenres", order = "020")
    public void createGenres(final MongoTemplate template) {
        final MongoCollection<Document> collection =
            template.getDb().getCollection(GENRES);

        final Path pathToGenresJson = getPath(GENRES_JSON_RESOURCE);
        final String jsonFromFile = readFileToString(pathToGenresJson);
        final Genre[] genres = new Gson().fromJson(jsonFromFile, Genre[].class);

        for (Genre g : genres) {
            collection.insertOne(new Document()
                .append("_id", new ObjectId(g.getId()))
                .append("genreName", g.getGenreName())
            );
        }
        log.info("Created collection {}", GENRES);
    }

    @ChangeSet(author = "user", id = "createAuthors", order = "010")
    public void createAuthors(final MongoTemplate template) {
        final MongoCollection<Document> collection =
            template.getDb().getCollection(AUTHORS);

        final Path pathToAuthorsJson = getPath(AUTHORS_JSON_RESOURCE);
        final String jsonFromFile = readFileToString(pathToAuthorsJson);
        final Author[] authors = new Gson().fromJson(jsonFromFile, Author[].class);

        for (Author a : authors) {
            collection.insertOne(new Document()
                .append("_id", new ObjectId(a.getId()))
                .append("firstName", a.getFirstName())
                .append("lastName", a.getLastName())
            );
        }
        log.info("Created collection {}", AUTHORS);
    }

    @ChangeSet(author = "user", id = "createBooks", order = "030")
    public void createBooks(final MongoTemplate template) {
        final MongoCollection<Document> collection =
            template.getDb().withCodecRegistry(codecRegistry).getCollection(BOOKS);

        final Path booksJsonPath = getPath(BOOKS_JSON_RESOURCE);
        final String jsonFromFile = readFileToString(booksJsonPath);
        final Book[] books = new Gson().fromJson(jsonFromFile, Book[].class);

        Arrays.stream(books).forEach(b -> {
                System.out.print(b.getId() + " " + b.getAuthors() + " ");
                System.out.print(b.getComments() + " " + b.getGenres() + " ");
                System.out.println(b.getTitle() + " " + b.getYear() + " ");
            }
        );

        for (Book b : books) {
            collection.insertOne(new Document()
                .append("_id", new ObjectId(b.getId()))
                .append("authors", b.getAuthors())
                .append("comments", b.getComments())
                .append("genres", b.getGenres())
                .append("title", b.getTitle())
                .append("year", b.getYear())
            );
        }
        log.info("Created collection {}", BOOKS);
    }

    public String readFileToString(final Path path) {
        String result = "";
        try (Stream<String> lines = Files.lines(path, Charset.forName("UTF-8"))) {
            result = lines
                .map(String::trim)
                .collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Path getPath(final String resource) {
        try {
            return Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                .getResource(resource)).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

}
