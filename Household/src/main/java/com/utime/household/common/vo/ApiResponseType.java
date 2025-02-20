package com.utime.household.common.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiResponseType {

    SUCCESS("200", "Success"),

    UNAUTHORIZED_RESPONSE("401", "Unauthorized"),
    FORBIDDEN_RESPONSE("403", "Forbidden"),
    NOT_FOUND_RESPONSE("404", "페이지를 찾을수 없습니다"),
    METHOD_NOT_ALLOWED_RESPONSE("405", "Method Not Allowed"),

    NOT_VALID_RESPONSE("409", "Not Valid"),
    NOT_FOUND_DATA_RESPONSE("409", "Not Found Data({ENTITY})"),
    ALREADY_DATA_RESPONSE("409", "Already Data({ENTITY})"),
    PARSE_ERROR_RESPONSE("409", "Parsing Error"),
    NOT_PRIMARY_ERROR_RESPONSE("409", "Not Primary Key Error"),
    SQL_ERROR_RESPONSE("409", "SQL Error"),
    ILLEGAL_ACCESS_RESPONSE("409", "Illegal Access"),
    TO_MANY_REQUESTS_RESPONSE("429", "Too Many Requests"),
    SC_INTERNAL_SERVER_ERROR("500", "서버에 문제가 발생했습니다"),
    
	REQ_ACCESS("1000", "로그인 필요한 서비스"),
	SOCIAL_MEMBER_JOIN_ERROR("1001", "소셜 회원가입 실패"),
	SOCIAL_LOGIN_FAIL("1002", "소셜 로그인에 실패하였습니다"),
	AUTHENTICATION_FAIL("2000", "인증 되지 않은 사용자"),
	ACCESS_DENINE("3000", "접근권한이 없는 사용자"),
	LOGIN_FAIL("4000", "로그인에 실패하였습니다"),
	REISSUE_FAIL("5000", "재발급 실패"),
	REQ_PASS_APP("6000", "ReqPassApp"),
	REQ_LOGIN("7000", "재로그인");

    private final String code;
    private final String message;

}