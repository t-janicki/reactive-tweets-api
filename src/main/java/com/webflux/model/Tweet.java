package com.webflux.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tweets")
@ToString
public class Tweet {
    @Id
    private String id;
    private String text;
    private Date createdAt = new Date();

}
