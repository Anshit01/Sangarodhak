package com.hashbash.sangarodhak.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hashbash.sangarodhak.R;

public class FragmentDonate extends Fragment {

    private final static int UPI_PAYMENT = 34;

    private Intent payIntent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_donate, container, false);

        view.findViewById(R.id.google_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getString(R.string.google_pay_package_name));
            }
        });

        view.findViewById(R.id.paytm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(getString(R.string.paytm_package_name));
            }
        });

        view.findViewById(R.id.phone_pe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDialog(getString(R.string.phone_pe_package_name));
                showDialog("Others");
            }
        });

        return view;
    }

    private void showDialog(final String type) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_payment);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.findViewById(R.id.confirm_payment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = ( (EditText) (dialog.findViewById(R.id.amount_to_pay))).getText().toString();

                if(amount.equals("0") || amount.isEmpty()){
                    ( (EditText) (dialog.findViewById(R.id.amount_to_pay))).setError("Amount cannot be zero");
                }
                else
                    payMoney(type, amount);
            }
        });
        dialog.show();
    }

    private void payMoney(String type, String amount) {
        Uri uri = new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", getString(R.string.pm_cares_account_upi))
                .appendQueryParameter("pn", getString(R.string.pm_cares_account_name))
                .appendQueryParameter("am", amount)
                .appendQueryParameter("tn", "My donation towards PM Cares")
                .appendQueryParameter("cu", "INR")
                .build();

        payIntent = new Intent(Intent.ACTION_VIEW);
        payIntent.setData(uri);
        if (!type.equals("Others")) {
            payIntent.setPackage(type);
            startActivityForResult(payIntent, UPI_PAYMENT);
        } else {
            Intent chooser = Intent.createChooser(payIntent, "Pay with");

            if (null != chooser.resolveActivity(getActivity().getPackageManager())) {
                startActivityForResult(chooser, UPI_PAYMENT);
            } else {
                Toast.makeText(getContext(), "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
