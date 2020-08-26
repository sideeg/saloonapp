package com.sideeg.saloonapp.ui.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sideeg.saloonapp.R;
import com.sideeg.saloonapp.models.BaseResponse;
import com.sideeg.saloonapp.models.SaloonServiceData;
import com.sideeg.saloonapp.models.SaloonServiceResponse;
import com.sideeg.saloonapp.models.Service;
import com.sideeg.saloonapp.models.getAllServiceResponse;
import com.sideeg.saloonapp.networking.ApiClient;
import com.sideeg.saloonapp.networking.NetWorkApi;
import com.sideeg.saloonapp.ui.ServiceAdapter;
import com.sideeg.saloonapp.utility.LocalSession;
import com.sideeg.saloonapp.utility.Utility;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private String TAG = "HomeFragment";
    private LocalSession mLocalSession;
    Dialog dialog;
    EditText tv_price,tv_wroker_name;
    private ServiceAdapter adapter;
    private RecyclerView ServiceRecyclerView;
    private List<SaloonServiceData> filterd_Saloon_Service_List;
    private  List<Service> servicesList;
    private String selectedServiceId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ServiceRecyclerView = rootView.findViewById(R.id.service_recycler_view);

        servicesList = new ArrayList<>();
        getSaloonService();

        //Getting the instance of Spinner and applying OnItemSelectedListener on it


        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            dialog = new Dialog(rootView.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.add_new_service);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);



            tv_price = dialog.findViewById(R.id.tv_price);
            tv_wroker_name = dialog.findViewById(R.id.tv_worker_name);

            getAllService();


            dialog.findViewById(R.id.cancel_button).setOnClickListener(v -> dialog.dismiss());
            dialog.findViewById(R.id.add_button).setOnClickListener(v -> {
                addSaloonService();
                if (dialog.isShowing())
                    dialog.dismiss();
                    }
            );
            dialog.show();
        });




        return rootView;
    }

    private void getSaloonService() {
        ProgressDialog loading =  ProgressDialog.show(getContext(), getString(R.string.loading), getString(R.string.wait), false, false);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        mLocalSession=new LocalSession(getContext());
        NetWorkApi api = ApiClient.getClient(ApiClient.BASE_URL).create(NetWorkApi.class);
        Call<SaloonServiceResponse> loginCall = api.getSaloonService();
        loginCall.enqueue(new Callback<SaloonServiceResponse>() {
            @Override
            public void onResponse(Call<SaloonServiceResponse> call, Response<SaloonServiceResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isError()) {
                        loading.dismiss();

                        Utility.showAlertDialog(getString(R.string.error), response.body().getMessage(), getContext());

                    } else {
                        Log.i(TAG, "token: " +  response.body().getData().toString());
                        loading.dismiss();
                        filterd_Saloon_Service_List = new ArrayList<>();
                        for (SaloonServiceData saloonServiceData : response.body().getData()) {
                            if (saloonServiceData.getSaloon_id().equals(LocalSession.getId()))
                                filterd_Saloon_Service_List.add(saloonServiceData);
                        }

                        adapter = new ServiceAdapter(getContext(),filterd_Saloon_Service_List);
                        ServiceRecyclerView.setLayoutManager(new LinearLayoutManager(ServiceRecyclerView.getContext()));
                        ServiceRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                    }
                } else {
                    Log.i(TAG, response.errorBody().toString());
                    loading.dismiss();
                    Utility.showAlertDialog(getString(R.string.error), getString(R.string.servererror), getContext());

                }
            }

            @Override
            public void onFailure(Call<SaloonServiceResponse> call, Throwable t) {
                Utility.showAlertDialog(getString(R.string.error), t.getMessage(), getContext());
                Utility.printLog(TAG, t.getMessage());
                loading.dismiss();

            }
        });
    }
 private void getAllService() {
        ProgressDialog loading =  ProgressDialog.show(getContext(), getString(R.string.loading), getString(R.string.wait), false, false);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        mLocalSession=new LocalSession(getContext());
        NetWorkApi api = ApiClient.getClient(ApiClient.BASE_URL).create(NetWorkApi.class);
        Call<getAllServiceResponse> loginCall = api.getAllService();
        loginCall.enqueue(new Callback<getAllServiceResponse>() {
            @Override
            public void onResponse(Call<getAllServiceResponse> call, Response<getAllServiceResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isError()) {
                        loading.dismiss();

                        Utility.showAlertDialog(getString(R.string.error), response.body().getMessage(), getContext());

                    } else {
                        Log.i(TAG, "token: " +  response.body().getData().toString());
                        loading.dismiss();
                        filterd_Saloon_Service_List = new ArrayList<>();
                        servicesList = response.body().getData() ;


                        Spinner spin =  dialog.findViewById(R.id.service_spinner);
                        spin.setOnItemSelectedListener(HomeFragment.this);
                        String[] temp = new String[servicesList.size()];
                        int i = 0;
                        for (Service service: servicesList)
                            if (service != null)
                                temp[i++] = service.getService_name();
                        //Creating the ArrayAdapter instance having the country list
                        ArrayAdapter aa = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,temp);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        spin.setAdapter(aa);


                    }
                } else {
                    Log.i(TAG, response.errorBody().toString());
                    loading.dismiss();
                    Utility.showAlertDialog(getString(R.string.error), getString(R.string.servererror), getContext());

                }
            }

            @Override
            public void onFailure(Call<getAllServiceResponse> call, Throwable t) {
                Utility.showAlertDialog(getString(R.string.error), t.getMessage(), getContext());
                Utility.printLog(TAG, t.getMessage());
                loading.dismiss();

            }
        });
    }

    private void addSaloonService() {
        ProgressDialog loading = Utility.GetProcessDialog(getActivity());
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        mLocalSession=new LocalSession(getContext());
        NetWorkApi api = ApiClient.getClient(ApiClient.BASE_URL).create(NetWorkApi.class);
        Call<BaseResponse> loginCall = api.addServiceToSaloon(LocalSession.getId(),selectedServiceId,tv_price.getText().toString(),
                tv_wroker_name.getText().toString());
        loginCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isError()) {
                        loading.dismiss();
                        dialog.dismiss();
                        Utility.showAlertDialog(getString(R.string.error), response.body().getMessage(), getContext());

                    } else {
                        Log.i(TAG, "token: " +  response.body().toString());

                        loading.dismiss();
                        dialog.dismiss();
                        getSaloonService();

                    }
                } else {
                    Log.i(TAG, response.errorBody().toString());
                    loading.dismiss();
                    dialog.dismiss();
                    Utility.showAlertDialog(getString(R.string.error), getString(R.string.servererror), getContext());

                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Utility.showAlertDialog(getString(R.string.error), t.getMessage(), getContext());
                Utility.printLog(TAG, t.getMessage());
                loading.dismiss();
                dialog.dismiss();
            }
        });
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        selectedServiceId = servicesList.get(position).getId();
     //   Toast.makeText(getContext(),servicesList.get(position).getService_name() , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }



}
