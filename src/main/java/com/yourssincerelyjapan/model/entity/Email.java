package com.yourssincerelyjapan.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "emails")
public class Email extends BaseEntity {

    @Column(name = "from_email")
    private String fromEmail;

    @Column(name = "to_email")
    private String toEmail;

    @Column(name = "subject")
    private String subjectEmail;

    @Column(name = "content")
    private String contentEmail;

    @Column
    private boolean sent;

    @Column
    private boolean received;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "received_on")
    private LocalDateTime receivedOn;
}
