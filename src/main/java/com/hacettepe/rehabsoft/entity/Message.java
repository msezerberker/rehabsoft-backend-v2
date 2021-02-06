package com.hacettepe.rehabsoft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "idgen", sequenceName = "message_seq", initialValue = 1, allocationSize = 1)
public class Message  extends BaseEntity{

        @Column(name = "message_content")
        private String messageContent;

        @Column(name = "message_title")
        private String messageTitle;

        @ManyToOne
        @JoinColumn(name = "sender_users_id")
        private User senderUser;

        @ManyToOne
        @JoinColumn(name = "receiver_users_id")
        private User receiverUser;


}
