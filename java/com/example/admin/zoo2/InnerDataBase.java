package com.example.admin.zoo2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import java.util.ArrayList;

public class InnerDataBase {

    private final String DB_NAME = "Zoo";
    private final int DB_VERSION = 1;
    private final int ADMIN_RIGHTS = 0;
    private final int USER_RIGHTS = 1;

    private String USERS_TABLE_NAME = "Users";
    private String USERS_COLUMN_ID = "_id";
    private String USERS_COLUMN_LOGIN = "login";
    private String USERS_COLUMN_PASSWORD = "password";
    private String USERS_COLUMN_ADMINISTRATOR = "adminRights";

    private String ANIMAL_TYPES_TABLE_NAME = "Animaltypes";
    private String ANIMAL_TYPES_COLUMN_ID = "_id";
    private String ANIMAL_TYPES_COLUMN_TYPE = "type";

    private String ANIMAL_CAGES_TABLE_NAME = "Animalcages";
    private String ANIMAL_CAGES_COLUMN_ID = "_id";
    private String ANIMAL_CAGES_COLUMN_CAGE = "cage";

    private String ANIMAL_CARETAKERS_TABLE_NAME = "Animalcaretakers";
    private String ANIMAL_CARETAKERS_COLUMN_ID = "_id";
    private String ANIMAL_CARETAKERS_COLUMN_NAME = "name";
    private String ANIMAL_CARETAKERS_COLUMN_SURNAME = "surname";

    private String ANIMALS_TABLE_NAME = "Animals";
    private String ANIMALS_COLUMN_ID = "_id";
    private String ANIMALS_COLUMN_NAME = "name";
    private String ANIMALS_COLUMN_TYPEID = "typeId";
    private String ANIMALS_COLUMN_AGE = "age";
    private String ANIMALS_COLUMN_CAGEID = "cageId";
    private String ANIMALS_COLUMN_CARETAKERID = "caretakerId";

    private Context context;
    private InnerSQLDB innerSQLDB;

    InnerDataBase(Context context) {
        this.context = context;
    }

    public void open() {
        innerSQLDB = new InnerSQLDB(context);
    }

    public void close() {
        innerSQLDB.close();
    }

    public Cursor getCursorUsers() {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getReadableDatabase();
        Cursor result;
        sqLiteDatabase.beginTransaction();
        try {
            result = sqLiteDatabase.query(USERS_TABLE_NAME, null, null, null, null, null, null);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
        return result;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> result = new ArrayList<>();
        Cursor cursor = getCursorUsers();
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(USERS_COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(USERS_COLUMN_LOGIN);
            int passwordIndex = cursor.getColumnIndex(USERS_COLUMN_PASSWORD);
            int adminIndex = cursor.getColumnIndex(USERS_COLUMN_ADMINISTRATOR);
            do {
                int userId = cursor.getInt(idIndex);
                String userName = cursor.getString(nameIndex);
                String userPassword = cursor.getString(passwordIndex);
                boolean isAdmin = (cursor.getInt(adminIndex) == 0 ? true : false);
                result.add(new User(userId, userName, userPassword, isAdmin));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public User getUserByName(String userName) {
        User result = new User("");
        ArrayList<User> userList = getAllUsers();
        for (User user : userList) {
            if (userName.equals(user.getUserName())) {
                result = user;
            }
        }
        return result;
    }

    public void addUser(User user) {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        sqLiteDatabase.beginTransaction();
        try {
            cValues.put(USERS_COLUMN_LOGIN, user.getUserName());
            cValues.put(USERS_COLUMN_PASSWORD, user.getUserPassword());
            cValues.put(USERS_COLUMN_ADMINISTRATOR, (user.isAdmin() ? ADMIN_RIGHTS : USER_RIGHTS));
            sqLiteDatabase.insert(USERS_TABLE_NAME, null, cValues);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public void updateUser(User user) {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        sqLiteDatabase.beginTransaction();
        try {
            cValues.put(USERS_COLUMN_LOGIN, user.getUserName());
            cValues.put(USERS_COLUMN_PASSWORD, user.getUserPassword());
            cValues.put(USERS_COLUMN_ADMINISTRATOR, (user.isAdmin() ? ADMIN_RIGHTS : USER_RIGHTS));
            sqLiteDatabase.update(USERS_TABLE_NAME, cValues, USERS_COLUMN_ID + " = " + user.getUserId(), null);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public void removeUser(User user) {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            sqLiteDatabase.delete(USERS_TABLE_NAME, USERS_COLUMN_LOGIN + " = \"" + user.getUserName() + "\"", null);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public boolean userIsExist(User user) {
        Cursor cursor = getCursorUsers();

        if (cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(USERS_COLUMN_LOGIN);
            do {
                if (user.getUserName().equals(cursor.getString(nameIndex))) {
                    cursor.close();
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }

    public Cursor getCursorAnimalTypes() {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getReadableDatabase();
        Cursor result;
        sqLiteDatabase.beginTransaction();
        try {
            result = sqLiteDatabase.query(ANIMAL_TYPES_TABLE_NAME, null, null, null, null, null, null);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
        return result;
    }

    public ArrayList<AnimalType> getAllAnimalTypes() {
        ArrayList<AnimalType> result = new ArrayList<>();
        Cursor cursor = getCursorAnimalTypes();
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(ANIMAL_TYPES_COLUMN_ID);
            int typeIndex = cursor.getColumnIndex(ANIMAL_TYPES_COLUMN_TYPE);
            do {
                int typeId = cursor.getInt(idIndex);
                String typeTitle = cursor.getString(typeIndex);
                result.add(new AnimalType(typeId, typeTitle));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public String getAnimalTypeTitleById(int id) {
        ArrayList<AnimalType> typeList = getAllAnimalTypes();
        for (AnimalType type : typeList) {
            if (type.getTypeId() == id) {
                return type.getTypeTitle();
            }
        }
        return "Данного типа нет в базе данных";
    }

    public AnimalType getAnimalTypeByTitle(String typeTitle) {
        AnimalType result = new AnimalType("");
        ArrayList<AnimalType> typeList = getAllAnimalTypes();
        for (AnimalType type : typeList) {
            if (typeTitle.equals(type.getTypeTitle())) {
                result = type;
            }
        }
        return result;
    }

    public void addAnimalType(AnimalType type) {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        sqLiteDatabase.beginTransaction();
        try {
            cValues.put(ANIMAL_TYPES_COLUMN_TYPE, type.getTypeTitle());
            sqLiteDatabase.insert(ANIMAL_TYPES_TABLE_NAME, null, cValues);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public void updateAnimalType(AnimalType type) {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        sqLiteDatabase.beginTransaction();
        try {
            cValues.put(ANIMAL_TYPES_COLUMN_TYPE, type.getTypeTitle());
            sqLiteDatabase.update(ANIMAL_TYPES_TABLE_NAME, cValues, ANIMAL_TYPES_COLUMN_ID + " = " + type.getTypeId(), null);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public void removeAnimalType(AnimalType type) {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            sqLiteDatabase.delete(ANIMAL_TYPES_TABLE_NAME, ANIMAL_TYPES_COLUMN_TYPE + " = \"" + type.getTypeTitle() + "\"", null);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public boolean animalTypeIsExist(AnimalType type) {
        Cursor cursor = getCursorAnimalTypes();

        if (cursor.moveToFirst()) {
            int typeIndex = cursor.getColumnIndex(ANIMAL_TYPES_COLUMN_TYPE);
            do {
                if (type.getTypeTitle().equals(cursor.getString(typeIndex))) {
                    cursor.close();
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }

    public Cursor getCursorAnimalCages() {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getReadableDatabase();
        Cursor result;
        sqLiteDatabase.beginTransaction();
        try {
            result = sqLiteDatabase.query(ANIMAL_CAGES_TABLE_NAME, null, null, null, null, null, null);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
        return result;
    }

    public ArrayList<AnimalCage> getAllAnimalCages() {
        ArrayList<AnimalCage> result = new ArrayList<>();
        Cursor cursor = getCursorAnimalCages();
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(ANIMAL_CAGES_COLUMN_ID);
            int cageIndex = cursor.getColumnIndex(ANIMAL_CAGES_COLUMN_CAGE);
            do {
                int cageId = cursor.getInt(idIndex);
                String cageTitle = cursor.getString(cageIndex);
                result.add(new AnimalCage(cageId, cageTitle));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    private String getAnimalCageTitleById(int id) {
        ArrayList<AnimalCage> cageList = getAllAnimalCages();
        for (AnimalCage cage : cageList) {
            if (cage.getCageId() == id) {
                return cage.getCageTitle();
            }
        }
        return "Клетка не найдена в базе данных";
    }

    public AnimalCage getAnimalCageByTitle(String cageTitle) {
        AnimalCage result = new AnimalCage("");
        ArrayList<AnimalCage> cageList = getAllAnimalCages();
        for (AnimalCage cage : cageList) {
            if (cageTitle.equals(cage.getCageTitle())) {
                result = cage;
            }
        }
        return result;
    }

    public void addAnimalCage(AnimalCage cage) {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        sqLiteDatabase.beginTransaction();
        try {
            cValues.put(ANIMAL_CAGES_COLUMN_CAGE, cage.getCageTitle());
            sqLiteDatabase.insert(ANIMAL_CAGES_TABLE_NAME, null, cValues);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public void updateAnimalCage(AnimalCage cage) {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        sqLiteDatabase.beginTransaction();
        try {
            cValues.put(ANIMAL_CAGES_COLUMN_CAGE, cage.getCageTitle());
            sqLiteDatabase.update(ANIMAL_CAGES_TABLE_NAME, cValues, ANIMAL_CAGES_COLUMN_ID + " = " + cage.getCageId(), null);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public void removeAnimalCage(AnimalCage cage) {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            sqLiteDatabase.delete(ANIMAL_CAGES_TABLE_NAME, ANIMAL_CAGES_COLUMN_CAGE + " = \"" + cage.getCageTitle() + "\"", null);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public boolean animalCageIsExist(AnimalCage cage) {
        Cursor cursor = getCursorAnimalCages();

        if (cursor.moveToFirst()) {
            int cageIndex = cursor.getColumnIndex(ANIMAL_CAGES_COLUMN_CAGE);
            do {
                if (cage.getCageTitle().equals(cursor.getString(cageIndex))) {
                    cursor.close();
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }

    public Cursor getCursorAnimalCaretakers() {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getReadableDatabase();
        Cursor result;
        sqLiteDatabase.beginTransaction();
        try {
            result = sqLiteDatabase.query(ANIMAL_CARETAKERS_TABLE_NAME, null, null, null, null, null, null);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
        return result;
    }

    public ArrayList<AnimalCaretaker> getAllAnimalCaretakers() {
        ArrayList<AnimalCaretaker> result = new ArrayList<>();
        Cursor cursor = getCursorAnimalCaretakers();
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(ANIMAL_CARETAKERS_COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(ANIMAL_CARETAKERS_COLUMN_NAME);
            int surnameIndex = cursor.getColumnIndex(ANIMAL_CARETAKERS_COLUMN_SURNAME);
            do {
                int ctakerId = cursor.getInt(idIndex);
                String ctakerName = cursor.getString(nameIndex);
                String ctakerSurname = cursor.getString(surnameIndex);
                result.add(new AnimalCaretaker(ctakerId, ctakerName, ctakerSurname));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    private AnimalCaretaker getAnimalCaretakerById(int id) {
        ArrayList<AnimalCaretaker> ctakerList = getAllAnimalCaretakers();
        String caretakerName = "Не найдено в базе данных";
        String caretakerSurname = "";
        for (AnimalCaretaker ctaker : ctakerList) {
            if (ctaker.getCaretakerId() == id) {
                caretakerName = ctaker.getCaretakerName();
                caretakerSurname = ctaker.getCaretakerSurname();
            }
        }
        return new AnimalCaretaker(caretakerName, caretakerSurname);
    }

    public AnimalCaretaker getAnimalCaretakerByName(String ctakerName) {
        AnimalCaretaker result = new AnimalCaretaker("");
        ArrayList<AnimalCaretaker> ctakerList = getAllAnimalCaretakers();
        for (AnimalCaretaker ctaker : ctakerList) {
            if (ctakerName.equals(ctaker.getCaretakerName())) {
                result = ctaker;
            }
        }
        return result;
    }

    public void addAnimalCaretaker(AnimalCaretaker ctaker) {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        sqLiteDatabase.beginTransaction();
        try {
            cValues.put(ANIMAL_CARETAKERS_COLUMN_NAME, ctaker.getCaretakerName());
            cValues.put(ANIMAL_CARETAKERS_COLUMN_SURNAME, ctaker.getCaretakerName());
            sqLiteDatabase.insert(ANIMAL_CARETAKERS_TABLE_NAME, null, cValues);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public void updateAnimalCaretaker(AnimalCaretaker ctaker) {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        sqLiteDatabase.beginTransaction();
        try {
            cValues.put(ANIMAL_CARETAKERS_COLUMN_NAME, ctaker.getCaretakerName());
            cValues.put(ANIMAL_CARETAKERS_COLUMN_SURNAME, ctaker.getCaretakerSurname());
            sqLiteDatabase.update(ANIMAL_CARETAKERS_TABLE_NAME, cValues, ANIMAL_CARETAKERS_COLUMN_ID + " = " + ctaker.getCaretakerId(), null);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public void removeAnimalCaretaker(AnimalCaretaker ctaker) {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            sqLiteDatabase.delete(ANIMAL_CARETAKERS_TABLE_NAME, ANIMAL_CARETAKERS_COLUMN_NAME + " = \"" + ctaker.getCaretakerName() + "\"", null);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public boolean animalCaretakerIsExist(AnimalCaretaker ctaker) {
        Cursor cursor = getCursorAnimalCaretakers();

        if (cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(ANIMAL_CARETAKERS_COLUMN_NAME);
            do {
                if (ctaker.getCaretakerName().equals(cursor.getString(nameIndex))) {
                    cursor.close();
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }

    public Cursor getCursorAnimals() {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getReadableDatabase();
        Cursor result;
        sqLiteDatabase.beginTransaction();
        try {
            result = sqLiteDatabase.query(ANIMALS_TABLE_NAME, null, null, null, null, null, null);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
        return result;
    }

    public ArrayList<Animal> getAllAnimals() {
        ArrayList<Animal> result = new ArrayList<>();
        Cursor cursor = getCursorAnimals();
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(ANIMALS_COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(ANIMALS_COLUMN_NAME);
            int typeIdIndex = cursor.getColumnIndex(ANIMALS_COLUMN_TYPEID);
            int ageIndex = cursor.getColumnIndex(ANIMALS_COLUMN_AGE);
            int cageIdIndex = cursor.getColumnIndex(ANIMALS_COLUMN_CAGEID);
            int caretakerIdIndex = cursor.getColumnIndex(ANIMALS_COLUMN_CARETAKERID);
            do {
                int animalId = cursor.getInt(idIndex);
                String animalName = cursor.getString(nameIndex);
                int animalTypeId = cursor.getInt(typeIdIndex);
                int animalAge = cursor.getInt(ageIndex);
                int animalCageId = cursor.getInt(cageIdIndex);
                int animalCaretakerId = cursor.getInt(caretakerIdIndex);
                result.add(new Animal(animalId, animalName, animalTypeId, animalAge, animalCageId, animalCaretakerId));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public Animal getAnimalByName(String animalName) {
        Animal result = new Animal("");
        ArrayList<Animal> animalList = getAllAnimals();
        for (Animal animal : animalList) {
            if (animalName.equals(animal.getAnimalName())) {
                result = animal;
            }
        }
        return result;
    }

    public String[] getAnimalById(int id) throws NullPointerException {
        Animal animal = new Animal("");
        ArrayList<Animal> animalList = getAllAnimals();
        for (Animal an : animalList) {
            if (an.getAnimalId() == id) {
                animal = an;
            }
        }
        AnimalCaretaker caretaker = getAnimalCaretakerById(animal.getAnimalCaretakerId());
        String[] result = new String[6];
        result[0] = animal.getAnimalName();
        result[1] = getAnimalTypeTitleById(animal.getAnimalTypeId());
        result[2] = String.valueOf(animal.getAnimalAge());
        result[3] = getAnimalCageTitleById(animal.getAnimalCageId());
        result[4] = caretaker.getCaretakerName();
        result[5] = caretaker.getCaretakerSurname();
        return result;
    }

    public void addAnimal(Animal animal) {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        sqLiteDatabase.beginTransaction();
        try {
            cValues.put(ANIMALS_COLUMN_NAME, animal.getAnimalName());
            cValues.put(ANIMALS_COLUMN_TYPEID, animal.getAnimalTypeId());
            cValues.put(ANIMALS_COLUMN_AGE, animal.getAnimalAge());
            cValues.put(ANIMALS_COLUMN_CAGEID, animal.getAnimalCageId());
            cValues.put(ANIMALS_COLUMN_CARETAKERID, animal.getAnimalCaretakerId());
            sqLiteDatabase.insert(ANIMALS_TABLE_NAME, null, cValues);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public void updateAnimal(Animal animal) {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        sqLiteDatabase.beginTransaction();
        try {
            cValues.put(ANIMALS_COLUMN_NAME, animal.getAnimalName());
            cValues.put(ANIMALS_COLUMN_TYPEID, animal.getAnimalTypeId());
            cValues.put(ANIMALS_COLUMN_AGE, animal.getAnimalAge());
            cValues.put(ANIMALS_COLUMN_CAGEID, animal.getAnimalCageId());
            cValues.put(ANIMALS_COLUMN_CARETAKERID, animal.getAnimalCaretakerId());
            sqLiteDatabase.update(ANIMALS_TABLE_NAME, cValues, ANIMALS_COLUMN_ID + " = " + animal.getAnimalId(), null);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public void removeAnimal(Animal animal) {
        SQLiteDatabase sqLiteDatabase = innerSQLDB.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        try {
            sqLiteDatabase.delete(ANIMALS_TABLE_NAME, ANIMALS_COLUMN_NAME + " = \"" + animal.getAnimalName() + "\"", null);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    public boolean animalIsExist(Animal animal) {
        Cursor cursor = getCursorAnimals();

        if (cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(ANIMALS_COLUMN_NAME);
            do {
                if (animal.getAnimalName().equals(cursor.getString(nameIndex))) {
                    cursor.close();
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }

    class InnerSQLDB extends SQLiteOpenHelper {
        public InnerSQLDB(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table " + USERS_TABLE_NAME + " ("
                    + USERS_COLUMN_ID + " integer primary key,"
                    + USERS_COLUMN_LOGIN + " text,"
                    + USERS_COLUMN_PASSWORD + " text,"
                    + USERS_COLUMN_ADMINISTRATOR + " integer)");

            sqLiteDatabase.execSQL("create table " + ANIMAL_TYPES_TABLE_NAME + " ("
                    + ANIMAL_TYPES_COLUMN_ID + " integer primary key,"
                    + ANIMAL_TYPES_COLUMN_TYPE + " text)");

            sqLiteDatabase.execSQL("create table " + ANIMAL_CAGES_TABLE_NAME + " ("
                    + ANIMAL_CAGES_COLUMN_ID + " integer primary key,"
                    + ANIMAL_CAGES_COLUMN_CAGE + " text)");

            sqLiteDatabase.execSQL("create table " + ANIMAL_CARETAKERS_TABLE_NAME + " ("
                    + ANIMAL_CARETAKERS_COLUMN_ID + " integer primary key,"
                    + ANIMAL_CARETAKERS_COLUMN_NAME + " text,"
                    + ANIMAL_CARETAKERS_COLUMN_SURNAME + " text)");

            sqLiteDatabase.execSQL("create table " + ANIMALS_TABLE_NAME + " ("
                    + ANIMALS_COLUMN_ID + " integer primary key,"
                    + ANIMALS_COLUMN_NAME + " text,"
                    + ANIMALS_COLUMN_TYPEID + " integer,"
                    + ANIMALS_COLUMN_AGE + " integer,"
                    + ANIMALS_COLUMN_CAGEID + " integer,"
                    + ANIMALS_COLUMN_CARETAKERID + " integer)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}

