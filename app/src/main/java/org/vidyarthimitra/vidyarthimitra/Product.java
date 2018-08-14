package org.vidyarthimitra.vidyarthimitra;

public class Product {
    private String ne_title;
    private String ne_desc;


    private String sd_image;



    Product(String ne_title, String ne_desc, String sd_image) {

        this.ne_title = ne_title;
        this.ne_desc = ne_desc;


        this.sd_image = sd_image;

    }


    public String getne_title() {
        return ne_title;
    }

    public String getne_desc() {
        return ne_desc;
    }


    public String getne_sd_image() {
        return sd_image;
    }


}