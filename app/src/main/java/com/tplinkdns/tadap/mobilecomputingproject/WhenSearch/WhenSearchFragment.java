package com.tplinkdns.tadap.mobilecomputingproject.WhenSearch;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.tplinkdns.tadap.mobilecomputingproject.MainActivity;
import com.tplinkdns.tadap.mobilecomputingproject.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WhenSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WhenSearchFragment extends Fragment implements LocationListener {

    ArrayList<CountryList> countryLists;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WhenSearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WhenSearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WhenSearchFragment newInstance(String param1, String param2) {
        WhenSearchFragment fragment = new WhenSearchFragment();
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
        return inflater.inflate(R.layout.fragment_when_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        SearchView searchView = view.findViewById(R.id.searchBar_whenSearching);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                Bundle bundle = new Bundle();
                bundle.putString("query", s);


                navController.navigate(R.id.action_whenSearchFragment_to_afterSearchFragment, bundle);

                return false;
            }



            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        //position
        View.OnClickListener clickPosition = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
//                Location location;

                final double KeymapX;
                final double KeymapY;

//                if (Build.VERSION.SDK_INT >= 23 &&
//                        ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(getActivity(), new String[]{
//                            android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
//                }
                int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);

                if(permissionCheck == PackageManager.PERMISSION_DENIED){ //포그라운드 위치 권한 확인

                    //위치 권한 요청
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }



//                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                else {
                    LocationManager location_Manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                    Location location = location_Manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


                    Log.d("WhenSearch","Permission"+PackageManager.PERMISSION_GRANTED);


                    try {
                        KeymapX = location.getLongitude();
                        KeymapY = location.getLatitude();
                        Log.d("WhenSearch","mapx: "+KeymapX);
                        Log.d("WhenSearch","mapy: "+KeymapY);
                        bundle.putDouble("mapx", KeymapX);
                        bundle.putDouble("mapy", KeymapY);

                    }catch (Exception e){
                        Log.w("WhenSearch","err: ",e);
                    }




                    navController.navigate(R.id.action_whenSearchFragment_to_afterSearchFragment, bundle);
                }
            }


        };

        TextView positionText = view.findViewById(R.id.textView);
        ImageView postionImage = view.findViewById(R.id.CurrentPositionImage);
        positionText.setOnClickListener(clickPosition);
        postionImage.setOnClickListener(clickPosition);


        //gridview
        GridView gridView = view.findViewById(R.id.gridCountryName);
        AddCity();

        GridAdapterCountryList gridAdapter = new GridAdapterCountryList(view.getContext(), countryLists);

        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getContext(), "Clicked: "+countryLists.get(i).cityName, Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("city", countryLists.get(i).cityName);

                navController.navigate(R.id.action_whenSearchFragment_to_afterSearchFragment, bundle);
            }
        });


    }
    private void AddCity(){

        countryLists = new ArrayList<>();

        countryLists.add(new CountryList("서울시"));
        countryLists.add(new CountryList("부산시"));
        countryLists.add(new CountryList("대구시"));
        countryLists.add(new CountryList("인천시"));
        countryLists.add(new CountryList("광주시"));
        countryLists.add(new CountryList("대전시"));
        countryLists.add(new CountryList("울산시"));
        countryLists.add(new CountryList("세종시"));

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}