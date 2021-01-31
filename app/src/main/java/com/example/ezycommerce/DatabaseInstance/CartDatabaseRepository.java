package com.example.ezycommerce.DatabaseInstance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.ezycommerce.JavaClassObject.Product;
import com.example.ezycommerce.JavaClassObject.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartDatabaseRepository extends SQLiteOpenHelper {
    private static CartDatabaseRepository instance;
    private static final String DATABASE_NAME = DatabaseConfiguration.DATABASE_NAME;
    private static final Integer DATABASE_VERSION = DatabaseConfiguration.DATABASE_VERSION;

    private static final String CART_TABLE = "Cart";
    private static final String BOOK_TABLE = "Book";

    private static final String CART_ID_COLUMN = "CartID";
    private static final String QUANTITY_COLUMN = "Quantity";

    private static final String BOOK_ID_COLUMN = "BookID";
    private static final String BOOK_NAME_COLUMN = "BookName";
    private static final String BOOK_PRICE_COLUMN = "BookPrice";
    private static final String BOOK_IMAGE_URL_COLUMN = "BookImageURL";
    public static CartDatabaseRepository getInstance(Context context) {
        if (instance == null) {
            instance = new CartDatabaseRepository(context);
        }
        return instance;
    }

    public CartDatabaseRepository(@Nullable Context context) {
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createCartTableQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(createCartTableQuery());
        onCreate(db);
    }

//    private void storeToBookTable(ArrayList<Product>products){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        String query =  "DROP TABLE IF EXISTS " + BOOK_TABLE +" CREATE TABLE "+ BOOK_TABLE +"(" +
//                BOOK_ID_COLUMN + " INTEGER," +
//                BOOK_NAME_COLUMN + " TEXT," +
//                BOOK_PRICE_COLUMN + " REAL," +
//                BOOK_IMAGE_URL_COLUMN + " TEXT" +
//                ")";
//
//        String insertQuery = " INSERT INTO "+ BOOK_TABLE +"("+ BOOK_ID_COLUMN + "," + BOOK_NAME_COLUMN + "," + BOOK_PRICE_COLUMN+ "," +BOOK_IMAGE_URL_COLUMN + ")VALUES ";
//        for(Product p : products){
//            insertQuery += "("+ p.Id+ p.Name+p.Price+p.ImageURL+"),";
//        }
//
//        if (insertQuery.substring(insertQuery.length() - 1).equals(",")){
//            insertQuery = insertQuery.substring(0, insertQuery.length() - 1);
//        }
//
//        db.execSQL(query + insertQuery);
//        Log.d("query", "storeToBookTable: "+ query + insertQuery);
//    }

    private String createCartTableQuery(){
         return  "CREATE TABLE " +  CART_TABLE + " (" +
            CART_ID_COLUMN  + " TEXT PRIMARY KEY," +
            BOOK_ID_COLUMN  + " INTEGER," +
            QUANTITY_COLUMN + " INTEGER," +
            BOOK_NAME_COLUMN + " TEXT," +
            BOOK_PRICE_COLUMN + " REAL," +
            BOOK_IMAGE_URL_COLUMN + " TEXT" +
            ")";

    }

    public Boolean insertCart(Transaction transaction){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CART_ID_COLUMN, UUID.randomUUID().toString());
            values.put(BOOK_ID_COLUMN, transaction.getProduct().getId());
            values.put(QUANTITY_COLUMN, transaction.getQuantity());
            values.put(BOOK_NAME_COLUMN, transaction.getProduct().getName());
            values.put(BOOK_PRICE_COLUMN, transaction.getProduct().getPrice());
            values.put(BOOK_IMAGE_URL_COLUMN, transaction.getProduct().getImageURL());

            db.insert(CART_TABLE, null, values);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateCartQyt(Transaction t, int qyt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Quantity",qyt);

        db.update(CART_TABLE, cv, CART_ID_COLUMN + "=?",new String[]{t.getTransactionID().toString()});
    }

    public Boolean deleteCart(Transaction t){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CART_TABLE, CART_ID_COLUMN + "= ?", new String[]{t.getTransactionID().toString()}) > 0;
    }

    public void deleteAllCart(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ CART_TABLE);
    }

    public ArrayList<Transaction> getCart() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ CART_TABLE , new String[]{});
        ArrayList<Transaction> carts = new ArrayList<>();

        while (cursor.moveToNext()) {
            Transaction t = new Transaction();
            Product p = new Product();

            p.setId(cursor.getInt(cursor.getColumnIndex(BOOK_ID_COLUMN)));
            p.setName(cursor.getString(cursor.getColumnIndex(BOOK_NAME_COLUMN)));
            p.setImageURL(cursor.getString(cursor.getColumnIndex(BOOK_IMAGE_URL_COLUMN)));
            p.setPrice(cursor.getFloat(cursor.getColumnIndex(BOOK_PRICE_COLUMN)));
            t.setTransactionID(UUID.fromString (cursor.getString(cursor.getColumnIndex(CART_ID_COLUMN))));
            t.setQuantity(cursor.getInt(cursor.getColumnIndex(QUANTITY_COLUMN)));
            t.setProduct(p);

            carts.add(t);
        }

        cursor.close();
        return carts;
    }
}
