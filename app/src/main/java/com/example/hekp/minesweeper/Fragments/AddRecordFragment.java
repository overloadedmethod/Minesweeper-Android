package com.example.hekp.minesweeper.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.hekp.minesweeper.R;
import com.example.hekp.minesweeper.Interfaces.IRecordable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;


public class AddRecordFragment extends Fragment implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    protected Context mContext;
    protected Button bAddRecord;
    protected EditText etWinner;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    protected double mLat, mLong;
    protected boolean isWasConnection = false;
    private IRecordable recordable;

    public AddRecordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }


    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_record, container, false);
        bAddRecord = (Button) view.findViewById(R.id.bAddRecord);
        etWinner = (EditText) view.findViewById(R.id.etWinner);

        if (getActivity() instanceof IRecordable)
            recordable = (IRecordable) getActivity();

        bAddRecord.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }


    @Override
    public void onClick(View v) {

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        if (mLastLocation != null) {
            recordable.addRecord(String.valueOf(etWinner.getText()),
                    mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
        else
            recordable.addRecord(String.valueOf(etWinner.getText()),
                    -1, -1);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            mLong = -1;
            mLat = -1;
        }
        isWasConnection = true;
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mLong = mLastLocation.getLongitude();
            mLat = mLastLocation.getLatitude();
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
        if(!isWasConnection){
            mLong = -1;
            mLat = -1;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if(!isWasConnection){
            mLong = -1;
            mLat = -1;
        }
    }
}
