package com.real.matcher;

import java.util.Date;

/**
 * This class is to store the internal db movie data.
 */
public class MovieEntryInternal {
    private int id;
    private String title;
    private String releaseYear;

    public MovieEntryInternal() {
    }

    public MovieEntryInternal(int id, String title, String releaseYear) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
    }

    public MovieEntryInternal(String title, String releaseYear) {
        this.title = title;
        this.releaseYear = releaseYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }


    @Override
    public int hashCode() {
        return title.hashCode() ^ releaseYear.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MovieEntryInternal)) {
            return false;
        }
        MovieEntryInternal other = (MovieEntryInternal) obj;
        return title.equals(other.title) && releaseYear.equals(other.releaseYear);
    }

}
