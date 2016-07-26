package in.eweblabs.careeradvance.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import in.eweblabs.careeradvance.Network.RetrofitInstance;
import in.eweblabs.careeradvance.Network.models.JobSugestionReqModel;
import in.eweblabs.careeradvance.Network.models.JobSugestionResModel;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.Utils.Logger;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Anwar on 7/26/2016.
 */
public class JobSugestionAdapter extends ArrayAdapter<String> implements Filterable {

    private static final String TAG = JobSugestionAdapter.class.getSimpleName();
    ArrayList<String> resultList;

    Context mContext;
//{"sarchKey":"Pro", "type":"location"}
    private String mType ;

    public JobSugestionAdapter(Context context, int resource , String type , ArrayList<String> resultList) {
        super(context, resource);

        mContext = context;
        this.resultList = resultList ;
        this.mType = type ;
    }

    @Override
    public int getCount() {
        // Last item will be the footer
        return resultList.size();
    }

    @Override
    public String getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.spinner_item_layout, parent, false);
        }
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(getItem(position));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    //resultList = mPlaceAPI.autocomplete(constraint.toString());
                    JobSugestionReqModel jobSugestionReqModel = new JobSugestionReqModel();
                    jobSugestionReqModel.setSarchKey(String.valueOf(constraint));
                    jobSugestionReqModel.setType(mType);

                    Call<JobSugestionResModel> responseBody = RetrofitInstance.getInstance().jobSuggestions(jobSugestionReqModel);
                    Response <JobSugestionResModel> resBody = null;
                    try {
                        resBody = responseBody.execute();
                        if(resBody != null){
                            JobSugestionResModel jobSugestionResModel = resBody.body();
                            if(jobSugestionResModel != null && jobSugestionResModel.getSuccess() == 1){
                                resultList = new ArrayList<String>(jobSugestionResModel.getKeywords());
                            }

                        }
                    } catch (IOException e) {
                        Logger.e(TAG,e.toString()+"");
                    }

                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }
}
