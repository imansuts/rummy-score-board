package work.demo.com.rummyscore.Model;

/**
 * Created by su on 3/16/18.
 */

public class InfoModel {

    String match_name = "";
    String card_value ;
    String player_number;
    String pack_value;

    public InfoModel(String match_name, String card_value, String player_number, String pack_value) {
        this.match_name = match_name;
        this.card_value = card_value;
        this.player_number = player_number;
        this.pack_value = pack_value;
    }

    public String getMatch_name() {
        return match_name;
    }

    public String getCard_value() {
        return card_value;
    }

    public String getPlayer_number() {
        return player_number;
    }

    public String getPack_value() {
        return pack_value;
    }
}
