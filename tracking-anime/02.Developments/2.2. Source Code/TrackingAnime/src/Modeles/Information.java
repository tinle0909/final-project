package Modeles;

public class Information {
    String userid, name,type,status,image,date;
    float score;

    public Information(String userid, String name, String type, String status, String image, float score,String date) {
        this.userid = userid;
        this.name = name;
        this.type = type;
        this.status = status;
        this.image = image;
        this.score = score;
        this.date = date;

    }

    public String getUserid() {
        return userid;
    }
    public String getName(){
        return name;
    }

    public String getType() {
        return type;
    }
    public String getStatus() {
        return status;
    }
    public String getImage() {
        return image;
    }

    public float getScore() {
        return score;
    }
    public String getDate() {
        return date;
    }
}
