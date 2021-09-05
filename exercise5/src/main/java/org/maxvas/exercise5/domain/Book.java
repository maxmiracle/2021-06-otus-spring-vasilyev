package org.maxvas.exercise5.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class Book {
    private UUID id;
    private String title;
    private UUID authorId;
    private UUID genreId;
}
