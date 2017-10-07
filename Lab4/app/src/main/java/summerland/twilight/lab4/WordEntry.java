package summerland.twilight.lab4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

public class WordEntry extends AppCompatActivity implements View.OnClickListener {

    EditText wordEntryEditText;
    Button butDict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_entry);
        wordEntryEditText = (EditText) findViewById(R.id.editWordEntry);
        butDict = (Button) findViewById(R.id.butDict);
        butDict.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        String word = wordEntryEditText.getText().toString();

        Intent i = new Intent();
        i.putExtra("WORD", word);
        setResult(RESULT_OK, i);
        finish();
    }
}
