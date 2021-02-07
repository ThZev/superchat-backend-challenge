package com.superchat.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne

@Entity
class MessageEntity : PanacheEntity() {

    @CreationTimestamp
    lateinit var date: LocalDateTime

    lateinit var message: String

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    var contactEntity: ContactEntity = ContactEntity()

    companion object: PanacheCompanion <MessageEntity> {
        fun saveMessage(messageEntity: MessageEntity) = persist(messageEntity)
    }
}