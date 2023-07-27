package com.serpider.service.megastream.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.api.ApiServer;
import com.serpider.service.megastream.databinding.FragmentProfileBinding;
import com.serpider.service.megastream.interfaces.Elements;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.User;
import com.serpider.service.megastream.util.DataSave;
import com.squareup.picasso.Picasso;
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

        if (dataSave.UserGetId(getContext()) == 0){
            mBinding.bodyPlzLogin.setVisibility(View.VISIBLE);
            mBinding.bodyProfile.setVisibility(View.GONE);
        }else {
            loadUserInfo();
        }
        mBinding.btnInfo.setOnClickListener(view1 -> Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_infoUserFragment));

        mBinding.btnSupport.setOnClickListener(view1 -> sheetSupport(getActivity()));

        mBinding.btnRoll.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.action_mainFragment_to_webFragment2);
            webFragment.titleWeb = "قوانین و مقررات";
            webFragment.urlWeb = Key.PRIVACY_POLICY;
        });

    }

    private void loadUserInfo() {
        
        requestUser = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);
        
        requestUser.getUserInfo(5).enqueue(new Callback<User>() {
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
                    mBinding.txtEmail.setText(user.getEmail());
                    Picasso.get().load(user.getProfile()).into(mBinding.imgVector);
                    mBinding.imgVector.setOnClickListener(view -> Elements.DialogPreImage(getActivity(), user.getProfile()));
                    Toast.makeText(getContext(), String.valueOf(user.getStatus()), Toast.LENGTH_SHORT).show();
                    if (user.getStatus() == 0){
                        mBinding.bodyProEmailApproval.setVisibility(View.VISIBLE);
                        mBinding.bodyProClub.setVisibility(View.GONE);
                    }else {
                        mBinding.bodyProEmailApproval.setVisibility(View.GONE);
                        mBinding.bodyProClub.setVisibility(View.VISIBLE);
                    }

                    mBinding.btnLogouAccount.setOnClickListener(view -> {
                        dataSave.UserIdSave(getContext(), 0);
                        mBinding.bodyPlzLogin.setVisibility(View.VISIBLE);
                        mBinding.bodyProfile.setVisibility(View.GONE);
                    });

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

    public static void sheetSupport(FragmentActivity activity) {
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
            String phoneNumber = "0911";
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            activity.startActivity(intent);
        });
        btnEmail.setOnClickListener(view1 -> {
            String emailAddress = "saeedghaoori1@gmail.com";
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailAddress));
            activity.startActivity(intent);
        });
        btnTelegram.setOnClickListener(view1 -> {
            String telegramId = "YOUR_TELEGRAM_ID";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/" + telegramId));
            activity.startActivity(intent);
        });
        btnTicket.setOnClickListener(view1 -> {
            sheetReport(activity);
        });

    }

    public static void sheetReport(FragmentActivity activity) {
        View view = activity.getLayoutInflater().inflate(R.layout.sheet_report, null);
        BottomSheetDialog reportSheet = new BottomSheetDialog(activity);
        reportSheet.setContentView(view);
        reportSheet.show();
    }

}