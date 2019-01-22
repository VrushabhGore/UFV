package ufv.ca.abby;

/**
 * Created by Vrushabh on 20-06-2017.
 */

public class study_bbi_recycle {
    public String file;
    public String title;

    public study_bbi_recycle(){

    }

    public study_bbi_recycle(String file, String title) {
        this.file = file;
        this.title = title;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
