package ufv.ca.abby;

/**
 * Created by Vrushabh on 17-06-2017.
 */

public class event_recycle {

    private String image,title;
    public event_recycle(){

    }

    public event_recycle(String image, String title) {
        this.image = image;
        this.title = title;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
