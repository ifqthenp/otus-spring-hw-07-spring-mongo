package com.otus.hw_07.domain;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "authors")
public class Author {

    @Id
    @SerializedName("_id")
    private String id;

    private String firstName;

    private String lastName;

}
