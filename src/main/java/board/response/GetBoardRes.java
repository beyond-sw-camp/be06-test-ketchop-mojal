package board.response;

public class GetBoardRes {
    Integer idx;
    String title;
    String contents;
    Integer max_capacity;
    Boolean post_type;
    Integer user_idx;

    public GetBoardRes(Integer idx, String title, String contents, Integer max_capacity, Boolean post_type, Integer user_idx) {
        this.idx = idx;
        this.title = title;
        this.contents = contents;
        this.max_capacity = max_capacity;
        this.post_type = post_type;
        this.user_idx = user_idx;
    }

    public GetBoardRes(String title, String contents, Integer max_capacity, Boolean post_type, Integer user_idx) {
        this.title = title;
        this.contents = contents;
        this.max_capacity = max_capacity;
        this.post_type = post_type;
        this.user_idx = user_idx;
    }

    public Integer getIdx() {
        return idx;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public Integer getMax_capacity() {
        return max_capacity;
    }

    public Boolean getPost_type() {
        return post_type;
    }

    public Integer getUser_idx() {
        return user_idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setMax_capacity(Integer max_capacity) {
        this.max_capacity = max_capacity;
    }

    public void setPost_type(Boolean post_type) {
        this.post_type = post_type;
    }

    public void setUser_idx(Integer user_idx) {
        this.user_idx = user_idx;
    }
}
