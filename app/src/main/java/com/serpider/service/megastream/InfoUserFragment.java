package com.serpider.service.megastream;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.databinding.FragmentInfoUserBinding;
import com.serpider.service.megastream.interfaces.Elements;
import com.serpider.service.megastream.model.User;
import com.serpider.service.megastream.util.DataSave;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoUserFragment extends Fragment {
    private ApiInterFace requestUser;
    private FragmentInfoUserBinding mBinding;
    private DataSave dataSave = new DataSave();
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

        loadUserInfo();

    }

    private void loadUserInfo() {

        requestUser = ApiClinent.getApiClinent(getActivity(), ApiServer.urlData()).create(ApiInterFace.class);

        requestUser.getUser(dataSave.UserGetId(getContext())).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (user.isSTATUS()){
                    Toast.makeText(getActivity(), user.getMESSAGE(), Toast.LENGTH_SHORT).show();
                    Elements.Message(getActivity(),user.getMESSAGE(), "SUCCESS");
                    mBinding.edInfoUsername.setText(user.getUser_name());
                    mBinding.edInfoEmail.setText(user.getEmail());
                    mBinding.edInfoNickName.setText(user.getNickname());
                    Picasso.get().load(user.getProfile()).into(mBinding.imgVector);
                    mBinding.imgVector.setOnClickListener(view -> Elements.DialogPreImage(getActivity(), user.getProfile()));

                }else {
                    Elements.Message(getActivity(),user.getMESSAGE(), "ERROR");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Elements.Message(getActivity(),"خطای سمت سرور", "ERROR");
            }
        });
    }
}