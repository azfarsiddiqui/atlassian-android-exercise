package com.atlassian.exercise.entities;

import com.atlassian.exercise.serializers.MessageSerializer;
import com.atlassian.exercise.listeners.MessageSegmentationListener;
import com.atlassian.exercise.servicemodels.LinkSegmentServiceModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
    This class represents a message, with the actual input and the parsed segments
 */
public class Message extends BaseEntity {
    String mMessage;
    ArrayList<EmoticonSegment> mEmoticonsList;
    ArrayList<MentionSegment> mMentionsList;
    ArrayList<LinkSegment> mLinksList;

    public Message(String message) {
        mMessage = message;
    }

    /**
        This method parses the message into different segments and then notifies
        the caller through listener. Listener required because of the network call
        to extract titles of urls
     */
    public void segmentize(final MessageSegmentationListener messageSegmentationListener) {
        mEmoticonsList = parseEmoticonsFromMessage();
        mMentionsList = parseMentionsFromMessage();
        mLinksList = parseLinksFromMessage();

        if (mLinksList == null || mLinksList.size() == 0) {
            if (messageSegmentationListener != null)
                messageSegmentationListener.onMessageSegmentationCompleted(Message.this);
        }

        for (final LinkSegment linkSegment : mLinksList) {
            new LinkSegmentServiceModel().fetchLinkTitle(linkSegment)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<LinkSegment>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        messageSegmentationListener.onMessageSegmentationCompleted(Message.this);
                    }

                    @Override
                    public void onNext(LinkSegment updatedLinkSegment) {
                        linkSegment.setTitle(updatedLinkSegment.getTitle());
                        if (messageSegmentationListener != null)
                            messageSegmentationListener.onMessageSegmentationCompleted(Message.this);
                    }
                });
        }
    }

    /**
     * Converts Message representation to a transferrable JSON string using
     * a custom serializer
     * @return
     */
    public String toJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Message.class, new MessageSerializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        return gson.toJson(this);
    }

    private ArrayList<EmoticonSegment> parseEmoticonsFromMessage() {
        Pattern emoticonPattern = Pattern.compile("\\([a-zA-Z0-9]{1,15}\\)");
        Matcher matcher = emoticonPattern.matcher(mMessage);

        ArrayList<EmoticonSegment> emoticonsList = new ArrayList<>();

        while (matcher.find()) {
            String text = mMessage.substring(matcher.start(), matcher.end()).replace("(", "").replace(")", "");
            EmoticonSegment emoticon = new EmoticonSegment(text);
            emoticonsList.add(emoticon);
        }

        return emoticonsList;
    }

    private ArrayList<MentionSegment> parseMentionsFromMessage() {
        Pattern mentionPattern = Pattern.compile("@[\\w]+");
        Matcher matcher = mentionPattern.matcher(mMessage);

        ArrayList<MentionSegment> mentionsList = new ArrayList<>();

        while (matcher.find()) {
            String text = mMessage.substring(matcher.start(), matcher.end()).replace("@", "");
            MentionSegment mentionSegment = new MentionSegment(text);
            mentionsList.add(mentionSegment);
        }

        return mentionsList;
    }

    private ArrayList<LinkSegment> parseLinksFromMessage() {
        Pattern linkPattern = Pattern.compile("(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        Matcher matcher = linkPattern.matcher(mMessage);

        ArrayList<LinkSegment> linksList = new ArrayList<>();

        while (matcher.find()) {
            LinkSegment linkSegment = new LinkSegment(mMessage.substring(matcher.start(), matcher.end()));
            linksList.add(linkSegment);
        }

        return linksList;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public ArrayList<EmoticonSegment> getEmoticonsList() {
        return mEmoticonsList;
    }

    public void setEmoticonsList(ArrayList<EmoticonSegment> emoticonsList) {
        mEmoticonsList = emoticonsList;
    }

    public ArrayList<MentionSegment> getMentionsList() {
        return mMentionsList;
    }

    public void setMentionsList(ArrayList<MentionSegment> mentionsList) {
        mMentionsList = mentionsList;
    }

    public ArrayList<LinkSegment> getLinksList() {
        return mLinksList;
    }

    public void setLinksList(ArrayList<LinkSegment> linksList) {
        mLinksList = linksList;
    }
}
