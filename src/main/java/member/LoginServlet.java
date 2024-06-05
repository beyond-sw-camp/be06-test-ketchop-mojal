package member;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.BaseResponse;
import config.BaseResponseMessage;
import member.request.PostMemberReq;
import member.response.GetMemberRes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

import static config.BaseResponseMessage.*;

@WebServlet("/member/login")
public class LoginServlet extends HttpServlet {
    MemberDao dao;
    ObjectMapper mapper;
    String jsonResponse;

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

        PostMemberReq dto = mapper.readValue(json.toString(), PostMemberReq.class);

        if (dto.getEmail() == null) {
            // 이메일을 비워두고 로그인 시도
            BaseResponse response = new BaseResponse(MEMBER_LOGIN_FAIL_EMAIL_EMPTY);
            jsonResponse = mapper.writeValueAsString(response);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(jsonResponse);
            return;
        }
        if (dto.getPw() == null) {
            // 비밀번호를 비워두고 로그인 시도
            BaseResponse response = new BaseResponse(MEMBER_LOGIN_FAIL_PASSWORD_EMPTY);
            jsonResponse = mapper.writeValueAsString(response);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(jsonResponse);
            return;
        }


        // ------------------- ------------------- -------------------

        // ------------------- 로그인 확인하는 Dao의 메소드 실행 -------------------
        GetMemberRes result = dao.find(dto);
//        Boolean result = false;
        // ------------------- ------------------- -------------------


        // ------------------- Dao의 처리 결과에 따른 응답 설정 부분 -------------------
        if (result != null) {
            req.getSession().setAttribute("isLogin", true);
            req.getSession().setAttribute("userEmail", result.getEmail());
            req.getSession().setAttribute("userPw", result.getPw());

            // 로그인 성공
            BaseResponse response = new BaseResponse(REQUEST_SUCCESS);
            jsonResponse = mapper.writeValueAsString(response);
        } else {
            // 로그인 실패
            BaseResponse response = new BaseResponse(MEMBER_LOGIN_FAIL);
            jsonResponse = mapper.writeValueAsString(response);
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonResponse);
        // ------------------- ------------------- -------------------

    }
}
