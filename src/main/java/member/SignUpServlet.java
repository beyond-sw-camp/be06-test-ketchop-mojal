package member;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.BaseResponse;
import config.BaseResponseMessage;
import member.request.PostMemberReq;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

@WebServlet("/member/signup")
public class SignUpServlet extends HttpServlet {
    MemberDao dao;
    ObjectMapper mapper;

    @Override
    public void init() {
        dao = new MemberDao();
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // ------------------- 클라이언트로부터 요청을 받아서 Dto에 저장하는 부분 -------------------
        BufferedReader reader = req.getReader();
        StringBuilder json = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            json.append(line);

        }
        //DTO에 저장
        PostMemberReq dto = mapper.readValue(json.toString(), PostMemberReq.class);



        // ------------------- 예외처리 ------------------- -------------------

        //이메일,닉네임, 비밀번호에에 아무값도 넣지 않았을때
        String jsonResponse;
        if(dto.getEmail().length() == 0){
            BaseResponse response = new BaseResponse(BaseResponseMessage.MEMBER_REGISTER_FAIL_EMAIL_EMPTY);
            jsonResponse = mapper.writeValueAsString(response);
        }
        if(dto.getNickname().length() == 0){
            BaseResponse response = new BaseResponse(BaseResponseMessage.MEMBER_REGISTER_FAIL_NICNAME_EMPTY);
            jsonResponse = mapper.writeValueAsString(response);
        }
        if(dto.getPassword().length() == 0){
            BaseResponse response = new BaseResponse(BaseResponseMessage.MEMBER_REGISTER_FAIL_PASSWORD_EMPTY);
            jsonResponse = mapper.writeValueAsString(response);
        }

        //이메일 형식확인
        String regExp = "^[-A-Za-z0-9!#$%&'*+/=?^_`{|}~]+(?:\\.[-A-Za-z0-9!#$%&'*+/=?^_`{|}~]+)*@(?:[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[-A-Za-z0-9]*[A-Za-z0-9])?$";
        Boolean emailTypeCheck = dto.getEmail().matches(regExp);
        if (!emailTypeCheck){
            BaseResponse response = new BaseResponse(BaseResponseMessage.MEMBER_REGISTER_FAIL_EMAIL_NOTRULL);
            jsonResponse = mapper.writeValueAsString(response);
        }

        //이메일,닉네임 중복체크
        Boolean emailCheck = dao.duplicationCheckEmail(dto);
        Boolean nicknameCheck = dao.duplicationCheckNickname(dto);

        //중복이면 중복에러메세지 중복이 아니면 성공메세지
        if (emailCheck != true) {
            BaseResponse response = new BaseResponse(BaseResponseMessage.MEMBER_REGISTER_FAIL_EMAIL_ALREADY);
            jsonResponse = mapper.writeValueAsString(response);
        } else if(nicknameCheck != true){
            BaseResponse response = new BaseResponse(BaseResponseMessage.MEMBER_REGISTER_FAIL_NICNAME_ALREADY);
            jsonResponse = mapper.writeValueAsString(response);
        }



        // ------------------- 회원 가입하는 Dao의 메소드 실행 -------------------
        Boolean result = dao.create(dto);
//        Boolean result = false;
        // ------------------- ------------------- -------------------


        // ------------------- Dao의 처리 결과에 따른 응답 설정 부분 -------------------
        if (result) {
            BaseResponse response = new BaseResponse(BaseResponseMessage.MEMBER_REGISTER_SUCCESS);
            jsonResponse = mapper.writeValueAsString(response);
        } else {
            BaseResponse response = new BaseResponse(BaseResponseMessage.MEMBER_REGISTER_FAIL_MEMBER_ALREADY);
            jsonResponse = mapper.writeValueAsString(response);
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonResponse);
        // ------------------- ------------------- -------------------

    }
}