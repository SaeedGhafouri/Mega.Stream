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
import com.serpider.service.megastream.adapter.GropingAdapter;
import com.serpider.service.megastream.adapter.ItemAdapter;
import com.serpider.service.megastream.databinding.FragmentDownloaderBinding;
import com.serpider.service.megastream.model.Download;
import com.serpider.service.megastream.model.Film;

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
        downloadList.add(new Download("Joker", "2020", "122", "https://cdn.downloadefilm.ir/images/d702e2a0-14a2-11ee-a9f4-29d00647f224.jpg", "https://dl.moviezland.ir/movies/collections/Extraction/Extraction.2.2023.mp4", 10));
        downloadList.add(new Download("Marvel 2022", "2021", "32", "https://cdn.downloadefilm.ir/images/d702e2a0-14a2-11ee-a9f4-29d00647f224.jpg","https://dl.moviezland.ir/movies/collections/Extraction/Extraction.2.2023.mp4",75

        ));
        adapter = new DownloadAdapter(getActivity(), downloadList);
        binding.recyclerDownload.setAdapter(adapter);

        binding.recyclerDownload.setAdapter(adapter);

    }
}