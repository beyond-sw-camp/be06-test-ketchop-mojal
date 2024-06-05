package member;

import board.response.GetBoardRes;
import com.zaxxer.hikari.HikariDataSource;
import config.DataSourceConfig;
import member.request.PostMemberReq;
import member.response.GetMemberRes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDao {
    HikariDataSource dataSourceConfig;

    MemberDao() {
        dataSourceConfig = DataSourceConfig.getInstance();
    }
    public GetMemberRes find(PostMemberReq dto) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = dataSourceConfig.getConnection();
            pstmt = connection.prepareStatement("SELECT * FROM mojalex.member WHERE email=? AND password=?");
            pstmt.setString(1, dto.getEmail());
            pstmt.setString(2, dto.getPw());
            rs = pstmt.executeQuery();
            GetMemberRes postMemberLoginRes = null;
            if (rs.next()) {
                postMemberLoginRes = new GetMemberRes(rs.getInt("idx"), rs.getString("email"), rs.getString("password"));
            }

            return postMemberLoginRes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException sqlEx) {
                } // ignore
                pstmt = null;
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqlEx) {
                } // ignore
                connection = null;
            }

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore
                rs = null;
            }
        }
    }
}
