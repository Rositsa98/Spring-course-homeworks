package com.fmi.course.spring.homework.one.model;

import com.fmi.course.spring.homework.one.enums.UserRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;

@Document("users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User{

    @Id
    private String id;

    @NonNull
    private String name;
    private String family;

    private String email;

    @NonNull
    @Size(min=8)
    private String password;

    @NonNull
    private UserRole role;

    private String picUrl;


}
