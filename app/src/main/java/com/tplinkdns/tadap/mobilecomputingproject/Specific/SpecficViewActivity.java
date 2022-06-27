package com.tplinkdns.tadap.mobilecomputingproject.Specific;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.tplinkdns.tadap.mobilecomputingproject.DB.FavoriteTableInfo;
import com.tplinkdns.tadap.mobilecomputingproject.DB.MyFavoriteDBHelper;
import com.tplinkdns.tadap.mobilecomputingproject.R;
import com.tplinkdns.tadap.mobilecomputingproject.fireBase.DataModelReview;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SpecficViewActivity extends AppCompatActivity implements OnMapReadyCallback {
    ImageView imageView;
    TextView name,adr;
    ImageButton call, page, favorite;
    MapView mapView;
    EditText editText;
    Button commit;
    private static NaverMap naverMap;
    double mapX, mapY;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    public FirebaseAuth mAuth;
    public FirebaseDatabase database;
    public DatabaseReference myRef;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clicked_show);



        MyFavoriteDBHelper myDBHelper = new MyFavoriteDBHelper(getApplicationContext());

        Intent intent = getIntent();

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        imageView = findViewById(R.id.SpecificImage);
        name = findViewById(R.id.SpecificName);
        adr = findViewById(R.id.SpecificAdd);
        call = findViewById(R.id.Calling);
        page = findViewById(R.id.homePage);
        favorite=findViewById(R.id.like);
        editText=findViewById(R.id.writeText);
        commit = findViewById(R.id.applyText);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        recyclerView = findViewById(R.id.ReviewRecycle);
        layoutManager = new LinearLayoutManager(getApplicationContext());

        MyFavoriteDBHelper myFavoriteDBHelper = new MyFavoriteDBHelper(getApplicationContext());
        SQLiteDatabase db = myFavoriteDBHelper.getWritableDatabase();
        Cursor cursor;
        String ContentID =intent.getExtras().getString("contentId");
        Log.d("SpecificViewActivity", "ContentID: "+ContentID);

        cursor = db.rawQuery("select "+FavoriteTableInfo.CONTENT_ID + " from "+FavoriteTableInfo.TABLE_NAME
                +" where "+FavoriteTableInfo.CONTENT_ID+" = "+ContentID,null);
        if (cursor.getCount()>0)
            favorite.setImageResource(R.drawable.ic_baseline_star_24);
        else
            favorite.setImageResource(R.drawable.ic_baseline_star_outline_24);



        Log.d("" +
                "","Content id get by String: " + intent.getExtras().getString("contentId"));

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cs;
                cs = db.rawQuery("select "+FavoriteTableInfo.CONTENT_ID + " from "+FavoriteTableInfo.TABLE_NAME
                        +" where "+FavoriteTableInfo.CONTENT_ID+" = "+ContentID,null);


                if (cs.getCount()>0) {
                    //삭제
                    myFavoriteDBHelper.deleteData(intent.getExtras().getString("contentId"),db);
                    favorite.setImageResource(R.drawable.ic_baseline_star_outline_24);
                }
                else {
                    //추가
                    ContentValues values = new ContentValues();
                    values.put(FavoriteTableInfo.CONTENT_ID, intent.getExtras().getString("contentId"));
                    values.put(FavoriteTableInfo.CAMP_NAME, intent.getExtras().getString("name"));
                    values.put(FavoriteTableInfo.IMAGE_URL, intent.getExtras().getString("imgUrl"));
                    values.put(FavoriteTableInfo.URL, intent.getExtras().getString("url"));
                    values.put(FavoriteTableInfo.CAMP_ADDRESS, intent.getExtras().getString("adr1"));
                    values.put(FavoriteTableInfo.CAMP_PHONE_NUM, intent.getExtras().getString("call"));
                    values.put(FavoriteTableInfo.CAMP_MAP_X, intent.getExtras().getString("mapX"));
                    values.put(FavoriteTableInfo.CAMP_MAP_Y, intent.getExtras().getString("mapY"));
                    values.put(FavoriteTableInfo.CAMP_PUBLIC_NAME, intent.getExtras().getString("doNm"));


                    Toast.makeText(SpecficViewActivity.this, "즐겨찾기에 추가되었습니다", Toast.LENGTH_SHORT).show();
                    long rowID = db.insert(FavoriteTableInfo.TABLE_NAME, null, values);
                    if(rowID == -1) Log.d("SpecificViewActivity", "Error to insert");
                    else Log.d("SpecificViewActivity", "insert Complete");
                    favorite.setImageResource(R.drawable.ic_baseline_star_24);
                }
                //temp to check is it run properly

                cs= db.rawQuery("select * from "+FavoriteTableInfo.TABLE_NAME+";",null);
                int i=0;
                while(cs.moveToNext()){//테이블 길이만큼
                    Log.d("DB",i+"data0: "+cs.getString(0));
                    Log.d("DB",i+"data1: "+cs.getString(1));
                    Log.d("DB",i+"data2: "+cs.getString(2));
                    Log.d("DB",i+"data3: "+cs.getString(3));
                    Log.d("DB",i+"data4: "+cs.getString(4));
                    Log.d("DB",i+"data5: "+cs.getString(5));
                    Log.d("DB",i+"data6: "+cs.getString(6));
                    Log.d("DB",i+"data7: "+cs.getString(7));
                    Log.d("DB",i+"data8: "+cs.getString(8));

                    i++;
                }
                cs.close();



            }
        });


        if(intent.getExtras().getString("imgUrl").equals("empty")){
            imageView.setImageResource(R.drawable.ic_baseline_close_24);
        }else{
            Glide.with(this).load(intent.getExtras().getString("imgUrl")).into(imageView);
        }

        name.setText(intent.getExtras().getString("name"));
        adr.setText(intent.getExtras().getString("adr1"));
        if(!intent.getExtras().getString("adr2").equals("empty"))
            adr.append(" "+ intent.getExtras().getString("adr2"));

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp =intent.getExtras().getString("call");
                if(temp.equals("empty")){
                    Toast.makeText(SpecficViewActivity.this, "전화번호가 없습니다", Toast.LENGTH_SHORT).show();
                }
                else{
                    temp=temp.replace("-","");
                    Log.d("test",temp);
                    temp="tel:"+temp;
                    Intent call = new Intent(Intent.ACTION_DIAL, Uri.parse(temp));
                    startActivity(call);
                }
            }
        });
        page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp =intent.getExtras().getString("url");
                if(temp.equals("empty")){
                    Toast.makeText(SpecficViewActivity.this, "홈페이지가 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                        Intent page = new Intent(Intent.ACTION_VIEW, Uri.parse(temp));
                        startActivity(page);

                }
            }
        });

        mapX = Double.parseDouble(intent.getExtras().getString("mapX"));
        mapY = Double.parseDouble(intent.getExtras().getString("mapY"));

        cursor.close();


        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mAuth = FirebaseAuth.getInstance();
                Log.d("Testing","Auth: " + mAuth.getUid());
                FirebaseUser user = mAuth.getCurrentUser();

                Log.d("Testing","Auth2: " + user.getEmail());

//                database = FirebaseDatabase.getInstance();
//                myRef = database.getReference();

                DataModelReview dataModelReview;

                if(mAuth.getUid()==null){
                    Toast.makeText(SpecficViewActivity.this, "로그인해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    dataModelReview = new DataModelReview(editText.getText().toString(),mAuth.getUid(),intent.getExtras().getString("contentId"), user.getEmail());
                    myRef.child("review").push().setValue(dataModelReview);
//                    myRef.child("review").child(intent.getExtras().getString("contentId")).child(mAuth.getUid()).setValue(editText.getText().toString());
                }
            }
        });

        myRef.child("review").child(intent.getExtras().getString("contentId")).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("Testing", "Error getting data", task.getException());
                }
                else {
                    Log.d("Testing", String.valueOf(task.getResult().getValue()));
                }
            }
        });

        ArrayList<DataModelReview> arrayList = new ArrayList<>();
        ArrayList<String> keyArrayList = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("review");
        Query query = mDatabase.orderByChild("ContentID").equalTo(intent.getExtras().getString("contentId"));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                keyArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DataModelReview dataModelReview = dataSnapshot.getValue(DataModelReview.class);
                    Log.d("Testing3","data snapshot key: "+dataSnapshot.getKey());
                    keyArrayList.add(dataSnapshot.getKey());
                    arrayList.add(dataModelReview);
                }

                for(int i = 0 ; i < arrayList.size();i++){
                    Log.d("Testing","Data1: " + arrayList.get(i).Uid);
                    Log.d("Testing","Data2: " + arrayList.get(i).content);
                    Log.d("Testing","Data3: " + arrayList.get(i).ContentID);
                    Log.d("Testing","Data4: " + arrayList.get(i).mail);
                }

                RecyclerView.Adapter<ReviewHolder> adapter =
                        new RecyclerViewReviewAdapter(getApplicationContext(),arrayList,keyArrayList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Testing","err");
            }
        });





//        Marker marker = new Marker();
//        marker.setPosition(latLng);

    }



    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

        CameraPosition cameraPosition = new CameraPosition(new LatLng(mapY,mapX),16);
        naverMap.setCameraPosition(cameraPosition);
        Marker marker = new Marker();
        marker.setPosition(new LatLng(mapY,mapX));
        marker.setMap(naverMap);

    }
}
