package com.tplinkdns.tadap.mobilecomputingproject.Searching;

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
import android.widget.SearchView;

import com.tplinkdns.tadap.mobilecomputingproject.DB.FavoriteTableInfo;
import com.tplinkdns.tadap.mobilecomputingproject.DB.SearchingDBHelper;
import com.tplinkdns.tadap.mobilecomputingproject.R;
import com.tplinkdns.tadap.mobilecomputingproject.XmlParser;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AfterSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AfterSearchFragment extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AfterSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AfterSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AfterSearchFragment newInstance(String param1, String param2) {
        AfterSearchFragment fragment = new AfterSearchFragment();
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

        return inflater.inflate(R.layout.fragment_after_search, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        SearchView searchView = view.findViewById(R.id.afterSearchSearchView);


        Bundle bundle = getArguments();
        XmlParser parser;


        Log.d("AfterSearch","query: "+bundle.getString("query"));
        Log.d("AfterSearch","mapX: "+bundle.getDouble("mapx"));
        Log.d("AfterSearch","mapY: "+bundle.getDouble("mapy"));
        Log.d("AfterSearch","City: "+bundle.getString("city"));


        //Query
        if(bundle.getString("query")!=null) {
            String searchValue = bundle.getString("query", "야영");

            Log.d("AfterSearchFragment.query search", "value: " + searchValue);

            searchView.setQuery(searchValue, false);
            searchView.clearFocus();

            parser = new XmlParser(searchValue);

            Log.d("AfterSearchFragment", "ready parse, Query");
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<SearchingList> arrayList;

                    parser.KeywordParse();

                    arrayList = parser.getResult();
                    Log.d("AfterSearchFragment", "afterparse");


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            Log.d("AfterSearchFragment", "uithread");

                            RecyclerView recyclerView = view.findViewById(R.id.RecyclerView);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());

                            for (int i = 0; i < arrayList.size(); i++) {
                                Log.d("AfterSearchFragment", "name: " + arrayList.get(i).name);
                                Log.d("AfterSearchFragment", "phone: " + arrayList.get(i).telNum);
                                Log.d("AfterSearchFragment", "URL " + arrayList.get(i).URL);
                            }

                            RecyclerView.Adapter<RecyclerSearchingListHolder> adapter = new RecyclerViewAdapterSearchingList(view.getContext(), arrayList);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);

                        }
                    });
                }
            });
            t.start();

            t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                                              @Override
                                              public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
                                                Log.d("AfterSearchFragment","Thread err: "+throwable);
                                              }
                                          });

                    }
        else if(bundle.getDouble("mapx")!=0.0){

            double mapX = bundle.getDouble("mapx", 0);
            double mapY = bundle.getDouble("mapy", 0);

            Log.d("AfterSearchFragment.positionSearch", "X: " + mapX + " Y: " + mapY);

            searchView.setQuery("내위치",false);

            searchView.clearFocus();

            parser = new XmlParser(bundle.getDouble("mapx", 0),bundle.getDouble("mapy", 0));
            Log.d("AfterSearchFragment.positionSearch", "ready parse, Location");

            Thread t =new Thread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<SearchingList> ParrayList;

                    parser.LocationParse();
                    ParrayList=parser.getResult();

//                    ParrayList = parser.getResult();
                    Log.d("AfterSearchFragment.positionSearch", "Location parse finished");


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Log.d("hong2", "uithread");

                            RecyclerView recyclerView = view.findViewById(R.id.RecyclerView);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());

                            for (int i = 0; i < ParrayList.size(); i++) {
                                Log.d("AfterSearchFragment.positionSearch", "name: " + ParrayList.get(i).name);
                                Log.d("AfterSearchFragment.positionSearch", "phone: " + ParrayList.get(i).telNum);
                                Log.d("AfterSearchFragment.positionSearch", "URL " + ParrayList.get(i).URL);
                            }
                            Log.d("AfterSearchFragment.positionSearch", "afterFor");

                            RecyclerView.Adapter<RecyclerSearchingListHolder> adapter = new RecyclerViewAdapterSearchingList(view.getContext(), ParrayList);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);

                        }
                    });
                }
            });
            t.start();

            t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
                    Log.d("AfterSearchFragment","Thread err: "+throwable);
                }
            });
        }
        else{
            String city = bundle.getString("city");
            Log.d("AfterSearch","City: "+city);

            SearchingDBHelper myDBHelper = new SearchingDBHelper(getContext());
            SQLiteDatabase db = myDBHelper.getWritableDatabase();
            Cursor cursor;
            ArrayList<SearchingList> arrayList = new ArrayList<>();
            try {
                cursor = db.rawQuery("select * from " + FavoriteTableInfo.SEARCHING_TABLE_NAME + " where " + FavoriteTableInfo.CAMP_PUBLIC_NAME + " = '" + city +"'", null);

                while (cursor.moveToNext()){
                    Log.d("DB","data0: "+cursor.getString(0));//contentId
                    Log.d("DB","data1: "+cursor.getString(1));//campName
                    Log.d("DB","data2: "+cursor.getString(2));//campDoName
                    Log.d("DB","data3: "+cursor.getString(3));//Image_URL
                    Log.d("DB","data4: "+cursor.getString(4));//URL
                    Log.d("DB","data5: "+cursor.getString(5));//mapX
                    Log.d("DB","data6: "+cursor.getString(6));//mapY
                    Log.d("DB","data7: "+cursor.getString(7));//address
                    Log.d("DB","data8: "+cursor.getString(8));//campTel

                    arrayList.add(new SearchingList(cursor.getString(1),cursor.getString(2),
                            cursor.getString(7),"empty",cursor.getString(8),
                            cursor.getString(4),"empty", "empty",cursor.getString(3),
                            cursor.getString(5),cursor.getString(6),cursor.getString(0)));

                }
            }catch (Exception e){
                Log.w("DB","err",e);
            }

            RecyclerView recyclerView = view.findViewById(R.id.RecyclerView);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());

            RecyclerView.Adapter<RecyclerSearchingListHolder> adapter = new RecyclerViewAdapterSearchingList(view.getContext(), arrayList);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);








        }

    }
}