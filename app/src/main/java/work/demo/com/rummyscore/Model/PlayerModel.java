package work.demo.com.rummyscore.Model;

/**
 * Created by su on 3/16/18.
 */

public class PlayerModel {

    String score, id;
    String name;

    public PlayerModel() {
    }

    public PlayerModel(String name) {
        this.name = name;
        this.score = "0";
    }

    public PlayerModel(String ID, String name, String score ) {
        this.score = score;
        this.name = name;
        this.id = ID;
    }

    public String getScore() {
        return score;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
