package com.serpider.service.megastream.ui.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.databinding.FragmentLoginBinding;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.Result;
import com.serpider.service.megastream.model.User;
import com.serpider.service.megastream.util.Connection;
import com.serpider.service.megastream.util.DataSave;
import com.serpider.service.megastream.util.SnackBoard;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    FragmentLoginBinding mBinding;
    ApiInterFace request;
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
        mBinding.btnLoginClose.setOnClickListener(view1 -> getActivity().onBackPressed());
        mBinding.btnLoginToSign.setOnClickListener(view1 -> toggleForm(1, true));
        mBinding.btnSignToLogin.setOnClickListener(view1 -> toggleForm(2, true));

        mBinding.btnLogin.setOnClickListener(view1 -> userLogin(view1));
        mBinding.btnSignup.setOnClickListener(view1 -> userSignup());
       /* mBinding.btnLoginClose.setOnClickListener(view1 -> {
            if (new Connection().isNetwork(getActivity())) {
                new Connection().showDialog(getActivity());
            }else {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainFragment);
            }
        });*/

        mBinding.btnLoginWithGoogle.setOnClickListener(view1 -> {
            SnackBoard.show(getActivity(), "این گزینه درحال حاضر فعال نمی باشد", 0);
        });
    }
    private void userLogin(View view) {
        request = ApiClinent.getApiClinent(getActivity(),Key.BASE_URL).create(ApiInterFace.class);

        String username = mBinding.edLoginUsername.getText().toString().trim();
        String password = mBinding.edLoginPassword.getText().toString().trim();

        if (username.isEmpty() || username.length() < 5) {
            SnackBoard.show(getActivity(),"نام کاربری باید حداقل 4 کارکتر باشد", 0);
        }else if (password.isEmpty() || password.length() < 8){
            SnackBoard.show(getActivity(),"رمز عبور باید حداقل 8 کارکتر باشد", 0);
        }else {
            request.userLogin(username, password).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    user = response.body();
                    if (user.isResult()){
                        SnackBoard.show(getActivity(),user.getMessage(), 0);
                        DataSave dataSave = new DataSave();
                        dataSave.UserIdSave(getContext(), user.getId());
                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainFragment);
                    }else {
                        SnackBoard.show(getActivity(),user.getMessage(), 0);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    SnackBoard.show(getActivity(),"خطای سمت سرور", 0);
                }
            });
        }

    }

    private void userSignup() {
        request = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);

        /*Init Strign from edit text*/
        String userName = mBinding.edUsername.getText().toString().trim();
        String userNick = mBinding.edNickname.getText().toString().trim();
        String userEmail = mBinding.edEmail.getText().toString().trim();
        String userPassword = mBinding.edPassword.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        if (userEmail.isEmpty() && userNick.isEmpty() && userEmail.isEmpty() && userPassword.isEmpty()) {
            mBinding.edUsername.setError("این فیلد را پر کنید");
            mBinding.edNickname.setError("این فیلد را پر کنید");
            mBinding.edEmail.setError("این فیلد را پر کنید");
            mBinding.edPassword.setError("این فیلد را پر کنید");
        }else if (userName.isEmpty()){
            mBinding.edUsername.setError("این فیلد را پر کنید");
        } else if (userNick.isEmpty()){
            mBinding.edNickname.setError("این فیلد را پر کنید");
        }else if (userEmail.isEmpty()){
            mBinding.edEmail.setError("این فیلد را پر کنید");
        }else if (userPassword.isEmpty()){
            mBinding.edPassword.setError("این فیلد را پر کنید");
        } else {
            if (userName.length() < 5){
                mBinding.edUsername.setError("حداقل پنج کارکتر");
                SnackBoard.show(getActivity(), "نام کاربری باید حداقل هشت کارکتر باشد", 0);
            }else if(Character.isDigit(userName.charAt(0))) {
                mBinding.edUsername.setError("حرف اول نباید عدد باشد");
                SnackBoard.show(getActivity(), "حرف اول نام کاربری نباید با عدد شروع شود", 0);
            } else if (!userEmail.matches(emailPattern)) {
                mBinding.edEmail.setError("ایمیل وارد شده نامعتبر است");
                SnackBoard.show(getActivity(), "ایمیل وارد شده نامعتبر است", 0);
            }else {
                request.getUserSignup(userName, userNick, "0", userEmail, userPassword, "0", "127.0.0.1").enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        user = response.body();
                        if (user.isResult()) {
                            //sheetOtp(user.getOtp(), user.getId());
                            SnackBoard.show(getActivity(),user.getMessage(), 1);
                            DataSave dataSave = new DataSave();
                            dataSave.UserIdSave(getContext(), user.getId());
                            Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_mainFragment);
                        }else {
                            SnackBoard.show(getActivity(),user.getMessage(), 0);
                        }
                    }

                    @Override
                    public void onFailure(Call <User> call, Throwable t) {
                        SnackBoard.show(getActivity(),"خطای سمت سرور، دوباره تلاش کنید", 0);
                    }
                });
            }
        }
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

    private void sheetOtp(int otpCode, int id_user) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.sheet_otp, null);
        BottomSheetDialog otpSheet = new BottomSheetDialog(getActivity());
        otpSheet.setContentView(view);
        otpSheet.show();

        PinView edPin = view.findViewById(R.id.edPin);

        edPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String edCode = charSequence.toString();
                int edCodeInt = Integer.parseInt(edCode);
                if (charSequence.length() == 5) {
                    Log.d("otp", edCodeInt + " = " + otpCode);
                    if (edCodeInt == otpCode){
                        submitAccount(id_user, 1);
                    }else {
                        Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void submitAccount(int id, int status) {
        request = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);

        request.getUserSubmit(id, status).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });


    }

}