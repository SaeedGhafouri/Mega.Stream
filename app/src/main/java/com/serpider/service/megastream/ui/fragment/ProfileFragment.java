package com.serpider.service.megastream.ui.fragment;

import static com.serpider.service.megastream.util.DataSave.SUP_EMAIL;
import static com.serpider.service.megastream.util.DataSave.SUP_PHONE;
import static com.serpider.service.megastream.util.DataSave.SUP_TELEGRAM;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.databinding.FragmentProfileBinding;
import com.serpider.service.megastream.databinding.SheetReportBinding;
import com.serpider.service.megastream.interfaces.Elements;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.User;
import com.serpider.service.megastream.util.DataSave;
import com.serpider.service.megastream.util.ReportSheet;
import com.serpider.service.megastream.util.SnackBoard;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding mBinding;
    ApiInterFace requestUser;
    private WebFragment webFragment;

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
        mBinding.btnFavorites.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_favoritesFragment));

        mBinding.btnProfileLogin.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_loginFragment));
        Toast.makeText(getActivity(), String.valueOf(dataSave.UserGetId(getActivity())), Toast.LENGTH_SHORT).show();
        if (dataSave.UserGetId(getContext()) == 0){
            mBinding.bodyPlzLogin.setVisibility(View.VISIBLE);
            mBinding.bodyProfile.setVisibility(View.GONE);
        }else {
            loadUserInfo();
        }

        mBinding.btnSupport.setOnClickListener(view1 -> sheetSupport(getActivity()));

        mBinding.btnRoll.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_webFragment2);
            webFragment.titleWeb = "قوانین و مقررات";
            webFragment.urlWeb = Key.PRIVACY_POLICY;
        });

        mBinding.btnSettings.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_settingsFragment));

    }

    private void loadUserInfo() {
        
        requestUser = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);
        
        requestUser.getUserDetail(dataSave.UserGetId(getContext())).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (user.isResult()){
                    new Handler().postDelayed(() -> {
                        mBinding.loader.setVisibility(View.GONE);
                        mBinding.scrollProfile.setVisibility(View.VISIBLE);
                    },1000);

                    //SnackBoard.show(getActivity(),"اطلاعات دریافت شد", 1);
                    mBinding.txtUsername.setText(user.getUsername());
                    if (user.getNickname().isEmpty()) {
                        mBinding.txtNickName.setVisibility(View.GONE);
                    }else {
                        mBinding.txtNickName.setText(user.getNickname());
                    }
                    mBinding.txtEmail.setText(user.getEmail());
                    Glide.with(getContext()).load(user.getVector()).into(mBinding.imgVector);

                    mBinding.imgVector.setOnClickListener(view -> Elements.DialogPreImage(getActivity(), user.getVector()));

                    if (user.getEmail_verfy() == 0){
                        mBinding.bodyProEmailApproval.setVisibility(View.VISIBLE);
                        mBinding.bodyProClub.setVisibility(View.GONE);
                    }else {
                        mBinding.bodyProEmailApproval.setVisibility(View.GONE);
                        mBinding.bodyProClub.setVisibility(View.VISIBLE);
                    }

                    mBinding.btnDownload.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_downloaderFragment));

                    mBinding.btnLogouAccount.setOnClickListener(view -> {
                        dataSave.UserIdSave(getContext(), 0);
                        mBinding.bodyPlzLogin.setVisibility(View.VISIBLE);
                        mBinding.bodyProfile.setVisibility(View.GONE);
                    });

                }else {
                    Toast.makeText(getActivity(), "noch", Toast.LENGTH_SHORT).show();
                    mBinding.loader.setVisibility(View.GONE);
                    mBinding.scrollProfile.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                mBinding.loader.setVisibility(View.GONE);
                mBinding.scrollProfile.setVisibility(View.VISIBLE);
            }
        });
    }

    public void sheetSupport(FragmentActivity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.sheet_support, null);
        BottomSheetDialog supportSheet = new BottomSheetDialog(activity);
        supportSheet.setContentView(view);
        supportSheet.show();
        LinearLayout btnTel, btnEmail, btnTelegram, btnTicket;
        btnTel = supportSheet.findViewById(R.id.btnTel);
        btnEmail = supportSheet.findViewById(R.id.btnEmail);
        btnTelegram = supportSheet.findViewById(R.id.btnTelegram);
        btnTicket = supportSheet.findViewById(R.id.btnTicket);

        btnTel.setOnClickListener(view1 -> {
            String phoneNumber = DataSave.getSharedPreferences(getActivity()).getString(SUP_PHONE, "");
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            activity.startActivity(intent);
        });
        btnEmail.setOnClickListener(view1 -> {
            String emailAddress = DataSave.getSharedPreferences(getActivity()).getString(SUP_EMAIL, "");
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailAddress));
            activity.startActivity(intent);
        });
        btnTelegram.setOnClickListener(view1 -> {
            String telegramId = DataSave.getSharedPreferences(getActivity()).getString(SUP_TELEGRAM, "");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/" + telegramId));
            activity.startActivity(intent);
        });
        btnTicket.setOnClickListener(view1 -> {
            ReportSheet reportSheet = new ReportSheet();
            reportSheet.show(getChildFragmentManager(), reportSheet.getTag());
        });

    }
}