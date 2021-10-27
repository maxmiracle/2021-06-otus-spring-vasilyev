package org.maxvas.exercise.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Genre {
    @Id
    private UUID id;

    @Field
    private String name;
}
