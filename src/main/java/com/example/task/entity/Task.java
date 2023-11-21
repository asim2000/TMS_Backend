package com.example.task.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;
    @ManyToOne()
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    Category category;
    @Column(name = "title")
    String title;
    @Column(name = "description")
    String description;
    @Column(name = "deadline")
    LocalDate deadline;
    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    Priority priority;
    @Column(name = "progress")
    @Enumerated(EnumType.STRING)
    Progress progress = Progress.IN_PROGRESS;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    Status status = Status.ACTIVE;
    @CreationTimestamp
    LocalDate createdDate;

}
