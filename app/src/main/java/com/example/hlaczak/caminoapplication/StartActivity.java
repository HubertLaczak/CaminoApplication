package com.example.hlaczak.caminoapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;

import com.example.hlaczak.caminoapplication.DuringPlanning.YourPlan;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class StartActivity extends AppCompatActivity {

    @BindView(R.id.btn_login) Button btn_login;
    @BindView(R.id.btn_register) Button btn_register;
    @BindView(R.id.button) Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_login)
    public void onbtn_login(){
        startActivity(new Intent(StartActivity.this, LoginActivity.class));

    }
    @OnClick(R.id.btn_register)
    public void onbtn_register(){
        startActivity(new Intent(StartActivity.this, LoginActivity.class));

    }

    @OnClick(R.id.button)
    public void onbtn_language(){
        startActivity(new Intent(StartActivity.this, YourPlan.class));
        //showChangeLanguageDialog();
    }

    private void showChangeLanguageDialog() {
        final String[] listItems = {"English", "Polski"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this) //nowe okno AlertDialog
                .setTitle(R.string.AlertDialog_Builder_title); //tytuł okienka z wyborem języków //Available Languages
        builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() { //-1 oznacza, że żadna z opcji nie jest "checked", nie jest zaznaczona
            @Override //Dialog Interface is..Interface used to allow the creator of a dialog to run some code when an item on the dialog is clicked.
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    setLocale("en");
                    dialog.cancel();
                } else {
                    setLocale("pl");
                    dialog.cancel();
                }
            }
        })
                .setIcon(R.drawable.language) //ustawienie ikonki języków
                .show();    //wyświetla AlertDialog
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang); //obiekt reprezentujący ustawienia geograficzne, polityczne itp.
        Locale.setDefault(locale);  //ustawienia lokalne dla JVM względem obiektu locale
        Configuration config = new Configuration();
        config.setLocale(locale); // //zmiana ustawień regionalnych
        getResources().updateConfiguration(config, getResources().getDisplayMetrics()); //uaktualnianie danych :)
        //getResources()..Umożliwia dostęp do zasobów i klas związanych z aplikacją, a także do wywołań dla
        // operacji na poziomie aplikacji, takich jak uruchamianie działań, nadawanie i otrzymywanie zamiarów itp.
    }

}
