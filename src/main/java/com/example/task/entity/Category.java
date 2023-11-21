package com.example.task.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;
    @ManyToOne()
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    User user;
    @Column(name = "name")
    String name;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    Status status = Status.ACTIVE;
    @CreationTimestamp
    LocalDate createdDate;
}
