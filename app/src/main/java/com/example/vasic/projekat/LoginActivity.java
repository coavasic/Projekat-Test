package com.example.vasic.projekat;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vasic.projekat.Model.Auction;
import com.example.vasic.projekat.Model.Item;
import com.example.vasic.projekat.Model.User;
import com.example.vasic.projekat.Tools.Mokap;
import com.example.vasic.projekat.dao.AuctionDao;
import com.example.vasic.projekat.dao.DatabaseHelper;
import com.example.vasic.projekat.dao.ItemDao;
import com.example.vasic.projekat.dao.UserDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private User user;
    private List<User> users = new ArrayList<>();

    private EditText userEditText,passEditText;
    private Button loginButton,initDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEditText = (EditText)findViewById(R.id.login_username_edit_text);
        passEditText = (EditText) findViewById(R.id.login_password_edit_text);
        loginButton = (Button) findViewById(R.id.login_button);
        initDb = (Button) findViewById(R.id.init_database_button);

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
                    finish();

                }else{


                    Toast.makeText(LoginActivity.this, "Pogresan username/password", Toast.LENGTH_LONG).show();
                }



            }
        });


        initDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHelper dh = new DatabaseHelper(LoginActivity.this);

                try {
                    ItemDao itemDao = new ItemDao(dh.getConnectionSource());
                    AuctionDao auctionDao = new AuctionDao(dh.getConnectionSource());
                    UserDao userDao = new UserDao(dh.getConnectionSource());

                    for(Item item1 : Mokap.items){
                        int resoult = itemDao.create(item1);

                        if(resoult == 1){
                            for(Auction auction : item1.getAutions()){

                                Log.i("Auction Error", auction.getEndDate() + "  " + auction.getStartPrice());
                                auctionDao.create(auction);
                                userDao.createIfNotExists(auction.getUser());

                            }
                        }
                    }

                    for (User user1: Mokap.users){

                        userDao.createIfNotExists(user1);
                    }





                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
    }




    @Override
    public void onBackPressed() {
        finish();
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
