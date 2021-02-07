package com.superchat.services

import com.superchat.util.ObjectMappingUtil
import com.superchat.entities.ContactEntity
import com.superchat.models.Contact
import com.superchat.models.Message
import com.superchat.util.ResponseUtil.buildHttpResponse
import javax.enterprise.context.ApplicationScoped
import javax.transaction.Transactional
import javax.ws.rs.core.Response

@ApplicationScoped
class ContactService {

    @Transactional
    fun createContact(contactEntity: ContactEntity): Response {
        ContactEntity.saveContact(contactEntity)
        val responseJsonPair = ObjectMappingUtil.toJsonPair("created", "${contactEntity.id}")
        return buildHttpResponse(Response.Status.CREATED, responseJsonPair)
    }

    fun listContacts(): Response {
        val contactEntityList = ContactEntity.getContactEntities()

        if (contactEntityList.isNullOrEmpty()) {
            return buildHttpResponse(Response.Status.NO_CONTENT, "")
        }
        contactEntityList.let {
            val contactList = Contact.getContactList(contactEntityList)
            return buildHttpResponse(Response.Status.OK, contactList)
        }
    }

    fun listMessagesWithContact(contactId: Long): Response {
        val messageEntityList = ContactEntity.getMessageEntities(contactId)

        if (messageEntityList.isNullOrEmpty()) {
             return buildHttpResponse(Response.Status.NO_CONTENT, "")
        }
        messageEntityList.let {
            val messageList = Message.getMessageList(messageEntityList)
            return buildHttpResponse(Response.Status.OK, messageList)
        }
    }
}