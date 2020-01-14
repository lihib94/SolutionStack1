package com.example.myapplicationtemp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Books {

    private String bookName;
    private String faculty;
    private String degree;
    private String subjuct;

    @Override
    public String toString() {
        return "Books{" +
                "bookName='" + bookName + '\'' +
                ", faculty='" + faculty + '\'' +
                ", degree='" + degree + '\'' +
                ", subjuct='" + subjuct + '\'' +
                '}';
    }

    public Books(String bName, String fac, String deg, String sub) {

        this.bookName = bName;
        this.faculty = fac;
        this.degree = deg;
        this.subjuct = sub;

    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSubjuct() {
        return subjuct;
    }

    public void setSubjuct(String subjuct) {
        this.subjuct = subjuct;
    }
}