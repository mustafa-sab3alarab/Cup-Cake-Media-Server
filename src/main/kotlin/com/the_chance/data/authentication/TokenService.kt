package com.the_chance.data.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class TokenService {

    private val secretKey = System.getenv("jwtSecret")

    fun generateJwt(userId: String): Token {
        val expireTime = Date(System.currentTimeMillis() + THREE_DAYS)

        val token = JWT.create()
            .withSubject(userId)
            .withExpiresAt(expireTime)
            .sign(Algorithm.HMAC256(secretKey))

        return Token(token, expireTime.time)

    }

    fun jwtVerifier(): JWTVerifier {
        return JWT.require(Algorithm.HMAC256(secretKey))
            .build()
    }

    companion object {
        private const val THREE_DAYS = 259200000L
    }
}
