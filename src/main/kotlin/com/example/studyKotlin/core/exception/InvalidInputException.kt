package com.example.studyKotlin.core.exception

class InvalidInputException(
    message: String = "Invalid Input"
) : RuntimeException(message)   // 메시지를 RuntimeException에게 넘rla