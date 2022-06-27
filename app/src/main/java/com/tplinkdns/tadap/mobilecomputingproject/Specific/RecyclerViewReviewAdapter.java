package com.tplinkdns.tadap.mobilecomputingproject.Specific;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tplinkdns.tadap.mobilecomputingproject.R;
import com.tplinkdns.tadap.mobilecomputingproject.Searching.RecyclerViewAdapterSearchingList;
import com.tplinkdns.tadap.mobilecomputingproject.fireBase.DataModelReview;

import java.util.ArrayList;
import java.util.Objects;

public class RecyclerViewReviewAdapter extends RecyclerView.Adapter<ReviewHolder> {

    Context context;
    final private ArrayList<DataModelReview> dataSet;
    final private ArrayList<String> keyDataSet;

    public RecyclerViewReviewAdapter(Context context, ArrayList<DataModelReview> dataSet, ArrayList<String> keyDataSet){
        this.context = context;
        this.dataSet = dataSet;
        this.keyDataSet = keyDataSet;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_recycle,parent,false);
        return new ReviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, @SuppressLint("RecyclerView") int position) {
//        holder.User.setText("유저"+(position+1)+": ");
        holder.User.setText(dataSet.get(position).mail);
        holder.Content.setText(dataSet.get(position).content);

        holder.Content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                Log.d("Testing5", "mAuth uid: "+mAuth.getUid());
                Log.d("Testing5", "dataset uid: "+dataSet.get(position).Uid);

                if(Objects.equals(mAuth.getUid(), dataSet.get(position).Uid)) {


                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference();


                    myRef.child("review").child(keyDataSet.get(position)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Log.e("Testing", "Error removing data", task.getException());
                            } else {
                                Log.d("Testing", String.valueOf(task.getResult()));
                                Toast.makeText(context, "삭제하였습니다.ㅅ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else Toast.makeText(context, "본인의 작성물이 아닙니다.", Toast.LENGTH_SHORT).show();



                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
//        return 0;
        return dataSet.size();
    }


}


