package work.demo.com.rummyscore.Activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.LinkedList;

import work.demo.com.rummyscore.Controller.ControllerClassToSaveData;
import work.demo.com.rummyscore.DataBaseHelper.DataBase;
import work.demo.com.rummyscore.Model.PlayerModel;
import work.demo.com.rummyscore.R;

public class TabLeDataActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean check_for_close_activity = false;
    private LinkedList<PlayerModel> playerModels = new LinkedList<>();
    //    private LinkedList<PlayerModel> scores_values = new LinkedList<>();
    private LinkedList<Integer> edittext_ids = new LinkedList<>();
    private Context context = TabLeDataActivity.this;
    private TextView pack_value_tv, card_value_tv;
    private DataBase dataBase;
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_le_data);

        dataBase = new DataBase(TabLeDataActivity.this);
        tableLayout = findViewById(R.id.table);

        pack_value_tv = findViewById(R.id.pack_value_tv);
        card_value_tv = findViewById(R.id.card_value_tv);

        if (!TextUtils.isEmpty(dataBase.getInfo().getPack_value())) {
            pack_value_tv.setText(pack_value_tv.getText().toString() + ":  " + dataBase.getInfo().getPack_value());
        }
        if (!TextUtils.isEmpty(dataBase.getInfo().getCard_value())) {
            card_value_tv.setText(card_value_tv.getText().toString() + ":  " + dataBase.getInfo().getCard_value());
        }


        String s = "";
        if (getIntent() != null) {
//            s = getIntent().getExtras().getString("player_list");
            /*playerModels =  new LinkedList<>(
                    (ArrayList<PlayerModel>) new Gson().fromJson(s, new TypeToken<List<PlayerModel>>(){}.getType()
                    )
            );*/
            playerModels = dataBase.getPlayerScores();
            s = String.valueOf(dataBase.getPlayerScores().size());
        }
        if (!TextUtils.isEmpty(s)) {
            Log.d("player_list: ", s);
        }

        addHeaders();
//        scores_values.add(new PlayerModel(3321));
//        scores_values.add(new PlayerModel(4334));
//        scores_values.add(new PlayerModel(33788));
        addData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save) {
            SaveData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(context);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setTextSize(16);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(this);
        return tv;
    }

    private EditText getEditTexttView(int id, int typeface, int bgColor) {
        EditText et = new EditText(context);
        et.setId(id);
        et.setGravity(Gravity.CENTER);
        et.setTextColor(Color.WHITE);
        try {
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(et, R.drawable.cursor_drawable);
        } catch (Exception ignored) {
        }
//        et.setInputType(InputType.TYPE_CLASS_NUMBER);
        et.setTextSize(16);
        et.setMaxLines(1);
        et.setInputType(EditorInfo.IME_FLAG_NO_ENTER_ACTION);

        /*--- OR ---*/
//        setCursorColor(et, Color.BLACK);

        et.setPadding(40, 40, 40, 40);
        et.setTypeface(Typeface.DEFAULT, typeface);
        et.setBackgroundColor(bgColor);
        et.setLayoutParams(getLayoutParams());
        et.setOnClickListener(this);
        return et;
    }

    @NonNull
    private TableRow.LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT,
                1.0f);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT);
    }

    /**
     * This function add the headers to the table
     **/
    public void addHeaders() {

        TableRow tr = new TableRow(context);
        tr.setLayoutParams(getLayoutParams());
        tr.addView(getTextView(0, "Players", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(0, "Enter Scores", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(0, "Total Scores", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tableLayout.addView(tr, getTblLayoutParams());
    }

    /**
     * This function add the data to the table
     **/
    public void addData() {
        int numCompanies = playerModels.size();
//        TableLayout tl = findViewById(R.id.table);
        for (int i = 0; i < numCompanies; i++) {
            int tot_score = Integer.parseInt(playerModels.get(i).getScore());
            TableRow tr = new TableRow(context);
            tr.setLayoutParams(getLayoutParams());
            if (playerModels.size() > 0) {
                tr.addView(getTextView(i + 1, playerModels.get(i).getName(), Color.WHITE, Typeface.NORMAL,
                        ContextCompat.getColor(context, R.color.colorAccent)));

                tr.addView(getEditTexttView(i + 1, Typeface.NORMAL,
                        ContextCompat.getColor(context, R.color.colorAccent)));

                tr.addView(getTextView(i + 1, String.valueOf(tot_score), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(context, R.color.colorAccent)));
                edittext_ids.add(i + 1);
            } else {
                tr.addView(getTextView(i + 1, String.valueOf(0), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(context, R.color.colorAccent)));
            }
            tableLayout.addView(tr, getTblLayoutParams());
        }
    }

    @Override
    public void onClick(View v) {
        /*if (v instanceof TextView) {
            int id = v.getId();
            TextView tv = findViewById(id);
            if (null != tv) {
                Log.i("onClick", "Clicked on row :: " + id);
                Toast.makeText(context, "Clicked on row :: " + id + ", Text :: " + tv.getText(), Toast.LENGTH_SHORT).show();
            }
        }*/
    }

    private void SaveData() {
        boolean b_check_for_controller = false;

        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow mRow = (TableRow) tableLayout.getChildAt(i);


            Log.d("check_bfr_id: ", playerModels.get(i - 1).getId());
            Log.d("check_bfr_name: ", playerModels.get(i - 1).getName());
            Log.d("check_bfr_score: ", playerModels.get(i - 1).getScore());

            Log.d("check_abc: ", String.valueOf(((EditText) mRow.getVirtualChildAt(1)).getText().toString()));

            playerModels.get(i - 1).setScore(String.valueOf(((EditText) mRow.getVirtualChildAt(1)).getText().toString().trim()));

            Log.d("check_aftr_id: ", playerModels.get(i - 1).getId());
            Log.d("check_aftr_name: ", playerModels.get(i - 1).getName());
            Log.d("check_aftr_score: ", playerModels.get(i - 1).getScore());

            dataBase.UpdatePlayerScoreTEMP(playerModels);

        }

        Log.d("size_lisst: ", String.valueOf(playerModels.size()));
        Log.d("size_aft_contrlr: ", String.valueOf(playerModels.size()));

        for (int i = 0; i < playerModels.size(); i++) {
            Log.d("ck_id: ", playerModels.get(i).getId());
            Log.d("ck_name: ", playerModels.get(i).getName());
            Log.d("ck_score: ", playerModels.get(i).getScore());

            if (TextUtils.isEmpty(playerModels.get(i).getScore())) {
                b_check_for_controller = true;
                break;
            } else {
                try {
                    Integer.parseInt(playerModels.get(i).getScore());
                } catch (Exception e) {
                    if (!playerModels.get(i).getScore().equals("p") && !playerModels.get(i).getScore().equals("P")) {
                        b_check_for_controller = true;
                        Log.d("sd: ", "tr");
                    }
                }
            }
        }

        if (!b_check_for_controller) {
            ControllerClassToSaveData controllerClassToSaveData = new ControllerClassToSaveData(context);
            controllerClassToSaveData.GetListOfPlayersWithHighestToLowOrder();
        } else {
            Toast.makeText(context, "Wrong Value", Toast.LENGTH_SHORT).show();
        }
    }

//    public static void setCursorColor(EditText view, @ColorInt int color) {
//        try {
//            // Get the cursor resource id
//            Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
//            field.setAccessible(true);
//            int drawableResId = field.getInt(view);
//
//            // Get the editor
//            field = TextView.class.getDeclaredField("mEditor");
//            field.setAccessible(true);
//            Object editor = field.get(view);
//
//            // Get the drawable and set a color filter
//            Drawable drawable = ContextCompat.getDrawable(view.getContext(), drawableResId);
//            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
//            Drawable[] drawables = {drawable, drawable};
//
//            // Set the drawables
//            field = editor.getClass().getDeclaredField("mCursorDrawable");
//            field.setAccessible(true);
//            field.set(editor, drawables);
//        } catch (Exception ignored) {
//        }
//    }

    public void SetData() {
        playerModels = dataBase.getPlayerScores();
        for (int i = 1; i < tableLayout.getChildCount(); i++) {
            TableRow mRow = (TableRow) tableLayout.getChildAt(i);

            ((TextView) mRow.getVirtualChildAt(2)).setText(playerModels.get(i - 1).getScore());

            Log.d("refresh_id: ", playerModels.get(i - 1).getId());
            Log.d("refresh_name: ", playerModels.get(i - 1).getName());
            Log.d("refresh_score: ", playerModels.get(i - 1).getScore());
            ((EditText) mRow.getVirtualChildAt(1)).setText("");
        }
        TableRow mRow = (TableRow) tableLayout.getChildAt(1);
        ((EditText) mRow.getVirtualChildAt(1)).requestFocus();

    }

    @Override
    public void onBackPressed() {

        if (check_for_close_activity) {
            DeleteAllDatabaseTable();

            super.onBackPressed();
            return;
        }

        this.check_for_close_activity = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                check_for_close_activity = false;
            }
        }, 2000);
    }

    public void DeleteAllDatabaseTable() {
        DataBase dataBase = new DataBase(context);
        dataBase.DeleteAllPlayerTable();
        dataBase.DeleteInfoTable();
        dataBase.DeleteAllPlayerTableTEMP();
        dataBase.DeleteAllPlayerTableTAKEN();
        dataBase.DeleteAllPlayerTableGIVEN();
        dataBase.DeleteAllPlayerTablePACK();
    }
}

