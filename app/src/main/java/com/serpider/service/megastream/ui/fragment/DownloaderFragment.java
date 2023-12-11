package com.serpider.service.megastream.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serpider.service.megastream.adapter.DownloadAdapter;
import com.serpider.service.megastream.databinding.FragmentDownloaderBinding;
import com.serpider.service.megastream.model.Download;

import java.util.ArrayList;
import java.util.List;

public class DownloaderFragment extends Fragment {
    private FragmentDownloaderBinding binding;
    private RecyclerView recyclerView;
    private DownloadAdapter adapter;
    private List<Download> downloadList = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentDownloaderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnBack.setOnClickListener(view1 -> getActivity().onBackPressed());
        binding.recyclerDownload.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        binding.recyclerDownload.setLayoutManager(layoutManager);
        startDownload(1, "Saeed", "2022", "10", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6fizNzdu8Tjdouj3avaO4QmsHRHpQNHC4mG7NewjcVlVdJWpA", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6fizNzdu8Tjdouj3avaO4QmsHRHpQNHC4mG7NewjcVlVdJWpA");
    }

    private void startDownload(int id, String title, String year, String progress, String posterUrl, String videoUrl) {
        Download download = new Download(title, year, progress, posterUrl, videoUrl, 0);
        download.setId(id);
        downloadList.add(download);
        adapter.updateData(downloadList);
        adapter = new DownloadAdapter(getActivity(), downloadList);
        binding.recyclerDownload.setAdapter(adapter);
    }
}