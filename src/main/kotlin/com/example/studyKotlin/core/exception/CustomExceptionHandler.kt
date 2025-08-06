package com.example.studyKotlin.core.exception

import com.example.studyKotlin.core.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(InvalidInputException::class)
    protected fun invalidInputException(ex: InvalidInputException): ResponseEntity<ErrorResponse> {
        val errors = ErrorResponse(ex.message ?: "Not Exception Message")   // ?: // null-safe 기본값 처리기. 왼쪽이 null이면 오른쪽 값을 써라
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    // VALIDATION에서 걸린 EXCEPTION 처리
    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val errors = mutableMapOf<String, String>()
        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.getDefaultMessage()
            errors[fieldName] = errorMessage ?: "NULL"
        }
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }
}