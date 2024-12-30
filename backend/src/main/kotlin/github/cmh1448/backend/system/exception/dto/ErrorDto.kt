package github.cmh1448.backend.system.exception.dto

import github.cmh1448.backend.system.exception.model.ErrorCode

class ErrorDto {

    class ErrorResponse(
        val statusCode: Number,
        val message: String,
        val codeName: String,
    ) {
        constructor(errorCode: ErrorCode) : this(
            errorCode.statusCode,
            errorCode.message,
            errorCode.name
        )

        constructor(statusCode: Number, message: String) : this(
            statusCode,
            message,
            "UNKNOWN"
        )

    }
}