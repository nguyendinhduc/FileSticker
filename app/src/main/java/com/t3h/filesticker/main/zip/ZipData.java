package com.t3h.filesticker.main.zip;

/**
 * Created by ducnd on 4/26/18.
 */

public class ZipData {
    private String path;
    private long sizeKB;
    private String title;

    public ZipData(String title, String path, long sizeKB) {
        this.title = title;
        this.path = path;
        this.sizeKB = sizeKB;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSizeKB() {
        return sizeKB;
    }

    public void setSizeKB(long sizeKB) {
        this.sizeKB = sizeKB;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
