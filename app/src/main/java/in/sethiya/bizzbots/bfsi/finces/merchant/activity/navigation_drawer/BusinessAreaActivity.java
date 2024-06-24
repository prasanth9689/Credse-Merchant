package in.sethiya.bizzbots.bfsi.finces.merchant.activity.navigation_drawer;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.adapter.AdapterHomeInvestments;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.adapter.AdapterHomeLoans;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.adapter.AdapterHomePayments;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.adapter.AdapterHomeSavings;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityBsinessAreaBinding;

public class BusinessAreaActivity extends AppCompatActivity {
    private ActivityBsinessAreaBinding binding;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBsinessAreaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadBusiness();
    }

    private void loadBusiness() {
        ArrayList<Integer> dashboardImage = new ArrayList<>(Arrays.asList(
                R.drawable.ic_report_new,
                R.drawable.ic_report_new,
                R.drawable.ic_report_new,
                R.drawable.ic_report_new,
                R.drawable.ic_report_new,
                R.drawable.ic_report_new,
                R.drawable.ic_report_new));

        ArrayList<String> dashboardName = new ArrayList<>(Arrays.asList(
                "DMR",
                "AEPS",
                "MPOS",
                "QR PAY" ,
                "Recharge",
                "BBPS" ,
                "Utility"
        ));
        binding.recyclerViewPayments.setLayoutManager(new GridLayoutManager(context, 3));
        binding.recyclerViewPayments.setHasFixedSize(true);
        binding.recyclerViewPayments.setItemViewCacheSize(20);
        AdapterHomePayments adapter = new AdapterHomePayments(context, dashboardImage, dashboardName);
        binding.recyclerViewPayments.setAdapter(adapter);

        loadSavings();
    }

    private void loadSavings() {
        ArrayList<Integer> dashboardImage = new ArrayList<>(Arrays.asList(
                R.drawable.ic_report_new,
                R.drawable.ic_report_new,
                R.drawable.ic_report_new,
                R.drawable.ic_report_new));

        ArrayList<String> dashboardName = new ArrayList<>(Arrays.asList(
                "DD",
                "RD",
                "Savings",
                "Gold"
        ));
        binding.recyclerViewSavings.setLayoutManager(new GridLayoutManager(context, 3));
        binding.recyclerViewSavings.setHasFixedSize(true);
        binding.recyclerViewSavings.setItemViewCacheSize(20);
        AdapterHomeSavings adapter = new AdapterHomeSavings(context, dashboardImage, dashboardName);
        binding.recyclerViewSavings.setAdapter(adapter);

        loadInvestments();
    }

    private void loadInvestments() {
        ArrayList<Integer> dashboardImage = new ArrayList<>(Arrays.asList(
                R.drawable.ic_report_new,
                R.drawable.ic_report_new));

        ArrayList<String> dashboardName = new ArrayList<>(Arrays.asList(
                "FD",
                "MIS"
        ));
        binding.recyclerViewInvestments.setLayoutManager(new GridLayoutManager(context, 3));
        binding.recyclerViewInvestments.setHasFixedSize(true);
        binding.recyclerViewInvestments.setItemViewCacheSize(20);
        AdapterHomeInvestments adapter = new AdapterHomeInvestments(context, dashboardImage, dashboardName);
        binding.recyclerViewInvestments.setAdapter(adapter);

        loadLoans();
    }

    private void loadLoans() {
        ArrayList<Integer> dashboardImage = new ArrayList<>(Arrays.asList(
                R.drawable.ic_report_new,
                R.drawable.ic_report_new,
                R.drawable.ic_report_new,
                R.drawable.ic_report_new));

        ArrayList<String> dashboardName = new ArrayList<>(Arrays.asList(
                "Gold",
                "Vehicle",
                "Personal",
                "Group"
        ));
        binding.recyclerViewLoans.setLayoutManager(new GridLayoutManager(context, 3));
        binding.recyclerViewLoans.setHasFixedSize(true);
        binding.recyclerViewLoans.setItemViewCacheSize(20);
        AdapterHomeLoans adapter = new AdapterHomeLoans(context, dashboardImage, dashboardName);
        binding.recyclerViewLoans.setAdapter(adapter);
    }
}