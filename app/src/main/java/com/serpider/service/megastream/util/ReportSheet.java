package com.serpider.service.megastream.util;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.serpider.service.megastream.R;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.databinding.SheetReportBinding;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportSheet extends DialogFragment {
    private SheetReportBinding mBinding;
    private ApiInterFace request;
    public ReportSheet() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = SheetReportBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<String> spinnerData = new ArrayList<>();
        spinnerData.add("پیشنهاد و انتقاد");
        spinnerData.add("تبلیغات");
        spinnerData.add("مشکل در پخش یا دانلود");
        spinnerData.add("اطلاعات غلط");
        spinnerData.add("مشکل کاربری");
        spinnerData.add("فراموشی رمزعبور و امنیت");
        spinnerData.add("اشتراک");
        spinnerData.add("محتوای نا مناسب و نابهتجار");
        spinnerData.add("همکاری با ما");
        spinnerData.add("سایر");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerData);
        spinnerAdapter.setDropDownViewResource(R.layout.dropdown_menu_popup_item);
        mBinding.spinnerReportType.setAdapter(spinnerAdapter);

        mBinding.btnSubmit.setOnClickListener(view1 -> {
            String subject = mBinding.edReportTitle.getText().toString().trim();
            String topic = "Spinner item";
            String message = mBinding.edReportDesc.getText().toString().trim();

            if (subject.isEmpty()) {
                mBinding.edReportTitle.setError("این فیلد را پر کنید");
            } else if (message.isEmpty()) {
                mBinding.edReportDesc.setError("این فیلد را پر کنید");
            }else {
                request(DataSave.UserGetId(getContext()), subject, topic, message);
            }

        });

    }

    private void request(int id, String subject, String topic, String message) {
        Toast.makeText(getContext(), "Request", Toast.LENGTH_SHORT).show();
        request = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);

        request.getUserReport(id, subject, topic, message).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                if (result.isStatus()) {
                    Toaster.success(getActivity(), result.getMessage(), Toast.LENGTH_LONG);
                    dismiss();
                }else {
                    Toaster.error(getActivity(), result.getMessage(), Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toaster.error(getActivity(), "خطای سمت سرور", Toast.LENGTH_LONG);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getDialog() != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            getDialog().getWindow().setLayout(width, height);
        }
    }
}