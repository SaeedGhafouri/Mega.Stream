package com.serpider.service.megastream.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.databinding.FragmentInfoUserBinding;
import com.serpider.service.megastream.interfaces.Elements;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.User;
import com.serpider.service.megastream.util.DataSave;
import com.serpider.service.megastream.util.SnackBoard;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoUserFragment extends Fragment {
    private ApiInterFace request;
    private FragmentInfoUserBinding mBinding;
    private DataSave dataSave = new DataSave();
    private int viewBody;
    
    private String oldPassword;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentInfoUserBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.btnBack.setOnClickListener(view1 -> getActivity().onBackPressed());
        viewBody = getArguments().getInt("viewBody", 1);
        loadUserInfo();
        if (viewBody == 1) {
            mBinding.titleInfoAccount.setText("اطلاعات کاربری");
            mBinding.btnChangeInfo.setOnClickListener(view1 -> changeInfo());
        } else if (viewBody == 2) {
            mBinding.loader.setVisibility(View.GONE);
            mBinding.scrollBodyPassword.setVisibility(View.VISIBLE);
            mBinding.titleInfoAccount.setText("رمزعبور و امنیت");
            mBinding.btnChangePassword.setOnClickListener(view1 -> changePassword());
        }
    }

    private void loadUserInfo() {
        request = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);
        request.getUserDetail(dataSave.UserGetId(getContext())).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                new Handler().postDelayed(() -> {
                    mBinding.loader.setVisibility(View.GONE);
                    if (viewBody == 1){
                        mBinding.scrollBodyInfo.setVisibility(View.VISIBLE);
                    }else {
                        mBinding.scrollBodyPassword.setVisibility(View.VISIBLE);
                    }
                },1000);
                if (user.isResult()){
                    mBinding.edInfoUsername.setText(user.getUsername());
                    mBinding.edInfoEmail.setText(user.getEmail());
                    mBinding.edInfoNickName.setText(user.getNickname());
                    oldPassword = user.getPassword();
                    if (user.getVector().isEmpty()) {
                        Glide.with(getContext()).load(R.drawable.ic_profile_cicle).into(mBinding.imgVector);
                    }else {
                        Glide.with(getContext()).load(user.getVector()).into(mBinding.imgVector);
                    }

                    mBinding.imgVector.setOnClickListener(view -> {
                        if (!user.getVector().isEmpty()) {
                            Elements.DialogPreImage(getActivity(), user.getVector());
                        }
                    });
                }else {
                    mBinding.loader.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                mBinding.loader.setVisibility(View.GONE);
                getActivity().onBackPressed();
            }
        });
    }

    /*Change info*/
    private void changeInfo() {

        String nickname = mBinding.edInfoNickName.getText().toString().trim();
        String username = mBinding.edInfoUsername.getText().toString().trim();

        request.getUserEditInfo(dataSave.UserGetId(getActivity()), nickname, username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                getActivity().onBackPressed();
                if (user.isResult()){
                    SnackBoard.show(getActivity(),  user.getMessage(),1);
                }else {
                    SnackBoard.show(getActivity(), user.getMessage(),0);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                SnackBoard.show(getActivity(), "خطای سمت سرور، دقایقی بعد امتحان کنید",0);
            }
        });

    }

    /*Change password*/
    private void changePassword() {
        String old = oldPassword;
        String old_p = mBinding.edOldPassword.getText().toString().trim();
        String new_p = mBinding.edInfoNewPassword.getText().toString().trim();
        String try_p = mBinding.edInfoTryPassword.getText().toString().trim();

        if (!old_p.equals(old)) {
            SnackBoard.show(getActivity(),"رمز قدیمی صحیح نمی باشد", 0);
            mBinding.edOldPassword.setError("رمزعبور قدیمی صحیح نمی باشد");
        } else if (new_p.length() < 7) {
            SnackBoard.show(getActivity(),"رمزعبور جدید باید حداقل هشت کارکتر باشد", 0);
            mBinding.edInfoNewPassword.setError("رمزعبور جدید باید حداقل هشت کارکتر باشد");
        } else if (!new_p.equals(try_p)) {
            Log.d("paaa", new_p + " = " + try_p);
            SnackBoard.show(getActivity(),"تکرار رمزعبور صحیح نمی باشد", 0);
            mBinding.edInfoTryPassword.setError("تکرار رمزعبور صحیح نمی باشد");
        }else {
            sendRequestPass(dataSave.UserGetId(getContext()), new_p);
        }

    }
    private void sendRequestPass(int id, String password) {
        request = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);

        request.getUserSecurity(password, id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                getActivity().onBackPressed();
                if (user.isResult()){
                    SnackBoard.show(getActivity(),  user.getMessage(),1);
                }else {
                    SnackBoard.show(getActivity(), user.getMessage(),0);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                SnackBoard.show(getActivity(), "خطای سمت سرور، دقایقی بعد امتحان کنید",0);
            }
        });


    }
}