package com.otus.hw_07.domain;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Document(collection = "books")
public class Book {

    @Id
    @SerializedName("_id")
    private final String id;

    private final String title;

    private final String year;

    private final List<String> authors;

    private final Set<String> genres;

    private List<Comment> comments;

}
