package com.serpider.service.megastream;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.databinding.FragmentResetPasswordBinding;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.Result;
import com.serpider.service.megastream.util.Toaster;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordFragment extends Fragment {
    private FragmentResetPasswordBinding binding;
    private ApiInterFace request;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        request = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);
        binding.btnBack.setOnClickListener(view1 -> getActivity().onBackPressed());


        binding.btnSubmit.setOnClickListener(view1 -> checkInput());

    }

    private void checkInput() {
        String edInput = binding.edLoginUsername.getText().toString().trim();
        String type;
        if (!edInput.isEmpty()){
            request.getUserResetPass(edInput).enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    Result result = response.body();
                    if (result.isStatus()){
                        Toaster.success(getActivity(), "کد تایید برای ایمیل شما ارسال شد", Toast.LENGTH_LONG);
                        sheetOtp(Integer.parseInt(result.getOtp()));
                    }else {
                        Toaster.error(getActivity(), "کاربری با این اطلاعات یافت نشد", Toast.LENGTH_LONG);
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {

                }
            });
        }


    }

    private boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void sheetOtp(int otpCode) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.sheet_otp, null);
        BottomSheetDialog otpSheet = new BottomSheetDialog(getActivity());
        otpSheet.setContentView(view);
        otpSheet.setCancelable(false);
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
}