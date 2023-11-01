package com.fastcampus.sns.model;

import com.fastcampus.sns.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import java.sql.Timestamp;

// TODO : implement
@AllArgsConstructor
@Getter
public class User {

    private Integer id;
    private String username;
    private String password;
    private UserRole userRole;
    private Timestamp registeredAt;
    private Timestamp updatedAt;

    private Timestamp deletedAt;

    public static User fromEntity(UserEntity entity){
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getRole(),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt());
    }
}
