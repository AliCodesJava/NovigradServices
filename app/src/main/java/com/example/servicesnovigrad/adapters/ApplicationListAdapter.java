package com.example.servicesnovigrad.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.servicesnovigrad.AdministratorAccount;
import com.example.servicesnovigrad.Employee;
import com.example.servicesnovigrad.R;
import com.example.servicesnovigrad.Service;
import com.example.servicesnovigrad.ServiceApplication;
import com.example.servicesnovigrad.User;
import com.example.servicesnovigrad.listeners.BtnClickListener;

import java.util.ArrayList;

public class ApplicationListAdapter extends ArrayAdapter<ServiceApplication> {

    private static class ViewHolder {
        private TextView serviceName;
        private TextView applicantName;
        private Button approveBtn;
        private Button rejectBtn;
        private Button viewButton;
    }

    private User currentUser;
    private BtnClickListener approveListener;
    private BtnClickListener rejectListener;
    private BtnClickListener viewListener;

    public ApplicationListAdapter(
            @NonNull Context context,
            @NonNull ArrayList<ServiceApplication> serviceApplication,
            @NonNull User currentUser,
            BtnClickListener aListener,
            BtnClickListener rListener,
            BtnClickListener vListener
    ) {
        super(context, R.layout.service_list_item, serviceApplication);
        this.currentUser = currentUser;
        this.approveListener = aListener;
        this.rejectListener = rListener;
        this.viewListener = vListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final ApplicationListAdapter.ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.application_list_item, parent, false);

            viewHolder = new ApplicationListAdapter.ViewHolder();
            viewHolder.serviceName = (TextView) convertView.findViewById(R.id.service_name);
            viewHolder.applicantName = (TextView) convertView.findViewById(R.id.applicant_name);
            viewHolder.approveBtn = (Button) convertView.findViewById(R.id.btn_approve);
            viewHolder.rejectBtn = (Button) convertView.findViewById(R.id.btn_reject);
            viewHolder.viewButton = (Button) convertView.findViewById(R.id.btn_view);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ApplicationListAdapter.ViewHolder) convertView.getTag();
        }

        ServiceApplication serviceApplication = getItem(position);

        if(serviceApplication != null){
            viewHolder.serviceName.setText(serviceApplication.getService().getServiceType());
            viewHolder.applicantName.setText(serviceApplication.getApplicant().getUsername());
            if(currentUser != null && currentUser instanceof Employee){
                viewHolder.rejectBtn.setTag(position);
                viewHolder.approveBtn.setTag(position);
                viewHolder.viewButton.setTag(position);

                viewHolder.rejectBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(rejectListener != null){
                            rejectListener.onBtnClick((Integer) v.getTag());
                        }
                    }
                });

                viewHolder.approveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(approveListener != null){
                            approveListener.onBtnClick((Integer) v.getTag());
                        }
                    }
                });

                viewHolder.viewButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(viewListener != null){
                            viewListener.onBtnClick((Integer) v.getTag());
                        }
                    }
                });

            }
        }

        return convertView;
    }
}
