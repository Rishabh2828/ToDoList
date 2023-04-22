package com.shurish.todolist;

import com.google.firebase.Timestamp;

public class Note {

    String title;
    String content;
    Timestamp timestamp;

    int color = R.color.white;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Note(int color) {
        this.color = color;
    }

    public Note() {
    }

    public Note(String title, String content, Timestamp timestamp) {
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }



    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}
