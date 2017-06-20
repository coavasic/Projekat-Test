package com.example.vasic.projekat;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vasic.projekat.Model.User;
import com.example.vasic.projekat.dao.DatabaseHelper;
import com.example.vasic.projekat.dao.UserDao;

import java.sql.SQLException;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyAccountActivity extends AppCompatActivity{

    private TextView nameText,emailText,addressText,phoneText;
    private Button callButton,smsButton, emailButton;
    private CircleImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        Intent intent = getIntent();

        long currentUserId = intent.getLongExtra("current_user",-1);
        User currentUser = findUserById(currentUserId);

        nameText = (TextView)findViewById(R.id.my_account_name);
        emailText = (TextView) findViewById(R.id.my_account_email);
        addressText = (TextView) findViewById(R.id.my_account_address);
        phoneText = (TextView)findViewById(R.id.my_account_phone);

        emailButton = (Button)findViewById(R.id.email_user_button);
        callButton = (Button) findViewById(R.id.call_user_button);
        smsButton = (Button) findViewById(R.id.sms_user_button);
        userImage = (CircleImageView) findViewById(R.id.circleImageView);

        userImage.setImageResource(Integer.parseInt(currentUser.getPicture()));
        nameText.setText(currentUser.getName());
        emailText.setText(currentUser.getEmail());
        addressText.setText(currentUser.getAddress());
        phoneText.setText(currentUser.getPhone());

        final String mailTo = "mailto:"+currentUser.getEmail();

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(mailTo));
                startActivity(emailIntent);
            }
        });














    }



    private User findUserById(long id){
        User user = new User();

        DatabaseHelper dh = new DatabaseHelper(MyAccountActivity.this);

        try {
            UserDao userDao = new UserDao(dh.getConnectionSource());
            user = userDao.queryForId(id);



        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
