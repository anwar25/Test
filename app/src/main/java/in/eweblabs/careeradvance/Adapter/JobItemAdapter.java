package in.eweblabs.careeradvance.Adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.eweblabs.careeradvance.Entity.Job;
import in.eweblabs.careeradvance.Interface.IRefreshList;
import in.eweblabs.careeradvance.R;

/**
 * Created by Akash.Singh on 12/11/2015.
 */
public class JobItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Job> listItems;
    FragmentActivity context;
    IRefreshList iRefreshList;

    private final  ApplyJobListener applyJob ;
    public interface ApplyJobListener{
        void jobApplied(Job job);
    }

    public JobItemAdapter(FragmentActivity context, ArrayList<Job> listItems, IRefreshList iRefreshList,
                          ApplyJobListener applyJobListener) {
        this.context = context;
        this.listItems = listItems;
        this.iRefreshList = iRefreshList;
        this.applyJob  = applyJobListener ;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_view, parent, false);
        return new ViewItem(v);
    }

    private Job getItem(int position)
    {
        return listItems.get(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewItem Viewitem = (ViewItem)holder;
            final Job job = getItem(position);

            if(!TextUtils.isEmpty(job.getJob_title()))
                Viewitem.text_job_title.setText(job.getJob_title());
            else
                Viewitem.text_job_title.setText("");

        if(!TextUtils.isEmpty(job.getExperience_min()))
            Viewitem.text_job_exp.setText(job.getExperience_min()+" - "+job.getExperience_max()+" Year");
        else
            Viewitem.text_job_exp.setText("");


        if(!TextUtils.isEmpty(job.getCompany()))
            Viewitem.text_company_name.setText(job.getCompany());
        else
            Viewitem.text_company_name.setText("");


        if(!TextUtils.isEmpty(job.getJob_loacation_city()))
            Viewitem.text_company_name.setText(job.getJob_loacation_city()+", "+job.getJob_loc_country());
        else
            Viewitem.text_company_name.setText("");

        Viewitem.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iRefreshList.onRefresh(getItem(position));
            }
        });
    }


    @Override
    public int getItemViewType(int position) {
          return position;
    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class ViewItem extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView text_job_location,text_job_title,text_company_name,text_job_exp;
        CardView card_view;
        public AppCompatButton applyJobButton ;
        public ViewItem(View itemView) {
            super(itemView);
            text_job_title = (TextView)itemView.findViewById(R.id.text_job_title);
            text_company_name = (TextView)itemView.findViewById(R.id.text_company_name);
            text_job_exp = (TextView)itemView.findViewById(R.id.text_job_exp);
            text_job_location = (TextView) itemView.findViewById(R.id.text_job_location);
            applyJobButton = (AppCompatButton) itemView.findViewById(R.id.btnApply);
            applyJobButton.setOnClickListener(this);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
        }

        @Override
        public void onClick(View v) {
            applyJob.jobApplied(listItems.get(getAdapterPosition()));
        }
    }

    public void clearData() {
        int size = this.listItems.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.listItems.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

}
