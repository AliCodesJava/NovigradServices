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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.servicesnovigrad.adapters.BranchListAdapter;
import com.example.servicesnovigrad.listeners.BtnClickListener;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.time.DayOfWeek;

public class Accueil extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter adapter;
    private TextView clientNameType;
    private TextView clientMessageTxtView;
    private EditText searchBranch;
    private Spinner serviceSpinner;
    private Spinner spinnerDay;
    private EditText timeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Intent intent = getIntent();
        final User currentUser = (User)intent.getSerializableExtra(RegisterForm.EXTRA_USER);
        String clientUserName = currentUser.getUsername();

        listView = (ListView) findViewById(R.id.lstView_branches);
        timeText = (EditText) findViewById(R.id.editTextTime3);
        spinnerDay = (Spinner) findViewById(R.id.spinner_day);
        clientMessageTxtView = (TextView)findViewById(R.id.txtView_client_message) ;
        clientNameType = (TextView)findViewById(R.id.txtView_client_type) ;
        searchBranch = (EditText)findViewById(R.id.editTxt_search_branch);
        serviceSpinner = findViewById(R.id.spinner_service);
        clientNameType.setText(String.format("%s - Client", clientUserName));

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();

        serviceSpinner.setAdapter(new ArrayAdapter<Service>(this,
                android.R.layout.simple_spinner_item, Service.serviceList));
        spinnerDay.setAdapter(new ArrayAdapter<DayOfWeek>(this,
                android.R.layout.simple_spinner_item, DayOfWeek.values()));

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
                        public void onBtnClick(final int position) {
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

                                            /** ADD RATING IN BACKEND -- done*/
                                            Log.i("onClick", String.format("%f %s", rating, comments));
                                            LoginForm.branchList.get(position).getRatings().put(LoginForm.user.getUsername(), rating);
                                            DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Users/Employees/" + LoginForm.branchList.get(position).getEmployeeUserName());
                                            DatabaseHelper.dbr.child("mainBranch").setValue(LoginForm.branchList.get(position));

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

                            intent.putExtra(RegisterForm.EXTRA_BRANCH, LoginForm.branchList.get(position));

                            startActivity(intent);
                        }
                    },
                    new BtnClickListener() {
                        @Override
                        public void onBtnClick(int position) {
                            final View userDialogView = inflater.inflate(R.layout.branch_schedule, null);
                            dialog.setTitle("Branch schedule");
                            dialog.setView(userDialogView);
                            TextView tv = userDialogView.findViewById(R.id.text_schedule);
                            tv.setText(LoginForm.branchList.get(position).getSchedule().toString());
                            dialog.setPositiveButton("ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            dialog.show();
                        }
                    }
            );

            clientMessageTxtView.setVisibility(View.GONE);
            searchBranch.setVisibility(View.VISIBLE);
            listView.setAdapter(adapter);
        }
    }

    public void btnSearchService(View view){

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
        adapter = new BranchListAdapter(
                this,
                ((Client)LoginForm.user).getBranchListBy((Service)serviceSpinner.getSelectedItem()),
                new BtnClickListener() {
                    @Override
                    public void onBtnClick(final int position) {
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
                                        ((Client)LoginForm.user).getBranchListBy((Service)serviceSpinner.getSelectedItem()).get(position).getRatings().put(LoginForm.user.getUsername(), rating);
                                        DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Users/Employees/" + ((Client)LoginForm.user).getBranchListBy((Service)serviceSpinner.getSelectedItem()).get(position).getEmployeeUserName());
                                        DatabaseHelper.dbr.child("mainBranch").setValue(((Client)LoginForm.user).getBranchListBy((Service)serviceSpinner.getSelectedItem()).get(position));
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

                        intent.putExtra(RegisterForm.EXTRA_USER, LoginForm.user);

                        intent.putExtra(RegisterForm.EXTRA_BRANCH, ((Client)LoginForm.user).getBranchListBy((Service)serviceSpinner.getSelectedItem()).get(position));

                        startActivity(intent);
                    }
                },
                new BtnClickListener() {
                    @Override
                    public void onBtnClick(int position) {
                        final View userDialogView = inflater.inflate(R.layout.branch_schedule, null);
                        dialog.setTitle("Branch schedule");
                        dialog.setView(userDialogView);
                        TextView tv = userDialogView.findViewById(R.id.text_schedule);
                        tv.setText(LoginForm.branchList.get(position).getSchedule().toString());
                        dialog.setPositiveButton("ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        dialog.show();
                    }
                }
        );

        clientMessageTxtView.setVisibility(View.GONE);
        searchBranch.setVisibility(View.VISIBLE);
        listView.setAdapter(adapter);
    }
    public void btnSearchTime(View view){

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
        try{
        adapter = new BranchListAdapter(
                this,
                ((Client)LoginForm.user).getBranchListBy((Integer.parseInt(""+timeText.getText().charAt(0)+timeText.getText().charAt(1))) * 60
                                + Integer.parseInt(""+timeText.getText().charAt(3)+timeText.getText().charAt(4)), (DayOfWeek) spinnerDay.getSelectedItem() ),
                new BtnClickListener() {
                    @Override
                    public void onBtnClick(final int position) {
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
                                        ((Client)LoginForm.user).getBranchListBy((Integer.parseInt(""+timeText.getText().charAt(0)+timeText.getText().charAt(1))) * 60
                                                + Integer.parseInt(""+timeText.getText().charAt(3)+timeText.getText().charAt(4)), (DayOfWeek) spinnerDay.getSelectedItem()).get(position).getRatings().put(LoginForm.user.getUsername(), rating);
                                        DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Users/Employees/" + ((Client)LoginForm.user).getBranchListBy((Integer.parseInt(""+timeText.getText().charAt(0)+timeText.getText().charAt(1))) * 60
                                                + Integer.parseInt(""+timeText.getText().charAt(3)+timeText.getText().charAt(4)), (DayOfWeek) spinnerDay.getSelectedItem()).get(position).getEmployeeUserName());
                                        DatabaseHelper.dbr.child("mainBranch").setValue(((Client)LoginForm.user).getBranchListBy((Integer.parseInt(""+timeText.getText().charAt(0)+timeText.getText().charAt(1))) * 60
                                                + Integer.parseInt(""+timeText.getText().charAt(3)+timeText.getText().charAt(4)), (DayOfWeek) spinnerDay.getSelectedItem()).get(position));
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

                        intent.putExtra(RegisterForm.EXTRA_USER, LoginForm.user);

                        intent.putExtra(RegisterForm.EXTRA_BRANCH, ((Client)LoginForm.user).getBranchListBy((Integer.parseInt(""+timeText.getText().charAt(0)+timeText.getText().charAt(1))) * 60
                                + Integer.parseInt(""+timeText.getText().charAt(3)+timeText.getText().charAt(4)), (DayOfWeek) spinnerDay.getSelectedItem()).get(position));

                        startActivity(intent);
                    }
                },
                new BtnClickListener() {
                    @Override
                    public void onBtnClick(int position) {
                        final View userDialogView = inflater.inflate(R.layout.branch_schedule, null);
                        dialog.setTitle("Branch schedule");
                        dialog.setView(userDialogView);
                        TextView tv = userDialogView.findViewById(R.id.text_schedule);
                        tv.setText(LoginForm.branchList.get(position).getSchedule().toString());
                        dialog.setPositiveButton("ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        dialog.show();
                    }
                }
        );

        clientMessageTxtView.setVisibility(View.GONE);
        searchBranch.setVisibility(View.VISIBLE);
        listView.setAdapter(adapter);
        }
        catch (Exception e ){

        }
    }
    public void btnSearchAddress(View view){

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
        adapter = new BranchListAdapter(
                this,
                ((Client)LoginForm.user).getBranchListBy(searchBranch.getText().toString()),
                new BtnClickListener() {
                    @Override
                    public void onBtnClick(final int position) {
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
                                        ((Client)LoginForm.user).getBranchListBy(searchBranch.getText().toString()).get(position).getRatings().put(LoginForm.user.getUsername(), rating);
                                        DatabaseHelper.dbr = FirebaseDatabase.getInstance().getReference("Users/Employees/" + ((Client)LoginForm.user).getBranchListBy(searchBranch.getText().toString()).get(position).getEmployeeUserName());
                                        DatabaseHelper.dbr.child("mainBranch").setValue(((Client)LoginForm.user).getBranchListBy(searchBranch.getText().toString()).get(position));
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

                        intent.putExtra(RegisterForm.EXTRA_USER, LoginForm.user);

                        intent.putExtra(RegisterForm.EXTRA_BRANCH, ((Client)LoginForm.user).getBranchListBy(searchBranch.getText().toString()).get(position));

                        startActivity(intent);
                    }
                },
                new BtnClickListener() {
                    @Override
                    public void onBtnClick(int position) {
                        final View userDialogView = inflater.inflate(R.layout.branch_schedule, null);
                        dialog.setTitle("Branch schedule");
                        dialog.setView(userDialogView);
                        TextView tv = userDialogView.findViewById(R.id.text_schedule);
                        tv.setText(LoginForm.branchList.get(position).getSchedule().toString());
                        dialog.setPositiveButton("ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        dialog.show();
                    }
                }
        );

        clientMessageTxtView.setVisibility(View.GONE);
        searchBranch.setVisibility(View.VISIBLE);
        listView.setAdapter(adapter);
    }
    public void btn_test_document(View view){
        Intent newIntent = new Intent(this, DocumentCreation.class);
        startActivity(newIntent);
    }
}