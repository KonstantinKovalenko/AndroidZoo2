package com.example.admin.zoo2.activities;

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

import com.example.admin.zoo2.R;
import com.example.admin.zoo2.database.InnerDataBase;
import com.example.admin.zoo2.database.Animal;
import com.example.admin.zoo2.database.AnimalCage;
import com.example.admin.zoo2.database.AnimalCaretaker;
import com.example.admin.zoo2.database.AnimalType;
import com.example.admin.zoo2.database.User;

import java.util.ArrayList;

public class DBManageActivity extends AppCompatActivity {

    private final int DIALOG_SHOW_ALL_USERS = 0;
    private final int DIALOG_ADD_USER = 1;
    private final int DIALOG_UPDATE_USER_STEP1 = 2;
    private final int DIALOG_UPDATE_USER_STEP2 = 100;
    private final int DIALOG_REMOVE_USER = 3;

    private final int DIALOG_SHOW_ALL_ANIMAL_TYPES = 4;
    private final int DIALOG_ADD_ANIMAL_TYPE = 5;
    private final int DIALOG_UPDATE_ANIMAL_TYPE_STEP1 = 6;
    private final int DIALOG_UPDATE_ANIMAL_TYPE_STEP2 = 101;
    private final int DIALOG_REMOVE_ANIMAL_TYPE = 7;

    private final int DIALOG_SHOW_ALL_ANIMAL_CAGES = 8;
    private final int DIALOG_ADD_ANIMAL_CAGE = 9;
    private final int DIALOG_UPDATE_ANIMAL_CAGE_STEP1 = 10;
    private final int DIALOG_UPDATE_ANIMAL_CAGE_STEP2 = 102;
    private final int DIALOG_REMOVE_ANIMAL_CAGE = 11;

    private final int DIALOG_SHOW_ALL_ANIMAL_CARETAKERS = 12;
    private final int DIALOG_ADD_ANIMAL_CARETAKER = 13;
    private final int DIALOG_UPDATE_ANIMAL_CARETAKER_STEP1 = 14;
    private final int DIALOG_UPDATE_ANIMAL_CARETAKER_STEP2 = 103;
    private final int DIALOG_REMOVE_ANIMAL_CARETAKER = 15;

    private final int DIALOG_SHOW_ALL_ANIMALS = 16;
    private final int DIALOG_ADD_ANIMAL = 17;
    private final int DIALOG_UPDATE_ANIMAL_STEP1 = 18;
    private final int DIALOG_UPDATE_ANIMAL_STEP2 = 104;
    private final int DIALOG_REMOVE_ANIMAL = 19;

    private EditText addUser_login, addUser_password, addCage_title, addType_title, addCaretaker_name, addCaretaker_surname, addAnimal_name, addAnimal_age;
    private Spinner addAnimal_spinType, addAnimal_spinCage, addAnimal_spinCaretaker;
    private CheckBox addUser_isAdmin;

    private InnerDataBase innerDataBase;
    private User currentUser;
    private User userForUpdate;
    private AnimalType animalTypeForUpdate;
    private AnimalCage animalCageForUpdate;
    private AnimalCaretaker animalCaretakerForUpdate;
    private Animal animalForUpdate;
    private int currentDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbmanage);
        initializeListView();
        initializeInnerDataBase();
        setCurrentUser();
    }

    private void initializeListView() {
        ListView dbmanage_lView = (ListView) findViewById(R.id.dbmanage_lView);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sArrayActions, android.R.layout.simple_list_item_1);
        dbmanage_lView.setAdapter(adapter);
        dbmanage_lView.setOnItemClickListener(listViewOnClickListener);
    }

    AdapterView.OnItemClickListener listViewOnClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            currentDialog = i;
            showDialog(currentDialog);
        }
    };

    private void initializeInnerDataBase() {
        innerDataBase = new InnerDataBase(this);
        innerDataBase.open();
    }

    private void setCurrentUser() {
        currentUser = getIntent().getParcelableExtra("currentUser");
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        LinearLayout view;
        String[] data = {""};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        int resource = getLayoutResourceByDialogId(id);
        if (id == DIALOG_ADD_USER || id == DIALOG_ADD_ANIMAL_TYPE || id == DIALOG_ADD_ANIMAL_CAGE
                || id == DIALOG_ADD_ANIMAL_CARETAKER || id == DIALOG_ADD_ANIMAL) {
            adb.setTitle("Добавление записи");
            view = (LinearLayout) getLayoutInflater().inflate(resource, null);
            adb.setView(view);
            adb.setPositiveButton("Добавить", overallDialogsListener);
        } else if (id == DIALOG_UPDATE_USER_STEP1 || id == DIALOG_UPDATE_ANIMAL_TYPE_STEP1 || id == DIALOG_UPDATE_ANIMAL_CAGE_STEP1
                || id == DIALOG_UPDATE_ANIMAL_CARETAKER_STEP1 || id == DIALOG_UPDATE_ANIMAL_STEP1) {
            adb.setTitle("Выберите пункт для изменения");
            adb.setSingleChoiceItems(data, -1, overallDialogsListener);
            adb.setPositiveButton("Изменить", overallDialogsListener);
        } else if (id == DIALOG_UPDATE_USER_STEP2 || id == DIALOG_UPDATE_ANIMAL_TYPE_STEP2 || id == DIALOG_UPDATE_ANIMAL_CAGE_STEP2
                || id == DIALOG_UPDATE_ANIMAL_CARETAKER_STEP2 || id == DIALOG_UPDATE_ANIMAL_STEP2) {
            adb.setTitle("Изменение записи");
            view = (LinearLayout) getLayoutInflater().inflate(resource, null);
            adb.setView(view);
            adb.setPositiveButton("Изменить", overallDialogsListener);
        } else if (id == DIALOG_REMOVE_USER || id == DIALOG_REMOVE_ANIMAL_TYPE || id == DIALOG_REMOVE_ANIMAL_CAGE
                || id == DIALOG_REMOVE_ANIMAL_CARETAKER || id == DIALOG_REMOVE_ANIMAL) {
            adb.setTitle("Удаление из базы данных");
            adb.setPositiveButton("Удалить", overallDialogsListener);
            adb.setSingleChoiceItems(data, -1, overallDialogsListener);
        } else if (id == DIALOG_SHOW_ALL_USERS || id == DIALOG_SHOW_ALL_ANIMAL_TYPES || id == DIALOG_SHOW_ALL_ANIMAL_CAGES
                || id == DIALOG_SHOW_ALL_ANIMAL_CARETAKERS || id == DIALOG_SHOW_ALL_ANIMALS) {
            adb.setTitle("Просмотр всех записей");
            adb.setPositiveButton("OK", overallDialogsListener);
            adb.setAdapter(adapter, overallDialogsListener);
        }
        if (id != DIALOG_SHOW_ALL_USERS && id != DIALOG_SHOW_ALL_ANIMAL_TYPES && id != DIALOG_SHOW_ALL_ANIMAL_CAGES
                && id != DIALOG_SHOW_ALL_ANIMAL_CARETAKERS && id != DIALOG_SHOW_ALL_ANIMALS) {
            adb.setNegativeButton("Отмена", overallDialogsListener);
        }
        return adb.create();
    }

    private int getLayoutResourceByDialogId(int dialogId) {
        int result = 0;
        if (dialogId == DIALOG_ADD_USER || dialogId == DIALOG_UPDATE_USER_STEP2) {
            result = R.layout.adduserdialog;
        } else if (dialogId == DIALOG_ADD_ANIMAL_TYPE || dialogId == DIALOG_UPDATE_ANIMAL_TYPE_STEP2) {
            result = R.layout.addtypedialog;
        } else if (dialogId == DIALOG_ADD_ANIMAL_CAGE || dialogId == DIALOG_UPDATE_ANIMAL_CAGE_STEP2) {
            result = R.layout.addcagedialog;
        } else if (dialogId == DIALOG_ADD_ANIMAL_CARETAKER || dialogId == DIALOG_UPDATE_ANIMAL_CARETAKER_STEP2) {
            result = R.layout.addcaretakerdialog;
        } else if (dialogId == DIALOG_ADD_ANIMAL || dialogId == DIALOG_UPDATE_ANIMAL_STEP2) {
            result = R.layout.addanimaldialog;
        }
        return result;
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

    private String[] getDataByDialogId(int dialogId) {
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

    private ArrayAdapter<CharSequence> getAdapterByDialogId(int dialogId, String[] data) {
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
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == DialogInterface.BUTTON_NEGATIVE) dialogInterface.dismiss();
            if (i == DialogInterface.BUTTON_POSITIVE) {
                switch (currentDialog) {
                    case DIALOG_ADD_USER:
                    case DIALOG_ADD_ANIMAL_TYPE:
                    case DIALOG_ADD_ANIMAL_CAGE:
                    case DIALOG_ADD_ANIMAL_CARETAKER:
                    case DIALOG_ADD_ANIMAL:
                        onClickAdd(currentDialog);
                        break;
                    case DIALOG_UPDATE_USER_STEP1:
                    case DIALOG_UPDATE_ANIMAL_CAGE_STEP1:
                    case DIALOG_UPDATE_ANIMAL_TYPE_STEP1:
                    case DIALOG_UPDATE_ANIMAL_CARETAKER_STEP1:
                    case DIALOG_UPDATE_ANIMAL_STEP1:
                        onClickUpdateStep1(currentDialog, dialogInterface);
                        break;
                    case DIALOG_UPDATE_USER_STEP2:
                    case DIALOG_UPDATE_ANIMAL_TYPE_STEP2:
                    case DIALOG_UPDATE_ANIMAL_CAGE_STEP2:
                    case DIALOG_UPDATE_ANIMAL_CARETAKER_STEP2:
                    case DIALOG_UPDATE_ANIMAL_STEP2:
                        onClickUpdateStep2(currentDialog);
                        break;
                    case DIALOG_REMOVE_USER:
                    case DIALOG_REMOVE_ANIMAL_TYPE:
                    case DIALOG_REMOVE_ANIMAL_CAGE:
                    case DIALOG_REMOVE_ANIMAL_CARETAKER:
                    case DIALOG_REMOVE_ANIMAL:
                        onClickRemove(currentDialog, dialogInterface);
                        break;
                }
            }
        }
    };

    private void onClickAdd(int dialogId) {
        String editString1;
        String editString2;
        boolean isAdmin;
        switch (dialogId) {
            case DIALOG_ADD_USER:
                editString1 = addUser_login.getText().toString();
                editString2 = addUser_password.getText().toString();
                isAdmin = addUser_isAdmin.isChecked();
                User user = new User(editString1, editString2, isAdmin);
                if (innerDataBase.userIsExist(user)) {
                    showToast("Такой пользователь уже существует");
                } else {
                    innerDataBase.addUser(new User(editString1, editString2, isAdmin));
                    showToast("Пользователь \"" + user.getUserName() + "\" добавлен");
                }
                break;
            case DIALOG_ADD_ANIMAL_TYPE:
                editString1 = addType_title.getText().toString();
                AnimalType type = new AnimalType(editString1);
                if (innerDataBase.animalTypeIsExist(type)) {
                    showToast("Такой тип животных уже существует");
                } else {
                    innerDataBase.addAnimalType(type);
                    showToast("Тип животного \"" + type.getTypeTitle() + "\" добавлен");
                }
                break;
            case DIALOG_ADD_ANIMAL_CAGE:
                editString1 = addCage_title.getText().toString();
                AnimalCage cage = new AnimalCage(editString1);
                if (innerDataBase.animalCageIsExist(cage)) {
                    showToast("Такая клетка уже существует");
                } else {
                    innerDataBase.addAnimalCage(cage);
                    showToast("Тип клетки \"" + cage.getCageTitle() + "\" добавлен");
                }
                break;
            case DIALOG_ADD_ANIMAL_CARETAKER:
                editString1 = addCaretaker_name.getText().toString();
                editString2 = addCaretaker_surname.getText().toString();
                AnimalCaretaker ctaker = new AnimalCaretaker(editString1, editString2);
                if (innerDataBase.animalCaretakerIsExist(ctaker)) {
                    showToast("Такой смотритель уже существует");
                } else {
                    innerDataBase.addAnimalCaretaker(new AnimalCaretaker(editString1, editString2));
                    showToast("Смотритель \"" + ctaker.getCaretakerName() + "\" добавлен");
                }
                break;
            case DIALOG_ADD_ANIMAL:
                editString1 = addAnimal_name.getText().toString();
                int age = Integer.parseInt(addAnimal_age.getText().toString());
                int animalType = addAnimal_spinType.getSelectedItemPosition() + 1;
                int animalCage = addAnimal_spinCage.getSelectedItemPosition() + 1;
                int animalCaretaker = addAnimal_spinCaretaker.getSelectedItemPosition() + 1;
                Animal animal = new Animal(editString1, animalType, age, animalCage, animalCaretaker);
                if (innerDataBase.animalIsExist(animal)) {
                    showToast("Такое животное уже существует");
                } else {
                    innerDataBase.addAnimal(animal);
                    showToast("Животное \"" + animal.getAnimalName() + "\" добавлено");
                }
                break;
        }
    }

    private void onClickUpdateStep1(int dialogId, DialogInterface dialogInterface) {
        ListView listView = ((AlertDialog) dialogInterface).getListView();
        int position = listView.getCheckedItemPosition();
        String editString = (String) listView.getItemAtPosition(position);
        switch (dialogId) {
            case DIALOG_UPDATE_USER_STEP1:
                userForUpdate = innerDataBase.getUserByName(editString);
                currentDialog = DIALOG_UPDATE_USER_STEP2;
                break;
            case DIALOG_UPDATE_ANIMAL_CAGE_STEP1:
                animalCageForUpdate = innerDataBase.getAnimalCageByTitle(editString);
                currentDialog = DIALOG_UPDATE_ANIMAL_CAGE_STEP2;
                break;
            case DIALOG_UPDATE_ANIMAL_TYPE_STEP1:
                animalTypeForUpdate = innerDataBase.getAnimalTypeByTitle(editString);
                currentDialog = DIALOG_UPDATE_ANIMAL_TYPE_STEP2;
                break;
            case DIALOG_UPDATE_ANIMAL_CARETAKER_STEP1:
                animalCaretakerForUpdate = innerDataBase.getAnimalCaretakerByName(editString);
                currentDialog = DIALOG_UPDATE_ANIMAL_CARETAKER_STEP2;
                break;
            case DIALOG_UPDATE_ANIMAL_STEP1:
                animalForUpdate = innerDataBase.getAnimalByName(editString);
                currentDialog = DIALOG_UPDATE_ANIMAL_STEP2;
                break;
        }
        showDialog(currentDialog);
    }

    private void onClickUpdateStep2(int dialogId) {
        String editString1;
        String editString2;
        boolean isAdmin;
        switch (dialogId) {
            case DIALOG_UPDATE_USER_STEP2:
                editString1 = addUser_login.getText().toString();
                editString2 = addUser_password.getText().toString();
                isAdmin = addUser_isAdmin.isChecked();
                userForUpdate.setUserName(editString1);
                userForUpdate.setUserPassword(editString2);
                userForUpdate.setAdmin(isAdmin);
                innerDataBase.updateUser(userForUpdate);
                showToast("Пользователь обновлен");
                break;
            case DIALOG_UPDATE_ANIMAL_TYPE_STEP2:
                editString1 = addType_title.getText().toString();
                animalTypeForUpdate.setTypeTitle(editString1);
                innerDataBase.updateAnimalType(animalTypeForUpdate);
                showToast("Тип животных обновлен");
                break;
            case DIALOG_UPDATE_ANIMAL_CAGE_STEP2:
                editString1 = addCage_title.getText().toString();
                animalCageForUpdate.setCageTitle(editString1);
                innerDataBase.updateAnimalCage(animalCageForUpdate);
                showToast("Клетка животных обновлена");
                break;
            case DIALOG_UPDATE_ANIMAL_CARETAKER_STEP2:
                editString1 = addCaretaker_name.getText().toString();
                editString2 = addCaretaker_surname.getText().toString();
                animalCaretakerForUpdate.setCaretakerName(editString1);
                animalCaretakerForUpdate.setCaretakerSurname(editString2);
                innerDataBase.updateAnimalCaretaker(animalCaretakerForUpdate);
                showToast("Смотритель обновлен");
                break;
            case DIALOG_UPDATE_ANIMAL_STEP2:
                editString1 = addAnimal_name.getText().toString();
                int age = Integer.parseInt(addAnimal_age.getText().toString());
                int animalType = addAnimal_spinType.getSelectedItemPosition() + 1;
                int animalCage = addAnimal_spinCage.getSelectedItemPosition() + 1;
                int animalCaretaker = addAnimal_spinCaretaker.getSelectedItemPosition() + 1;
                animalForUpdate.setAnimalName(editString1);
                animalForUpdate.setAnimalAge(age);
                animalForUpdate.setAnimalTypeId(animalType);
                animalForUpdate.setAnimalCageId(animalCage);
                animalForUpdate.setAnimalCaretakerId(animalCaretaker);
                innerDataBase.updateAnimal(animalForUpdate);
                showToast("Животное обновлено");
                break;
        }
    }

    private void onClickRemove(int dialogId, DialogInterface dialogInterface) {
        ListView listView = ((AlertDialog) dialogInterface).getListView();
        int position = listView.getCheckedItemPosition();
        String editString = (String) listView.getItemAtPosition(position);
        switch (dialogId) {
            case DIALOG_REMOVE_USER:
                if (currentUser.getUserName().equals(editString)) {
                    showToast("Невозможно удалить текущего пользователя");
                    return;
                }
                innerDataBase.removeUser(new User(editString));
                showToast("Пользователь \"" + editString + "\" удален");
                break;
            case DIALOG_REMOVE_ANIMAL_TYPE:
                innerDataBase.removeAnimalType(new AnimalType(editString));
                showToast("Тип животных \"" + editString + "\" удален");
                break;
            case DIALOG_REMOVE_ANIMAL_CAGE:
                innerDataBase.removeAnimalCage(new AnimalCage(editString));
                showToast("Клетка животных \"" + editString + "\" удалена");
                break;
            case DIALOG_REMOVE_ANIMAL_CARETAKER:
                innerDataBase.removeAnimalCaretaker(new AnimalCaretaker(editString));
                showToast("Смотритель \"" + editString + "\" удален");
                break;
            case DIALOG_REMOVE_ANIMAL:
                innerDataBase.removeAnimal(new Animal(editString));
                showToast("Животное \"" + editString + "\" удалено");
                break;
        }
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        innerDataBase.close();
    }
}
