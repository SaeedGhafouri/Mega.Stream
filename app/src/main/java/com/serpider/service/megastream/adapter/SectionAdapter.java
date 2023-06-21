package com.serpider.service.megastream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.model.Season;
import com.serpider.service.megastream.model.Section;
import com.serpider.service.megastream.model.Serial_Play;
import com.serpider.service.megastream.ui.DetailsFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.MyViewHolder> {

    Context context;
    List<Section> data;
    FragmentActivity activity;

    List<Serial_Play> serialPlayList = new ArrayList<>();
    RecyclerView recyclerSerialPlay;
    ApiInterFace requestSerialPlay;
    QualitySerialAdapter qualitySerialAdapter;


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
        holder.txtTitle.setText(data.get(position).getSection_title());
        String value = data.get(position).getSection_value();
        if (value.length() == 1) {
            holder.txtValue.setText("0"+value);
        }else {
            holder.txtValue.setText(value);
        }

        holder.itemView.setOnClickListener(view -> {
           qualitySheetSerial(data.get(position).getSection_unique());
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

    private void qualitySheetSerial(String unique) {
        View view = activity.getLayoutInflater().inflate(R.layout.sheet_quality, null);
        BottomSheetDialog QualitySheet = new BottomSheetDialog(activity);
        QualitySheet.setContentView(view);
        QualitySheet.show();
        requestSerialPlay = ApiClinent.getApiClinent(activity, ApiServer.urlData()).create(ApiInterFace.class);
        recyclerSerialPlay = view.findViewById(R.id.qualityRecycler);
        recyclerSerialPlay.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false);
        recyclerSerialPlay.setLayoutManager(layoutManager);
        requestSerialPlay.getSerialPlay(unique).enqueue(new Callback<List<Serial_Play>>() {
            @Override
            public void onResponse(Call<List<Serial_Play>> call, Response<List<Serial_Play>> response) {
                serialPlayList = response.body();
                qualitySerialAdapter = new QualitySerialAdapter(activity.getApplicationContext(), serialPlayList, activity);
                recyclerSerialPlay.setAdapter(qualitySerialAdapter);
            }
            @Override
            public void onFailure(Call<List<Serial_Play>> call, Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
