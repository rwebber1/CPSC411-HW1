package com.example

import com.example.Dao.claim.Claim
import com.example.Dao.claim.ClaimDao
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.utils.io.readAvailable
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    routing{
        post("/ClaimService/add") {
            val contType = call.request.contentType()
            val data = call.request.receiveChannel()
            val dataLength = data.availableForRead
            var output = ByteArray(dataLength)
            data.readAvailable(output)
            val str = String(output) //for further processing

            //JSON Deserialization
            val gsonStr = Gson().fromJson(str, Claim::class.java)
            val claim = Claim(UUID.randomUUID(), gsonStr.title, gsonStr.date, isSolved = false)
            ClaimDao().addClaim(claim)

            println("HTTP is using POST Method with /post ${contType}${str}")
            call.respondText("the POST response was successfully processed.", status= HttpStatusCode.OK, contentType = ContentType.Text.Plain)
        }

        get("ClaimService/getAll"){
            val claimList = ClaimDao().getAll()
            println("The number of claims: ${claimList.size}")

            //JSON Serialization/Deserialization
            val respJsonStr = Gson().toJson(claimList)
            call.respondText(respJsonStr, status= HttpStatusCode.OK, contentType= ContentType.Application.Json)
        }

    }
}

