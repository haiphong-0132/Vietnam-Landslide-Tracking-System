package team08.oop.lms.model;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Image {
    private String source_images;
    private String url_video;

    public Image(){

    }

    public Image(String source_images, String url_video){
        this.source_images = source_images;
        this.url_video = url_video;
    }

    public String getSource_images(){
        return source_images;
    }

    public String getUrl_video(){
        return url_video;
    }

    public void setSource_images(String source_images){
        this.source_images = source_images;
    }

    public void setUrl_video(String url_video){
        this.url_video = url_video;
    }

    public List<String> getSourceImagesList(){
        if (source_images == null){
            return Collections.emptyList();
        }
        return Arrays.asList(source_images.split(","));
    }

    @Override
    public String toString(){
        return "Image{" + "source_images=" + source_images + ", url_video=" + url_video + "}";
    }
}
