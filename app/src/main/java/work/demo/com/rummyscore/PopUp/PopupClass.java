package work.demo.com.rummyscore.PopUp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;

import work.demo.com.rummyscore.Activity.HomeActivity;
import work.demo.com.rummyscore.Activity.TabLeDataActivity;
import work.demo.com.rummyscore.DataBaseHelper.DataBase;
import work.demo.com.rummyscore.Model.InfoModel;
import work.demo.com.rummyscore.Model.PlayerModel;
import work.demo.com.rummyscore.R;

/**
 * Created by su on 3/13/18.
 */

public class PopupClass extends Dialog{

    private boolean aBoolean_check_for_show_fields = true;
    private int number_of_player;
    private int i = 0;
    private LinkedList<PlayerModel> players = new LinkedList<>();
    private Button button_close, button_enter;
    private EditText name_of_players_edittext;
    private Context context;

    public PopupClass(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public PopupClass(@NonNull Context context, boolean b, int i) {
        super(context);
        this.context = context;
        aBoolean_check_for_show_fields = b;
        number_of_player = i;
        Log.d("check_player: ", String.valueOf(number_of_player));
    }

    public PopupClass(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected PopupClass(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(R.layout.popup_layout);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        ShowFields(aBoolean_check_for_show_fields);
        button_close = findViewById(R.id.button_close);
        button_enter = findViewById(R.id.button_enter);
        name_of_players_edittext = findViewById(R.id.name_of_players_edittext);

        name_of_players_edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_DONE){
                    button_enter.performClick();
                    return true;
                }
                return false;
            }
        });

        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (players.size()<=number_of_player-1){
//                    Toast.makeText(context, "DataBase Deleted", Toast.LENGTH_SHORT).show();
                    DataBase dataBase = new DataBase(context);
                    dataBase.DeleteAllPlayerTable();
                    dataBase.DeleteInfoTable();
                    dataBase.DeleteAllPlayerTableTEMP();
                    dataBase.DeleteAllPlayerTableTAKEN();
                    dataBase.DeleteAllPlayerTableGIVEN();
                    dataBase.DeleteAllPlayerTablePACK();
                }
            }
        });




        button_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(name_of_players_edittext.getText().toString().trim()) && i<number_of_player) {
                    players.add(new PlayerModel(name_of_players_edittext.getText().toString().trim()));
                    i++;
                    name_of_players_edittext.setText("");
                }
                if (players.size()==number_of_player-1){
                    button_enter.setText("Finish");
                }
                if (players.size()==number_of_player){
//                    Toast.makeText(context, "DataBase Hitted", Toast.LENGTH_SHORT).show();
                    button_enter.setEnabled(false);
                    dismiss();
                    CreateTables();

                    /*Gson gson = new Gson();
                    Intent intent = new Intent(context, TabLeDataActivity.class);
                    intent.putExtra("player_list", gson.toJson(players));
                    context.startActivity(intent);*/
//                    addData();
                }
                Log.d("check_size: ", String.valueOf(players.size()));
            }
        });

    }

   /* private boolean SavePlayer(){

    }*/

    private void ShowFields(boolean b){

        int i = View.INVISIBLE;
        if (b){
            i = View.VISIBLE;
        }else {
            i = View.INVISIBLE;
        }
        findViewById(R.id.textView4).setVisibility(i);
        findViewById(R.id.name_of_players_edittext).setVisibility(i);
        findViewById(R.id.button_enter).setVisibility(i);

    }

    private void CreateTables(){

        String match_name = ((HomeActivity)context).GetMatchName();
        String card_value = ((HomeActivity)context).GetCardValue();
        String pack_value = ((HomeActivity)context).GetPackValue();
        InfoModel infoModel = new InfoModel(match_name, card_value, String.valueOf(number_of_player), pack_value);

        DataBase dataBase = new DataBase(context);
        dataBase.DeleteAllPlayerTable();
        dataBase.DeleteInfoTable();
        dataBase.DeleteAllPlayerTableTEMP();
        dataBase.DeleteAllPlayerTableTAKEN();
        dataBase.DeleteAllPlayerTableGIVEN();
        dataBase.DeleteAllPlayerTablePACK();
        dataBase.AddInfo(infoModel);


        for (int i = 0; i<players.size(); i++){
            String id, name, score;
            id = String.valueOf(i+1);
            name = String.valueOf(players.get(i).getName());
            score = players.get(i).getScore();

            PlayerModel playerModel = new PlayerModel(id, name, score);
            dataBase.AddPlayerScore(playerModel);
            dataBase.AddPlayerScoreTEMP(playerModel);
            dataBase.AddPlayerScoreTAKEN(playerModel);
            dataBase.AddPlayerScoreGIVEN(playerModel);
        }

        Log.d("size_pop_info: ", String.valueOf(dataBase.getInfo()));
        Log.d("size_pop_player: ", String.valueOf(dataBase.getPlayerScores()));

        Intent intent = new Intent(context, TabLeDataActivity.class);
        context.startActivity(intent);

    }

}
