package Modeles;

import javafx.scene.image.Image;

import java.util.Date;

public class Database {
    private String title,studio,introduction;
    private Image poster;
    private int status,episodes,new_episode,type,season,nation;
    private Date aried;
    public Database(Image poster, String title, int season, int type, int status, int nation, Date aried, int episodes, int new_episode,
                    String studio, String introduction){
        this.poster=poster;
        this.title=title;
        this.season=season;
        this.type=type;
        this.status=status;
        this.nation=nation;
        this.aried=aried;
        this.episodes=episodes;
        this.new_episode=new_episode;
        this.studio=studio;
        this.introduction=introduction;
    }

    public Image getPoster() {
        return poster;
    }

    public String getTitle() {
        return title;
    }

    public String getStudio() {
        return studio;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getStatus() {
        return status;
    }

    public int getEpisodes() {
        return episodes;
    }

    public int getNew_episode() {
        return new_episode;
    }

    public int getType() {
        return type;
    }

    public int getSeason() {
        return season;
    }

    public int getNation() {
        return nation;
    }

    public Date getAried() {
        return aried;
    }
}
