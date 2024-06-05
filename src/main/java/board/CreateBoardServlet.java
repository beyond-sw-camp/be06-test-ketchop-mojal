package board;

import board.request.PostBoardReq;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.BaseResponse;
import config.BaseResponseMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/board/create")
public class CreateBoardServlet extends HttpServlet {
    BoardDao dao;
    ObjectMapper mapper;


    @Override
    public void init() {
        dao = new BoardDao();
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 클라이언트로부터 요청을 받아서 Dto에 저장하는 부분 ---------
        BufferedReader reader = req.getReader();
        StringBuilder json = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }
        PostBoardReq dto = mapper.readValue(json.toString(), PostBoardReq.class);


        // 게시글 작성 Dao 실행 전에 데이터확인
        Integer result = 0;

        if(Objects.equals(dto.getTitle(), "")) {
            result = 2;
        }
        if(dto.getContents() == null) {
            result = 3;
        }
        if(dto.getMax_capacity() == null) {
            result = 4;
        }
        if(dto.getPost_type() == null) {
            result = 5;
        }
        if(dto.getUser_idx() == null) {
            result = 6;
        }

//        System.out.println("Dao 전 "+result);

        // 게시글 작성하는 Dao의 메소드 실행--------------------------------------
        if(result == 0) {
            result = dao.create(dto);
        };

//        System.out.println("Dao 후 "+result);

        // Dao의 처리 결과에 따른 응답 설정 부분------------------------------------
        String jsonResponse;
        if (result == 1) {
            // 제대로 저장 됐으면? 게시글 등록 성공 시
            BaseResponse response = new BaseResponse(BaseResponseMessage.REQUEST_SUCCESS);
            jsonResponse = mapper.writeValueAsString(response);
        } else if(result == 2) {
            // 타이틀 입력 안했을 때
            BaseResponse response = new BaseResponse(BaseResponseMessage.BOARD_GET_LIST_FAIL_EMPTY_TITLE);
            jsonResponse = mapper.writeValueAsString(response);
        } else if(result == 3) {
            /// 소개글 입력 안했을 때
            BaseResponse response = new BaseResponse(BaseResponseMessage.BOARD_GET_LIST_FAIL_EMPTY_CONTENTS);
            jsonResponse = mapper.writeValueAsString(response);
        } else if(result == 4) {
            // 인원수 선택 안했을 때
            BaseResponse response = new BaseResponse(BaseResponseMessage.BOARD_GET_LIST_FAIL_EMPTY_CAPACITY);
            jsonResponse = mapper.writeValueAsString(response);
        } else if(result == 5) {
            /// 게시글 타입 선택 안했을 때
            BaseResponse response = new BaseResponse(BaseResponseMessage.BOARD_GET_LIST_FAIL_NOTCHECK_STATE);
            jsonResponse = mapper.writeValueAsString(response);
        } else {
            // 뭔가 오류났으면?
            BaseResponse response = new BaseResponse(BaseResponseMessage.REQUEST_FAIL_WHAT);
            jsonResponse = mapper.writeValueAsString(response);
        }



        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonResponse);
    }
}
