package com.serpider.service.megastream.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.serpider.service.megastream.R;
import com.serpider.service.megastream.adapter.CommentAdapter;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.databinding.FragmentCommentBinding;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.Comment;
import com.serpider.service.megastream.util.DataSave;
import com.serpider.service.megastream.util.SnackBoard;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFragment extends Fragment {
    ApiInterFace requestComment, requestAddComment;
    CommentAdapter commentAdapter;
    List<Comment> listComment = new ArrayList<>();
    RecyclerView recyclerComment;
    FragmentCommentBinding mBinding;
    public int itemId;
    private CountDownTimer countDownTimer;
    private final long startTimeInMillis = 10000;
    private long timeLeftInMillis = startTimeInMillis;
    private boolean isSend = true;
    public Handler handler = new Handler();

    private EditText edComment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /*if (edComment != null) {
            edComment.post(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edComment, InputMethodManager.SHOW_IMPLICIT);
                    edComment.requestFocus();
                }
            });
        } else {
            Log.e("YourTag", "edComment is null");
        }*/
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
        mBinding.btnBack.setOnClickListener(view1 -> getActivity().onBackPressed());

        mBinding.edComment.setText("saed");

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("DETAILS_ITEM", Context.MODE_PRIVATE);
        int idUnique = sharedPreferences.getInt("ID_ITEM", 0);

        Log.d("id", idUnique+"");
        loadComment(idUnique);

        mBinding.btnSend.setOnClickListener(view1 -> {
            if (isSend) {
                if (mBinding.edComment.getText().toString().trim().length() > 1) {
                    addComment(DataSave.UserGetId(getContext()), idUnique);
                }else {
                    SnackBoard.show(getActivity(), "لطفا نظر خود را وارد کنید", 0);
                }
            }
        });
        mBinding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadComment(idUnique);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.refresh.setRefreshing(false);
                    }
                },2000);

            }
        });

        mBinding.btnLogin.setOnClickListener(view1 -> Navigation.findNavController(view).navigate(R.id.action_commentFragment_to_loginFragment));

        if (DataSave.UserGetId(getContext()) == 0) {
            mBinding.bodyEmpyUser.setVisibility(View.VISIBLE);
            mBinding.bodyEdComment.setVisibility(View.GONE);
        }else {
            mBinding.bodyEmpyUser.setVisibility(View.GONE);
            mBinding.bodyEdComment.setVisibility(View.VISIBLE);
        }

    }

    private void timer() {
        countDownTimer = new CountDownTimer(startTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                mBinding.btnSend.setVisibility(View.VISIBLE);
                mBinding.txtTimerComment.setVisibility(View.GONE);
                isSend = true;
            }

        }.start();
    }

    private void addComment(int user_id, int item_id) {
        mBinding.btnSend.setVisibility(View.GONE);
        mBinding.txtTimerComment.setVisibility(View.VISIBLE);
        timer();
        String msg = mBinding.edComment.getText().toString().trim();

        requestAddComment = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);
        requestAddComment.getCommentAdd(user_id, item_id, msg).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Comment comment = response.body();
                isSend = false;
                mBinding.edComment.setText("");
                if (comment.isStatus()) {
                    SnackBoard.show(getActivity(), comment.getMessage(), 1);
                }else {
                    SnackBoard.show(getActivity(), comment.getMessage(), 0);
                }
            }
            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                isSend = false;
                SnackBoard.show(getActivity(), "خطای سمت سرور", 0);
            }
        });
    }
    private void loadComment(int id) {
        requestComment = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);
        recyclerComment = mBinding.recyclerComment;
        recyclerComment.setHasFixedSize(true);
        GridLayoutManager layoutManager =
                new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerComment.setLayoutManager(layoutManager);
        requestComment.getComment(id).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                mBinding.loader.setVisibility(View.GONE);
                listComment = response.body();
                commentAdapter = new CommentAdapter(getActivity(), listComment);
                recyclerComment.setAdapter(commentAdapter);
                if (listComment.size() == 0) {
                    mBinding.bodyEmpty.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                mBinding.bodyEmpty.setVisibility(View.VISIBLE);
                mBinding.loader.setVisibility(View.GONE);
            }
        });

    }

    /*Timer*/
    private void updateTimer() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        mBinding.txtTimerComment.setText(timeLeftFormatted);
    }
}