package summerland.twilight.lab4;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    private static final int REQUEST_WORD_ENTRY = 0;
    Button butDictionary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        butDictionary = (Button) findViewById(R.id.butDict);
        butDictionary.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, WordEntry.class);

                startActivityForResult(i, REQUEST_WORD_ENTRY);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_WORD_ENTRY)
        {
            if(resultCode==RESULT_OK)
            {
                String word_entered = data.getExtras().getString("WORD");
                Uri webpage = Uri.parse("http://www.merriam-webster.com/dictionary/" +
                 word_entered);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        }
    }

}
