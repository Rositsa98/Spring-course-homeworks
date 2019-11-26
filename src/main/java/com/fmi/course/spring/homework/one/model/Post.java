package com.fmi.course.spring.homework.one.model;

import com.fmi.course.spring.homework.one.enums.PostStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document("posts")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Post {

    @Id
    private String id;

    @NonNull
    private Date datePublished;

    @NonNull
    private String author;

    @NonNull
    private String title;

    private String content;

    private List<String> tags;

    private String picUrl;

    private PostStatus status = PostStatus.ACTIVE;

}
