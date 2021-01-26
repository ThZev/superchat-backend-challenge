package com.superchat.services

import com.fasterxml.jackson.databind.JsonNode
import com.superchat.config.HttpConfig
import com.superchat.config.TelegramConfig
import com.superchat.util.ObjectMapperUtil
import com.superchat.entities.ContactEntity
import com.superchat.entities.MessageEntity
import com.superchat.util.MessagingUtil.sendHttpResponse
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.core.Response

@ApplicationScoped
class MessageService {

    @Inject
    lateinit var httpconfig: HttpConfig

    @Inject
    lateinit var telegramConfig: TelegramConfig

    @Transactional
    fun createMessage(contactId: Long, messsageEntity: MessageEntity): Response {
        val contactEntity = ContactEntity.getContactById(contactId)

        contactEntity?.let {
            messsageEntity.message = replacePlaceholders(contactEntity.name, messsageEntity.message)
            messsageEntity.contactEntity = contactEntity
            MessageEntity.saveMessage(messsageEntity)

            return sendHttpResponse(Response.Status.CREATED,"${messsageEntity.message}")
        }
        return sendHttpResponse(Response.Status.NOT_FOUND,"")
    }

    fun replacePlaceholders(contactName: String, message: String): String {
        var substitutedMessage = message
        if (placeholderPresent(BITCOIN_PLACEHOLDER, message)) {
            substitutedMessage = replaceBitcoinPlaceholder(httpconfig.bitcoinUrl, substitutedMessage)
        }
        if (placeholderPresent(CONTACT_PLACEHOLDER, message)) {
            substitutedMessage = replaceContactPlacholder(contactName, substitutedMessage)
        }
        if (placeholderPresent(MEMBER_NUMBER, message)) {
            substitutedMessage = replaceMemberNumberPlaceholder(substitutedMessage)
        }
        return substitutedMessage
    }

    fun placeholderPresent(placeholder: String, message: String): Boolean {
        if (placeholder in message) {
            return true
        }
        return false
    }

    fun replaceBitcoinPlaceholder(bitcoinUrl: String, message: String): String {
        val currentBitcoinRate = getCurrentBitcoinRate(bitcoinUrl)
        return message
            .replace("$BITCOIN_PLACEHOLDER", currentBitcoinRate, ignoreCase = true)
    }

    fun getCurrentBitcoinRate(bitcoinUrl: String): String {
        val bitoinData = httpconfig.sendGetRequest(bitcoinUrl)
        val jsonNode = ObjectMapperUtil.toJsonNode(bitoinData)
        return jsonNode["bpi"]["EUR"]["rate"].asText()
    }

    fun replaceContactPlacholder(contactName: String, message: String): String {
        return message
            .replace(CONTACT_PLACEHOLDER, contactName, ignoreCase = true)
    }

    fun replaceMemberNumberPlaceholder(message: String): String {
        val memberNumber = ContactEntity.getContactEntitiesCount().toString()
        return message
            .replace(MEMBER_NUMBER, memberNumber, ignoreCase = true)
    }

    fun createMessageFromTelegramUpdate(telegramUpdate: String): Response{
        val telegramContactId = telegramConfig.telegramContactId.toLong()
        val messageEntity = MessageEntity()
        messageEntity.message = getTextFromTelegramUpdate(telegramUpdate)
        return createMessage(telegramContactId, messageEntity)
    }

    fun getTextFromTelegramUpdate(telegramUpdate: String): String{
        val rootNode: JsonNode = ObjectMapperUtil.objectMapper.readTree(telegramUpdate)
        return rootNode["message"]["text"].asText()
    }

    companion object {
        const val BITCOIN_PLACEHOLDER = "{bitcoin}"
        const val CONTACT_PLACEHOLDER = "{contact}"
        const val MEMBER_NUMBER = "{member}"
    }
}