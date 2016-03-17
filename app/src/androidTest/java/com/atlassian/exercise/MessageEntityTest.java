package com.atlassian.exercise;

import com.atlassian.exercise.entities.Message;
import com.atlassian.exercise.listeners.MessageSegmentationListener;

import junit.framework.TestCase;

public class MessageEntityTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testSingleMention() {
        Message message = new Message("@john lets go!");
        message.segmentize(new MessageSegmentationListener() {
            @Override
            public void onMessageSegmentationCompleted(Message message) {
                assertTrue(message.getMentionsList().get(0).getContent().equals("john"));
            }
        });
    }

    public void testMultipleMentions() {
        Message message = new Message("@john @smith lets go!");
        message.segmentize(new MessageSegmentationListener() {
            @Override
            public void onMessageSegmentationCompleted(Message message) {
                assertTrue(message.getMentionsList().get(0).getContent().equals("john"));
                assertTrue(message.getMentionsList().get(1).getContent().equals("smith"));
            }
        });
    }

    public void testSingleEmoticon() {
        Message message = new Message("hey guys, lets go out for a (coffee)");
        message.segmentize(new MessageSegmentationListener() {
            @Override
            public void onMessageSegmentationCompleted(Message message) {
                assertTrue(message.getEmoticonsList().get(0).getContent().equals("coffee"));
            }
        });
    }

    public void testMultipleEmoticon() {
        Message message = new Message("hey guys, lets go out in (rain) and have some (coffee)");
        message.segmentize(new MessageSegmentationListener() {
            @Override
            public void onMessageSegmentationCompleted(Message message) {
                assertTrue(message.getEmoticonsList().get(0).getContent().equals("rain"));
                assertTrue(message.getEmoticonsList().get(1).getContent().equals("coffee"));
            }
        });
    }

    public void testSingleLink() {
        Message message = new Message("hey mate, see this: http://www.cricinfo.com");
        message.segmentize(new MessageSegmentationListener() {
            @Override
            public void onMessageSegmentationCompleted(Message message) {
                assertTrue(message.getLinksList().get(0).getContent().equals("http://www.cricinfo.com"));
            }
        });
    }

    public void testMultipleLink() {
        Message message = new Message("hey mate, see this: http://www.cricinfo.com " +
                "and http://www.google.com");
        message.segmentize(new MessageSegmentationListener() {
            @Override
            public void onMessageSegmentationCompleted(Message message) {
                assertTrue(message.getLinksList().get(0).getContent().equals("http://www.cricinfo.com"));
                assertTrue(message.getLinksList().get(1).getContent().equals("http://www.google.com"));
            }
        });
    }

    public void testMultipleSegments() {
        Message message = new Message("hey @azfar, lets go for a (coffee) at " +
                "http://www.starbucks.com.au");
        message.segmentize(new MessageSegmentationListener() {
            @Override
            public void onMessageSegmentationCompleted(Message message) {
                assertTrue(message.getMentionsList().get(0).getContent().equals("azfar"));
                assertTrue(message.getEmoticonsList().get(0).getContent().equals("coffee"));
                assertTrue(message.getLinksList().get(0).getContent().equals("http://www.starbucks.com.au"));
            }
        });
    }
}
