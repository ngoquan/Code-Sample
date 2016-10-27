package com.example.ngoquan.demorecyclerview.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NGOQUAN on 7/26/2016.
 */
public class DataItem {
    private int id;
    private String title, genre, year;
    public boolean checked = false;
    public DataItem() {}

    public DataItem(int id, String title, String genre, String year) {
        this.id = id;
        this.genre = genre;
        this.year = year;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean setAllChecked(ArrayList<DataItem> arrayList, boolean value) {
        if (arrayList == null)
            return false;
        if (arrayList.size() > 0) {
            if (arrayList.get(0).checked) {
                for (int i = 0; i < arrayList.size(); i++) {
                    arrayList.get(i).checked = value;
                }
            }
        }
        return true;
    }

    public static boolean toggleCheckAll(List<DataItem> arrayList) {
        if (arrayList == null)
            return false;
        if (arrayList.size() > 0) {
            if (arrayList.get(0).checked) {
                for(int i = 0; i < arrayList.size(); i++) {
                    arrayList.get(i).checked = false;
                }
            } else {
                for(int i = 0; i < arrayList.size(); i++) {
                    arrayList.get(i).checked = true;
                }
            }
        }
        return true;
    }
}
