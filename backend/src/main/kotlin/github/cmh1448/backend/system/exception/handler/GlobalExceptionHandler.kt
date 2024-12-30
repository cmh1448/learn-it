package github.cmh1448.backend.system.exception.handler

import github.cmh1448.backend.system.exception.model.ErrorCode
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this.javaClass)!!


    @ExceptionHandler(HttpMessageConversionException::class)
    fun handleRestException(error: Exception): ResponseEntity<ErrorDto.ErrorResponse> {
//        log.error("{Internal Exception}: " + exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorDto.ErrorResponse(
                    ErrorCode.GLOBAL_BAD_REQUEST
                )
            )
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleMethodNotSupportedException(): ResponseEntity<ErrorDto.ErrorResponse> {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(
                ErrorDto.ErrorResponse(
                    ErrorCode.GLOBAL_METHOD_NOT_ALLOWED
                )
            ) }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(): ResponseEntity<ErrorDto.ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorDto.ErrorResponse(
                    ErrorCode.GLOBAL_INVALID_PARAMETER
                )
            )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorDto.ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorDto.ErrorResponse(
                    ErrorCode.GLOBAL_BAD_REQUEST
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<ErrorDto.ErrorResponse> {
        log.error("{Internal Exception}: " + exception.message, exception)

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorDto.ErrorResponse(
                    ErrorCode.INTERNAL_SERVER_ERROR
                )
            )
    }
}