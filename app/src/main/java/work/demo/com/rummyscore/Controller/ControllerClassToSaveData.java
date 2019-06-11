package work.demo.com.rummyscore.Controller;

import android.content.Context;
import android.content.Intent;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Log;

import java.util.Iterator;
import java.util.LinkedList;

import work.demo.com.rummyscore.Activity.TabLeDataActivity;
import work.demo.com.rummyscore.DataBaseHelper.DataBase;
import work.demo.com.rummyscore.Model.PlayerModel;

/**
 * Created by su on 3/19/18.
 */

public class ControllerClassToSaveData {

    LinkedList<PlayerModel> sorted_list = new LinkedList<>();
    LinkedList<PlayerModel> playerModels_taken= new LinkedList<>();
    LinkedList<PlayerModel> playerModels_given = new LinkedList<>();
    LinkedList<PlayerModel> playerModels_given_scores = new LinkedList<>();
    LinkedList<PlayerModel> playerModels_taken_scores = new LinkedList<>();
    Context context;
    LinkedList<PlayerModel> playerModels_list = new LinkedList<>();
    LinkedList<PlayerModel> desc_player_model = new LinkedList<>();
    PlayerModel playerModel;
    DataBase dataBase;
    int anInt_list_size = 0;


    public ControllerClassToSaveData(Context context) {
        this.context = context;
        dataBase = new DataBase(context);
        this.playerModels_list = dataBase.getPlayerScoresTEMP();
        this.desc_player_model = new LinkedList<>();
    }

    public void GetListOfPlayersWithHighestToLowOrder(){


        for (int i = 0; i<playerModels_list.size(); i++){
            Log.d("temp_list_id: ", playerModels_list.get(i).getId());
            Log.d("temp_list_name: ", playerModels_list.get(i).getName());
            Log.d("temp_list_score: ", playerModels_list.get(i).getScore());
        }

        sortPack();

    }




    private void sortPack(){
        anInt_list_size = playerModels_list.size();
        dataBase.DeleteAllPlayerTablePACK();
        while (playerModels_list.size()>0) {
            for (Iterator<PlayerModel> iter = playerModels_list.iterator(); iter.hasNext(); ) {
                PlayerModel data = iter.next();
                if (data.getScore().equals("p") || data.getScore().equals("P")) {
                    PlayerModel playerModel = data;
                    playerModel.setScore(dataBase.getInfo().getPack_value());
                    dataBase.AddPlayerScorePACK(playerModel);
                    iter.remove();
                }else {
                    sorted_list.add(data);
                    Log.d("id_sorted_list: ", data.getId());
                    Log.d("scores_sorted_list: ", data.getScore());
                    iter.remove();
                }
            }
        }
        /*for (int i = 0; i< playerModels_list.size(); i++){
            if (playerModels_list.get(i).getScore().equals("p") || playerModels_list.get(i).getScore().equals("P")){
                PlayerModel playerModel = playerModels_list.get(i);
                playerModel.setScore(dataBase.getInfo().getPack_value());
                dataBase.AddPlayerScorePACK(playerModel);
                playerModels_list.remove(i);
            }
            Log.d("chk_pack_sort_id: ", playerModels_list.get(i).getId());
            Log.d("chk_pack_sort_name: ", playerModels_list.get(i).getName());
            Log.d("chk_pack_sort_score: ", playerModels_list.get(i).getScore());

        }*/


        anInt_list_size = sorted_list.size();

        while (sorted_list.size()>0){
            getHighestCardHolder();
        }

        while(playerModels_taken.size()>0) {
            FinalValueCount_taken();
        }

        if (playerModels_taken.size()==0){
            dataBase.UpdatePlayerScoreTAKEN(playerModels_taken_scores);
        }

        this.playerModels_list = dataBase.getPlayerScoresTEMP();
        this.sorted_list.clear();

        while (playerModels_list.size()>0) {
            for (Iterator<PlayerModel> iter = playerModels_list.iterator(); iter.hasNext(); ) {
                PlayerModel data = iter.next();
                if (data.getScore().equals("p") || data.getScore().equals("P")) {
                    /*PlayerModel playerModel = data;
                    playerModel.setScore(dataBase.getInfo().getPack_value());
                    dataBase.DeleteAllPlayerTablePACK();
                    dataBase.AddPlayerScorePACK(playerModel);*/
                    iter.remove();
                }else {
                    sorted_list.add(data);
                    Log.d("id_sorted_list: ", data.getId());
                    Log.d("scores_sorted_list: ", data.getScore());
                    iter.remove();
                }
            }
        }


        while (sorted_list.size()>0){
            getHighestCardHolder();
        }

        while(playerModels_given.size()>0){
            Log.d("sdss: ", "triw");
            FinalValueCount_2_given();
        }

        if (playerModels_given.size()==0){
            dataBase.UpdatePlayerScoreGIVEN(dataBase.getPlayerScoresPACK());
            dataBase.UpdatePlayerScoreGIVEN(SummationOfListItems(playerModels_given_scores, dataBase.getPlayerScoresTAKEN()));
        }

        dataBase.UpdatePlayerScore(SummationOfListItems(dataBase.getPlayerScores(), dataBase.getPlayerScoresGIVEN()));

        ((TabLeDataActivity)context).SetData();
    }


    private void getHighestCardHolder(){

        playerModel = sorted_list.get(0);
        for (int i = 1; i< sorted_list.size(); i++){
            if (!TextUtils.isEmpty(sorted_list.get(i).getScore())) {
                if (Integer.parseInt(sorted_list.get(i).getScore()) > Integer.parseInt(playerModel.getScore())) {
                    playerModel = sorted_list.get(i);
                }
            }
        }
        Log.d("id_prev_list: ", playerModel.getId());
        DeleteListItem(playerModel);
    }

    private void DeleteListItem(PlayerModel playerModel){
        for(Iterator<PlayerModel> iter = sorted_list.iterator(); iter.hasNext();) {
            PlayerModel data = iter.next();
            if (data.getId() == playerModel.getId()) {
                desc_player_model.add(data);
                Log.d("id_next_list: ", data.getId());
                Log.d("scores_next_list: ", data.getScore());


                Log.d("given_size: ", String.valueOf(playerModels_given.size()));
                Log.d("given_taken: ", String.valueOf(playerModels_taken.size()));
                iter.remove();
            }
        }
        playerModels_given = new LinkedList<>();
        playerModels_given.addAll(desc_player_model);
        playerModels_taken = new LinkedList<>();
        playerModels_taken.addAll(desc_player_model);
        Log.d("size_list_prev: ", String.valueOf(sorted_list.size()));
        Log.d("size_list_next: ", String.valueOf(desc_player_model.size()));
    }






    private void FinalValueCount_taken(){

        PlayerModel playerModel = playerModels_taken.get(playerModels_taken.size()-1);
        String s = playerModel.getScore();
        Log.d("last_score: ", s);
        int total_card = 0;

        for (int i = playerModels_taken.size()-2; i>=0; i--){
            total_card = total_card + (Integer.parseInt(s)-Integer.parseInt(playerModels_taken.get(i).getScore()));
        }

        Log.d("check_list_size: ", String.valueOf(anInt_list_size));

        if (anInt_list_size==playerModels_taken.size()) {
            playerModel.setScore(String.valueOf(
                    (total_card*Integer.parseInt(dataBase.getInfo().getCard_value()))
                            -(dataBase.getPlayerScoresPACK().size()*Integer.parseInt(dataBase.getInfo().getPack_value()))
            ));
            Log.d("size_pack: ", String.valueOf(dataBase.getPlayerScoresPACK().size()));
        }else {
            playerModel.setScore(String.valueOf(total_card*Integer.parseInt(dataBase.getInfo().getCard_value())));
        }
        DeleteListItem_2(playerModel);
        Log.d("check_value_taken: ", String.valueOf(total_card));
    }

    private void DeleteListItem_2(PlayerModel playerModel){
        for(Iterator<PlayerModel> iter = playerModels_taken.iterator(); iter.hasNext();) {
            PlayerModel data = iter.next();
            if (data.getId() == playerModel.getId()) {
                playerModels_taken_scores.add(data);


                Log.d("size_taken_list: ", String.valueOf(playerModels_taken_scores.size()));
                Log.d("id_final_list: ", data.getId());
                Log.d("scores_final_list: ", data.getScore());
                iter.remove();
            }
        }
        /*if (playerModels_taken.size()==0){
            dataBase.UpdatePlayerScore(SummationOfListItems(playerModels_taken_scores, dataBase.getPlayerScores()));
        }*/



    }




    private void FinalValueCount_2_given(){

        PlayerModel playerModel = playerModels_given.get(0);
        String s = playerModel.getScore();
        Log.d("last_score_3: ", s);
        int total_card = 0;

        for (int i = 1; i<playerModels_given.size(); i++){
            total_card = total_card + (Integer.parseInt(s)-Integer.parseInt(playerModels_given.get(i).getScore()));
        }

        playerModel.setScore(String.valueOf(total_card*Integer.parseInt(dataBase.getInfo().getCard_value())));
//        playerModel.setScore(String.valueOf(total_card));
        DeleteListItem_3(playerModel);
        Log.d("check_value_abc_3: ", String.valueOf(total_card));
    }

    private void DeleteListItem_3(PlayerModel playerModel){
        for(Iterator<PlayerModel> iter = playerModels_given.iterator(); iter.hasNext();) {
            PlayerModel data = iter.next();
            if (data.getId() == playerModel.getId()) {
                playerModels_given_scores.add(data);
//                dataBase.UpdatePlayerScore(playerModels_given_scores);
                Log.d("chk_size_lis: ", String.valueOf(playerModels_given_scores.size()));
                Log.d("id_final_list_2: ", data.getId());
                Log.d("scores_final_list_2: ", data.getScore());
                iter.remove();
            }
        }
        /*if (playerModels_given.size()==0){
            dataBase.UpdatePlayerScore(SummationOfListItems(playerModels_given_scores, dataBase.getPlayerScores()));
        }*/


    }



    private LinkedList<PlayerModel> SummationOfListItems(LinkedList<PlayerModel> playerModels_1, LinkedList<PlayerModel> playerModels_2){
        LinkedList<PlayerModel> playerModels = new LinkedList<>();
        for (int i = 0; i<playerModels_1.size(); i++){
            for (int j = 0; j<playerModels_2.size(); j++){
                if (playerModels_1.get(i).getId().equals(playerModels_2.get(j).getId())){
                    int s = Integer.parseInt(playerModels_1.get(i).getScore()) + Integer.parseInt(playerModels_2.get(j).getScore());
                    Log.d("summCheck: ", String.valueOf(s));
                    playerModels.add(new PlayerModel(playerModels_1.get(i).getId(), playerModels_1.get(i).getName(), String.valueOf(s)));
                }
            }

        }
        return playerModels;
    }
}