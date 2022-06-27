package com.tplinkdns.tadap.mobilecomputingproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tplinkdns.tadap.mobilecomputingproject.DB.FavoriteTableInfo;
import com.tplinkdns.tadap.mobilecomputingproject.DB.SearchingDBHelper;
import com.tplinkdns.tadap.mobilecomputingproject.Searching.SearchingList;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public FirebaseAuth mAuth;
    public FirebaseDatabase database;
    public DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //getAuth
//        mAuth = FirebaseAuth.getInstance();
//        Log.d("Testing","Auth: " + mAuth.getUid());
//
//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference();
//
////        DataModel dataModel = new DataModel("String","ContentID");
////        if(mAuth.getUid()!=null) {
////            myRef.child(mAuth.getUid()).setValue(dataModel);
////        }
//
//        myRef.child(mAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if(!task.isSuccessful()){
//                    Log.e("Testing", "Error getting data", task.getException());
//                }
//                else{
//                    Log.d("Testing", String.valueOf(task.getResult().getValue()));
//                }
//            }
//        });


        SearchingDBHelper myDBHelper = new SearchingDBHelper(getApplicationContext());
        SQLiteDatabase db = myDBHelper.getWritableDatabase();

        XmlParser xmlParser = new XmlParser();

        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<SearchingList> arrayList;
                xmlParser.FullDBSize();
                int size = xmlParser.getSize();
                Log.d("MainActivity","ParsingSize: "+size);

                Cursor cursor;
                cursor = db.rawQuery("select * from "+ FavoriteTableInfo.SEARCHING_TABLE_NAME,null);
                Log.d("MainActivity","DBSize: "+cursor.getCount());


                if(size>cursor.getCount()){
                    xmlParser.FullDBParse(size);
                    arrayList = xmlParser.getResult();
                    ContentValues values = new ContentValues();
                    long rowID;

                    for(int i = 0 ; i < arrayList.size();i++){
                        values.clear();
                        values.put(FavoriteTableInfo.CONTENT_ID,arrayList.get(i).contentId);
                        values.put(FavoriteTableInfo.CAMP_NAME,arrayList.get(i).name);
                        values.put(FavoriteTableInfo.CAMP_PUBLIC_NAME,arrayList.get(i).doNm);
                        values.put(FavoriteTableInfo.IMAGE_URL,arrayList.get(i).ImageUrl);
                        values.put(FavoriteTableInfo.URL,arrayList.get(i).URL);
                        values.put(FavoriteTableInfo.CAMP_MAP_X,arrayList.get(i).mapX);
                        values.put(FavoriteTableInfo.CAMP_MAP_Y,arrayList.get(i).mapY);
                        values.put(FavoriteTableInfo.CAMP_ADDRESS,arrayList.get(i).adrStreetName);
                        values.put(FavoriteTableInfo.CAMP_PHONE_NUM,arrayList.get(i).telNum);

                        rowID = db.insert(FavoriteTableInfo.SEARCHING_TABLE_NAME,null,values);
                        if(rowID == -1) Log.d("MainActivity", "Error to insert"+"already Exist");
                        else Log.d("MainActivity", "insert Complete");
                    }
                }
            }
        }).start();

        ImageView login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "login clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);

            }
        });




    }



}