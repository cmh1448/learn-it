package github.cmh1448.backend.system.exception.model

import github.cmh1448.backend.system.exception.model.ErrorCode

class RestException(
    errorCode: ErrorCode,
    trace: Exception,
) : RuntimeException(
    errorCode.message,
    trace
)