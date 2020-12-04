package com.example.servicesnovigrad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.servicesnovigrad.adapters.BranchListAdapter;
import com.example.servicesnovigrad.listeners.BtnClickListener;

public class Accueil extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter adapter;
    private TextView clientNameType;
    private TextView clientMessageTxtView;
    private EditText searchBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Intent intent = getIntent();
        final User currentUser = (User)intent.getSerializableExtra(RegisterForm.EXTRA_USER);
        String clientUserName = currentUser.getUsername();

        listView = (ListView) findViewById(R.id.lstView_branches);
        clientMessageTxtView = (TextView)findViewById(R.id.txtView_client_message) ;
        clientNameType = (TextView)findViewById(R.id.txtView_client_type) ;
        searchBranch = (EditText)findViewById(R.id.editTxt_search_branch);

        clientNameType.setText(String.format("%s - Client", clientUserName));

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();

        if(LoginForm.branchList.size() == 0){
            clientMessageTxtView.setVisibility(View.VISIBLE);
            clientMessageTxtView.setTextColor(Color.BLACK);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    (float) 1.0
            );
            clientMessageTxtView.setLayoutParams(param);
            clientMessageTxtView.setTextSize(TypedValue.COMPLEX_UNIT_SP,36);
            clientMessageTxtView.setText("Sorry, we couldn't find any branches.");
            searchBranch.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
        }
        else{
            adapter = new BranchListAdapter(
                    this,
                    LoginForm.branchList,
                    new BtnClickListener() {
                        @Override
                        public void onBtnClick(int position) {
                            final View userDialogView = inflater.inflate(R.layout.rate_dialog, null);
                            dialog.setTitle("Rate branch");
                            dialog.setView(userDialogView);
                            dialog.setPositiveButton("Submit",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            float rating =
                                                    ((RatingBar)userDialogView.findViewById(R.id.ratingBar_rate_branch)).getRating();
                                            String comments =
                                                    ((EditText)userDialogView.findViewById(R.id.editText_comment_branch)).getText().toString();

                                            /** ADD RATING IN BACKEND */
                                            Log.i("onClick", String.format("%f %s", rating, comments));
                                            dialog.dismiss();
                                        }
                                    });
                            dialog.show();
                        }
                    },
                    new BtnClickListener() {
                        @Override
                        public void onBtnClick(int position) {
                            final Intent intent = new Intent(Accueil.this, ModifyService.class);

                            intent.putExtra(RegisterForm.EXTRA_USER, currentUser);

                            startActivity(intent);
                        }
                    },
                    new BtnClickListener() {
                        @Override
                        public void onBtnClick(int position) {
                            Log.i("onClick", "More info button clicked!");
                        }
                    }
            );

            clientMessageTxtView.setVisibility(View.GONE);
            searchBranch.setVisibility(View.VISIBLE);
            listView.setAdapter(adapter);
        }
    }

    public void btn_test_document(View view){
        Intent newIntent = new Intent(this, DocumentCreation.class);
        startActivity(newIntent);
    }
}