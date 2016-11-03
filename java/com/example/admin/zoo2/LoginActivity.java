package com.example.admin.zoo2;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private final String LOG_TAG = "myTag";

    private Button login_btnEnter, login_btnRegister;
    private EditText login_eTextLogin, login_eTextPassword;

    private String login;
    private String password;
    private InnerDataBase innerDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initializeViews();
        setOnClickListeners();

        innerDataBase = new InnerDataBase(this);
    }

    private void initializeViews() {
        login_btnEnter = (Button) findViewById(R.id.login_btnEnter);
        login_btnRegister = (Button) findViewById(R.id.login_btnRegister);
        login_eTextLogin = (EditText) findViewById(R.id.login_eTextLogin);
        login_eTextPassword = (EditText) findViewById(R.id.login_eTextPassword);
    }

    private void setOnClickListeners() {
        login_btnEnter.setOnClickListener(this);
        login_btnRegister.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        innerDataBase.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        innerDataBase.close();
    }

    @Override
    public void onClick(View view) {
        if (accessDenied(view.getId())) return;
        switch (view.getId()) {
            case R.id.login_btnEnter:
                User user = innerDataBase.getUserByName(login);
                startActivity(new Intent(this, MainActivity.class).putExtra("currentUser", user));
                break;
            case R.id.login_btnRegister:
                innerDataBase.addUser(new User(login, password, false));
                Toast toast = Toast.makeText(this, "Пользователь успешно добавлен", Toast.LENGTH_SHORT);
                toast.show();
                break;
        }
    }

    private boolean accessDenied(int viewId) {
        Toast toast = Toast.makeText(this, "", Toast.LENGTH_LONG);

        login = login_eTextLogin.getText().toString();
        password = login_eTextPassword.getText().toString();

        if (login.length() == 0 || password.length() == 0) {
            toast.setText("Введите логин и пароль");
            toast.show();
            return true;
        }

        if (viewId == R.id.login_btnEnter) {
            User user = innerDataBase.getUserByName(login);
            if (user == null) {
                toast.setText("Такого пользователя не существует");
                toast.show();
                return true;
            }
            if (!user.getUserPassword().equals(password)) {
                toast.setText("Пароли не совпадают");
                toast.show();
                return true;
            }
        }
        if (viewId == R.id.login_btnRegister) {
            if (innerDataBase.userIsExist(new User(login))) {
                toast.setText("Такой пользователь уже существует");
                toast.show();
                return true;
            }
            if (login.length() > 10) {
                toast.setText("Длина логина должна быть не более 10 символов");
                toast.show();
                return true;
            } else if (password.length() > 20) {
                toast.setText("Длина пароля должна быть не более 20 символов");
                toast.show();
                return true;
            }
        }
        return false;
    }
}