package config;

public enum BaseResponseMessage {
    // 요청 성공 1000
    // 공통
    REQUEST_SUCCESS(true, 1000, "요청이 정상적으로 처리되었습니다."),
    REQUEST_FAIL_WHAT(true, 1000, "요청이 실패했습니다."),
    REQUEST_FAIL_SELECT(false, 300, "조회실패."),
    REQUEST_FAIL_MEMBER(false, 500, "회원이 아닙니다."),
    REQUEST_FAIL_EMPTY_LOGIN(false, 600, "로그인 정보가 없습니다."),
    REQUEST_FAIL_NOTWRITER(false, 700, "작성자가 아닙니다."),


    // 회원 기능 2000
    // 회원가입
    MEMBER_REGISTER_FAIL_NICNAME_EMPTY(false, 2101, "닉네임을 입력해주세요."),
    MEMBER_REGISTER_FAIL_EMAIL_EMPTY(false, 2102, "이메일을 입력해주세요."),
    MEMBER_REGISTER_FAIL_EMAIL_NOTRULL(false, 2103, "이메일 형식이 맞지 않습니다."),
    MEMBER_REGISTER_FAIL_EMAIL_NOTCHECK(false, 2104, "이메일 인증에 실패했습니다."),
    MEMBER_REGISTER_FAIL_PASSWORD_EMPTY(false, 2105, "비밀번호를 입력해주세요."),
    MEMBER_REGISTER_FAIL_PASSWORD_NOTRULL(false, 2106, "비밀번호 형식이 맞지 않습니다."),
    MEMBER_REGISTER_FAIL_PASSWORD_NOTCHECK(false, 2107, "비밀번호가 일치하지 않습니다."),
    MEMBER_REGISTER_FAIL_EMAIL_ALREADY(false, 2108, "이미 사용중인 이메일 입니다."),
    MEMBER_REGISTER_FAIL_NICNAME_ALREADY(false, 2109, "이미 존재하는 닉네임 입니다."),
    MEMBER_REGISTER_FAIL_MAX_POWER(false, 2110, "재능 선택은 최대 5개까지 선택할 수 있습니다."),
    MEMBER_REGISTER_FAIL_PLUS_POWER(false, 2111, "재능을 한 개 이상 선택해주세요."),
    MEMBER_REGISTER_FAIL_MEMBER_ALREADY(false, 2112, "이미 가입된 회원입니다."),
    // 로그인
    MEMBER_LOGIN_FAIL(false, 2201, "로그인에 실패하였습니다."),
    MEMBER_LOGIN_FAIL_EMAIL_EMPTY(false, 2202, "이메일을 입력해주세요."),
    MEMBER_LOGIN_FAIL_PASSWORD_EMPTY(false, 2203, "비밀번호를 입력해주세요."),
    // 로그아웃
    // 아이디 찾기
    // 비밀번호 찾기
    // 회원정보 수정
    // 회원 탈퇴
    // 마이페이지


    // 게시판 기능 4000
    BOARD_GET_LIST_FAIL_EMPTY_TITLE(false, 4101, "제목이 입력되지 않았습니다."),
    BOARD_GET_LIST_FAIL_MAX_TITLE(false, 4102, "제목의 글자수 제한을 초과하였습니다."),
    BOARD_GET_LIST_FAIL_EMPTY_TOPCATEGORY(false, 4103, "재능의 상위 카테고리를 선택하지 않았습니다."),
    BOARD_GET_LIST_FAIL_MAX_BTMCATEGORY(false, 4104, "하위 카테고리의 글자수 제한을 초과하였습니다."),
    BOARD_GET_LIST_FAIL_EMPTY_CONTENTS(false, 4105, "소개글이 입력되지 않았습니다."),
    BOARD_GET_LIST_FAIL_MAX_CONTENTS(false, 4016, "소개글의 글자수 제한을 초과하였습니다."),
    BOARD_GET_LIST_FAIL_NOTCHECK_STATE(false, 4107, "교환/나눔을 선택하지 않았습니다."),
    BOARD_GET_LIST_FAIL_EMPTY_WANT_TOPCATEGORY(false, 4108, "받고싶은 재능의 상위 카테고리를 선택하지 않았습니다."),
    BOARD_GET_LIST_FAIL_EMPTY_TIME(false, 4109, "시간을 설정해주세요."),
    BOARD_GET_LIST_FAIL_TIMESETTING(false, 4110, "0~24시간 내에서만 설정 가능합니다."),
    BOARD_GET_LIST_FAIL_EMPTY_CAPACITY(false, 4111, "모집 인원을 설정하지 않았습니다."),


    // 채팅 기능 5000

    ;



    private Boolean success;
    private Integer code;
    private String message;

    BaseResponseMessage(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
