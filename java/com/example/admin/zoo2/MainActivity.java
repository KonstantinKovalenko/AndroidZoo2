package com.example.admin.zoo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "myTag";
    private final int MENU_ITEM_DBMANAGE = 111;
    private final int MENU_ITEM_LOGOUT = 222;

    private InnerDataBase innerDataBase;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        innerDataBase = new InnerDataBase(this);
        currentUser = getIntent().getParcelableExtra("currentUser");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add(0, MENU_ITEM_LOGOUT, 0, currentUser.getUserName());
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        item = menu.add(0, MENU_ITEM_DBMANAGE, 0, "Работа с базами данных");
        if (!currentUser.isAdmin()) {
            item.setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ITEM_DBMANAGE:
                startActivity(new Intent(this, DBManageActivity.class).putExtra("currentUser", currentUser));
                break;
            case MENU_ITEM_LOGOUT:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        innerDataBase.open();
        showAllUsers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        innerDataBase.close();
    }

    private void showAllUsers() {
        ArrayList<User> userList = innerDataBase.getAllUsers();
        for (User user : userList) {
            Log.d(LOG_TAG, "ID = " + user.getUserId()
                    + ", Name = " + user.getUserName()
                    + ", password = " + user.getUserPassword()
                    + ", account is admin: " + user.isAdmin());
        }
    }
}
