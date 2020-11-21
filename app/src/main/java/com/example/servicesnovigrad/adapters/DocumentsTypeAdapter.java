package com.example.servicesnovigrad.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.servicesnovigrad.DatabaseHelper;
import com.example.servicesnovigrad.DocumentType;
import com.example.servicesnovigrad.R;
import com.example.servicesnovigrad.Service;

import java.util.ArrayList;


/*
Class used to populate document types and id they are required for a service
 */

public class DocumentsTypeAdapter extends ArrayAdapter<DocumentType> {

    private Service service;

    // View holder
    private static class ViewHolder {
        private TextView documentType;
        private CheckBox required;
    }

    // Set the document item layout
    public DocumentsTypeAdapter(@NonNull Context context, @NonNull ArrayList<DocumentType> docTypes, @NonNull Service aService) {
        super(context, R.layout.document_item, docTypes);
        this.service = aService;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        DocumentsTypeAdapter.ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.document_item, parent, false);

            viewHolder = new DocumentsTypeAdapter.ViewHolder();
            viewHolder.documentType = (TextView) convertView.findViewById(R.id.txtView_document_type);
            viewHolder.required = (CheckBox) convertView.findViewById(R.id.chkBox_document_added);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (DocumentsTypeAdapter.ViewHolder) convertView.getTag();
        }

        final DocumentType docType = getItem(position);

        if(docType != null && service != null) {
            viewHolder.documentType.setText(docType.toString());
            if(service.getRequiredDocument().contains(docType)){
                viewHolder.required.setChecked(true);
            }else{
                viewHolder.required.setChecked(false);
            }

            viewHolder.required.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    addOrRemoveServiceDocument(service, docType);
                }
            });
        }

        return convertView;
    }

    // Adds or removes automatically depending if document exist
    public void addOrRemoveServiceDocument(Service service, DocumentType docType){
        if(service == null){ return; }

        DatabaseHelper.dbr = DatabaseHelper.setToPath("Services/" + service.getServiceType()
                + "/requiredDocument");

        if(service.getRequiredDocument().contains(docType)){
            service.removeRequiredDoc(docType);
            DatabaseHelper.dbr.setValue(service.getRequiredDocument());
            return;
        }

        service.addRequiredDoc(docType);
        DatabaseHelper.dbr.setValue(service.getRequiredDocument());
    }
}
