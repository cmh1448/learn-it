package github.cmh1448.backend.system.exception.model

class RestException(
    val errorCode: ErrorCode,
    trace: Exception,
) : RuntimeException(
    errorCode.message,
    trace
) {
    constructor(errorCode: ErrorCode) : this(errorCode, RuntimeException())
}