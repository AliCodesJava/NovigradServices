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
import com.example.servicesnovigrad.User;
import com.example.servicesnovigrad.listeners.BtnClickListener;

import java.util.ArrayList;

public class ServiceListAdapter extends ArrayAdapter<Service> {
    private static class ViewHolder {
        private TextView serviceName;
        private Button serviceApplyBtn;
        private Button serviceDocumentsBtn;
        private Button serviceEditBtn;
        private Button serviceDeleteBtn;
    }

    private User currentUser;
    private BtnClickListener deleteListener;
    private BtnClickListener applyListener;
    private BtnClickListener editServiceListener;
    private BtnClickListener addDocsListener;

    public ServiceListAdapter(
            @NonNull Context context,
            @NonNull ArrayList<Service> services,
            @NonNull User currentUser,
            BtnClickListener aListener,
            BtnClickListener dListener,
            BtnClickListener eListener,
            BtnClickListener addDocsListener
    ) {
        super(context, R.layout.service_list_item, services);
        this.currentUser = currentUser;
        this.applyListener = aListener;
        this.deleteListener = dListener;
        this.editServiceListener = eListener;
        this.addDocsListener = addDocsListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ServiceListAdapter.ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.service_list_item, parent, false);

            viewHolder = new ServiceListAdapter.ViewHolder();
            viewHolder.serviceName = (TextView) convertView.findViewById(R.id.txtView_item_service_name);
            viewHolder.serviceApplyBtn = (Button) convertView.findViewById(R.id.btn_apply_service);
            viewHolder.serviceDocumentsBtn = (Button) convertView.findViewById(R.id.btn_documents);
            viewHolder.serviceEditBtn = (Button) convertView.findViewById(R.id.btn_edit_service);
            viewHolder.serviceDeleteBtn = (Button) convertView.findViewById(R.id.btn_delete_service);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ServiceListAdapter.ViewHolder) convertView.getTag();
        }

        Service service = getItem(position);

        if(service != null){
            viewHolder.serviceName.setText(service.getServiceType());
            viewHolder.serviceApplyBtn.setTag(position);
            if(currentUser != null && currentUser instanceof AdministratorAccount){
                viewHolder.serviceApplyBtn.setVisibility(View.GONE);
                viewHolder.serviceDocumentsBtn.setVisibility(View.VISIBLE);
                viewHolder.serviceEditBtn.setVisibility(View.VISIBLE);
                viewHolder.serviceDeleteBtn.setVisibility(View.VISIBLE);

                viewHolder.serviceDeleteBtn.setTag(position);
                viewHolder.serviceEditBtn.setTag(position);
                viewHolder.serviceDocumentsBtn.setTag(position);

                viewHolder.serviceEditBtn.setText("Edit - $ " + service.getPriceString());

                viewHolder.serviceDeleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(deleteListener != null){
                            deleteListener.onBtnClick((Integer) v.getTag());
                        }
                    }
                });

                viewHolder.serviceEditBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(editServiceListener != null){
                            editServiceListener.onBtnClick((Integer) v.getTag());
                        }
                    }
                });

                viewHolder.serviceDocumentsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(addDocsListener != null){
                            addDocsListener.onBtnClick((Integer) v.getTag());
                        }
                    }
                });

            }

            else{
                viewHolder.serviceDeleteBtn.setVisibility(View.VISIBLE);

                viewHolder.serviceDeleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(applyListener != null){
                            applyListener.onBtnClick((Integer) v.getTag());
                        }
                    }
                });
                viewHolder.serviceDeleteBtn.setText("Remove this service");

            }
        }

        return convertView;
    }
}
