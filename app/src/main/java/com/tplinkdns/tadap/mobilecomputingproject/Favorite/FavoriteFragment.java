package com.tplinkdns.tadap.mobilecomputingproject.Favorite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tplinkdns.tadap.mobilecomputingproject.DB.FavoriteTableInfo;
import com.tplinkdns.tadap.mobilecomputingproject.DB.MyFavoriteDBHelper;
import com.tplinkdns.tadap.mobilecomputingproject.R;
import com.tplinkdns.tadap.mobilecomputingproject.Searching.RecyclerSearchingListHolder;
import com.tplinkdns.tadap.mobilecomputingproject.Searching.SearchingList;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<SearchingList> dataSet;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        MyFavoriteDBHelper myDBHelper = new MyFavoriteDBHelper(getContext());
        Cursor cursor;
        SQLiteDatabase db = myDBHelper.getWritableDatabase();

        dataSet = new ArrayList<>();

        cursor=db.rawQuery("select * from "+ FavoriteTableInfo.TABLE_NAME
                ,null);
        int i = 0;
        while(cursor.moveToNext()){//테이블 길이만큼
            Log.d("DB","data0: "+cursor.getString(0));//contentId
            Log.d("DB","data1: "+cursor.getString(1));//campName
            Log.d("DB","data2: "+cursor.getString(2));//campDoName
            Log.d("DB","data3: "+cursor.getString(3));//Image_URL
            Log.d("DB","data4: "+cursor.getString(4));//URL
            Log.d("DB","data5: "+cursor.getString(5));//mapX
            Log.d("DB","data6: "+cursor.getString(6));//mapY
            Log.d("DB","data7: "+cursor.getString(7));//address
            Log.d("DB","data8: "+cursor.getString(8));//campTel
            dataSet.add(new SearchingList(cursor.getString(1),cursor.getString(2),
                    cursor.getString(7),"empty",cursor.getString(8),
                    cursor.getString(4),"empty", "empty",cursor.getString(3),
                    cursor.getString(5),cursor.getString(6),cursor.getString(0)));

            Log.d("DB","i: "+i);//campTel

        }
        RecyclerView recyclerView = view.findViewById(R.id.favoriteRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        RecyclerView.Adapter<RecyclerSearchingListHolder> adapter = new RecyclerViewAdapterFavorite(view.getContext(),dataSet);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        cursor.close();

    }
}