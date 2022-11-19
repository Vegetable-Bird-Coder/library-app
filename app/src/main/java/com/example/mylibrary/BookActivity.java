package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookId";

    private TextView txtBookName, txtAuthor, txtPages, txtDescription;
    private Button btnAddToWantRead, btnAddToAlreadyRead, btnAddToCurRead, btnAddToFavorite;
    private ImageView bookImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();

        Intent intent = getIntent();
        if (intent != null) {
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if (bookId != -1) {
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if (incomingBook != null) {
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWantToReadBooks(incomingBook);
                    handleCurReadBooks(incomingBook);
                    handleFavoriteBooks(incomingBook);
                }
            }
        }



    }

    private void handleAlreadyRead(Book book) {
        ArrayList<Book> alreadyReadBook = Utils.getInstance(this).getAlreadyReadBook();

        boolean exit = false;

        for (Book b : alreadyReadBook) {
            if (b.getId() == book.getId()) {
                exit = true;
                break;
            }
        }

        if (exit) {
            btnAddToAlreadyRead.setEnabled(false);
        } else {
            btnAddToAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance(BookActivity.this).addToAlreadyRead(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, AlreadyReadActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something Wrong Happened", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleWantToReadBooks(Book book) {
        ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getWantToReadBook();

        boolean exit = false;

        for (Book b : wantToReadBooks) {
            if (b.getId() == book.getId()) {
                exit = true;
                break;
            }
        }

        if (exit) {
            btnAddToWantRead.setEnabled(false);
        } else {
            btnAddToWantRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance(BookActivity.this).addToWantRead(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, WantToReadActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something Wrong Happened", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleCurReadBooks(Book book) {
        ArrayList<Book> curReadBooks = Utils.getInstance(this).getCurReadBook();

        boolean exit = false;

        for (Book b : curReadBooks) {
            if (b.getId() == book.getId()) {
                exit = true;
                break;
            }
        }

        if (exit) {
            btnAddToCurRead.setEnabled(false);
        } else {
            btnAddToCurRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance(BookActivity.this).addToCurRead(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, CurrentReadActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something Wrong Happened", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleFavoriteBooks(Book book) {
        ArrayList<Book> favoriteBooks = Utils.getInstance(this).getFavoriteBook();

        boolean exit = false;

        for (Book b : favoriteBooks) {
            if (b.getId() == book.getId()) {
                exit = true;
                break;
            }
        }

        if (exit) {
            btnAddToFavorite.setEnabled(false);
        } else {
            btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance(BookActivity.this).addToFavoriteRead(book)) {
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this, FavoriteReadActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something Wrong Happened", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void initViews() {
        txtAuthor = findViewById(R.id.txtAuthorAB);
        txtBookName = findViewById(R.id.txtBookNameAB);
        txtPages = findViewById(R.id.txtPage);
        txtDescription = findViewById(R.id.txtDesc);

        btnAddToAlreadyRead = findViewById(R.id.btnAddToAlreadyRead);
        btnAddToCurRead = findViewById(R.id.btnAddToCurRead);
        btnAddToFavorite = findViewById(R.id.btnAddToFavorite);
        btnAddToWantRead = findViewById(R.id.btnAddToWantRead);

        bookImage = findViewById(R.id.imgBookAB);
    }

    private void setData(Book book) {
        txtBookName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtDescription.setText(book.getLongDesc());
        Glide.with(this)
                .asBitmap()
                .load(book.getImageUrl())
                .into(bookImage);
    }
}