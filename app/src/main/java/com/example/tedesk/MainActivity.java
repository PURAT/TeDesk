package com.example.tedesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tedesk.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {

    private Toast backToast;
    private long backPressedMillis;

    Button register, signIn;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (Button) findViewById(R.id.button_sign_up);
        signIn = (Button) findViewById(R.id.button_sign_in);
        root = findViewById(R.id.start_window);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWindowRegister();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWindowSignIn();
            }
        });

    }

    private void showWindowSignIn() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Вход");
        dialog.setMessage("Введите данные для входа");

        LayoutInflater flater = LayoutInflater.from(this);
        View sign_in_window = flater.inflate(R.layout.sign_in_window, null);
        dialog.setView(sign_in_window);

        final MaterialEditText email = sign_in_window.findViewById(R.id.email_field);
        final MaterialEditText password = sign_in_window.findViewById(R.id.password_field);

        dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.setPositiveButton("Войти", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String emailText = email.getText().toString();
                final String passwordText = password.getText().toString();

                if (TextUtils.isEmpty(emailText)) {
                    Snackbar.make(root, "Введите email", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordText)) {
                    Snackbar.make(root, "Введите пароль", Snackbar.LENGTH_LONG).show();
                    return;
                }

                auth.signInWithEmailAndPassword(emailText, passwordText)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e.getMessage().contains("network"))
                            Snackbar.make(root, "Проверьте подключение к интернету", Snackbar.LENGTH_LONG).show();
                        else
                            Snackbar.make(root, "Неправильный пароль или email", Snackbar.LENGTH_LONG).show();
                    }
                });

            }
        });

        dialog.show();

    }

    private void showWindowRegister() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Зарегистроваться");
        dialog.setMessage("Введите данные для регистрации");

        LayoutInflater flater = LayoutInflater.from(this);
        View register_window = flater.inflate(R.layout.register_window, null);
        dialog.setView(register_window);

        final MaterialEditText email = register_window.findViewById(R.id.email_field);
        final MaterialEditText password = register_window.findViewById(R.id.password_field);
        final MaterialEditText name = register_window.findViewById(R.id.name_field);
        final MaterialEditText surname = register_window.findViewById(R.id.surname_field);

        dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton("Подтвердить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                final String emailText = email.getText().toString();
                final String passwordText = password.getText().toString();
                final String nameText = name.getText().toString();
                final String surNameText = surname.getText().toString();

                if (checkFields(emailText, passwordText, nameText, surNameText)) {
                    //Регистрация пользователя
                    auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    User user = new User();
                                    user.setEmail(emailText);
                                    user.setPassword(passwordText);
                                    user.setName(nameText);
                                    user.setSurname(surNameText);

                                    users.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).setValue(user);
                                }
                            }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Snackbar.make(root, "Вы были зарегистрированы ", Snackbar.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar.make(root, "Ошибка регистрации " + e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        dialog.show();
    }

    private boolean checkFields(String emailText, String passwordText, String nameText, String surnameText) {
        if (TextUtils.isEmpty(nameText)) {
            Snackbar.make(root, "Введите имя", Snackbar.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(surnameText)) {
            Snackbar.make(root, "Введите фамилию", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(emailText)) {
            Snackbar.make(root, "Введите email", Snackbar.LENGTH_LONG).show();
            return false;
        }
        if (passwordText.length() < 6) {
            Snackbar.make(root, "Введите пароль (больше 5 символов)", Snackbar.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (backPressedMillis + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(this, "Нажмите ещё раз, чтобы выйти", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedMillis = System.currentTimeMillis();
    }
}
