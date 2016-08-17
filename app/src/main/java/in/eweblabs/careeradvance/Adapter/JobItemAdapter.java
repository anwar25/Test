package in.eweblabs.careeradvance.Adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import in.eweblabs.careeradvance.Entity.Job;
import in.eweblabs.careeradvance.Interface.IRefreshList;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.Utils.Logger;

/**
 * Created by Anwar shaikh on 12/11/2015.
 */
public class JobItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static ArrayList<Job> jobArrayList;
    FragmentActivity context;
    IRefreshList iRefreshList;
    private static boolean showApplyButton;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private static ApplyJobListener applyJob;


    public interface ApplyJobListener {
        void jobApplied(Job job);
    }

    public JobItemAdapter(FragmentActivity context, ArrayList<Job> jobArrayList, IRefreshList iRefreshList,
                          ApplyJobListener applyJobListener, boolean showApplyButon ,RecyclerView mRecyclerView ) {
        this.context = context;
        this.jobArrayList = jobArrayList;
        this.iRefreshList = iRefreshList;
        this.applyJob = applyJobListener;
        showApplyButton = showApplyButon;
    }

    private OnLoadMoreListener mOnLoadMoreListener;
    private static  LoadingViewHolder loadingViewHolder ;
    public void setLoaded() {
        //isLoading = false;
        loadingViewHolder.mLoadingText.setText(context.getString(R.string.loadmore_job));
        loadingViewHolder.mLoadingImage.setVisibility(View.VISIBLE);
        loadingViewHolder.progressBar.setVisibility(View.GONE);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_view, parent, false);
            return new JobViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more_layout, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    private Job getItem(int position) {
        return jobArrayList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return jobArrayList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof JobViewHolder) {
            JobViewHolder viewitem = (JobViewHolder) holder;
            final Job job = getItem(position);

            if (!TextUtils.isEmpty(job.getJob_title()))
                viewitem.text_job_title.setText(job.getJob_title());
            else
                viewitem.text_job_title.setText("");

            if (!TextUtils.isEmpty(job.getExperience_min()))
                viewitem.text_job_exp.setText(job.getExperience_min() + " - " + job.getExperience_max() + " Year");
            else
                viewitem.text_job_exp.setText("");

            if (!TextUtils.isEmpty(job.getCompany()))
                viewitem.text_company_name.setText(job.getCompany());
            else
                viewitem.text_company_name.setText("");

            if (!TextUtils.isEmpty(job.getJob_loacation_city()))
                viewitem.text_job_location.setText(job.getJob_loacation_city() + ", " + job.getJob_loc_country());
            else
                viewitem.text_job_location.setText("");

            viewitem.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iRefreshList.onRefresh(getItem(position));
                }
            });

            Date jobPostingDate = null;
            try {
                SimpleDateFormat jobDateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
                jobPostingDate = jobDateFormat.parse(job.getDate_post());
                Date currentDate = new Date();
                long diff = currentDate.getTime() - jobPostingDate.getTime() ;
                //System.out.println ("anwar: " + );
                long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                if(days >= 365){
                    viewitem.jobAgeTextView.setText(context.getString(R.string.year_ago,days/365));
                }else if( days >= 30){
                    viewitem.jobAgeTextView.setText(context.getString(R.string.month_ago,days/30));
                }else if (days == 1){
                    viewitem.jobAgeTextView.setText(context.getString(R.string.yesterday));
                }else if(days == 0){
                    viewitem.jobAgeTextView.setText(context.getString(R.string.today));
                } else {
                    viewitem.jobAgeTextView.setText(context.getString(R.string.job_age,days));
                }
            } catch (ParseException e) {
                viewitem.jobAgeTextView.setText(job.getDate_post());
                Logger.e("JobItemAdapter",e.getMessage()+"");
            }
        } else {
            loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.loadingViewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadingViewHolder.mLoadingImage.setVisibility(View.GONE);
                    loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                    loadingViewHolder.mLoadingText.setText(context.getString(R.string.loading));
                    loadingViewHolder.progressBar.setIndeterminate(true);
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                }
            });
          //  loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }


    @Override
    public int getItemCount() {
        return jobArrayList == null ? 0 : jobArrayList.size();
    }

    static class JobViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView text_job_location, text_job_title, text_company_name, text_job_exp;
        public TextView jobAgeTextView ;
        CardView card_view;
        public AppCompatButton applyJobButton;

        public JobViewHolder(View itemView) {
            super(itemView);
            text_job_title = (TextView) itemView.findViewById(R.id.text_job_title);
            text_company_name = (TextView) itemView.findViewById(R.id.text_company_name);
            text_job_exp = (TextView) itemView.findViewById(R.id.text_job_exp);
            text_job_location = (TextView) itemView.findViewById(R.id.text_job_location);
            jobAgeTextView = (TextView) itemView.findViewById(R.id.jobAgeTextView);
            applyJobButton = (AppCompatButton) itemView.findViewById(R.id.btnApply);
            applyJobButton.setOnClickListener(this);
            if (!showApplyButton) {
                applyJobButton.setVisibility(View.GONE);
            }
            card_view = (CardView) itemView.findViewById(R.id.card_view);
        }

        @Override
        public void onClick(View v) {
            applyJob.jobApplied(jobArrayList.get(getAdapterPosition()));
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ImageView mLoadingImage ;
        public TextView mLoadingText ;
        public LinearLayout loadingViewLayout ;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            loadingViewLayout = (LinearLayout)itemView.findViewById(R.id.loadingViewLayout);
            mLoadingImage = (ImageView) itemView.findViewById(R.id.loadImage);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
            mLoadingText = (TextView) itemView.findViewById(R.id.loadingTextView);

        }
    }

    public void clearData() {
        int size = this.jobArrayList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.jobArrayList.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

}
