package com.bls.imagesearching;

public class PhotoModel {
    private String title;
    private String image_url;
    private String id;

    public PhotoModel(String title, String image_url, String id) {
        this.title = title;
        this.image_url = image_url;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image_url;
    }

    public void setImage(String image) {
        this.image_url = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
