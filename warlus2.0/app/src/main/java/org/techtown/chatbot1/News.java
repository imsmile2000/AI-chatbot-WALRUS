package org.techtown.chatbot1;

public class News {
    String image_url;
    String title;
    String url;
    String date;

    public News(String image_url, String title, String url,String date){
        this.image_url = image_url;
        this.title = title;
        this.url = url;
        this.date = date;
    }

    public String getImage_url(){
        return image_url;
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
