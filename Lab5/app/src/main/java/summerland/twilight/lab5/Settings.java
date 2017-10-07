package summerland.twilight.lab5;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Settings extends AppCompatActivity implements View.OnClickListener{

    private Button saveButton;
    private EditText textSize;
    private EditText textColour;
    private EditText bgColor;
    private TextView welcomeTxt;

    private SharedPreferences sharedPreferences;
    public static String  DEFAULT = "not available";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        saveButton = (Button) findViewById(R.id.buttonSave);
        textSize = (EditText) findViewById(R.id.editTextTextSize);
        textColour = (EditText) findViewById(R.id.editTextTextColor);
        bgColor = (EditText) findViewById(R.id.editTextBGC);
        welcomeTxt = (TextView) findViewById(R.id.TextViewWelcome);

        sharedPreferences = getSharedPreferences("Mydata", Context.MODE_PRIVATE);

        int size = sharedPreferences.getInt("SIZE", 20);
        int txtColor = sharedPreferences.getInt("TEXTCOLOR", Color.BLACK);
        int bgColour = sharedPreferences.getInt("BGCOLOR", Color.WHITE);
        String username = sharedPreferences.getString("USERNAME", DEFAULT);

        welcomeTxt.setText("Welcome back " + username);
        welcomeTxt.setTextSize(size);
        welcomeTxt.setTextColor(txtColor);

        getWindow().getDecorView().setBackgroundColor(bgColour);
        saveButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        try{
            int size = Integer.valueOf(textSize.getText().toString());
            int txtColor = Color.parseColor(textColour.getText().toString());
            int bgColour = Color.parseColor(bgColor.getText().toString());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("SIZE",size);
            editor.putInt("TEXTCOLOR", txtColor);
            editor.putInt("BGCOLOR", bgColour);
            editor.commit();
            welcomeTxt.setTextSize(size);
            welcomeTxt.setTextColor(txtColor);

            getWindow().getDecorView().setBackgroundColor(bgColour);
        }
        catch (IllegalArgumentException e)
        {
            Toast.makeText(this, "Please enter a valid color format or font size", Toast.LENGTH_LONG).show();
        }
    }
}
