package board;

import board.response.GetBoardRes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/board")
public class ReadBoardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("요청 성공");
        BoardDao boardDao = new BoardDao();
        List<GetBoardRes> boardResList = boardDao.readAll();
        System.out.println(boardResList);
    }
}
