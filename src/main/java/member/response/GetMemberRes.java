package member.response;

public class GetMemberRes {
    Integer idx;
    String email;
    String password;

    public GetMemberRes(Integer idx, String email, String password) {
        this.idx = idx;
        this.email = email;
        this.password = password;
    }

    public Integer getIdx() { return idx; }

    public void setIdx(Integer idx) { this.idx = idx; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPw() { return password; }

    public void setPw(String password) { this.password = password; }
}
