package Mobs;

public abstract class Mob {
    private String type = "";
    private String image = new String("");

    public String getImage() {
        return image;
    }

    public void setImage(String path) {
        image = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String tipo) {
        type = tipo;
    }
}
