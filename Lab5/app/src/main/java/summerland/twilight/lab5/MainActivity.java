package summerland.twilight.lab5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;


public class MainActivity extends Activity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private Button submitButton;
    private EditText editPass;
    private EditText editUser;
    public static String  DEFAULT = "not available";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("Mydata", Context.MODE_PRIVATE);

        String username = sharedPreferences.getString("USERNAME", DEFAULT);
        String password = sharedPreferences.getString("PASSWORD", DEFAULT);
        submitButton = (Button) findViewById(R.id.buttonSubmit);
        editPass = (EditText) findViewById(R.id.editTextPassword);
        editUser = (EditText) findViewById(R.id.editTextUsername);
        submitButton.setOnClickListener(this);
        if(!username.equals(DEFAULT) && !password.equals(DEFAULT))
        {
            Intent intent = new Intent(this, LoginUserActivity.class);
            finish();
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String tempPass = editPass.getText().toString();
        String tempUser = editUser.getText().toString();
        if(checkCredentials(tempPass, tempUser))
        {
            editor.putString("USERNAME", tempUser);
            editor.putString("PASSWORD", tempPass);
            editor.commit();
            Intent intent = new Intent(this, LoginUserActivity.class);
            finish();
            startActivity(intent);
        }

        //change activity
    }

    private boolean checkCredentials(String pass, String user)
    {
        if(pass.equals("") || pass.equals(DEFAULT))
        {
            Toast.makeText(this, "please use a different password", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(user.equals("") || user.equals(DEFAULT))
        {
            Toast.makeText(this, "please use a different username", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
