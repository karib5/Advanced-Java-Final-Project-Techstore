package com.techstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("categories")
public class Category {
    @Id
    private Long categoryId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}