package work.demo.com.rummyscore.DataBaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;

import work.demo.com.rummyscore.Constants.Constant;
import work.demo.com.rummyscore.Model.InfoModel;
import work.demo.com.rummyscore.Model.PlayerModel;

/**
 * Created by su on 3/16/18.
 */

public class DataBase extends SQLiteOpenHelper {

    private Context context;
    private String PLAYER_TABLE = "PlayerTable";
    private String PLAYER_NAME_COLUMN = "PlayerName";
    private String PLAYER_SCORE_COLUMN = "PlayerScore";
    private String PLAYER_ID = "PlayerID";

    private String INFO_TABLE = "InfoTableMain";
    private String MATCH_NAME = "MatchName";
    private String CARD_VALUE = "CardValue";
    private String NUMBER_OF_PLAYER = "NumberOfPlayer";
    private String PACK_VALUE = "PackValue";

    private String PLAYER_TABLE_TEMPORARY = "PlayerTableTemp";
    private String PLAYER_TABLE_TAKEN= "PlayerTableTaken";
    private String PLAYER_TABLE_GIVEN= "PlayerTableGiven";
    private String PLAYER_TABLE_PACK= "PlayerTablePack";



    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        this.context = context;
    }

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        this.context = context;
    }

    public DataBase(Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            String CREATE_PLAYER_TABLE = "CREATE TABLE " + PLAYER_TABLE + "("
                    + PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + PLAYER_NAME_COLUMN + " BLOB,"
                    + PLAYER_SCORE_COLUMN + " BLOB"
                    + ")";
            db.execSQL(CREATE_PLAYER_TABLE);

            String CREATE_PLAYER_TABLE_TEMP = "CREATE TABLE " + PLAYER_TABLE_TEMPORARY + "("
                    + PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + PLAYER_NAME_COLUMN + " BLOB,"
                    + PLAYER_SCORE_COLUMN + " BLOB"
                    + ")";
            db.execSQL(CREATE_PLAYER_TABLE_TEMP);

            String CREATE_PLAYER_TABLE_TAKEN = "CREATE TABLE " + PLAYER_TABLE_TAKEN + "("
                    + PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + PLAYER_NAME_COLUMN + " BLOB,"
                    + PLAYER_SCORE_COLUMN + " BLOB"
                    + ")";
            db.execSQL(CREATE_PLAYER_TABLE_TAKEN);

            String CREATE_PLAYER_TABLE_GIVEN = "CREATE TABLE " + PLAYER_TABLE_GIVEN + "("
                    + PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + PLAYER_NAME_COLUMN + " BLOB,"
                    + PLAYER_SCORE_COLUMN + " BLOB"
                    + ")";
            db.execSQL(CREATE_PLAYER_TABLE_GIVEN);

            String CREATE_PLAYER_TABLE_PACK= "CREATE TABLE " + PLAYER_TABLE_PACK+ "("
                    + PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + PLAYER_NAME_COLUMN + " BLOB,"
                    + PLAYER_SCORE_COLUMN + " BLOB"
                    + ")";
            db.execSQL(CREATE_PLAYER_TABLE_PACK);


            String CREATE_INFO_TABLE = "CREATE TABLE " + INFO_TABLE + "("
                    + MATCH_NAME + " BLOB,"
                    + CARD_VALUE + " BLOB,"
                    + NUMBER_OF_PLAYER + " BLOB,"
                    + PACK_VALUE + " BLOB"
                    + ")";
            db.execSQL(CREATE_INFO_TABLE);


        } catch (Exception e) {
            Log.d("DB_Creation_Error: ", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void DeleteAllPlayerTable() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + PLAYER_TABLE);
        } catch (SQLException e) {
        }
    }

    public void DeleteAllPlayerTableTEMP() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + PLAYER_TABLE_TEMPORARY);
        } catch (SQLException e) {
        }
    }

    public void DeleteAllPlayerTableTAKEN() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + PLAYER_TABLE_TAKEN);
        } catch (SQLException e) {
        }
    }

    public void DeleteAllPlayerTableGIVEN() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + PLAYER_TABLE_GIVEN);
        } catch (SQLException e) {
        }
    }

    public void DeleteAllPlayerTablePACK() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + PLAYER_TABLE_PACK);
        } catch (SQLException e) {
        }
    }

    public void DeleteInfoTable() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from " + INFO_TABLE);
        } catch (SQLException e) {
        }
    }

    public void AddPlayerScore(PlayerModel playerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAYER_ID, playerModel.getId());
        values.put(PLAYER_NAME_COLUMN, playerModel.getName());
        values.put(PLAYER_SCORE_COLUMN, playerModel.getScore());
        db.insert(PLAYER_TABLE, null, values);
        db.close();
    }

    public void AddPlayerScoreTEMP(PlayerModel playerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAYER_ID, playerModel.getId());
        values.put(PLAYER_NAME_COLUMN, playerModel.getName());
        values.put(PLAYER_SCORE_COLUMN, playerModel.getScore());
        db.insert(PLAYER_TABLE_TEMPORARY, null, values);
        db.close();
    }

    public void AddPlayerScoreTAKEN(PlayerModel playerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAYER_ID, playerModel.getId());
        values.put(PLAYER_NAME_COLUMN, playerModel.getName());
        values.put(PLAYER_SCORE_COLUMN, playerModel.getScore());
        db.insert(PLAYER_TABLE_TAKEN, null, values);
        db.close();
    }

    public void AddPlayerScoreGIVEN(PlayerModel playerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAYER_ID, playerModel.getId());
        values.put(PLAYER_NAME_COLUMN, playerModel.getName());
        values.put(PLAYER_SCORE_COLUMN, playerModel.getScore());
        db.insert(PLAYER_TABLE_GIVEN, null, values);
        db.close();
    }

    public void AddPlayerScorePACK(PlayerModel playerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAYER_ID, playerModel.getId());
        values.put(PLAYER_NAME_COLUMN, playerModel.getName());
        values.put(PLAYER_SCORE_COLUMN, playerModel.getScore());
        db.insert(PLAYER_TABLE_PACK, null, values);
        db.close();
    }

    public void AddInfo(InfoModel infoModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MATCH_NAME, infoModel.getMatch_name());
        values.put(CARD_VALUE, infoModel.getCard_value());
        values.put(NUMBER_OF_PLAYER, infoModel.getPlayer_number());
        values.put(PACK_VALUE, infoModel.getPack_value());
        db.insert(INFO_TABLE, null, values);
        db.close();
    }


    public void UpdatePlayerScore(PlayerModel playerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAYER_ID, playerModel.getId());
        values.put(PLAYER_NAME_COLUMN, playerModel.getName());
        values.put(PLAYER_SCORE_COLUMN, playerModel.getScore());
//        db.update(PLAYER_TABLE, values, PLAYER_ID + "=" +"'"+playerModel.getId()+"'", null);
        db.update(PLAYER_TABLE, values, PLAYER_ID + "=" +playerModel.getId(), null);
        db.close();
    }

    public void UpdatePlayerScoreTEMP(PlayerModel playerModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAYER_ID, playerModel.getId());
        values.put(PLAYER_NAME_COLUMN, playerModel.getName());
        values.put(PLAYER_SCORE_COLUMN, playerModel.getScore());
//        db.update(PLAYER_TABLE, values, PLAYER_ID + "=" +"'"+playerModel.getId()+"'", null);
        db.update(PLAYER_TABLE_TEMPORARY, values, PLAYER_ID + "=" +playerModel.getId(), null);
        db.close();
    }

    public void UpdatePlayerScore(LinkedList<PlayerModel> playerModels) {
        if (playerModels!=null && playerModels.size()>0) {
            for (int i = 0; i<playerModels.size(); i++) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(PLAYER_ID, playerModels.get(i).getId());
                values.put(PLAYER_NAME_COLUMN, playerModels.get(i).getName());
                values.put(PLAYER_SCORE_COLUMN, playerModels.get(i).getScore());
                db.update(PLAYER_TABLE, values, PLAYER_ID + "=" + playerModels.get(i).getId(), null);
                db.close();
            }
        }
    }

    public void UpdatePlayerScoreTEMP(LinkedList<PlayerModel> playerModels) {
        if (playerModels!=null && playerModels.size()>0) {
            for (int i = 0; i<playerModels.size(); i++) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(PLAYER_ID, playerModels.get(i).getId());
                values.put(PLAYER_NAME_COLUMN, playerModels.get(i).getName());
                values.put(PLAYER_SCORE_COLUMN, playerModels.get(i).getScore());
                db.update(PLAYER_TABLE_TEMPORARY, values, PLAYER_ID + "=" + playerModels.get(i).getId(), null);
                db.close();
            }
        }
    }

    public void UpdatePlayerScoreTAKEN(LinkedList<PlayerModel> playerModels) {
        if (playerModels!=null && playerModels.size()>0) {
            for (int i = 0; i<playerModels.size(); i++) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(PLAYER_ID, playerModels.get(i).getId());
                values.put(PLAYER_NAME_COLUMN, playerModels.get(i).getName());
                values.put(PLAYER_SCORE_COLUMN, playerModels.get(i).getScore());
                db.update(PLAYER_TABLE_TAKEN, values, PLAYER_ID + "=" + playerModels.get(i).getId(), null);
                db.close();
            }
        }
    }

    public void UpdatePlayerScoreGIVEN(LinkedList<PlayerModel> playerModels) {
        if (playerModels!=null && playerModels.size()>0) {
            for (int i = 0; i<playerModels.size(); i++) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(PLAYER_ID, playerModels.get(i).getId());
                values.put(PLAYER_NAME_COLUMN, playerModels.get(i).getName());
                values.put(PLAYER_SCORE_COLUMN, playerModels.get(i).getScore());
                db.update(PLAYER_TABLE_GIVEN, values, PLAYER_ID + "=" + playerModels.get(i).getId(), null);
                db.close();
            }
        }
    }



    public LinkedList<PlayerModel> getPlayerScores() {
        LinkedList<PlayerModel> alldata = new LinkedList<>();
        String selectQuery = "SELECT  * FROM " + PLAYER_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {

                    PlayerModel momDataType = new PlayerModel(cursor.getString(cursor.getColumnIndex(PLAYER_ID)),
                            cursor.getString(cursor.getColumnIndex(PLAYER_NAME_COLUMN)),
                            cursor.getString(cursor.getColumnIndex(PLAYER_SCORE_COLUMN)));
                    alldata.add(momDataType);
                }
                while (cursor.moveToNext());
            }
        } catch (IllegalStateException e) {
            Log.i("Error: ", e.getMessage());
        }
        Log.d("Size_player_table: ", String.valueOf(alldata.size()));
        cursor.close();
        return alldata;
    }

    public LinkedList<PlayerModel> getPlayerScoresTEMP() {
        LinkedList<PlayerModel> alldata = new LinkedList<>();
        String selectQuery = "SELECT  * FROM " + PLAYER_TABLE_TEMPORARY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {

                    PlayerModel momDataType = new PlayerModel(cursor.getString(cursor.getColumnIndex(PLAYER_ID)),
                            cursor.getString(cursor.getColumnIndex(PLAYER_NAME_COLUMN)),
                            cursor.getString(cursor.getColumnIndex(PLAYER_SCORE_COLUMN)));
                    alldata.add(momDataType);
                }
                while (cursor.moveToNext());
            }
        } catch (IllegalStateException e) {
            Log.i("Error: ", e.getMessage());
        }
        Log.d("Size_player_table: ", String.valueOf(alldata.size()));
        cursor.close();
        return alldata;
    }

    public LinkedList<PlayerModel> getPlayerScoresTAKEN() {
        LinkedList<PlayerModel> alldata = new LinkedList<>();
        String selectQuery = "SELECT  * FROM " + PLAYER_TABLE_TAKEN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {

                    PlayerModel momDataType = new PlayerModel(cursor.getString(cursor.getColumnIndex(PLAYER_ID)),
                            cursor.getString(cursor.getColumnIndex(PLAYER_NAME_COLUMN)),
                            cursor.getString(cursor.getColumnIndex(PLAYER_SCORE_COLUMN)));
                    alldata.add(momDataType);
                }
                while (cursor.moveToNext());
            }
        } catch (IllegalStateException e) {
            Log.i("Error: ", e.getMessage());
        }
        Log.d("Size_player_table: ", String.valueOf(alldata.size()));
        cursor.close();
        return alldata;
    }

    public LinkedList<PlayerModel> getPlayerScoresGIVEN() {
        LinkedList<PlayerModel> alldata = new LinkedList<>();
        String selectQuery = "SELECT  * FROM " + PLAYER_TABLE_GIVEN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {

                    PlayerModel momDataType = new PlayerModel(cursor.getString(cursor.getColumnIndex(PLAYER_ID)),
                            cursor.getString(cursor.getColumnIndex(PLAYER_NAME_COLUMN)),
                            cursor.getString(cursor.getColumnIndex(PLAYER_SCORE_COLUMN)));
                    alldata.add(momDataType);
                }
                while (cursor.moveToNext());
            }
        } catch (IllegalStateException e) {
            Log.i("Error: ", e.getMessage());
        }
        Log.d("Size_player_table: ", String.valueOf(alldata.size()));
        cursor.close();
        return alldata;
    }

    public LinkedList<PlayerModel> getPlayerScoresPACK() {
        LinkedList<PlayerModel> alldata = new LinkedList<>();
        String selectQuery = "SELECT  * FROM " + PLAYER_TABLE_PACK;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {

                    PlayerModel momDataType = new PlayerModel(cursor.getString(cursor.getColumnIndex(PLAYER_ID)),
                            cursor.getString(cursor.getColumnIndex(PLAYER_NAME_COLUMN)),
                            cursor.getString(cursor.getColumnIndex(PLAYER_SCORE_COLUMN)));
                    alldata.add(momDataType);
                }
                while (cursor.moveToNext());
            }
        } catch (IllegalStateException e) {
            Log.i("Error: ", e.getMessage());
        }
        Log.d("Size_player_table: ", String.valueOf(alldata.size()));
        cursor.close();
        return alldata;
    }

    public InfoModel getInfo() {
        LinkedList<InfoModel> alldata = new LinkedList<>();
        String selectQuery = "SELECT  * FROM " + INFO_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {

                    InfoModel infoModel = new InfoModel(cursor.getString(cursor.getColumnIndex(MATCH_NAME)),
                            cursor.getString(cursor.getColumnIndex(CARD_VALUE)),
                            cursor.getString(cursor.getColumnIndex(NUMBER_OF_PLAYER)),
                            cursor.getString(cursor.getColumnIndex(PACK_VALUE)));
                    alldata.add(infoModel);
                }
                while (cursor.moveToNext());
            }
        } catch (IllegalStateException e) {
            Log.i("Error: ", e.getMessage());
        }
        Log.d("Size_info_table: ", String.valueOf(alldata.size()));
        cursor.close();
        return alldata.get(0);
    }


}
