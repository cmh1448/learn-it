package github.cmh1448.backend.system.exception.model

enum class ErrorCode(
    val statusCode: Int,
    val message: String
) {
    GLOBAL_BAD_REQUEST(400, "올바르지 않은 요청입니다."),
    GLOBAL_NOT_FOUND(404, "요청한 사항을 찾을 수 없습니다."),
    GLOBAL_ALREADY_EXIST(400, "요청의 대상이 이미 존재합니다."),
    GLOBAL_METHOD_NOT_ALLOWED(405, "허용되지 않는 Method 입니다."),
    GLOBAL_INVALID_PARAMETER(400, "올바르지 않은 파라미터입니다."),

    INTERNAL_SERVER_ERROR(500, "알수없는 오류가 발생하였습니다."),
}