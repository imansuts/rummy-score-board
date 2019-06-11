package work.demo.com.rummyscore.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import work.demo.com.rummyscore.PopUp.PopupClass;
import work.demo.com.rummyscore.R;

public class HomeActivity extends AppCompatActivity {

    EditText match_name_edittext, card_value_edittext, number_of_employees_edittext, pack_value_edittext;
    Button button_enter;
    String match_name = "", card_value = "", pack_value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        match_name_edittext = findViewById(R.id.match_name_edittext);
        card_value_edittext = findViewById(R.id.card_value_edittext);
        number_of_employees_edittext = findViewById(R.id.number_of_employees_edittext);
        pack_value_edittext = findViewById(R.id.pack_value_edittext);
        button_enter = findViewById(R.id.button_enter);

        pack_value_edittext.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    button_enter.performClick();
                    return true;
                }
                return false;
            }
        });

        button_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(number_of_employees_edittext.getText().toString().trim()) &&
                        !TextUtils.isEmpty(match_name_edittext.getText().toString().trim()) &&
                        !TextUtils.isEmpty(card_value_edittext.getText().toString().trim()) &&
                        !TextUtils.isEmpty(pack_value_edittext.getText().toString().trim())) {

                    match_name = match_name_edittext.getText().toString().trim();
                    card_value = card_value_edittext.getText().toString().trim();
                    pack_value = pack_value_edittext.getText().toString().trim();


                    int i = Integer.parseInt(number_of_employees_edittext.getText().toString().trim());
                    PopupClass popupClass = new PopupClass(HomeActivity.this, true, i);
                    popupClass.show();
                }
            }
        });
    }

    public String GetMatchName(){
     return match_name;
    }

    public String GetCardValue(){
        return card_value;
    }

    public String GetPackValue(){
        return pack_value;
    }
}
