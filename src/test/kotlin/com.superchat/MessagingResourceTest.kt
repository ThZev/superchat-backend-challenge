package com.superchat

import com.superchat.entities.MessageEntity
import com.superchat.resources.MessagingResource
import com.superchat.services.ContactService
import com.superchat.services.MessageService
import io.quarkus.test.common.QuarkusTestResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.junit.jupiter.api.Test
import io.quarkus.test.common.http.TestHTTPEndpoint
import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.Order
import io.restassured.http.ContentType
import org.junit.jupiter.api.Disabled

@QuarkusTest
@TestHTTPEndpoint(MessagingResource::class)
class MessagingResourceTest {

    @Order(1)
    @Test
    fun testCreateContact() {
        given()
            .`when`()
            .contentType(ContentType.JSON)
            .body(CONTACT_ONE)
            .post("/contact/create")
            .then()
            .statusCode(201)
            .assertThat()
            .body(containsString("created"))
    }

    @Order(2)
    @Test
    fun testFullContactList() {
        given()
            .`when`()
            .contentType(ContentType.JSON)
            .get("/contact/list")
            .then()
            .statusCode(200)
            .assertThat()
            .body(containsString("Peter"))
    }

    @Order(3)
    @Test
    fun testSendMessaggeSuccessfuly() {
        given()
            .`when`()
            .contentType(ContentType.JSON)
            .pathParam("id","1")
            .body(MESSAGE_ONE)
            .post("/contact/{id}/message")
            .then()
            .statusCode(201)
            .assertThat()
            .body(containsString("member"))
    }

    @Order(4)
    @Test
    fun testGetMessagesUnsuccessfully() {
        given()
            .`when`()
            .contentType(ContentType.JSON)
            .pathParam("id","600")
            .get("/contact/{id}/history")
            .then()
            .statusCode(204)
            .assertThat()
    }

    @Order(5)
    @Test
    fun testSendMessageUnsuccessfully() {
        given()
            .`when`()
            .contentType(ContentType.JSON)
            .pathParam("id","600")
            .body(MESSAGE_ONE)
            .post("/contact/{id}/message")
            .then()
            .statusCode(404)
            .assertThat()
            .body(containsString(""))
    }

    @Order(6)
    @Test
    @Disabled
    fun testReceiveTelegramMessage() {
        given()
            .`when`()
            .contentType(ContentType.JSON)
            .body(TELEGRAM_MESSAGE)
            .post("/1585271717:AAGISwSBVhhzlZ95_xV3T3I9coJ9h2gDLNQ")
            .then()
            .statusCode(201)
            .assertThat()
            .body(containsString("This is a Telegram Message"))
    }

    companion object{
        const val CONTACT_ONE = "{\"name\":\"Peter Maier\",\n" +
                "\"birth\":\"04/03/1987\",\n" +
                "\"email\":\"bernd.peter@gmail.com\",\n" +
                "\"phone\":\"01523545445\"}"

        const val MESSAGE_ONE = "{\"message\":\"Dear {contact}, " +
                "welcome as member number {member}\"}"

        const val TELEGRAM_MESSAGE = "{\"update_id\":\"905647244\",\n" +
            "\"message\": {\"text\":\"This is a Telegram Message\"}\"}"
        }
}