package com.serpider.service.megastream.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.adapter.SpinnerAdapter;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.databinding.FragmentDonateBinding;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.Donate;
import com.serpider.service.megastream.util.DataSave;
import com.serpider.service.megastream.util.SnackBoard;
import com.zarinpal.ZarinPalBillingClient;
import com.zarinpal.billing.purchase.Purchase;
import com.zarinpal.client.BillingClientStateListener;
import com.zarinpal.client.ClientState;
import com.zarinpal.provider.core.future.FutureCompletionListener;
import com.zarinpal.provider.core.future.TaskResult;
import com.zarinpal.provider.model.response.Receipt;

import java.text.DecimalFormat;
import java.util.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonateFragment extends Fragment {
    ApiInterFace requestDonate;
    private Donate donate;
    private long MainPrice;
    FragmentDonateBinding mBinding;
    private DecimalFormat decimalFormat;
    private String forText;
    private int donateId;

    private ZarinPalBillingClient client;

    private static final String TAG = "InAppBilling Sample: ";

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
        mBinding.btnBack.setOnClickListener(view1 -> getActivity().onBackPressed());
        loadDonate();
        setupSpinner();
    }

    private void loadDonate() {

        requestDonate = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);

        requestDonate.getDonateBy().enqueue(new Callback<Donate>() {
            @Override
            public void onResponse(Call<Donate> call, Response<Donate> response) {
                donate = response.body();
                if (response.isSuccessful()) {
                    mBinding.titleDonate.setText(donate.getTitle());
                    mBinding.descDonate.setText(donate.getDesc());
                    forText = donate.getTitle();
                    donateId = donate.getId();
                    Glide.with(getActivity()).load(donate.getBanner()).into(mBinding.bannerDonate);
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
        decimalFormat = new DecimalFormat("#,###");
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
                /*showPaymentSheet(donateId, DataSave.UserGetId(getContext()), generateInvoiceId(), forText, MainPrice);
                */
                if (MainPrice > 1000000000){
                    SnackBoard.show(getActivity(), "مبلغ وارد شده بیش از حد مجاز است", 2);
                    mBinding.edPrice.setError("مبلغ را اصلاح کنید");
                }else {
                    paymentZarinPal(MainPrice);
                }
            }
        });

        mBinding.edPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    mBinding.edPrice.removeTextChangedListener(this);
                    String originalText = editable.toString();
                    if (!originalText.isEmpty()) {
                        long longVal = Long.parseLong(originalText.replace(",", ""));
                        String formattedText = decimalFormat.format(longVal);
                        mBinding.edPrice.setText(formattedText);
                        mBinding.edPrice.setSelection(formattedText.length());
                        MainPrice = Long.parseLong(formattedText.replace("," , ""));
                    }
                } finally {
                    mBinding.edPrice.addTextChangedListener(this);
                }
            }
        });
    }

    private void paymentZarinPal(long price) {
        BillingClientStateListener stateListener = new BillingClientStateListener() {
            @Override
            public void onClientSetupFinished(@NonNull ClientState clientState) {

            }

            @Override
            public void onClientServiceDisconnected() {
                Log.v(TAG, "Billing client Disconnected");
                // When Service disconnects
            }
        };

        // Create the billing client instance
        client = ZarinPalBillingClient.newBuilder(getActivity())
                .enableShowInvoice()
                .setListener(stateListener)
                .setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                .build();

        // Set up the purchase
        Purchase purchase = Purchase.newBuilder()
                .asPaymentRequest("95cf8788-7866-428b-a301-a66b4a4a5099", price, "http://pluslux.xyz", price + " IRR Purchase")
                .build();

        client.launchBillingFlow(purchase, new FutureCompletionListener<Receipt>() {
            @Override
            public void onComplete(TaskResult<Receipt> task) {
                if (task.isSuccess()) {
                    Receipt receipt = task.getSuccess();
                    Log.v(TAG, receipt.getTransactionID());

                    // Display a Toast with the transaction ID
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(requireContext(), "Transaction ID: " + receipt.getTransactionID(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Handle the failure
                    task.getFailure().printStackTrace();
                }
            }
        });
    }

    private void showPaymentSheet(int dntId, int userId, String invoiceId, String forTxt, long price) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.sheet_payment_details, null);
        BottomSheetDialog paymentSheet = new BottomSheetDialog(getActivity());
        paymentSheet.setContentView(view);
        paymentSheet.show();
        TextView txtUserId, txtInvoiceId, txtFor, txtPrice;
        MaterialButton btnPayment;
        txtUserId = view.findViewById(R.id.txtUserId);
        txtInvoiceId = view.findViewById(R.id.txtInvoiceId);
        txtFor = view.findViewById(R.id.txtFor);
        txtPrice = view.findViewById(R.id.txtPrice);
        btnPayment = view.findViewById(R.id.btnPayment);

        txtUserId.setText(String.valueOf(userId));
        txtInvoiceId.setText(invoiceId);
        txtFor.setText(forTxt);
        txtPrice.setText(String.valueOf(price));


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

    private String generateInvoiceId() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(9);
        for (int i = 0; i < 9; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }
        return stringBuilder.toString();
    }

}