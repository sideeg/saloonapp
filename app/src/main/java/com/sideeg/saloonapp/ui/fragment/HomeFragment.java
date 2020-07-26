package com.sideeg.saloonapp.ui.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sideeg.saloonapp.R;
import com.sideeg.saloonapp.models.SaloonServiceResponse;
import com.sideeg.saloonapp.networking.ApiClient;
import com.sideeg.saloonapp.networking.NetWorkApi;
import com.sideeg.saloonapp.ui.ServiceAdapter;
import com.sideeg.saloonapp.utility.LocalSession;
import com.sideeg.saloonapp.utility.Utility;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private String TAG = "HomeFragment";
    private LocalSession mLocalSession;
    Dialog dialog;
    EditText tv_price,tv_wroker_name;
    private ServiceAdapter adapter;
    private RecyclerView ServiceRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        tv_price = rootView.findViewById(R.id.tv_price);
        tv_wroker_name = rootView.findViewById(R.id.tv_worker_name);
        ServiceRecyclerView = rootView.findViewById(R.id.service_recycler_view);

        getSaloonService();

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            dialog = new Dialog(rootView.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.add_new_service);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);

            dialog.findViewById(R.id.cancel_button).setOnClickListener(v -> dialog.dismiss());
            dialog.show();
        });

        return rootView;
    }

    private void getSaloonService() {
        ProgressDialog loading = Utility.GetProcessDialog(getActivity());
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
                        adapter = new ServiceAdapter(getContext(),response.body().getData());
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

    private void addSaloonService() {
        ProgressDialog loading = Utility.GetProcessDialog(getActivity());
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
                        dialog.dismiss();
                        Utility.showAlertDialog(getString(R.string.error), response.body().getMessage(), getContext());

                    } else {
                        Log.i(TAG, "token: " +  response.body().getData().toString());

                        loading.dismiss();
                        dialog.dismiss();

                    }
                } else {
                    Log.i(TAG, response.errorBody().toString());
                    loading.dismiss();
                    dialog.dismiss();
                    Utility.showAlertDialog(getString(R.string.error), getString(R.string.servererror), getContext());

                }
            }

            @Override
            public void onFailure(Call<SaloonServiceResponse> call, Throwable t) {
                Utility.showAlertDialog(getString(R.string.error), t.getMessage(), getContext());
                Utility.printLog(TAG, t.getMessage());
                loading.dismiss();
                dialog.dismiss();
            }
        });
    }
}
