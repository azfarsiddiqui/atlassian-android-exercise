package com.atlassian.exercise.entities;

public class LinkSegment extends BaseMessageSegment {
    String mTitle;

    public LinkSegment(String content) {
        super(content);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
