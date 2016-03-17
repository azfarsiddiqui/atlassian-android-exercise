package com.atlassian.exercise.servicemodels;

import com.atlassian.exercise.entities.LinkSegment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.functions.Func1;

public class LinkSegmentServiceModel extends BaseServiceModel {

    /**
     * This method is responsible for fetching a URL's title and setting
     * that up on the object thats passed in as the parameter
     * @param userMessageLinkSegmentSegment
     * @return
     */
    public Observable<LinkSegment> fetchLinkTitle(final LinkSegment userMessageLinkSegmentSegment) {
        return super.doHttpGet(userMessageLinkSegmentSegment.getContent()).map(new Func1<String, LinkSegment>() {
            @Override
            public LinkSegment call(String html) {
                Pattern titlePattern = Pattern.compile("<title>(.*?)</title>", Pattern.CASE_INSENSITIVE);
                Matcher matcher = titlePattern.matcher(html);

                if (matcher.find() && matcher.groupCount() > 0) {
                    String title = matcher.group(1);
                    userMessageLinkSegmentSegment.setTitle(title);
                }

                return userMessageLinkSegmentSegment;
            }
        });
    }
}
