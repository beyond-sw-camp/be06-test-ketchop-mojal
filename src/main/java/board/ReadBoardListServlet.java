package board;

import board.response.GetBoardRes;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.BaseResponse;
import config.BaseResponseMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/board/read/all")
public class ReadBoardListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("요청 성공");

        BoardDao boardDao = new BoardDao();
        List<GetBoardRes> boardResList = boardDao.readAll();
//
        if(!boardResList.isEmpty()){
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonResponse = mapper.writeValueAsString(boardResList);

//            System.out.println(jsonResponse);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(jsonResponse);

        }else{
            System.out.println("fail");
            String subMessage = "게시글 목록 조회 실패";
            BaseResponse baseResponse = new BaseResponse<String>(BaseResponseMessage.REQUEST_FAIL_SELECT,subMessage);

            ObjectMapper mapper = new ObjectMapper();
            String jsonResponse = mapper.writeValueAsString(baseResponse);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(jsonResponse);
        }


    }
}
