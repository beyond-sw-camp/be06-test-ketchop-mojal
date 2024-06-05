package board;

import board.request.PostBoardReq;
import board.response.GetBoardRes;
import com.zaxxer.hikari.HikariDataSource;
import config.DataSourceConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BoardDao {
    HikariDataSource hikariDataSourceConfig;

    BoardDao(){
        hikariDataSourceConfig = DataSourceConfig.getInstance();
    }



    public Integer create(PostBoardReq dto) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        Integer result = null;

        try {
            connection = hikariDataSourceConfig.getConnection();
            pstmt = connection.prepareStatement("INSERT INTO mojalex.board (title, contents, max_capacity, post_type, user_idx) VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1, dto.getTitle());
            pstmt.setString(2, dto.getContents());
            pstmt.setInt(3, dto.getMax_capacity());
            pstmt.setInt(4, dto.getPost_type());
            pstmt.setInt(5, dto.getUser_idx());
            result = pstmt.executeUpdate(); // 성공시 1? 실패시 0?
            // executeUpdate()는 INSERT, UPDATE, DELETE와 같은 DML(Data Manipulation Language)에서 실행 결과로 영향을 받은 레코드 수를 반환한다.
            // executeUpdate()는 반환 타입이 int이므로, 쿼리 실행 결과로 반환되는 값을 int로 받아와야 한다.
            // executeUpdate()는 행의 개수를 반환하기 때문에  rs를 사용할 필요 없다.

            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException sqlEx) { } // ignore
                pstmt = null;
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqlEx) { } // ignore
                connection = null;
            }
        }

    }



    public List<GetBoardRes> readAll(){
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = hikariDataSourceConfig.getConnection();
            pstmt = connection.prepareStatement("SELECT * FROM board");

            rs = pstmt.executeQuery();
            List<GetBoardRes> result = new LinkedList<>();

            while(rs.next()){
                GetBoardRes getBoardRes = new GetBoardRes(rs.getString("title"),rs.getString("contents"),rs.getInt("max_capacity"),rs.getBoolean("post_type"),rs.getInt("user_idx"));
                result.add(getBoardRes);
            }

            return result;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
