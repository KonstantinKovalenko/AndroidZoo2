package com.example.admin.zoo2.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.admin.zoo2.R;
import com.example.admin.zoo2.database.InnerDataBase;
import com.example.admin.zoo2.database.Animal;
import com.example.admin.zoo2.database.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final int MENU_ITEM_DBMANAGE = 111;
    private final int MENU_ITEM_LOGOUT = 222;
    private final int DIALOG_SHOW_ANIMAL = 1;

    private final String LISTITEM_ANIMAL_TYPE = "type";
    private final String LISTITEM_ANIMAL_NAME = "name";

    private TextView animalobserve_animalName, animalobserve_animalType, animalobserve_animalAge,
            animalobserve_animalCage, animalobserve_animalCaretaker;

    private InnerDataBase innerDataBase;
    private User currentUser;
    private ListView main_listAnimals;
    private String[] animalForObserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initializeViews();
        initializeAndOpenInnerDataBase();
        setCurrentUser();
    }

    private void initializeViews() {
        main_listAnimals = (ListView) findViewById(R.id.main_lvAnimals);
    }

    private void initializeAndOpenInnerDataBase() {
        innerDataBase = new InnerDataBase(this);
        innerDataBase.open();
    }

    private void setCurrentUser() {
        currentUser = getIntent().getParcelableExtra("currentUser");
    }

    @Override
    protected void onResume() {
        initializeAnimalList();
        super.onResume();
    }

    private void initializeAnimalList() {
        ArrayList<Animal> animalList = innerDataBase.getAllAnimals();
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> map;
        for (Animal animal : animalList) {
            String animalType = innerDataBase.getAnimalTypeTitleById(animal.getAnimalTypeId());
            String animalName = animal.getAnimalName();
            map = new HashMap<>();
            map.put(LISTITEM_ANIMAL_TYPE, "Вид: " + animalType);
            map.put(LISTITEM_ANIMAL_NAME, "Имя: " + animalName);
            data.add(map);
        }
        String[] from = {LISTITEM_ANIMAL_TYPE, LISTITEM_ANIMAL_NAME};
        int[] to = {R.id.item_tvAnimalType, R.id.item_tvAnimalName};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.adapteritems, from, to);
        main_listAnimals.setAdapter(simpleAdapter);
        main_listAnimals.setOnItemClickListener(onAnimalListClickListener);
    }

    AdapterView.OnItemClickListener onAnimalListClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            buildAnimalForObserve(i);
            showDialog(DIALOG_SHOW_ANIMAL);
        }
    };

    private void buildAnimalForObserve(int animalListId) {
        animalForObserve = innerDataBase.getAnimalById(animalListId + 1);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Полный обзор животного");
        LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.animalobservedialog, null);
        adb.setView(view);
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return adb.create();
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        String space = " ";
        animalobserve_animalName = (TextView) dialog.getWindow().findViewById(R.id.animalobserve_animalName);
        animalobserve_animalType = (TextView) dialog.getWindow().findViewById(R.id.animalobserve_animalType);
        animalobserve_animalAge = (TextView) dialog.getWindow().findViewById(R.id.animalobserve_animalAge);
        animalobserve_animalCage = (TextView) dialog.getWindow().findViewById(R.id.animalobserve_animalCage);
        animalobserve_animalCaretaker = (TextView) dialog.getWindow().findViewById(R.id.animalobserve_animalCaretaker);
        animalobserve_animalName.setText(animalForObserve[0]);
        animalobserve_animalType.setText(animalForObserve[1]);
        animalobserve_animalAge.setText(animalForObserve[2]);
        animalobserve_animalCage.setText(animalForObserve[3]);
        animalobserve_animalCaretaker.setText(animalForObserve[4] + space + animalForObserve[5]);
        dialog.setCanceledOnTouchOutside(false);
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
    protected void onDestroy() {
        super.onDestroy();
        innerDataBase.close();
    }
}
