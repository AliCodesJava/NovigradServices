package com.example.servicesnovigrad.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.servicesnovigrad.Address;
import com.example.servicesnovigrad.AdministratorAccount;
import com.example.servicesnovigrad.Branch;
import com.example.servicesnovigrad.R;
import com.example.servicesnovigrad.Service;
import com.example.servicesnovigrad.User;
import com.example.servicesnovigrad.WeeklySchedule;
import com.example.servicesnovigrad.listeners.BtnClickListener;

import java.util.ArrayList;
import java.util.Map;

public class BranchListAdapter extends ArrayAdapter<Branch> {

    private static class ViewHolder {
        private TextView branchName;
        private TextView branchAddress;
        private TextView branchAddress1;
        private Button evaluateBranchBtn;
        private Button applyServicesBtn;
        private Button infoBranchBtn;
        private RatingBar rating;
    }

    private BtnClickListener evaluateBranchListener;
    private BtnClickListener applyServicesListener;
    private BtnClickListener infoBranchListener;

    public BranchListAdapter(
            @NonNull Context context,
            @NonNull ArrayList<Branch> branches,
            BtnClickListener evaluateBranchListener,
            BtnClickListener applyServicesListener,
            BtnClickListener infoBranchListener
    ) {
        super(context, R.layout.branch_list_item, branches);
        this.evaluateBranchListener = evaluateBranchListener;
        this.applyServicesListener = applyServicesListener;
        this.infoBranchListener = infoBranchListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        BranchListAdapter.ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.branch_list_item,
                    parent, false);

            viewHolder = new BranchListAdapter.ViewHolder();
            viewHolder.rating = (RatingBar) convertView.findViewById(R.id.ratingBar);
            viewHolder.branchName = (TextView) convertView.findViewById(R.id.txtView_item_branch_name);
            viewHolder.branchAddress = (TextView) convertView.findViewById(R.id.txtView_item_branch_address);
            viewHolder.branchAddress1 = (TextView) convertView.findViewById(R.id.txtView_item_branch_address_1);
            viewHolder.evaluateBranchBtn = (Button) convertView.findViewById(R.id.btn_client_rate_branch);
            viewHolder.applyServicesBtn = (Button) convertView.findViewById(R.id.btn_client_add_services);
            viewHolder.infoBranchBtn = (Button) convertView.findViewById(R.id.btn_branch_schedule);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (BranchListAdapter.ViewHolder) convertView.getTag();
        }

        Branch branch = getItem(position);

        if(branch != null){
            Address address = branch.getAddress();
            WeeklySchedule schedule = branch.getSchedule();
            String addressTxt = String.format
                    (
                            "%s %s" + (address.getApartmentNumber() >= 0 ? " (Apt %s)": ""),
                            address.getStreetNumber(),
                            address.getStreetName(),
                            address.getApartmentNumber() >= 0 ? Integer.toString(address.getApartmentNumber()) : ""
                    );

            String addressTxt1 = String.format
                    (
                            "%s, %s",
                            address.getCity(),
                            address.getPostalCode()
                    );

            // Add branch data to placeholder
            viewHolder.branchName.setText("Branch " + (position + 1));
            viewHolder.branchAddress.setText(addressTxt);
            viewHolder.branchAddress1.setText(addressTxt1);
            int i = 0;
            int sum = 0;
            for (Map.Entry<String,Float> e:
                 branch.getRatings().entrySet()) {
                i++;
                sum+=e.getValue();
            }
            if(i!=0)
                viewHolder.rating.setRating(sum);

            // Add click listeners implementations
            viewHolder.evaluateBranchBtn.setTag(position);
            viewHolder.applyServicesBtn.setTag(position);
            viewHolder.infoBranchBtn.setTag(position);

            viewHolder.evaluateBranchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(evaluateBranchListener != null){
                        evaluateBranchListener.onBtnClick((Integer) v.getTag());
                    }
                }
            });

            viewHolder.applyServicesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(applyServicesListener != null){
                        applyServicesListener.onBtnClick((Integer) v.getTag());
                    }
                }
            });

            viewHolder.infoBranchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(infoBranchListener != null){
                        infoBranchListener.onBtnClick((Integer) v.getTag());
                    }
                }
            });
        }

        return convertView;
    }
}
