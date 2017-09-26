package co.id.franknco.ui.addcard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.id.franknco.R;
import co.id.franknco.ui.main.MainActivity;

public class AddCard extends AppCompatActivity {
    @BindView(R.id.btn_addcard)Button btn_addcard;
    @BindView(R.id.et_cardnumber)EditText et_cardnumber;
    @BindView(R.id.et_pincard)EditText et_pincard;

    SharedPreferences sharedpreferences;

    public static final String cardPref = "CardPref";
    public static final String cardNum = "CardNum";
    public static final String cardPin = "CardPin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(cardPref, Context.MODE_PRIVATE);
        btn_addcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = et_cardnumber.getText().toString();
                String b = et_pincard.getText().toString();

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(cardNum,a);
                editor.putString(cardPin, b);
                editor.commit();

                Toast.makeText(AddCard.this, "Successful Add Card", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(AddCard.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                finish();
            }
        });
    }
}
