package com.example.ngoquan.demogooglemap.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ngoquan.demogooglemap.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by NGOQUAN on 7/19/2016.
 */
public class PlacesAutoCompleteAdapter extends RecyclerView.Adapter<PlacesAutoCompleteAdapter.PredictionHolder> implements Filterable {

    private static final String TAG = "PlaceAutoCompleteAdapter";
    private ArrayList<PlaceAutocomplete> mResultList;
    private GoogleApiClient mGoogleApiClient;
    private LatLngBounds mBounds;
    private AutocompleteFilter mPlaceFilter;

    private Context context;
    private int layout;

    public PlacesAutoCompleteAdapter(Context context, AutocompleteFilter mPlaceFilter, LatLngBounds mBounds, GoogleApiClient mGoogleApiClient, int layout) {
        this.context = context;
        this.mPlaceFilter = mPlaceFilter;
        this.mBounds = mBounds;
        this.mGoogleApiClient = mGoogleApiClient;
        this.layout = layout;
    }

    /**
     * Sets the bounds for all subsequent queries.
     */
    public void setBounds(LatLngBounds mBounds) {
        this.mBounds = mBounds;
    }


    @Override
    public PredictionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(layout, parent, false);
        PredictionHolder predictionHolder = new PredictionHolder(convertView);
        return predictionHolder;
    }

    @Override
    public void onBindViewHolder(PredictionHolder holder, int position) {
        holder.mPrediction.setText(mResultList.get(position).description);
    }

    public PlaceAutocomplete getItem(int position) {
        return mResultList.get(position);
    }

    @Override
    public int getItemCount() {
        if (mResultList != null)
            return mResultList.size();
        else
            return 0;
    }

//    Return the filter for the current set of autocomplete results


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
//                Skip the autocomplete query if no constraints are given
                if (constraint != null) {
//                    query the autocomplete API for the (constraint) search string
                    mResultList = getAutoComplete(constraint);
                    if (mResultList != null) {
                        results.values = mResultList;
                        results.count = mResultList.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
            }
        };
        return filter;
    }

    /**
     * @param constraint
     * @return
     */
    private ArrayList<PlaceAutocomplete> getAutoComplete(CharSequence constraint) {
        if (mGoogleApiClient.isConnected()) {
            Log.i("", "Starting autocomplete query for: " + constraint);
            PendingResult<AutocompletePredictionBuffer> results = Places.GeoDataApi.getAutocompletePredictions(mGoogleApiClient, constraint.toString(), mBounds, mPlaceFilter);
            // This method should have been called off the main UI thread. Block and wait for at most 60s
            // for a result from the API.
            AutocompletePredictionBuffer autocompletePredictions = results.await(60, TimeUnit.SECONDS);
            // Confirm that the query completed successfully, otherwise return null
            final Status status = autocompletePredictions.getStatus();
            if (!status.isSuccess()) {
                Toast.makeText(context, "Error contacting API: " + status.toString(),
                        Toast.LENGTH_SHORT).show();
                autocompletePredictions.release();
                return null;
            }
            // Copy the results into our own data structure, because we can't hold onto the buffer.
            // AutocompletePrediction objects encapsulate the API response (place ID and description).
            Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
            ArrayList resultList = new ArrayList(autocompletePredictions.getCount());
            while (iterator.hasNext()) {
                AutocompletePrediction prediction = iterator.next();
                resultList.add(new PlaceAutocomplete(prediction.getPlaceId(), prediction.getDescription()));

            }

            autocompletePredictions.release();
            return resultList;
        }
        Log.e("", "Google API client is not connected for autocomplete query.");
        return null;
    }

    public class PredictionHolder extends RecyclerView.ViewHolder {
        private TextView mPrediction;
        private RelativeLayout mRow;
        public PredictionHolder(View itemView) {
            super(itemView);
            mPrediction = (TextView) itemView.findViewById(R.id.address);
            mRow = (RelativeLayout) itemView.findViewById(R.id.predictedRow);
        }
    }

    /**
     * Holder for Places Geo Data Autocomplete API results.
     */
    public class PlaceAutocomplete {
        public CharSequence placeId;
        public CharSequence description;
        public PlaceAutocomplete(CharSequence placeId, CharSequence description) {
            this.placeId = placeId;
            this.description = description;
        }

        @Override
        public String toString() {
            return this.description.toString();
        }
    }
}
