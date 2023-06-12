package com.serpider.service.megastream.ui;

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
import com.serpider.service.megastream.databinding.FragmentProfileBinding;
import com.serpider.service.megastream.model.Network;
import com.serpider.service.megastream.model.User;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding mBinding;
    ApiInterFace requestUser;

    private Network.DataSave dataSave = new Network.DataSave();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentProfileBinding.inflate(inflater, container, false);
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
                    mBinding.txtUsername.setText(user.getUser_name());
                    mBinding.txtEmail.setText(user.getUser_email());
                    Picasso.get().load(user.getUser_vector()).into(mBinding.imgVector);
                    Toast.makeText(getActivity(), user.getUser_name(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), user.getMESSAGE(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}