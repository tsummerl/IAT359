package summerland.twilight.lab5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginUserActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    EditText editPass;
    EditText editUser;
    Button buttonRegister;
    Button buttonLogin;
    public static String  DEFAULT = "not available";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        sharedPreferences = getSharedPreferences("Mydata", Context.MODE_PRIVATE);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editPass = (EditText) findViewById(R.id.editTextPassword);
        editUser = (EditText) findViewById(R.id.editTextUsername);
        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (view.getId())
        {
            case R.id.buttonLogin:
                String username = sharedPreferences.getString("USERNAME", DEFAULT);
                String password = sharedPreferences.getString("PASSWORD", DEFAULT);
                if(username.equals(editUser.getText().toString()) &&
                        password.equals(editPass.getText().toString()))
                {
                    String text = "Welcome Back " + username;
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(this, Settings.class);
                    startActivity(i);
                }
                else
                {
                    editor.putString("USERNAME", DEFAULT);
                    editor.putString("PASSWORD", DEFAULT);
                    editor.commit();
                    Toast.makeText(this, "Register as a new user", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                }
                break;
            case R.id.buttonRegister:
                editor.putString("USERNAME", DEFAULT);
                editor.putString("PASSWORD", DEFAULT);
                editor.commit();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
        }

    }
}
