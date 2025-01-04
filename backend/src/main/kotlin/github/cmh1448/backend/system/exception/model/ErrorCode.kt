package github.cmh1448.backend.system.exception.model

enum class ErrorCode(
    val statusCode: Int,
    val message: String
) {
    // GLOBAL
    GLOBAL_BAD_REQUEST(400, "올바르지 않은 요청입니다."),
    GLOBAL_NOT_FOUND(404, "요청한 사항을 찾을 수 없습니다."),
    GLOBAL_ALREADY_EXIST(400, "요청의 대상이 이미 존재합니다."),
    GLOBAL_METHOD_NOT_ALLOWED(405, "허용되지 않는 Method 입니다."),
    GLOBAL_INVALID_PARAMETER(400, "올바르지 않은 파라미터입니다."),

    //JWT
    JWT_INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    JWT_PARSE_ERROR(401, "토큰을 파싱하는데 실패하였습니다."),
    JWT_TOKEN_EXPIRED(401, "토큰이 만료되었습니다."),
    JWT_TOKEN_MISSING(401, "토큰이 없습니다."),

    // USER
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    USER_PASSWORD_MISMATCH(400, "비밀번호가 일치하지 않습니다."),

    // STUDY
    STUDY_TYPE_CHANGE_NOT_ALLOWED(400, "공개 스터디는 스터디 유형을 변경할 수 없습니다."),
    STUDY_NOT_PUBLIC(400, "공개 스터디가 아닙니다."),
    STUDY_ONLY_MASTER_CAN_INVITE(400, "스터디의 마스터만 초대를 할 수 있습니다."),
    STUDY_ONLY_MASTER_CAN_DELETE(403, "스터디의 마스터만 삭제할 수 있습니다."),
    STUDY_NOT_MEMBER(400, "스터디에 가입되어 있지 않습니다."),
    STUDY_ALREADY_MEMBER(400, "이미 스터디에 가입되어 있습니다."),

    STUDY_ONLY_MASTER_CAN_KICK(403, "스터디의 마스터만 추방할 수 있습니다."),
    STUDY_INVITATION_EXPIRED(400, "초대가 만료되었습니다."),
    INTERNAL_SERVER_ERROR(500, "알수없는 오류가 발생하였습니다."),
}