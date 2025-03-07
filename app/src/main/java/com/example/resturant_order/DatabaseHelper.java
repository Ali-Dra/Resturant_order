package com.example.resturant_order;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "restaurant.db";
    private static final int DATABASE_VERSION = 4;


    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_FOOD_ITEMS = "food_items";
    private static final String TABLE_CART = "cart";
    private static final String TABLE_ORDERS = "orders";

    private static final String COLUMN_CATEGORY_ID = "id";
    private static final String COLUMN_CATEGORY_NAME = "name";
    private static final String COLUMN_CATEGORY_IMAGE = "image";
    private static final String COLUMN_CATEGORY_COUNT = "count";

    private static final String COLUMN_ITEM_ID = "id";
    private static final String COLUMN_ITEM_NAME = "name";
    private static final String COLUMN_ITEM_PRICE = "price";
    private static final String COLUMN_ITEM_IMAGE = "image";
    private static final String COLUMN_ITEM_CATEGORY_ID = "category_id";

    private static final String COLUMN_CART_ID = "id";
    private static final String COLUMN_CART_ITEM_ID = "item_id";
    private static final String COLUMN_CART_QUANTITY = "quantity";

    private static final String COLUMN_ORDER_ID = "id";
    private static final String COLUMN_ORDER_USERNAME = "username";
    private static final String COLUMN_ORDER_TABLE_NUMBER = "table_number";
    private static final String COLUMN_ORDER_TIMESTAMP = "timestamp";
    private static final String COLUMN_ORDER_TOTAL = "total";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + " (" +
                COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY_NAME + " TEXT, " + COLUMN_CATEGORY_COUNT + " INTEGER, " +
                COLUMN_CATEGORY_IMAGE + " INTEGER)";
        db.execSQL(CREATE_CATEGORIES_TABLE);


        String CREATE_FOOD_ITEMS_TABLE = "CREATE TABLE " + TABLE_FOOD_ITEMS + " (" +
                COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_NAME + " TEXT, " +
                COLUMN_ITEM_PRICE + " REAL, " +
                COLUMN_ITEM_IMAGE + " INTEGER, " +
                COLUMN_ITEM_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_ITEM_CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORIES + "(" + COLUMN_CATEGORY_ID + "))";
        db.execSQL(CREATE_FOOD_ITEMS_TABLE);

        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + " (" +
                COLUMN_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CART_ITEM_ID + " INTEGER, " +
                COLUMN_CART_QUANTITY + " INTEGER DEFAULT 1, " +
                "FOREIGN KEY(" + COLUMN_CART_ITEM_ID + ") REFERENCES " + TABLE_FOOD_ITEMS + "(" + COLUMN_ITEM_ID + "))";
        db.execSQL(CREATE_CART_TABLE);

        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + " (" +
                COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ORDER_USERNAME + " TEXT, " +
                COLUMN_ORDER_TABLE_NUMBER + " INTEGER, " +
                COLUMN_ORDER_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                COLUMN_ORDER_TOTAL + " REAL)";
        db.execSQL(CREATE_ORDERS_TABLE);

        insertDefaultData(db);
    }

    private void insertDefaultData(SQLiteDatabase db) {
        insertCategory(db, "Arabic Food", R.drawable.arabic_category);
        insertCategory(db, "Fast Food", R.drawable.fast_category);
        insertCategory(db, "Chinese Food", R.drawable.chines_category);
        insertCategory(db, "Indian Food", R.drawable.indian_category);

        insertFoodItem(db, "Shawarma", 5.99, R.drawable.sharwa, 1);
        insertFoodItem(db, "Hummus", 4.50, R.drawable.husmus, 1);
        insertFoodItem(db, "Falafel", 3.99,R.drawable.falafel, 1);
        insertFoodItem(db, "Kebab", 6.99, R.drawable.kabab, 1);

        insertFoodItem(db, "Burger", 4.99, R.drawable.burger, 2);
        insertFoodItem(db, "Pizza", 8.99, R.drawable.pizza, 2);
        insertFoodItem(db, "Hot Dog", 3.99, R.drawable.hotdog, 2);
        insertFoodItem(db, "French Fries", 2.99, R.drawable.fries, 2);

        insertFoodItem(db, "Noodles", 6.50, R.drawable.noodle, 3);
        insertFoodItem(db, "Fried Rice", 5.99, R.drawable.fried_rice, 3);
        insertFoodItem(db, "Spring Rolls", 4.50,R.drawable.rolls, 3);
        insertFoodItem(db, "Kung Pao Chicken", 7.99, R.drawable.chicken, 3);


        insertFoodItem(db, "Biryani", 7.99, R.drawable.brryani, 4);
        insertFoodItem(db, "Butter Chicken", 8.99, R.drawable.butter_chicken, 4);
        insertFoodItem(db, "Naan Bread", 2.99,R.drawable.naan, 4);
        insertFoodItem(db, "Samosa", 3.50,R.drawable.samsosa, 4);
    }

    private void insertCategory(SQLiteDatabase db, String name, int image) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_NAME, name);
        values.put(COLUMN_CATEGORY_IMAGE, image);
        values.put(COLUMN_CATEGORY_COUNT, 4);
        db.insert(TABLE_CATEGORIES, null, values);
    }

    private void insertFoodItem(SQLiteDatabase db, String name, double price, int image, int categoryId) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, name);
        values.put(COLUMN_ITEM_PRICE, price);
        values.put(COLUMN_ITEM_IMAGE, image);
        values.put(COLUMN_ITEM_CATEGORY_ID, categoryId);
        db.insert(TABLE_FOOD_ITEMS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        onCreate(db);
    }

    public List<FoodCategory> getAllCategories() {
        List<FoodCategory> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CATEGORIES, null);

        if (cursor.moveToFirst()) {
            do {
                categories.add(new FoodCategory(  cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(3), cursor.getInt(2)

                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categories;
    }

    public List<FoodItem> getFoodItemsByCategory(int categoryId) {
        List<FoodItem> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FOOD_ITEMS + " WHERE " + COLUMN_ITEM_CATEGORY_ID + " = ?", new String[]{String.valueOf(categoryId)});

        if (cursor.moveToFirst()) {
            do {
                items.add(new FoodItem( cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        cursor.getInt(3), cursor.getInt(4)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    public void addToCart(FoodItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_CART,
                new String[]{COLUMN_CART_QUANTITY},
                COLUMN_CART_ITEM_ID + " = ?",
                new String[]{String.valueOf(item.getId())},
                null, null, null);

        ContentValues values = new ContentValues();

        if (cursor.moveToFirst()) {
            int currentQuantity = cursor.getInt(0);
            values.put(COLUMN_CART_QUANTITY, currentQuantity + 1);
            db.update(TABLE_CART, values,
                    COLUMN_CART_ITEM_ID + " = ?",
                    new String[]{String.valueOf(item.getId())});
        } else {
            values.put(COLUMN_CART_ITEM_ID, item.getId());
            values.put(COLUMN_CART_QUANTITY, 1);
            db.insert(TABLE_CART, null, values);
        }

        cursor.close();
    }

    public List<CartItem> getCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT f.*, c.quantity FROM " + TABLE_FOOD_ITEMS + " f " +
                      "JOIN " + TABLE_CART + " c ON f.id = c.item_id";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") CartItem item = new CartItem(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)),
                    cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_PRICE)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_CART_QUANTITY))
                );
                cartItems.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cartItems;
    }

    public void removeFromCart(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, COLUMN_CART_ITEM_ID + " = ?",
                 new String[]{String.valueOf(itemId)});
    }

    public long createOrder(String username, List<CartItem> items) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            double total = 0;
            for (CartItem item : items) {
                total += item.getPrice() * item.getQuantity();
            }

            int tableNumber = (int) (Math.random() * 10) + 1;

            ContentValues orderValues = new ContentValues();
            orderValues.put(COLUMN_ORDER_USERNAME, username);
            orderValues.put(COLUMN_ORDER_TABLE_NUMBER, tableNumber);
            orderValues.put(COLUMN_ORDER_TOTAL, total);

            long orderId = db.insert(TABLE_ORDERS, null, orderValues);

            db.delete(TABLE_CART, null, null);

            db.setTransactionSuccessful();
            return orderId;
        } finally {
            db.endTransaction();
        }
    }

    public String getCategoryName(int categoryId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String categoryName = "";

        Cursor cursor = db.query(TABLE_CATEGORIES,
                new String[]{COLUMN_CATEGORY_NAME},
                "id = ?",
                new String[]{String.valueOf(categoryId)},
                null, null, null);

        if (cursor.moveToFirst()) {
            categoryName = cursor.getString(0);
        }
        cursor.close();
        return categoryName;
    }
}
