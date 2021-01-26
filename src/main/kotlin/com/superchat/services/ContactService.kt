package com.superchat.services

import com.superchat.util.ObjectMapperUtil
import com.superchat.entities.ContactEntity
import com.superchat.models.Message
import com.superchat.util.MessagingUtil.sendHttpResponse
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional
import javax.ws.rs.core.Response

@ApplicationScoped
class ContactService {

    @Transactional
    fun createContact(contactEntity: ContactEntity): Response {
        ContactEntity.saveContact(contactEntity)
        val responseJsonPair = ObjectMapperUtil.toJsonPair("created", "${contactEntity.id}")
        return sendHttpResponse(Response.Status.CREATED, responseJsonPair)
    }

    fun listContacts(): Response {
        val contactEntityList = ContactEntity.getContactEntities()

        if (contactEntityList.isNullOrEmpty()) {
            return sendHttpResponse(Response.Status.NO_CONTENT, "")
        }
        return sendHttpResponse(Response.Status.OK, contactEntityList)
    }

    fun listMessagesWithContact(contactId: Long): Response {
        val messageEntityList = ContactEntity.getMessageEntities(contactId)

        if (messageEntityList.isNullOrEmpty()) {
             return sendHttpResponse(Response.Status.NO_CONTENT, "")
        }
        messageEntityList.let {
            val messageList = Message.getMessageList(messageEntityList).map { it.message }
            return sendHttpResponse(Response.Status.OK, messageList)
        }
    }
}