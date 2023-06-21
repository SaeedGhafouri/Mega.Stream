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
import com.serpider.service.megastream.databinding.FragmentDonateBinding;
import com.serpider.service.megastream.model.Donate;
import com.serpider.service.megastream.model.Result;
import com.serpider.service.megastream.model.User;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonateFragment extends Fragment {
    ApiInterFace requestDonate;
    private Donate donate;
    private String Price;
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


    }

    private void loadDonate() {

        requestDonate = ApiClinent.getApiClinent(getActivity(), ApiServer.urlData()).create(ApiInterFace.class);

        requestDonate.getDonate().enqueue(new Callback<Donate>() {
            @Override
            public void onResponse(Call<Donate> call, Response<Donate> response) {
                donate = response.body();
                if (response.isSuccessful()) {

                    mBinding.titleDonate.setText(donate.getDonate_title());
                    mBinding.descDonate.setText(donate.getDonate_desc());
                    Picasso.get().load(donate.getDonate_image()).into(mBinding.bannerDonate);

                    runPsyment();

                }else {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Donate> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void runPsyment() {

        mBinding.btnPayment.setEnabled(true);
        mBinding.btnPayment.setOnClickListener(view -> {
            String edPrice = mBinding.edPrice.getText().toString().trim();

            if (!edPrice.isEmpty()) {
                Price = edPrice;
            }else {
                final int id = (mBinding.radioGrpup).getCheckedRadioButtonId();

                switch (id) {
                    case R.id.price10:
                        Toast.makeText(getActivity(), "ده هزار تومان", Toast.LENGTH_SHORT).show();
                        break;
                }
            }



        });

    }
}