package com.example.myapplicationtemp;

public class ExBook {

    private String bookName;
    private String page;
    private String ex;
    private String image;
    private String user;

    public ExBook(String bookNmae, String page, String ex, String image, String user) {
        this.bookName = bookNmae;
        this.page = page;
        this.ex = ex;
        this.image = image;
        this.user = user;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookNmae(String bookNmae) {
        this.bookName = bookNmae;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
