package com.serpider.service.megastream.ui.fragment;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.serpider.service.megastream.util.Loader;
import com.serpider.service.megastream.util.SnackBoard;

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    private static final int RC_READ_EXTERNAL_STORAGE = 1;
    private FragmentLoginBinding mBinding;
    private ApiInterFace request;
    private User user;
    private Uri selectedImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private MultipartBody.Part imagePart;
    private Loader loader;
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

        loader = new Loader(getActivity());

        mBinding.btnLogin.setOnClickListener(view1 -> userLogin(view1));
        mBinding.btnSignup.setOnClickListener(view1 -> userSignup());
       /* mBinding.btnLoginClose.setOnClickListener(view1 -> {
            if (new Connection().isNetwork(getActivity())) {
                new Connection().showDialog(getActivity());
            }else {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainFragment);
            }
        });*/
        mBinding.btnUploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
                if (EasyPermissions.hasPermissions(getActivity(), perms)) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
                } else {
                    EasyPermissions.requestPermissions(getActivity(), "برای دسترسی به حافظه اجازه را بدهید.", RC_READ_EXTERNAL_STORAGE, perms);
                }
            }
        });

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
            request.getUserLogin(username, password,  1).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    user = response.body();
                    if (user.isResult()){
                        SnackBoard.show(getActivity(),user.getMessage(), 1);
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
                if (selectedImageUri != null){
                    File file = new File(Objects.requireNonNull(getRealPathFromUri(selectedImageUri)));
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                    imagePart = MultipartBody.Part.createFormData("USER_VECTOR", file.getName(), requestFile);
                }
                loader.show();
                request.getUserSignup(
                        RequestBody.create(MediaType.parse("text/plain"), userName),
                        RequestBody.create(MediaType.parse("text/plain"), userNick),
                        RequestBody.create(MediaType.parse("text/plain"), "0"),
                        RequestBody.create(MediaType.parse("text/plain"), userEmail),
                        RequestBody.create(MediaType.parse("text/plain"), userPassword),
                        RequestBody.create(MediaType.parse("text/plain"), "0"),
                        RequestBody.create(MediaType.parse("text/plain"), "127.0.0.1"),
                        imagePart).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        user = response.body();
                        loader.close();
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
                        loader.close();
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.getData();
        }
        Glide.with(getActivity()).load(selectedImageUri).into(mBinding.imgProfile);
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
    }

}