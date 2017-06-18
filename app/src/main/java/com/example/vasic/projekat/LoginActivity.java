package com.example.vasic.projekat;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vasic.projekat.Model.User;
import com.example.vasic.projekat.dao.DatabaseHelper;
import com.example.vasic.projekat.dao.UserDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private User user;
    private List<User> users = new ArrayList<>();

    private EditText userEditText,passEditText;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEditText = (EditText)findViewById(R.id.login_username_edit_text);
        passEditText = (EditText) findViewById(R.id.login_password_edit_text);
        loginButton = (Button) findViewById(R.id.login_button);

        users = getAllUsers();








        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = userEditText.getText().toString();
                String password = passEditText.getText().toString();

                user = login(username, password);
                if(user != null){
                    Intent intent = new Intent(LoginActivity.this,ItemsActivity.class);
                   intent.putExtra("current_user",user.getId());
                   startActivity(intent);

                }else{


                    Toast.makeText(LoginActivity.this, "Pogresan username/password", Toast.LENGTH_LONG).show();
                }



            }
        });
    }



    private List<User> getAllUsers(){

        DatabaseHelper dh = new DatabaseHelper(LoginActivity.this);


        try {
            UserDao userDao = new UserDao(dh.getConnectionSource());

            users = userDao.queryForAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return users;

    }

    private User login(String username1, String password1){


        for(User user: users){

            if(user.getEmail().equals(username1)&& user.getPassword().equals(password1)){

                return user;
            }
        }

        return null;
    }


}
