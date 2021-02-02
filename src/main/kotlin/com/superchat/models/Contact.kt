package com.superchat.models

import com.superchat.entities.ContactEntity
import io.quarkus.runtime.annotations.RegisterForReflection

@RegisterForReflection
data class Contact(
    val name: String,
    val birth: String,
    val email: String,
    val phone: String
) {
    companion object {

        fun getContactList(contactEntityList: List<ContactEntity>): List<Contact> {
            return contactEntityList.map { Contact of it }
        }

        private infix fun of (contactEntity: ContactEntity): Contact {
            return Contact(
                name = contactEntity.name,
                birth = contactEntity.birth,
                email = contactEntity.email,
                phone = contactEntity.phone
            )
        }
    }
}


