package ufv.ca.abby;

/**
 * Created by Vrushabh on 20-06-2017.
 */

public class result_bim_recycle {
    public String file;
    public String title;

    public result_bim_recycle(){

    }

    public result_bim_recycle(String file, String title) {
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
