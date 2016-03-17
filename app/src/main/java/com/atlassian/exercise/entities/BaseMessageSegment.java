package com.atlassian.exercise.entities;

/**
    This is the base class for all types of message segments
    For example link, emoticon, mention
 */
public abstract class BaseMessageSegment extends BaseEntity {
    protected String mContent;

    public BaseMessageSegment(String content) {
        mContent = content;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
