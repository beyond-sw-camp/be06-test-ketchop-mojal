package member;


import board.response.GetBoardRes;
import com.zaxxer.hikari.HikariDataSource;
import config.DataSourceConfig;
import member.request.PostMemberReq;
import member.request.PostMemberReq;
import member.response.GetMemberRes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDao {
    HikariDataSource dataSourceConfig;

    public MemberDao() {
        dataSourceConfig = DataSourceConfig.getInstance();
    }

    public Boolean create(PostMemberReq dto) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        Integer result = null;
        try {
            connection = dataSourceConfig.getConnection();
            pstmt = connection.prepareStatement("INSERT INTO mojalex.member (email, password, nickname, talent) VALUES (?,?,?,?)");
            pstmt.setString(1, dto.getEmail());
            pstmt.setString(2, dto.getPassword());
            pstmt.setString(3, dto.getNickname());
            pstmt.setString(4, dto.getTalent());
            result = pstmt.executeUpdate();
            System.out.println("result : "+result);


            if (result > 0) {
                return true;
            }
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
        }
        return false;
    }

    /***************중복확인메소드*****************/
    //이메일중복
    public Boolean duplicationCheckEmail(PostMemberReq dto) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = dataSourceConfig.getConnection();
            //prepareStatement : SQL문을 데이터베이스로 전송하는데 사용
            pstmt = connection.prepareStatement("SELECT email FROM mojalex.member WHERE email = "+"\""+dto.getEmail()+"\"");

            rs= pstmt.executeQuery();
            //rs.next()가 처음실행되었을때는 테이블의 속성줄이 가장 첫번쨰로 읽히기 떄문에 테이블에 값이 들어왔는지 확인하기 위해서는 rs.next()를 한번 더 실행해줘야 값을 읽어올 수 있다.
            rs.next();//테이블 속성줄

            //테이블에 값이 읽힐경우 true반환(중복됨)
            if (rs.next()) {
                return true;
            }
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
        }
        //아니면 false를 반환(중복되지 않음)
        return false;
    }

    //닉네임중복
    public Boolean duplicationCheckNickname(PostMemberReq dto) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = dataSourceConfig.getConnection();
            //prepareStatement : SQL문을 데이터베이스로 전송하는데 사용
            pstmt = connection.prepareStatement("SELECT email FROM mojalex.member WHERE nickname = ?");
            //쿼리문을 실행할건데 첫번째 물음표 안에 dto.getNickname()의 값을 setString함수를 통해서 string으로 바꿔서 넣어준다.
            pstmt.setString(1,dto.getNickname());
            rs= pstmt.executeQuery();
            rs.next();//테이블 속성줄

            //테이블에 값이 읽힐경우 true반환(중복됨)
            if (rs.next()) {
                return true;
            }
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
        }
        //아니면 false를 반환(중복되지 않음)
        return false;
    }


    //수정안함!
    public GetMemberRes find(GetMemberRes dto) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = dataSourceConfig.getConnection();
            pstmt = connection.prepareStatement("SELECT * FROM member WHERE email=? AND password=?");
            pstmt.setString(1, dto.getEmail());
            pstmt.setString(2, dto.getPassword());
            rs = pstmt.executeQuery();
            GetMemberRes postMemberLoginRes = null;
            if (rs.next()) {
                postMemberLoginRes = new GetMemberRes(rs.getInt("idx"), rs.getString("id"), rs.getString("name"));
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
