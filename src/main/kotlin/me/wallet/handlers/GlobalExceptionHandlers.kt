package me.wallet.handlers

import me.wallet.features.history.models.CommonResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus


@ControllerAdvice
class GlobalExceptionHandlers {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<CommonResponse> {
        val errors = ex.bindingResult.allErrors.map {
            val fName = (it as FieldError).field
            "$fName is ${it.defaultMessage}"
        }.toList().joinToString(prefix = "[", postfix = "]")
        return ResponseEntity.badRequest().body(
            CommonResponse(
                code = HttpStatus.BAD_REQUEST.value(),
                message = "$errors"
            )
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(
        ex: HttpMessageNotReadableException
    ): ResponseEntity<CommonResponse> {
        logger.warn(ex.message)
        val requiredField = findFieldInError(ex.message)
        return ResponseEntity.badRequest().body(
            CommonResponse(
                code = HttpStatus.BAD_REQUEST.value(),
                message = "$requiredField in request is required or not valid format."
            )
        )
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleGeneralException(
        ex: Exception
    ): ResponseEntity<CommonResponse> {
        logger.warn(ex.message)
        return ResponseEntity.internalServerError().body(
            CommonResponse(
            code = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = "Internal server error"
        )
        )
    }

    private fun findFieldInError(message: String?): String {
        val field = message?.let {
            it.substring(it.lastIndexOf("[") + 1, it.lastIndexOf("]")).replace("\"", "")
        } ?: "some field"
        return field
    }
}