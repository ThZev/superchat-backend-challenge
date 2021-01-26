package com.superchat.util

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import javax.inject.Inject
import javax.inject.Singleton

object ObjectMapperUtil {

    val objectMapper = ObjectMapper()

    fun toJsonPair(key: String, value: String): ObjectNode {
        return objectMapper.createObjectNode()
            .put(key, value)
    }

    fun toJsonNode(json: String): JsonNode{
        return objectMapper.readTree(json)
    }
}