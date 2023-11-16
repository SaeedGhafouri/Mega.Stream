package com.serpider.service.megastream.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.Genre;
import com.serpider.service.megastream.model.Movie;
import com.serpider.service.megastream.model.Season;
import com.serpider.service.megastream.model.Section;
import com.serpider.service.megastream.util.SnackBoard;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.MyViewHolder> {

    Context context;
    List<Season> data;
    FragmentActivity fragmentActivity;
    RecyclerView recyclerSection;
    ApiInterFace requestSection;
    List<Section> listSection = new ArrayList<>();
    SectionAdapter sectionAdapter;

    public SeasonAdapter(Context context, List<Season> data, FragmentActivity fragmentActivity) {
        this.context = context;
        this.data = data;
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public SeasonAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_season, parent, false);
        return new SeasonAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonAdapter.MyViewHolder holder, int position) {
        holder.txtTitle.setText(data.get(position).getTitle());
        String value = String.valueOf(data.get(position).getSort());
        if (value.length() == 1) {
            holder.txtValue.setText("0"+value);
        }else {
            holder.txtValue.setText(value);
        }

        holder.itemView.setOnClickListener(view -> loadSection(fragmentActivity, data.get(position).getTitle() ,data.get(position).getId()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtValue;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.itemSeasonTitle);
            txtValue = itemView.findViewById(R.id.itemSeasonValue);
        }
    }

    private void loadSection(FragmentActivity fragmentActivity, String titleValue, int IdSeason) {
        View view = fragmentActivity.getLayoutInflater().inflate(R.layout.sheet_section, null);
        BottomSheetDialog sheetSection = new BottomSheetDialog(fragmentActivity);
        sheetSection.setContentView(view);
        sheetSection.show();

        TextView titleSeason;
        titleSeason = sheetSection.findViewById(R.id.sheetSectionTitle);
        recyclerSection = sheetSection.findViewById(R.id.recyclerSection);

        titleSeason.setText(titleValue);
        requestSection = ApiClinent.getApiClinent(fragmentActivity, Key.BASE_URL).create(ApiInterFace.class);

        recyclerSection.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(fragmentActivity, 1, GridLayoutManager.VERTICAL, false);
        recyclerSection.setLayoutManager(layoutManager);

        requestSection.getSerialSection(IdSeason).enqueue(new Callback<List<Section>>() {
            @Override
            public void onResponse(Call<List<Section>> call, Response<List<Section>> response) {
                listSection = response.body();
                sectionAdapter = new SectionAdapter(fragmentActivity.getApplicationContext(), listSection, fragmentActivity);
                recyclerSection.setAdapter(sectionAdapter);
            }

            @Override
            public void onFailure(Call<List<Section>> call, Throwable t) {
                SnackBoard.show(fragmentActivity,"خطای سمت سرور", 0);
            }
        });


    }

}
