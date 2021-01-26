package com.superchat.resources

import com.superchat.entities.ContactEntity
import com.superchat.entities.MessageEntity
import com.superchat.services.ContactService
import com.superchat.services.MessageService
import javax.inject.Inject
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.MediaType.APPLICATION_JSON
import javax.ws.rs.core.Response

@Path("/messaging")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
class MessagingResource {

    @Inject
    lateinit var contactService: ContactService

    @Inject
    lateinit var messageService: MessageService

    @POST
    @Path("/contact/create")
    fun createContact(@Valid contactEntity: ContactEntity): Response {
        return contactService.createContact(contactEntity)
    }

    @GET
    @Path("/contact/list")
    fun getContacts(): Response {
        return contactService.listContacts()
    }

    @POST
    @Path("/contact/{id}/message")
    fun sendMessage(
        @PathParam("id") id: Long,
        @Valid messageEntity: MessageEntity
    ) :Response {
        return messageService.createMessage(id, messageEntity)
    }

    @GET
    @Path("/contact/{id}/history")
    fun listMessageHistory(
        @PathParam("id") id: Long
    ): Response {
        return contactService.listMessagesWithContact(id)
    }

    @POST //Telegram token must be compile time constant
    @Path("/1585271717:AAGISwSBVhhzlZ95_xV3T3I9coJ9h2gDLNQ")
    fun receiveTelegramMessage(telegramUpdate: String): Response {
        return messageService.createMessageFromTelegramUpdate(telegramUpdate)
    }
}