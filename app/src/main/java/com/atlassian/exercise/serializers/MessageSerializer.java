package com.atlassian.exercise.serializers;

import com.atlassian.exercise.entities.EmoticonSegment;
import com.atlassian.exercise.entities.LinkSegment;
import com.atlassian.exercise.entities.MentionSegment;
import com.atlassian.exercise.entities.Message;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class MessageSerializer implements JsonSerializer<Message> {

    @Override
    public JsonElement serialize(Message src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        if (src.getEmoticonsList() != null && src.getEmoticonsList().size() > 0) {
            JsonArray emoticons = new JsonArray();

            for (EmoticonSegment emoticon : src.getEmoticonsList()) {
                emoticons.add(new JsonPrimitive(emoticon.getContent()));
            }

            jsonObject.add("emoticons", emoticons);
        }

        if (src.getMentionsList() != null && src.getMentionsList().size() > 0) {
            JsonArray mentions = new JsonArray();

            for (MentionSegment mentionSegment : src.getMentionsList()) {
                mentions.add(new JsonPrimitive(mentionSegment.getContent()));
            }

            jsonObject.add("mentions", mentions);
        }

        if (src.getLinksList() != null && src.getLinksList().size() > 0) {
            JsonArray links = new JsonArray();

            for (LinkSegment linkSegment : src.getLinksList()) {
                JsonObject linkObject = new JsonObject();
                linkObject.addProperty("url", linkSegment.getContent());
                linkObject.addProperty("title", linkSegment.getTitle());
                links.add(linkObject);
            }

            jsonObject.add("links", links);
        }

        return jsonObject;
    }
}
