package com.serpider.service.megastream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.PlayUrl;
import com.serpider.service.megastream.model.Section;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.MyViewHolder> {

    Context context;
    List<Section> data;
    FragmentActivity activity;

    List<PlayUrl> serialPlayList = new ArrayList<>();
    RecyclerView recyclerSerialPlay;
    ApiInterFace requestSerialPlay;
    QualityAdapter qualitySerialAdapter;


    public SectionAdapter(Context context, List<Section> data, FragmentActivity activity) {
        this.context = context;
        this.data = data;
        this.activity = activity;
    }

    @NonNull
    @Override
    public SectionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_section, parent, false);
        return new SectionAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionAdapter.MyViewHolder holder, int position) {
        holder.txtTitle.setText(data.get(position).getTitle());
        String value = String.valueOf(data.get(position).getSort());
        if (value.length() == 1) {
            holder.txtValue.setText("0"+value);
        }else {
            holder.txtValue.setText(value);
        }

        holder.itemView.setOnClickListener(view -> {
           qualitySheetSerial(data.get(position).getId());
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtValue, txtQuality;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.itemSectionTitle);
            txtValue = itemView.findViewById(R.id.itemSectionValue);
            txtQuality = itemView.findViewById(R.id.itemSectionQuality);
        }
    }

    private void qualitySheetSerial(int id) {
        View view = activity.getLayoutInflater().inflate(R.layout.sheet_quality, null);
        BottomSheetDialog QualitySheet = new BottomSheetDialog(activity);
        QualitySheet.setContentView(view);
        QualitySheet.show();
        requestSerialPlay = ApiClinent.getApiClinent(activity, Key.BASE_URL).create(ApiInterFace.class);
        recyclerSerialPlay = view.findViewById(R.id.qualityRecycler);
        recyclerSerialPlay.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false);
        recyclerSerialPlay.setLayoutManager(layoutManager);
        requestSerialPlay.getSerialUrl(id).enqueue(new Callback<List<PlayUrl>>() {
            @Override
            public void onResponse(Call<List<PlayUrl>> call, Response<List<PlayUrl>> response) {
                serialPlayList = response.body();
                qualitySerialAdapter = new QualityAdapter(activity.getApplicationContext(), serialPlayList, activity, "");
                recyclerSerialPlay.setAdapter(qualitySerialAdapter);
            }
            @Override
            public void onFailure(Call<List<PlayUrl>> call, Throwable t) {
            }
        });
    }

}
