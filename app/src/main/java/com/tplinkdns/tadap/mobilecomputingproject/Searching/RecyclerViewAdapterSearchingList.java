package com.tplinkdns.tadap.mobilecomputingproject.Searching;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tplinkdns.tadap.mobilecomputingproject.R;
import com.tplinkdns.tadap.mobilecomputingproject.Specific.SpecficViewActivity;

import java.util.ArrayList;

public class RecyclerViewAdapterSearchingList extends RecyclerView.Adapter<RecyclerSearchingListHolder> {

    final private ArrayList<SearchingList> dataSet;
    Context context;

    public RecyclerViewAdapterSearchingList(Context context, ArrayList<SearchingList>dataSet){
        this.context=context;
        this.dataSet=dataSet;
    }

    @NonNull
    @Override
    public RecyclerSearchingListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.after_serarch_recycle,parent,false);
        return new RecyclerSearchingListHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerSearchingListHolder holder, int position) {

        if(dataSet.get(position).ImageUrl.equals("empty")){
            holder.imageView.setImageResource(R.drawable.ic_baseline_close_24);
        }else{
            Glide.with(context).load(dataSet.get(position).ImageUrl).into(holder.imageView);
        }//이미지

        holder.name.setText(dataSet.get(position).name);//이름

        holder.address1.setText(dataSet.get(position).adrStreetName);//주소
        if(dataSet.get(position).adrSpecificName.equals("empty")){
            holder.address2.setText("");
        }
        else {
            holder.address2.append(dataSet.get(position).adrSpecificName);//주소2번이 비어있는지 확인
        }

        holder.call.setText(dataSet.get(position).telNum);//전화번호
        Log.d("RecyclerViewAdapterSearchingList","telnum ("+position+") : "+dataSet.get(position).telNum);




        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SpecficViewActivity.class);

                intent.putExtra("imgUrl",dataSet.get(holder.getAdapterPosition()).ImageUrl);
                intent.putExtra("name",dataSet.get(holder.getAdapterPosition()).name);
                intent.putExtra("adr1",dataSet.get(holder.getAdapterPosition()).adrStreetName);
                intent.putExtra("adr2",dataSet.get(holder.getAdapterPosition()).adrSpecificName);
                intent.putExtra("call",dataSet.get(holder.getAdapterPosition()).telNum);
                intent.putExtra("url",dataSet.get(holder.getAdapterPosition()).URL);
                intent.putExtra("mapX",dataSet.get(holder.getAdapterPosition()).mapX);
                intent.putExtra("mapY",dataSet.get(holder.getAdapterPosition()).mapY);
                intent.putExtra("contentId",dataSet.get(holder.getAdapterPosition()).contentId);//primaryKey
                Log.d("RecyclerViewAdapter_SearchingList",
                        "Content id : " + dataSet.get(holder.getAdapterPosition()).contentId);
                Log.d("RecyclerViewAdapter_SearchingList",
                        "Content id is int? : " + dataSet.get(holder.getAdapterPosition()).contentId.getClass().getName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
//        Log.d("hong","dataSet.size(): " + dataSet.size());
        return dataSet.size();
//        return 5;
    }
}
