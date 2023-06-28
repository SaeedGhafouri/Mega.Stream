package com.serpider.service.megastream.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.transition.Fade;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.serpider.service.megastream.R;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.databinding.FragmentLoginBinding;
import com.serpider.service.megastream.model.Network;
import com.serpider.service.megastream.model.Result;
import com.serpider.service.megastream.model.User;
import com.serpider.service.megastream.util.Connection;
import com.serpider.service.megastream.util.DataSave;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    FragmentLoginBinding mBinding;
    ApiInterFace requestSignup;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentLoginBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.btnLoginToSign.setOnClickListener(view1 -> toggleForm(1, true));
        mBinding.btnSignToLogin.setOnClickListener(view1 -> toggleForm(2, true));

        mBinding.btnLogin.setOnClickListener(view1 -> userLogin(view1));
        mBinding.btnSignup.setOnClickListener(view1 -> userSignup());
        mBinding.btnLoginClose.setOnClickListener(view1 -> {
            if (new Connection().isNetwork(getActivity())) {
                new Connection().showDialog(getActivity());
            }else {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainFragment);
            }
        });
    }

    private void userLogin(View view) {
        requestSignup = ApiClinent.getApiClinent(getActivity(),ApiServer.urlData()).create(ApiInterFace.class);

        String username = mBinding.edLoginUsername.getText().toString().trim();
        String password = mBinding.edLoginPassword.getText().toString().trim();

        if (username.isEmpty() || username.length() < 5) {
            Toast.makeText(getActivity(), "نام کاربری باید حداقل 4 کارکتر باشد", Toast.LENGTH_SHORT).show();
        }else if (password.isEmpty() || password.length() < 8){
            Toast.makeText(getActivity(), "رمز عبور باید حداقل 8 کارکتر باشد", Toast.LENGTH_SHORT).show();
        }else {
            requestSignup.getUserLogin(username, password).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    user = response.body();
                    if (user.isSTATUS()){
                        Toast.makeText(getActivity(), user.getMESSAGE(), Toast.LENGTH_SHORT).show();
                        DataSave dataSave = new DataSave();
                        dataSave.UserIdSave(getContext(), user.getUNIQUE_ID());
                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainFragment);
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

    private void userSignup() {
        requestSignup = ApiClinent.getApiClinent(getActivity(),ApiServer.urlData()).create(ApiInterFace.class);

        /*Init Strign from edit text*/
        String userName = mBinding.edUsername.getText().toString().trim();
        String userNick = mBinding.edNickname.getText().toString().trim();
        String userEmail = mBinding.edEmail.getText().toString().trim();
        String userPassword = mBinding.edPassword.getText().toString().trim();

        requestSignup.getUserSignUp(userName, userNick, "0", userEmail, userPassword, "0").enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user1 = response.body();

                if (user1.isSTATUS()) {
                    Toast.makeText(getActivity(), user1.getMESSAGE(), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), user1.getMESSAGE() , Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call <User> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleForm(int form, boolean show) {

        Transition transition = new Slide(Gravity.BOTTOM);
        transition.setDuration(600);
        if (form == 1) {
            transition.addTarget(mBinding.formSignup);
            TransitionManager.beginDelayedTransition(mBinding.formSignup, transition);
            mBinding.formSignup.setVisibility(show ? View.VISIBLE : View.GONE);
            mBinding.formLogin.setVisibility(show ? View.GONE : View.VISIBLE);
        }else {
            transition.addTarget(mBinding.formLogin);
            TransitionManager.beginDelayedTransition(mBinding.formLogin, transition);
            mBinding.formLogin.setVisibility(show ? View.VISIBLE : View.GONE);
            mBinding.formSignup.setVisibility(show ? View.GONE : View.VISIBLE);
        }


    }
}