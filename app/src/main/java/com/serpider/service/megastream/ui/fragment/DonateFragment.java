package com.serpider.service.megastream.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.adapter.SpinnerAdapter;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.databinding.FragmentDonateBinding;
import com.serpider.service.megastream.model.Donate;
import com.serpider.service.megastream.model.Result;
import com.serpider.service.megastream.model.User;
import com.serpider.service.megastream.util.SnackBoard;
import com.zarinpal.ZarinPalBillingClient;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonateFragment extends Fragment {
    ApiInterFace requestDonate;
    private Donate donate;
    private long MainPrice;
    FragmentDonateBinding mBinding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentDonateBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadDonate();
        setupSpinner();
    }

    private void loadDonate() {

        requestDonate = ApiClinent.getApiClinent(getActivity(), ApiServer.urlData()).create(ApiInterFace.class);

        requestDonate.getDonates().enqueue(new Callback<Donate>() {
            @Override
            public void onResponse(Call<Donate> call, Response<Donate> response) {
                donate = response.body();
                if (response.isSuccessful()) {

                    mBinding.titleDonate.setText(donate.getTitle());
                    mBinding.descDonate.setText(donate.getCaption());
                    Glide.with(getActivity()).load(donate.getImage()).into(mBinding.bannerDonate);

                    runPeyment();

                }else {
                    SnackBoard.show(getActivity(),"دونیتی برای حمایت وجود ندارد", 0);
                }
            }
            @Override
            public void onFailure(Call<Donate> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void runPeyment() {

        mBinding.btnPayment.setEnabled(true);
        mBinding.btnPayment.setOnClickListener(view -> {
            String edText = mBinding.edPrice.getText().toString().trim();
            if (MainPrice == 0) {
                if (!edText.isEmpty()){
                    MainPrice = Long.parseLong(mBinding.edPrice.getText().toString().trim());
                }else {
                    SnackBoard.show(getActivity(), "لطفا مبلغ خود را وارد کنید", 2);
                    mBinding.edPrice.setError("مبلغ را وارد کنید");
                }
            }else {
                Toast.makeText(getActivity(), " " + MainPrice, Toast.LENGTH_SHORT).show();
                zarinPal(MainPrice);
            }


        });

    }

    private void setupSpinner() {

        String[] items = {"ده هزار تومان", "بیست هزار تومان", "پنجاه هزار تومان", "صد هزار تومان", "مبلغ دلخواه"};
        int[] icons = {R.drawable.img_10, R.drawable.img_20, R.drawable.img_50, R.drawable.img_100, R.drawable.img_custom};
        long[] prices = {10000, 20000, 50000, 100000, 0};
        SpinnerAdapter adapter = new SpinnerAdapter(getActivity(), items, icons, prices);
        mBinding.pricesSpinner.setAdapter(adapter);

        mBinding.pricesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (prices[position] == 0){
                    mBinding.edPrice.setVisibility(View.VISIBLE);
                    MainPrice = 0;
                }else {
                    mBinding.edPrice.setVisibility(View.GONE);
                    MainPrice = prices[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

    }

    private void zarinPal(long price) {

    }
}