package com.otus.hw_07.library;

import com.otus.hw_07.domain.Author;
import com.otus.hw_07.domain.Book;
import com.otus.hw_07.domain.Genre;
import com.otus.hw_07.repositories.AuthorRepository;
import com.otus.hw_07.repositories.BookRepository;
import com.otus.hw_07.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {

    private AuthorRepository authorRepository;
    private GenreRepository genreRepository;
    private BookRepository bookRepository;

    @Autowired
    public LibraryService(final AuthorRepository authorRepository,
                          final GenreRepository genreRepository,
                          final BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> findBooksByTitle(final String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> findAuthorsByFirstName(final String firstName) {
        return authorRepository.findAuthorByFirstNameContainingIgnoreCase(firstName);
    }

    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    public List<Genre> findGenresByName(final String genreName) {
        return genreRepository.findGenresByName(genreName);
    }

}
