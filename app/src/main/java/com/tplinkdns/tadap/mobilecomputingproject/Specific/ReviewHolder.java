package com.tplinkdns.tadap.mobilecomputingproject.Specific;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tplinkdns.tadap.mobilecomputingproject.R;

public class ReviewHolder extends RecyclerView.ViewHolder{

    TextView User, Content;

    public ReviewHolder(@NonNull View itemView) {
        super(itemView);

        User = itemView.findViewById(R.id.Name);
        Content = itemView.findViewById(R.id.Content);

    }
}
