package com.otus.hw_07.domain;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    @SerializedName("_id")
    private String id;

    private String title;

    private String year;

    private List<String> authors;

    private Set<String> genres;

    private List<Comment> comments;

}
