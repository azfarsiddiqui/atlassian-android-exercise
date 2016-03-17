package com.atlassian.exercise.listeners;

import com.atlassian.exercise.entities.Message;

/**
 * This listener is used to notify when the message segmentation finishes
 */
public interface MessageSegmentationListener {
    void onMessageSegmentationCompleted(Message message);
}
