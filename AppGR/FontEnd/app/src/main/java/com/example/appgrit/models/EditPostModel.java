package com.example.appgrit.models;

import java.util.List;

public class EditPostModel {
    private String UserId;
    private String PostId;
    private String Content;
    private String EditTime;
    private List<ImagePostModel> imagePostModelList;

    // Constructors, getters, and setters

    public EditPostModel() {
    }

    public EditPostModel(String userId, String postId, String content, String editTime, List<ImagePostModel> imagePostModelList) {
        UserId = userId;
        PostId = postId;
        Content = content;
        EditTime = editTime;
        this.imagePostModelList = imagePostModelList;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPostId() {
        return PostId;
    }

    public void setPostId(String postId) {
        PostId = postId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getEditTime() {
        return EditTime;
    }

    public void setEditTime(String editTime) {
        EditTime = editTime;
    }

    public List<ImagePostModel> getImagePostModelList() {
        return imagePostModelList;
    }

    public void setImagePostModelList(List<ImagePostModel> imagePostModelList) {
        this.imagePostModelList = imagePostModelList;
    }
}
