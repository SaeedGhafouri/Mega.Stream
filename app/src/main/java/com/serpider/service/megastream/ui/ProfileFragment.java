package com.serpider.service.megastream.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.databinding.FragmentProfileBinding;
import com.serpider.service.megastream.interfaces.Elements;
import com.serpider.service.megastream.model.User;
import com.serpider.service.megastream.util.DataSave;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding mBinding;
    ApiInterFace requestUser;

    private DataSave dataSave = new DataSave();
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

        mBinding.btnFavorites.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_favoritesFragment));

        mBinding.btnProfileLogin.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_loginFragment));

        if (dataSave.UserGetId(getContext()).isEmpty()){
            mBinding.bodyPlzLogin.setVisibility(View.VISIBLE);
            mBinding.bodyProfile.setVisibility(View.GONE);
        }

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
                    /*if (user.getUser_nickname().isEmpty()) {
                        mBinding.txtNickName.setVisibility(View.GONE);
                    }else {
                        mBinding.txtNickName.setText(user.getUser_nickname());
                    }*/
                    mBinding.txtEmail.setText(user.getUser_email());
                    Picasso.get().load(user.getUser_vector()).into(mBinding.imgVector);
                    mBinding.imgVector.setOnClickListener(view -> Elements.DialogPreImage(getActivity(), user.getUser_vector()));

                    if (user.getUser_status().equals("0")){
                        mBinding.bodyProEmailApproval.setVisibility(View.VISIBLE);
                        mBinding.bodyProClub.setVisibility(View.GONE);
                    }

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