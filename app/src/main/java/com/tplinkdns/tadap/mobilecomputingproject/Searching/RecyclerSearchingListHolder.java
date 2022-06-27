package com.tplinkdns.tadap.mobilecomputingproject.Searching;



import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tplinkdns.tadap.mobilecomputingproject.R;

import java.net.URL;

public class RecyclerSearchingListHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView name, address1,address2, call, URL;
    public androidx.constraintlayout.widget.ConstraintLayout layout;

    public RecyclerSearchingListHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageView);
        name = itemView.findViewById(R.id.campPlaceName);
        address1 =itemView.findViewById(R.id.campAddress1);
        address2 =itemView.findViewById(R.id.campAddress2);
        call = itemView.findViewById(R.id.campCall);
        layout = itemView.findViewById(R.id.recycler_camp);

    }
}
