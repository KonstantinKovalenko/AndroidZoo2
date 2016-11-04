package com.example.admin.zoo2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class DBManageActivity extends AppCompatActivity {

    private final int DIALOG_SHOW_ALL_USERS = 100;
    private final int DIALOG_ADD_USER = 101;
    private final int DIALOG_UPDATE_USER_STEP1 = 102;
    private final int DIALOG_UPDATE_USER_STEP2 = 10222;
    private final int DIALOG_REMOVE_USER = 103;

    private final int DIALOG_SHOW_ALL_ANIMAL_TYPES = 104;
    private final int DIALOG_ADD_ANIMAL_TYPE = 105;
    private final int DIALOG_UPDATE_ANIMAL_TYPE_STEP1 = 106;
    private final int DIALOG_UPDATE_ANIMAL_TYPE_STEP2 = 10666;
    private final int DIALOG_REMOVE_ANIMAL_TYPE = 107;

    private final int DIALOG_SHOW_ALL_ANIMAL_CAGES = 108;
    private final int DIALOG_ADD_ANIMAL_CAGE = 109;
    private final int DIALOG_UPDATE_ANIMAL_CAGE_STEP1 = 110;
    private final int DIALOG_UPDATE_ANIMAL_CAGE_STEP2 = 11010;
    private final int DIALOG_REMOVE_ANIMAL_CAGE = 111;

    private final int DIALOG_SHOW_ALL_ANIMAL_CARETAKERS = 112;
    private final int DIALOG_ADD_ANIMAL_CARETAKER = 113;
    private final int DIALOG_UPDATE_ANIMAL_CARETAKER_STEP1 = 114;
    private final int DIALOG_UPDATE_ANIMAL_CARETAKER_STEP2 = 11414;
    private final int DIALOG_REMOVE_ANIMAL_CARETAKER = 115;

    private final int DIALOG_SHOW_ALL_ANIMALS = 116;
    private final int DIALOG_ADD_ANIMAL = 117;
    private final int DIALOG_UPDATE_ANIMAL_STEP1 = 118;
    private final int DIALOG_UPDATE_ANIMAL_STEP2 = 11818;
    private final int DIALOG_REMOVE_ANIMAL = 119;

    private EditText addUser_login, addUser_password, addCage_title, addType_title, addCaretaker_name, addCaretaker_surname, addAnimal_name, addAnimal_age;
    private Spinner addAnimal_spinType, addAnimal_spinCage, addAnimal_spinCaretaker;
    private CheckBox addUser_isAdmin;

    private ListView dbmanage_lView;

    private int currentDialog = -1;
    private InnerDataBase innerDataBase;
    private User userForUpdate;
    private User currentUser;
    private AnimalType animalTypeForUpdate;
    private AnimalCage animalCageForUpdate;
    private AnimalCaretaker animalCaretakerForUpdate;
    private Animal animalForUpdate;

    private final int ACTION_SHOW_ALL_USERS = 0;
    private final int ACTION_ADD_USER = 1;
    private final int ACTION_UPDATE_USER = 2;
    private final int ACTION_REMOVE_USER = 3;

    private final int ACTION_SHOW_ALL_ANIMAL_TYPES = 4;
    private final int ACTION_ADD_ANIMAL_TYPE = 5;
    private final int ACTION_UPDATE_ANIMAL_TYPE = 6;
    private final int ACTION_REMOVE_ANIMAL_TYPE = 7;

    private final int ACTION_SHOW_ALL_ANIMAL_CAGES = 8;
    private final int ACTION_ADD_ANIMAL_CAGE = 9;
    private final int ACTION_UPDATE_ANIMAL_CAGE = 10;
    private final int ACTION_REMOVE_ANIMAL_CAGE = 11;

    private final int ACTION_SHOW_ALL_ANIMAL_CARETAKERS = 12;
    private final int ACTION_ADD_ANIMAL_CARETAKER = 13;
    private final int ACTION_UPDATE_ANIMAL_CARETAKER = 14;
    private final int ACTION_REMOVE_ANIMAL_CARETAKER = 15;

    private final int ACTION_SHOW_ALL_ANIMALS = 16;
    private final int ACTION_ADD_ANIMAL = 17;
    private final int ACTION_UPDATE_ANIMAL = 18;
    private final int ACTION_REMOVE_ANIMAL = 19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbmanage);
        initializeListView();
        initializeInnerDataBase();
        setCurrentUser();
    }

    private void initializeListView() {
        dbmanage_lView = (ListView) findViewById(R.id.dbmanage_lView);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sArrayActions, android.R.layout.simple_list_item_1);
        dbmanage_lView.setAdapter(adapter);
        dbmanage_lView.setOnItemClickListener(listViewOnClickListener);
    }

    private void initializeInnerDataBase() {
        innerDataBase = new InnerDataBase(this);
        innerDataBase.open();
    }

    private void setCurrentUser() {
        currentUser = getIntent().getParcelableExtra("currentUser");
    }

    @Override
    protected void onDestroy() {
        super.onPause();
        innerDataBase.close();
    }

    AdapterView.OnItemClickListener listViewOnClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            switch (i) {
                case ACTION_SHOW_ALL_USERS:
                    currentDialog = DIALOG_SHOW_ALL_USERS;
                    break;
                case ACTION_ADD_USER:
                    currentDialog = DIALOG_ADD_USER;
                    break;
                case ACTION_UPDATE_USER:
                    currentDialog = DIALOG_UPDATE_USER_STEP1;
                    break;
                case ACTION_REMOVE_USER:
                    currentDialog = DIALOG_REMOVE_USER;
                    break;
                case ACTION_SHOW_ALL_ANIMAL_TYPES:
                    currentDialog = DIALOG_SHOW_ALL_ANIMAL_TYPES;
                    break;
                case ACTION_ADD_ANIMAL_TYPE:
                    currentDialog = DIALOG_ADD_ANIMAL_TYPE;
                    break;
                case ACTION_UPDATE_ANIMAL_TYPE:
                    currentDialog = DIALOG_UPDATE_ANIMAL_TYPE_STEP1;
                    break;
                case ACTION_REMOVE_ANIMAL_TYPE:
                    currentDialog = DIALOG_REMOVE_ANIMAL_TYPE;
                    break;
                case ACTION_SHOW_ALL_ANIMAL_CAGES:
                    currentDialog = DIALOG_SHOW_ALL_ANIMAL_CAGES;
                    break;
                case ACTION_ADD_ANIMAL_CAGE:
                    currentDialog = DIALOG_ADD_ANIMAL_CAGE;
                    break;
                case ACTION_UPDATE_ANIMAL_CAGE:
                    currentDialog = DIALOG_UPDATE_ANIMAL_CAGE_STEP1;
                    break;
                case ACTION_REMOVE_ANIMAL_CAGE:
                    currentDialog = DIALOG_REMOVE_ANIMAL_CAGE;
                    break;
                case ACTION_SHOW_ALL_ANIMAL_CARETAKERS:
                    currentDialog = DIALOG_SHOW_ALL_ANIMAL_CARETAKERS;
                    break;
                case ACTION_ADD_ANIMAL_CARETAKER:
                    currentDialog = DIALOG_ADD_ANIMAL_CARETAKER;
                    break;
                case ACTION_UPDATE_ANIMAL_CARETAKER:
                    currentDialog = DIALOG_UPDATE_ANIMAL_CARETAKER_STEP1;
                    break;
                case ACTION_REMOVE_ANIMAL_CARETAKER:
                    currentDialog = DIALOG_REMOVE_ANIMAL_CARETAKER;
                    break;
                case ACTION_SHOW_ALL_ANIMALS:
                    currentDialog = DIALOG_SHOW_ALL_ANIMALS;
                    break;
                case ACTION_ADD_ANIMAL:
                    currentDialog = DIALOG_ADD_ANIMAL;
                    break;
                case ACTION_UPDATE_ANIMAL:
                    currentDialog = DIALOG_UPDATE_ANIMAL_STEP1;
                    break;
                case ACTION_REMOVE_ANIMAL:
                    currentDialog = DIALOG_REMOVE_ANIMAL;
                    break;
            }
            showDialog(currentDialog);
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        String[] data = {""};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        LinearLayout view;
        switch (id) {
            case DIALOG_ADD_USER:
                adb.setTitle("Добавление пользователя");
                adb.setPositiveButton("Добавить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                view = (LinearLayout) getLayoutInflater().inflate(R.layout.adduserdialog, null);
                adb.setView(view);
                break;
            case DIALOG_SHOW_ALL_USERS:
                adb.setTitle("Список пользователей");
                adb.setAdapter(adapter, overallDialogsListener);
                adb.setPositiveButton("OK", overallDialogsListener);
                break;
            case DIALOG_UPDATE_USER_STEP1:
                adb.setTitle("Выберите пользователя для изменения");
                adb.setSingleChoiceItems(data, -1, overallDialogsListener);
                adb.setPositiveButton("Изменить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                break;
            case DIALOG_UPDATE_USER_STEP2:
                adb.setTitle("Изменить пользователя \"" + userForUpdate.getUserName() + "\"");
                adb.setPositiveButton("Изменить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                view = (LinearLayout) getLayoutInflater().inflate(R.layout.adduserdialog, null);
                adb.setView(view);
                break;
            case DIALOG_REMOVE_USER:
                adb.setTitle("Выберите пользователя для удаления");
                adb.setSingleChoiceItems(data, -1, overallDialogsListener);
                adb.setPositiveButton("Удалить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                break;
            case DIALOG_ADD_ANIMAL_TYPE:
                adb.setTitle("Добавление типа животного");
                adb.setPositiveButton("Добавить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                view = (LinearLayout) getLayoutInflater().inflate(R.layout.addtypedialog, null);
                adb.setView(view);
                break;
            case DIALOG_SHOW_ALL_ANIMAL_TYPES:
                adb.setTitle("Список типов животных");
                adb.setAdapter(adapter, overallDialogsListener);
                adb.setPositiveButton("OK", overallDialogsListener);
                break;
            case DIALOG_UPDATE_ANIMAL_TYPE_STEP1:
                adb.setTitle("Выберите тип для изменения");
                adb.setSingleChoiceItems(data, -1, overallDialogsListener);
                adb.setPositiveButton("Изменить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                break;
            case DIALOG_UPDATE_ANIMAL_TYPE_STEP2:
                adb.setTitle("Изменить тип \"" + animalTypeForUpdate.getTypeTitle() + "\"");
                adb.setPositiveButton("Изменить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                view = (LinearLayout) getLayoutInflater().inflate(R.layout.addtypedialog, null);
                adb.setView(view);
                break;
            case DIALOG_REMOVE_ANIMAL_TYPE:
                adb.setTitle("Выберите тип для удаления");
                adb.setSingleChoiceItems(data, -1, overallDialogsListener);
                adb.setPositiveButton("Удалить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                break;
            case DIALOG_ADD_ANIMAL_CAGE:
                adb.setTitle("Добавление клетки");
                adb.setPositiveButton("Добавить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                view = (LinearLayout) getLayoutInflater().inflate(R.layout.addcagedialog, null);
                adb.setView(view);
                break;
            case DIALOG_SHOW_ALL_ANIMAL_CAGES:
                adb.setTitle("Список клеток для животных");
                adb.setAdapter(adapter, overallDialogsListener);
                adb.setPositiveButton("OK", overallDialogsListener);
                break;
            case DIALOG_UPDATE_ANIMAL_CAGE_STEP1:
                adb.setTitle("Выберите клетку для изменения");
                adb.setSingleChoiceItems(data, -1, overallDialogsListener);
                adb.setPositiveButton("Изменить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                break;
            case DIALOG_UPDATE_ANIMAL_CAGE_STEP2:
                adb.setTitle("Изменить клетку \"" + animalCageForUpdate.getCageTitle() + "\"");
                adb.setPositiveButton("Изменить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                view = (LinearLayout) getLayoutInflater().inflate(R.layout.addcagedialog, null);
                adb.setView(view);
                break;
            case DIALOG_REMOVE_ANIMAL_CAGE:
                adb.setTitle("Выберите клетку для удаления");
                adb.setSingleChoiceItems(data, -1, overallDialogsListener);
                adb.setPositiveButton("Удалить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                break;
            case DIALOG_ADD_ANIMAL_CARETAKER:
                adb.setTitle("Добавление смотрителя");
                adb.setPositiveButton("Добавить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                view = (LinearLayout) getLayoutInflater().inflate(R.layout.addcaretakerdialog, null);
                adb.setView(view);
                break;
            case DIALOG_SHOW_ALL_ANIMAL_CARETAKERS:
                adb.setTitle("Список смотрителей за животными");
                adb.setAdapter(adapter, overallDialogsListener);
                adb.setPositiveButton("OK", overallDialogsListener);
                break;
            case DIALOG_UPDATE_ANIMAL_CARETAKER_STEP1:
                adb.setTitle("Выберите смотрителя для изменения");
                adb.setSingleChoiceItems(data, -1, overallDialogsListener);
                adb.setPositiveButton("Изменить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                break;
            case DIALOG_UPDATE_ANIMAL_CARETAKER_STEP2:
                adb.setTitle("Изменить смотрителя \"" + animalCaretakerForUpdate.getCaretakerName() + "\"");
                adb.setPositiveButton("Изменить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                view = (LinearLayout) getLayoutInflater().inflate(R.layout.addcaretakerdialog, null);
                adb.setView(view);
                break;
            case DIALOG_REMOVE_ANIMAL_CARETAKER:
                adb.setTitle("Выберите смотрителя для удаления");
                adb.setSingleChoiceItems(data, -1, overallDialogsListener);
                adb.setPositiveButton("Удалить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                break;
            case DIALOG_ADD_ANIMAL:
                adb.setTitle("Добавление животного");
                adb.setPositiveButton("Добавить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                view = (LinearLayout) getLayoutInflater().inflate(R.layout.addanimaldialog, null);
                adb.setView(view);
                break;
            case DIALOG_SHOW_ALL_ANIMALS:
                adb.setTitle("Список животных");
                adb.setAdapter(adapter, overallDialogsListener);
                adb.setPositiveButton("OK", overallDialogsListener);
                break;
            case DIALOG_UPDATE_ANIMAL_STEP1:
                adb.setTitle("Выберите животное для изменения");
                adb.setSingleChoiceItems(data, -1, overallDialogsListener);
                adb.setPositiveButton("Изменить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                break;
            case DIALOG_UPDATE_ANIMAL_STEP2:
                adb.setTitle("Изменить животное \"" + animalForUpdate.getAnimalName() + "\"");
                adb.setPositiveButton("Изменить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                view = (LinearLayout) getLayoutInflater().inflate(R.layout.addanimaldialog, null);
                adb.setView(view);
                break;
            case DIALOG_REMOVE_ANIMAL:
                adb.setTitle("Выберите животное для удаления");
                adb.setSingleChoiceItems(data, -1, overallDialogsListener);
                adb.setPositiveButton("Удалить", overallDialogsListener);
                adb.setNegativeButton("Отмена", overallDialogsListener);
                break;
        }
        return adb.create();
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        final String EMPTY = "";
        AlertDialog aDialog = ((AlertDialog) dialog);
        String[] data = getDataByDialogId(id);
        ArrayAdapter<CharSequence> adapter = getAdapterByDialogId(id, data);
        switch (id) {
            case DIALOG_SHOW_ALL_USERS:
            case DIALOG_UPDATE_USER_STEP1:
            case DIALOG_REMOVE_USER:
            case DIALOG_SHOW_ALL_ANIMAL_TYPES:
            case DIALOG_UPDATE_ANIMAL_TYPE_STEP1:
            case DIALOG_REMOVE_ANIMAL_TYPE:
            case DIALOG_SHOW_ALL_ANIMAL_CAGES:
            case DIALOG_UPDATE_ANIMAL_CAGE_STEP1:
            case DIALOG_REMOVE_ANIMAL_CAGE:
            case DIALOG_SHOW_ALL_ANIMAL_CARETAKERS:
            case DIALOG_UPDATE_ANIMAL_CARETAKER_STEP1:
            case DIALOG_REMOVE_ANIMAL_CARETAKER:
            case DIALOG_SHOW_ALL_ANIMALS:
            case DIALOG_UPDATE_ANIMAL_STEP1:
            case DIALOG_REMOVE_ANIMAL:
                aDialog.getListView().setAdapter(adapter);
                break;
            case DIALOG_ADD_USER:
            case DIALOG_UPDATE_USER_STEP2:
                addUser_login = (EditText) dialog.getWindow().findViewById(R.id.addUser_login);
                addUser_password = (EditText) dialog.getWindow().findViewById(R.id.addUser_password);
                addUser_isAdmin = (CheckBox) dialog.getWindow().findViewById(R.id.addUser_isAdmin);
                addUser_login.setText(EMPTY);
                addUser_password.setText(EMPTY);
                addUser_isAdmin.setChecked(false);
                break;
            case DIALOG_ADD_ANIMAL_TYPE:
            case DIALOG_UPDATE_ANIMAL_TYPE_STEP2:
                addType_title = (EditText) dialog.getWindow().findViewById(R.id.addtype_title);
                addType_title.setText(EMPTY);
                addType_title.setHint("animal type");
                break;
            case DIALOG_ADD_ANIMAL_CAGE:
            case DIALOG_UPDATE_ANIMAL_CAGE_STEP2:
                addCage_title = (EditText) dialog.getWindow().findViewById(R.id.addcage_title);
                addCage_title.setText(EMPTY);
                addCage_title.setHint("animal cage");
                break;
            case DIALOG_ADD_ANIMAL_CARETAKER:
            case DIALOG_UPDATE_ANIMAL_CARETAKER_STEP2:
                addCaretaker_name = (EditText) dialog.getWindow().findViewById(R.id.addCaretaker_name);
                addCaretaker_surname = (EditText) dialog.getWindow().findViewById(R.id.addCaretaker_surName);
                addCaretaker_name.setText(EMPTY);
                addCaretaker_surname.setText(EMPTY);
                break;
            case DIALOG_ADD_ANIMAL:
            case DIALOG_UPDATE_ANIMAL_STEP2:
                addAnimal_name = (EditText) dialog.getWindow().findViewById(R.id.addAnimal_name);
                addAnimal_age = (EditText) dialog.getWindow().findViewById(R.id.addAnimal_age);
                addAnimal_spinType = (Spinner) dialog.getWindow().findViewById(R.id.addAnimal_spinType);
                addAnimal_spinCage = (Spinner) dialog.getWindow().findViewById(R.id.addAnimal_spinCage);
                addAnimal_spinCaretaker = (Spinner) dialog.getWindow().findViewById(R.id.addAnimal_spinCaretaker);
                configureSpinners();
                addAnimal_name.setText(EMPTY);
                addAnimal_age.setText(EMPTY);
                break;
        }
        dialog.setCanceledOnTouchOutside(false);
    }

    private String[] getDataByDialogId(int dialogId) throws NullPointerException {
        String[] result = null;
        if (dialogId == DIALOG_SHOW_ALL_USERS || dialogId == DIALOG_UPDATE_USER_STEP1 || dialogId == DIALOG_REMOVE_USER) {
            result = getAllLogins(innerDataBase.getAllUsers());
        } else if (dialogId == DIALOG_SHOW_ALL_ANIMAL_TYPES || dialogId == DIALOG_UPDATE_ANIMAL_TYPE_STEP1 || dialogId == DIALOG_REMOVE_ANIMAL_TYPE) {
            result = getAllTypes(innerDataBase.getAllAnimalTypes());
        } else if (dialogId == DIALOG_SHOW_ALL_ANIMAL_CAGES || dialogId == DIALOG_UPDATE_ANIMAL_CAGE_STEP1 || dialogId == DIALOG_REMOVE_ANIMAL_CAGE) {
            result = getAllCages(innerDataBase.getAllAnimalCages());
        } else if (dialogId == DIALOG_SHOW_ALL_ANIMAL_CARETAKERS || dialogId == DIALOG_UPDATE_ANIMAL_CARETAKER_STEP1 || dialogId == DIALOG_REMOVE_ANIMAL_CARETAKER) {
            result = getAllCaretakers(innerDataBase.getAllAnimalCaretakers());
        } else if (dialogId == DIALOG_SHOW_ALL_ANIMALS || dialogId == DIALOG_UPDATE_ANIMAL_STEP1 || dialogId == DIALOG_REMOVE_ANIMAL) {
            result = getAllAnimals(innerDataBase.getAllAnimals());
        }
        return result;
    }

    private String[] getAllLogins(ArrayList<User> userList) {
        String[] result = new String[userList.size()];
        int counter = 0;
        for (User user : userList) {
            result[counter++] = user.getUserName();
        }
        return result;
    }

    private String[] getAllTypes(ArrayList<AnimalType> typeList) {
        String[] result = new String[typeList.size()];
        int counter = 0;
        for (AnimalType type : typeList) {
            result[counter++] = type.getTypeTitle();
        }
        return result;
    }

    private String[] getAllCages(ArrayList<AnimalCage> cageList) {
        String[] result = new String[cageList.size()];
        int counter = 0;
        for (AnimalCage cage : cageList) {
            result[counter++] = cage.getCageTitle();
        }
        return result;
    }

    private String[] getAllCaretakers(ArrayList<AnimalCaretaker> caretakerList) {
        String[] result = new String[caretakerList.size()];
        int counter = 0;
        for (AnimalCaretaker caretaker : caretakerList) {
            result[counter++] = caretaker.getCaretakerName();
        }
        return result;
    }

    private String[] getAllAnimals(ArrayList<Animal> animalList) {
        String[] result = new String[animalList.size()];
        int counter = 0;
        for (Animal animal : animalList) {
            result[counter++] = animal.getAnimalName();
        }
        return result;
    }

    private ArrayAdapter<CharSequence> getAdapterByDialogId(int dialogId, String[] data) throws NullPointerException {
        ArrayAdapter<CharSequence> result = null;
        if (dialogId == DIALOG_SHOW_ALL_USERS || dialogId == DIALOG_SHOW_ALL_ANIMAL_TYPES ||
                dialogId == DIALOG_SHOW_ALL_ANIMAL_CAGES || dialogId == DIALOG_SHOW_ALL_ANIMAL_CARETAKERS || dialogId == DIALOG_SHOW_ALL_ANIMALS) {
            result = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, data);
        } else if (dialogId == DIALOG_UPDATE_USER_STEP1 || dialogId == DIALOG_REMOVE_USER ||
                dialogId == DIALOG_UPDATE_ANIMAL_TYPE_STEP1 || dialogId == DIALOG_REMOVE_ANIMAL_TYPE ||
                dialogId == DIALOG_UPDATE_ANIMAL_CAGE_STEP1 || dialogId == DIALOG_REMOVE_ANIMAL_CAGE ||
                dialogId == DIALOG_UPDATE_ANIMAL_CARETAKER_STEP1 || dialogId == DIALOG_REMOVE_ANIMAL_CARETAKER ||
                dialogId == DIALOG_UPDATE_ANIMAL_STEP1 || dialogId == DIALOG_REMOVE_ANIMAL) {
            result = new ArrayAdapter<CharSequence>(this, android.R.layout.select_dialog_singlechoice, android.R.id.text1, data);
        }
        return result;
    }

    private void configureSpinners() {
        String[] data = getDataByDialogId(DIALOG_REMOVE_ANIMAL_TYPE);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addAnimal_spinType.setAdapter(adapter);

        data = getDataByDialogId(DIALOG_REMOVE_ANIMAL_CAGE);
        adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addAnimal_spinCage.setAdapter(adapter);

        data = getDataByDialogId(DIALOG_REMOVE_ANIMAL_CARETAKER);
        adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addAnimal_spinCaretaker.setAdapter(adapter);
    }


    DialogInterface.OnClickListener overallDialogsListener = new DialogInterface.OnClickListener() {
        ListView listView;
        String login;
        String password;
        String userName;
        String surname;
        String title;
        int position;
        boolean isAdmin;

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == DialogInterface.BUTTON_NEGATIVE) dialogInterface.dismiss();
            if (i == DialogInterface.BUTTON_POSITIVE) {
                switch (currentDialog) {
                    case DIALOG_SHOW_ALL_USERS:
                        break;
                    case DIALOG_ADD_USER:
                        login = addUser_login.getText().toString();
                        password = addUser_password.getText().toString();
                        isAdmin = addUser_isAdmin.isChecked();
                        User user = new User(login, password, isAdmin);
                        if (innerDataBase.userIsExist(user)) {
                            showToast("Такой пользователь уже существует");
                        } else {
                            innerDataBase.addUser(new User(login, password, isAdmin));
                            showToast("Пользователь \"" + user.getUserName() + "\" добавлен");
                        }
                        break;
                    case DIALOG_UPDATE_USER_STEP1:
                        listView = ((AlertDialog) dialogInterface).getListView();
                        position = listView.getCheckedItemPosition();
                        userName = (String) listView.getItemAtPosition(position);
                        userForUpdate = innerDataBase.getUserByName(userName);
                        currentDialog = DIALOG_UPDATE_USER_STEP2;
                        showDialog(currentDialog);
                        break;
                    case DIALOG_UPDATE_USER_STEP2:
                        login = addUser_login.getText().toString();
                        password = addUser_password.getText().toString();
                        isAdmin = addUser_isAdmin.isChecked();
                        userForUpdate.setUserName(login);
                        userForUpdate.setUserPassword(password);
                        userForUpdate.setAdmin(isAdmin);
                        innerDataBase.updateUser(userForUpdate);
                        showToast("Пользователь обновлен");
                        break;
                    case DIALOG_REMOVE_USER:
                        listView = ((AlertDialog) dialogInterface).getListView();
                        position = listView.getCheckedItemPosition();
                        userName = (String) listView.getItemAtPosition(position);
                        if (currentUser.getUserName().equals(userName)) {
                            showToast("Невозможно удалить пользователя");
                            return;
                        }
                        innerDataBase.removeUser(new User(userName));
                        showToast("Пользователь \"" + userName + "\" удален");
                        break;
                    case DIALOG_SHOW_ALL_ANIMAL_TYPES:
                        break;
                    case DIALOG_ADD_ANIMAL_TYPE:
                        title = addType_title.getText().toString();
                        AnimalType type = new AnimalType(title);
                        if (innerDataBase.animalTypeIsExist(type)) {
                            showToast("Такой тип животных уже существует");
                        } else {
                            innerDataBase.addAnimalType(type);
                            showToast("Тип животного \"" + type.getTypeTitle() + "\" добавлен");
                        }
                        break;
                    case DIALOG_UPDATE_ANIMAL_TYPE_STEP1:
                        listView = ((AlertDialog) dialogInterface).getListView();
                        position = listView.getCheckedItemPosition();
                        title = (String) listView.getItemAtPosition(position);
                        animalTypeForUpdate = innerDataBase.getAnimalTypeByTitle(title);
                        currentDialog = DIALOG_UPDATE_ANIMAL_TYPE_STEP2;
                        showDialog(currentDialog);
                        break;
                    case DIALOG_UPDATE_ANIMAL_TYPE_STEP2:
                        title = addType_title.getText().toString();
                        animalTypeForUpdate.setTypeTitle(title);
                        innerDataBase.updateAnimalType(animalTypeForUpdate);
                        showToast("Тип животных обновлен");
                        break;
                    case DIALOG_REMOVE_ANIMAL_TYPE:
                        listView = ((AlertDialog) dialogInterface).getListView();
                        position = listView.getCheckedItemPosition();
                        title = (String) listView.getItemAtPosition(position);
                        innerDataBase.removeAnimalType(new AnimalType(title));
                        showToast("Тип животных \"" + title + "\" удален");
                        break;
                    case DIALOG_SHOW_ALL_ANIMAL_CAGES:
                        break;
                    case DIALOG_ADD_ANIMAL_CAGE:
                        title = addCage_title.getText().toString();
                        AnimalCage cage = new AnimalCage(title);
                        if (innerDataBase.animalCageIsExist(cage)) {
                            showToast("Такая клетка уже существует");
                        } else {
                            innerDataBase.addAnimalCage(cage);
                            showToast("Тип клетки \"" + cage.getCageTitle() + "\" добавлен");
                        }
                        break;
                    case DIALOG_UPDATE_ANIMAL_CAGE_STEP1:
                        listView = ((AlertDialog) dialogInterface).getListView();
                        position = listView.getCheckedItemPosition();
                        title = (String) listView.getItemAtPosition(position);
                        animalCageForUpdate = innerDataBase.getAnimalCageByTitle(title);
                        currentDialog = DIALOG_UPDATE_ANIMAL_CAGE_STEP2;
                        showDialog(currentDialog);
                        break;
                    case DIALOG_UPDATE_ANIMAL_CAGE_STEP2:
                        title = addCage_title.getText().toString();
                        animalCageForUpdate.setCageTitle(title);
                        innerDataBase.updateAnimalCage(animalCageForUpdate);
                        showToast("Клетка животных обновлена");
                        break;
                    case DIALOG_REMOVE_ANIMAL_CAGE:
                        listView = ((AlertDialog) dialogInterface).getListView();
                        position = listView.getCheckedItemPosition();
                        title = (String) listView.getItemAtPosition(position);
                        innerDataBase.removeAnimalCage(new AnimalCage(title));
                        showToast("Клетка животных \"" + title + "\" удалена");
                        break;
                    case DIALOG_SHOW_ALL_ANIMAL_CARETAKERS:
                        break;
                    case DIALOG_ADD_ANIMAL_CARETAKER:
                        userName = addCaretaker_name.getText().toString();
                        surname = addCaretaker_surname.getText().toString();
                        AnimalCaretaker ctaker = new AnimalCaretaker(userName, surname);
                        if (innerDataBase.animalCaretakerIsExist(ctaker)) {
                            showToast("Такой смотритель уже существует");
                        } else {
                            innerDataBase.addAnimalCaretaker(new AnimalCaretaker(userName, surname));
                            showToast("Смотритель \"" + ctaker.getCaretakerName() + "\" добавлен");
                        }
                        break;
                    case DIALOG_UPDATE_ANIMAL_CARETAKER_STEP1:
                        listView = ((AlertDialog) dialogInterface).getListView();
                        position = listView.getCheckedItemPosition();
                        userName = (String) listView.getItemAtPosition(position);
                        animalCaretakerForUpdate = innerDataBase.getAnimalCaretakerByName(userName);
                        currentDialog = DIALOG_UPDATE_ANIMAL_CARETAKER_STEP2;
                        showDialog(currentDialog);
                        break;
                    case DIALOG_UPDATE_ANIMAL_CARETAKER_STEP2:
                        userName = addCaretaker_name.getText().toString();
                        surname = addCaretaker_surname.getText().toString();
                        animalCaretakerForUpdate.setCaretakerName(userName);
                        animalCaretakerForUpdate.setCaretakerSurname(surname);
                        innerDataBase.updateAnimalCaretaker(animalCaretakerForUpdate);
                        showToast("Смотритель обновлен");
                        break;
                    case DIALOG_REMOVE_ANIMAL_CARETAKER:
                        listView = ((AlertDialog) dialogInterface).getListView();
                        position = listView.getCheckedItemPosition();
                        userName = (String) listView.getItemAtPosition(position);
                        innerDataBase.removeAnimalCaretaker(new AnimalCaretaker(userName));
                        showToast("Смотритель \"" + userName + "\" удален");
                        break;
                    case DIALOG_SHOW_ALL_ANIMALS:
                        break;
                    case DIALOG_ADD_ANIMAL:
                        userName = addAnimal_name.getText().toString();
                        int age = Integer.parseInt(addAnimal_age.getText().toString());
                        int animalType = addAnimal_spinType.getSelectedItemPosition() + 1;
                        int animalCage = addAnimal_spinCage.getSelectedItemPosition() + 1;
                        int animalCaretaker = addAnimal_spinCaretaker.getSelectedItemPosition() + 1;
                        Animal animal = new Animal(userName, animalType, age, animalCage, animalCaretaker);
                        if (innerDataBase.animalIsExist(animal)) {
                            showToast("Такое животное уже существует");
                        } else {
                            innerDataBase.addAnimal(animal);
                            showToast("Животное \"" + animal.getAnimalName() + "\" добавлено");
                        }
                        break;
                    case DIALOG_UPDATE_ANIMAL_STEP1:
                        listView = ((AlertDialog) dialogInterface).getListView();
                        position = listView.getCheckedItemPosition();
                        userName = (String) listView.getItemAtPosition(position);
                        animalForUpdate = innerDataBase.getAnimalByName(userName);
                        currentDialog = DIALOG_UPDATE_ANIMAL_STEP2;
                        showDialog(currentDialog);
                        break;
                    case DIALOG_UPDATE_ANIMAL_STEP2:
                        userName = addAnimal_name.getText().toString();
                        age = Integer.parseInt(addAnimal_age.getText().toString());
                        animalType = addAnimal_spinType.getSelectedItemPosition() + 1;
                        animalCage = addAnimal_spinCage.getSelectedItemPosition() + 1;
                        animalCaretaker = addAnimal_spinCaretaker.getSelectedItemPosition() + 1;
                        animalForUpdate.setAnimalName(userName);
                        animalForUpdate.setAnimalAge(age);
                        animalForUpdate.setAnimalTypeId(animalType);
                        animalForUpdate.setAnimalCageId(animalCage);
                        animalForUpdate.setAnimalCaretakerId(animalCaretaker);
                        innerDataBase.updateAnimal(animalForUpdate);
                        showToast("Животное обновлено");
                        break;
                    case DIALOG_REMOVE_ANIMAL:
                        listView = ((AlertDialog) dialogInterface).getListView();
                        position = listView.getCheckedItemPosition();
                        userName = (String) listView.getItemAtPosition(position);
                        innerDataBase.removeAnimal(new Animal(userName));
                        showToast("Животное \"" + userName + "\" удалено");
                        break;
                }
            }
        }
    };

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
