package com.hacettepe.rehabsoft.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "online_meeting")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "online_meeting_seq", initialValue = 1, allocationSize = 1)
public class OnlineMeeting extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "doctor_user_id")
    private User doctorUser;

    @ManyToOne
    @JoinColumn(name = "patient_user_id")
    private User patientUser;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "meeting_date")
    private LocalDateTime meetingDate;
}
