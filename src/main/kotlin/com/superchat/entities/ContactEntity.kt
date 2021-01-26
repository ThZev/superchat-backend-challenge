package com.superchat.entities
import com.fasterxml.jackson.annotation.JsonIgnore
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
class ContactEntity : PanacheEntity() {

    @NotBlank
    lateinit var name: String

    @NotBlank
    lateinit var birth: String

    @NotBlank
    lateinit var email: String

    @NotBlank
    lateinit var phone: String

    @JsonIgnore
    @OneToMany(mappedBy = "contactEntity", orphanRemoval = true)
    var messageEntities = mutableListOf<MessageEntity>()

    companion object: PanacheCompanion<ContactEntity> {
        fun getContactById(contactId: Long) = findById(contactId)
        fun getContactEntities() = findAll().list()
        fun getContactEntitiesCount() = findAll().list().count()
        fun getMessageEntities(contactId: Long) = findById(contactId)?.messageEntities
        fun saveContact(contactEntity: ContactEntity) = persist(contactEntity)
    }
}