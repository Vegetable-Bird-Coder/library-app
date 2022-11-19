package com.example.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static Utils instance;

    private static final String ALL_BOOKS_KEY = "all_books";
    private static final String ALREADY_READ_KEY = "already_read_books";
    private static final String WANT_TO_READ_KEY = "want_to_read_books";
    private static final String CURRENTLY_READING_KEY = "currently_reading_books";
    private static final String FAVORITE_KEY = "favorite_books";
    private SharedPreferences sharedPreferences;

    private Utils(Context context) {
        sharedPreferences = context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);


        if (null == getAllBooks()) {
            initData();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if (null == getAlreadyReadBook()) {
            editor.putString(ALREADY_READ_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if (null == getWantToReadBook()) {
            editor.putString(WANT_TO_READ_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if (null == getCurReadBook()) {
            editor.putString(CURRENTLY_READING_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if (null == getFavoriteBook()) {
            editor.putString(FAVORITE_KEY, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
    }

    private void initData() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(1, "1Q84", "Haruki Murakami", 1350, "https://alittleblogofbooks.files.wordpress.com/2012/06/1q84.jpg",
                "A work of maddening brilliance", "Long Description"));
        books.add(new Book(2, "The Myth of Sisyphus", "Albert Camus", 250, "https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/820e6a44067829.5806a364b49f8.jpg",
                "Seeking what is true is not seeking what is desirable", "Long Description"));
       SharedPreferences.Editor editor = sharedPreferences.edit();
       Gson gSon = new Gson();
       editor.putString(ALL_BOOKS_KEY, gSon.toJson(books));
       editor.commit();

    }


    public static synchronized Utils getInstance(Context context) {
        if (null != instance) {
            return instance;
        } else {
            instance = new Utils(context);
            return instance;
        }
    }

    public ArrayList<Book> getAllBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getAlreadyReadBook() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALREADY_READ_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getWantToReadBook() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(WANT_TO_READ_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getCurReadBook() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(CURRENTLY_READING_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getFavoriteBook() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(FAVORITE_KEY, null), type);
        return books;
    }

    public Book getBookById(int id) {
        ArrayList<Book> books = getAllBooks();
        if (books != null) {
            for (Book book : getAllBooks()) {
                if (book.getId() == id) {
                    return book;
                }
            }
        }

        return null;
    }

    public boolean addToAlreadyRead(Book book) {
        ArrayList<Book> books = getAlreadyReadBook();
        if (books != null) {
            if (books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(ALREADY_READ_KEY);
                editor.putString(ALREADY_READ_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToWantRead(Book book) {
        ArrayList<Book> books = getWantToReadBook();
        if (books != null) {
            if (books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(WANT_TO_READ_KEY);
                editor.putString(WANT_TO_READ_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToCurRead(Book book) {
        ArrayList<Book> books = getCurReadBook();
        if (books != null) {
            if (books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(CURRENTLY_READING_KEY);
                editor.putString(CURRENTLY_READING_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToFavoriteRead(Book book) {
        ArrayList<Book> books = getFavoriteBook();
        if (books != null) {
            if (books.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(FAVORITE_KEY);
                editor.putString(FAVORITE_KEY, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean removeFromAlreadyRead(Book book) {
        ArrayList<Book> books = getAlreadyReadBook();
        if (books != null) {
            for (Book b : books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(ALREADY_READ_KEY);
                        editor.putString(ALREADY_READ_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromWantRead(Book book) {
        ArrayList<Book> books = getWantToReadBook();
        if (books != null) {
            for (Book b : books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(WANT_TO_READ_KEY);
                        editor.putString(WANT_TO_READ_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromCurRead(Book book) {
        ArrayList<Book> books = getCurReadBook();
        if (books != null) {
            for (Book b : books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(CURRENTLY_READING_KEY);
                        editor.putString(CURRENTLY_READING_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFromFavoriteBook(Book book) {
        ArrayList<Book> books = getFavoriteBook();
        if (books != null) {
            for (Book b : books) {
                if (b.getId() == book.getId()) {
                    if (books.remove(b)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(FAVORITE_KEY);
                        editor.putString(FAVORITE_KEY, gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
