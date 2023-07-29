package com.serpider.service.megastream.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.serpider.service.megastream.adapter.CommentAdapter;
import com.serpider.service.megastream.adapter.ItemAdapter;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.databinding.FragmentCommentBinding;
import com.serpider.service.megastream.model.Comment;
import com.serpider.service.megastream.model.CommentPOJO;
import com.serpider.service.megastream.model.Film;
import com.serpider.service.megastream.model.Result;
import com.serpider.service.megastream.util.DataSave;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFragment extends Fragment {
    ApiInterFace requestComment;
    CommentAdapter commentAdapter;
    List<Comment> listComment = new ArrayList<>();
    RecyclerView recyclerComment;
    FragmentCommentBinding mBinding;
    public String itemId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentCommentBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadComment();
        DataSave dataSave = new DataSave();
        mBinding.btnSend.setOnClickListener(view1 -> addComment("12", "13"));

    }

    private void addComment(String user_id, String item_id) {
        String msg = mBinding.edComment.getText().toString().trim();

        requestComment = ApiClinent.getApiClinent(getActivity(), ApiServer.urlData()).create(ApiInterFace.class);

        requestComment.getAddComment(user_id, item_id, msg).enqueue(new Callback<CommentPOJO>() {
            @Override
            public void onResponse(Call<CommentPOJO> call, Response<CommentPOJO> response) {
                CommentPOJO result = response.body();
            }

            @Override
            public void onFailure(Call<CommentPOJO> call, Throwable t) {

            }
        });


    }

    private void loadComment() {

        requestComment = ApiClinent.getApiClinent(getActivity(), ApiServer.urlData()).create(ApiInterFace.class);
        recyclerComment = mBinding.recyclerComment;
        recyclerComment.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerComment.setLayoutManager(layoutManager);
        requestComment.getComment().enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                listComment = response.body();
                commentAdapter = new CommentAdapter(getActivity().getApplicationContext(), listComment, getActivity());
                recyclerComment.setAdapter(commentAdapter);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
            }
        });

    }
}