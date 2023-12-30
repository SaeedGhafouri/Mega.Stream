package com.serpider.service.megastream;

import static com.serpider.service.megastream.util.Utility.applyDiscount;
import static com.serpider.service.megastream.util.Utility.formatPrice;

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
import android.widget.Toast;
import com.serpider.service.megastream.api.ApiClinent;
import com.serpider.service.megastream.api.ApiInterFace;
import com.serpider.service.megastream.databinding.FragmentPaymentBinding;
import com.serpider.service.megastream.interfaces.Key;
import com.serpider.service.megastream.model.Result;
import com.serpider.service.megastream.util.DataSave;

import com.serpider.service.megastream.util.Toaster;
import com.zarinpal.ZarinPalBillingClient;
import com.zarinpal.billing.purchase.Purchase;
import com.zarinpal.client.BillingClientStateListener;
import com.zarinpal.client.ClientState;
import com.zarinpal.provider.core.future.FutureCompletionListener;
import com.zarinpal.provider.core.future.TaskResult;
import com.zarinpal.provider.model.response.Receipt;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentFragment extends Fragment {
    private FragmentPaymentBinding binding;
    private int type, UserId, subId;
    private long pricePure, priceTax, discount, discount_percent;
    private String payTopic, payDesc;
    private ApiInterFace request;

    /*Zarin pal*/
    private String forText;
    private int donateId;
    private ZarinPalBillingClient client;
    private static final String TAG = "InAppBilling Sample: ";
    private long MainPrice;

    public static PaymentFragment newInstance(int type, int userId, int subId, long discount, long discount_percent,
                                              long pricePure, long priceTax,
                                              String payTopic, String payDesc) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putInt("TYPE", type);
        args.putInt("USER_ID", userId);
        args.putInt("SUB_ID", subId);
        args.putLong("DISCOUNT", discount);
        args.putLong("DISCOUNT_PERCENT", discount_percent);
        args.putLong("PRICE_PURE", pricePure);
        args.putLong("PRICE_TAX", priceTax);
        args.putString("PAY_TOPIC", payTopic);
        args.putString("PAY_DESC", payDesc);
        fragment.setArguments(args);
        return fragment;
    }
    private void retrieveArguments() {
        if (getArguments() != null) {
            type = getArguments().getInt("TYPE");
            UserId = getArguments().getInt("USER_ID");
            subId = getArguments().getInt("SUB_ID");
            discount = getArguments().getLong("DISCOUNT");
            discount_percent = getArguments().getLong("DISCOUNT_PERCENT");
            pricePure = getArguments().getLong("PRICE_PURE");
            priceTax = getArguments().getLong("PRICE_TAX");
            payTopic = getArguments().getString("PAY_TOPIC");
            payDesc = getArguments().getString("PAY_DESC");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        retrieveArguments();
        request = ApiClinent.getApiClinent(getActivity(), Key.BASE_URL).create(ApiInterFace.class);

        Toast.makeText(getActivity(), String.valueOf(UserId), Toast.LENGTH_SHORT).show();
        loadInfo();

        binding.btnApplyCoupon.setOnClickListener(view1 -> {
            String couponKey = binding.edCoupon.getText().toString().trim();
            if (couponKey.isEmpty()){
                binding.edCoupon.setError("کد تخفیف را وارد کنید");
            }else {
                checkCoupon(couponKey);
            }
        });

        binding.btnPayment.setOnClickListener(view1 -> requestPayment("zarin"));

        binding.btnDeleteCoupon.setOnClickListener(view1 -> {
            binding.btnDeleteCoupon.setVisibility(View.GONE);
            binding.btnApplyCoupon.setVisibility(View.VISIBLE);
            binding.txtPaymentPayable.setText(applyDiscount(pricePure, discount));
            binding.txtPaymentDiscount.setText(discount(discount));
            binding.edCoupon.setEnabled(true);
        });

    }

    private void requestPayment(String payment) {
        if (payment.equals("zarin")) {
            paymentZarinPal(pricePure);
        }

    }

    private void loadInfo() {
        binding.txtTopicPayment.setText(payTopic);
        binding.txtDescPayment.setText(payDesc);
        binding.txtPaymentNetPrice.setText(formatPrice(discount));
        binding.txtPaymentDiscount.setText(discount(discount_percent));
        binding.txtPaymentDiscount.setVisibility(View.GONE);
        binding.txtPaymentTax.setText(formatPrice(priceTax));
        binding.txtPaymentPayable.setText(formatPrice(pricePure));
    }

    private void checkCoupon(String coupon) {
        binding.btnApplyCoupon.setVisibility(View.GONE);
        binding.loaderCoupon.setVisibility(View.VISIBLE);
        request.getApplyCoupon(DataSave.UserGetId(getActivity()), subId, coupon).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                if (result.isStatus()) {
                    binding.edCoupon.setError(null);
                    binding.txtPaymentPayable.setText(applyDiscount(pricePure, result.getCpn_value()));
                    binding.txtPaymentDiscount.setText(discount(result.getCpn_value()));
                    binding.edCoupon.setEnabled(false);
                    binding.btnApplyCoupon.setVisibility(View.GONE);
                    binding.loaderCoupon.setVisibility(View.GONE);
                    binding.btnDeleteCoupon.setVisibility(View.VISIBLE);
                }else {
                    binding.edCoupon.setError(result.getMessage());
                    binding.btnApplyCoupon.setVisibility(View.VISIBLE);
                    binding.loaderCoupon.setVisibility(View.GONE);
                    binding.btnDeleteCoupon.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toaster.error(getActivity(), "خطای سمت سرور", Toast.LENGTH_LONG);
            }
        });

    }

    private String discount(long dis) {
        if (dis < 100) {
            return dis + "%";
        }else {
            return dis + " تومان";
        }
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
                .asPaymentRequest("95cf8788-7866-428b-a301-a66b4a4a5099", price, "http://pluslux.xyz/Maxuem/payment_status.php?status=1234", price + " IRR Purchase")
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

}