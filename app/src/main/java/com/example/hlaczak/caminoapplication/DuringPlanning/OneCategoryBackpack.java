package com.example.hlaczak.caminoapplication.DuringPlanning;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hlaczak.caminoapplication.Adapters.ListViewAdapter;
import com.example.hlaczak.caminoapplication.Model.SingleItem;
import com.example.hlaczak.caminoapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OneCategoryBackpack extends AppCompatActivity {
    private Button btn_Save;
    private Button btn_Exit;

//    private Button btn_Positive_Item;
//    private Button btn_Negative_Item;

        @BindView(R.id.btn_Negative_Item) Button btn_Negative_Item;
        @BindView(R.id.btn_Positive_Item) Button btn_Positive_Item;


    private FloatingActionButton floatingButton;
    private EditText et_newName;
    private TextView tv_groupName;
    private TextView tv_Amount; //Wyświetla wartość seekbar
//    @BindView(R.id.tv_Amount) TextView tv_Amount;
//    @BindView(R.id.seekBar) SeekBar seekBar;
    private EditText et_Weigth;
    private EditText et_Name;
    private int et_Amount;


    private SeekBar seekBar;

    private AlertDialog adName;
    private AlertDialog adItem;
    private View nameView;
    private View addView;

    private String category; //which 1of4 category was opened (First/Second..)
    private SharedPreferences sharedPref;

    private ListView listView; //https://www.youtube.com/watch?v=e0hiSgK9vzk
    private ListViewAdapter adapter;
    private List<String> ItemsList = new ArrayList<>();
    public static boolean isActionMode = false;
    public static List<String> UserSelection = new ArrayList<>();
    public static ActionMode actionMode = null;

    long iCount;
    FirebaseAuth auth;

    List<SingleItem> mSingleItems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_category_backpack);

        nameView = LayoutInflater.from(OneCategoryBackpack.this).inflate(R.layout.alert_dialog,null);
        addView = LayoutInflater.from(OneCategoryBackpack.this).inflate(R.layout.add_item_dialog,null);

        View view = View.inflate(getApplicationContext(), R.layout.add_item_dialog, null);
        ButterKnife.bind(this,view);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        category = getIntent().getExtras().getString("Category","Category Name");


        AlertDialog.Builder builderName = new AlertDialog.Builder(this);
        builderName.setView(nameView);
        adName = builderName.create();

        AlertDialog.Builder builderAdd = new AlertDialog.Builder(this);
        builderAdd.setView(addView);
        adItem = builderAdd.create();


        btn_Save = nameView.findViewById(R.id.btn_Save);
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onSaveClick();
            }
        });
        btn_Exit = nameView.findViewById(R.id.btn_Exit);
        btn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adName.dismiss();
                et_newName.setText("");
            }
        });
        et_newName = nameView.findViewById(R.id.et_newName);
        tv_groupName = findViewById(R.id.tv_groupName);
        tv_groupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adName.show();

                if(et_newName.length()==0){
                    btn_Save.setEnabled(false);
                } else {
                    btn_Save.setEnabled(true);
                }
            }
        });
        floatingButton = findViewById(R.id.floatingButton);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adItem.show();
            }
        });



//        btn_Negative_Item = findViewById(R.id.btn_Negative_Item);
//        btn_Positive_Item = findViewById(R.id.btn_Positive_Item);

        tv_Amount = addView.findViewById(R.id.tv_Amount); //textview do wyświetlania postępu seekbaru
        seekBar = addView.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_Amount.setText("Ilość: " + String.valueOf(progress));
                et_Amount = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(OneCategoryBackpack.this, "Stop", Toast.LENGTH_SHORT).show();
            }
        });



        listView = findViewById(R.id.mListView);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(modeListener);

        et_Weigth = addView.findViewById(R.id.et_Weigth);
        et_Name = addView.findViewById(R.id.et_Name);

        et_newName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    btn_Save.setEnabled(false);
                } else {
                    btn_Save.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mSingleItems = new ArrayList<>();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        refreshItemsList(this);
    }

    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.first_menu, menu);

            isActionMode = true;
            actionMode = mode;
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch(item.getItemId()){

                case R.id.action_delete:
                    adapter.removeItems(UserSelection);
                    mode.finish();
                    return true;

                case R.id.action_share:
                    return true;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            isActionMode = false;
            actionMode = null;
            UserSelection.clear();

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        String nameFromPref = sharedPref.getString(category + "GroupName", "Nazwa");
        tv_groupName.setText(nameFromPref);
    }

    private void onSaveClick(){
        String newName = et_newName.getText().toString();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(category + "GroupName", newName);
        editor.commit();
        Toast.makeText(this, "Zmieniono nazwę", Toast.LENGTH_SHORT).show();
        tv_groupName.setText(newName);
        et_newName.setText("");
        adName.dismiss();
    }

    public void addItem(View view) { // btn_Positive_Item.setOnClickListener
        String name = et_Name.getText().toString();
        String amount = String.valueOf(et_Amount);
        String weight = et_Weigth.getText().toString();

        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        FirebaseUser firebaseUser = auth.getCurrentUser();
//        String userid = firebaseUser.getUid();
        DatabaseReference myRef = database.getReference(category + "ItemsList")./*.child(userid).*/child(name  + "_" + amount + "_" + weight);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("category", category);
        hashMap.put("name", name);
        hashMap.put("amount", amount);
        hashMap.put("weigth", weight);

        myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    adItem.dismiss();
                    Toast.makeText(OneCategoryBackpack.this, "Zapisano!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        adapter = new ListViewAdapter(ItemsList, this);
        listView.setAdapter(adapter);

        dontAddItem(view);
    }

    public void dontAddItem(View view) {  // btn_Negative_Item.setOnClickListener
        et_Name.setText("");
        seekBar.setProgress(0);
        et_Weigth.setText("");
        adItem.dismiss();
    }


    public void refreshItemsList(final Context context){
        final DatabaseReference databaseReference  = FirebaseDatabase.getInstance().getReference(category + "ItemsList");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mSingleItems.clear();
                ItemsList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    SingleItem singleItem = snapshot.getValue(SingleItem.class);
                    mSingleItems.add(singleItem);
                    ItemsList.add(singleItem.getAmount() + "x " + singleItem.getName() + " " + singleItem.getWeigth() + "g");
                }

                adapter = new ListViewAdapter(ItemsList, context);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void countChildren(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myCount = database.getReference(category + "ItemsList");
        myCount.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    iCount = dataSnapshot.getChildrenCount();
                } else {
                    iCount = 0;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
