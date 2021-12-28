package org.techtown.chatbot1;

public class Notice {
    String kind;
    String title;
    String url;
    String date;

    public Notice(String kind, String title, String url, String date){
        this.kind = kind;
        this.title = title;
        this.url = url;
        this.date = date;
    }

    public String getKind(){
        return kind;
    }

    public String getTitle(){
        return title;
    }

    public String getUrl(){
        return url;
    }

    public String getDate(){
        return date;
    }
}
