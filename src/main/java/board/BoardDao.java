package board;

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
