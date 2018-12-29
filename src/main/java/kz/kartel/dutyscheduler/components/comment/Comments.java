package kz.kartel.dutyscheduler.components.comment;

import java.util.List;

public class Comments {
    private String Result;
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }
}
