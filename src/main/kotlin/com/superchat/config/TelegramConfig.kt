package com.superchat.config

import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
class TelegramConfig {

    @Inject
    @ConfigProperty(name = "telegram.contact.id")
    lateinit var telegramContactId: String
}