package com.example.sqldatabase;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.sqldatabase.R.id.txt_tencv;

public class CongViecAdapter extends RecyclerView.Adapter<CongViecAdapter.ViewHodler>  {
    private ArrayList<CongViec> arrayListCV = new ArrayList<>();
    private LayoutInflater inflater;
    private MainActivity context;

    public CongViecAdapter(ArrayList<CongViec> arrayListCV, MainActivity context) {
       this.arrayListCV = arrayListCV;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_congviec, viewGroup,false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler viewHodler, int i) {
        final CongViec congViec = arrayListCV.get(i);
        viewHodler.tenCV.setText(congViec.getTenCV());
        viewHodler.imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.dialogSua(congViec.getTenCV(),congViec.getId());
            }
        });
        viewHodler.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.dalogXoa(congViec.getTenCV(), congViec.getId());
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayListCV.size();
    }

    class ViewHodler extends RecyclerView.ViewHolder{

         TextView tenCV;
         ImageView imageViewDelete;
         ImageView imageEdit;
        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            tenCV = itemView.findViewById(txt_tencv);
            imageEdit = itemView.findViewById(R.id.add_sql);
            imageViewDelete = itemView.findViewById(R.id.delete_sql);
        }
    }


}
