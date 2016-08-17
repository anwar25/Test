package in.eweblabs.careeradvance.Search;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.eweblabs.careeradvance.Entity.RecentSearch;
import in.eweblabs.careeradvance.R;

/**
 * Created by Anwar on 8/16/2016.
 */
public class RecentJobsAdapter extends RecyclerView.Adapter<RecentJobsAdapter.ViewHolder>{

    private static ArrayList<RecentSearch> mDataset;
    private static RecentJobListener recentJobListener ;

    public interface RecentJobListener {
        void jobSearchClicked(RecentSearch recentSearch);
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.recentJobTextView);
            mTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recentJobListener.jobSearchClicked(mDataset.get(getAdapterPosition()));
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecentJobsAdapter(ArrayList<RecentSearch> myDataset, RecentJobListener recentJobListener ) {
        mDataset = myDataset;
        this.recentJobListener = recentJobListener ;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecentJobsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recent_search_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        RecentSearch recentSearch = mDataset.get(position) ;
        if(TextUtils.isEmpty(mDataset.get(position).getKeyword())){
            holder.mTextView.setText(mDataset.get(position).getLocation());
        }else if(TextUtils.isEmpty(mDataset.get(position).getLocation())){
            holder.mTextView.setText(mDataset.get(position).getKeyword());
        }else{
            holder.mTextView.setText(mDataset.get(position).getKeyword()+" | "+mDataset.get(position).getLocation());
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
