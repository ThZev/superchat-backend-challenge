package com.superchat.util

import javax.ws.rs.core.Response

object MessagingUtil {

    fun sendHttpResponse(status: Response.Status, entity: Any): Response{
        return Response.status(status)
            .entity(entity)
            .build()
    }
}