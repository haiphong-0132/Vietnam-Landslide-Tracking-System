package team08.oop.lms.model;

public class MiniFeature {
    private Feature feature;
    private Image image;
    public  Feature getFeature(){
        return feature;
    }

    public Image getImage(){
        return image;
    }

    public void setFeature(Feature feature){
        this.feature = feature;
    }

    public void setImage(Image image){
        this.image = image;
    }

    public MiniFeature(Feature feature, Image image){
        this.feature = feature;
        this.image = image;
    }

    @Override
    public String toString(){
        return "MiniFeature{" + "feature=" + feature + "}";
    }
}
