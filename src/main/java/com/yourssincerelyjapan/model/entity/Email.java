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

    @Column(name = "email_from")
    private String emailFrom;

    @Column(name = "email_to")
    private String emailTo;

    @Column(name = "subject")
    private String emailSubject;

    @Column(name = "content")
    private String emailContent;

    @Column
    private boolean sent;

    @Column
    private boolean received;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "received_on")
    private LocalDateTime receivedOn;
}
