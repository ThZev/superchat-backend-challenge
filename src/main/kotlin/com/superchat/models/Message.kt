package com.superchat.models

import com.superchat.entities.MessageEntity
import io.quarkus.runtime.annotations.RegisterForReflection
import java.time.LocalDateTime

@RegisterForReflection
data class Message(
    val date : LocalDateTime,
    val name: String,
    val message: String
) {
    companion object {

        fun getMessageList(messageEntityList: List<MessageEntity>): List<Message> {
            return messageEntityList.map { Message of it }
        }

        infix fun of (messageEntity: MessageEntity): Message {
            return Message(
                date = messageEntity.date,
                name = messageEntity.contactEntity.name,
                message = messageEntity.message
            )
        }
    }
}

