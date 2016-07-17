package in.eweblabs.careeradvance.Adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.eweblabs.careeradvance.Network.models.Result;
import in.eweblabs.careeradvance.R;

/**
 * Created by Anwar Shaikh on 12/11/2015.
 */
public class AppliedJobItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Result> listItems;
    Context context;


    public AppliedJobItemAdapter(Context context, ArrayList<Result> listItems) {
        this.context = context;
        this.listItems = listItems;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_view, parent, false);
        return new ViewItem(v);
    }

    private Result getItem(int position)
    {
        return listItems.get(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewItem Viewitem = (ViewItem)holder;
            final Result job = getItem(position);

            if(!TextUtils.isEmpty(job.getJobTitle()))
                Viewitem.text_job_title.setText(job.getJobTitle());
            else
                Viewitem.text_job_title.setText("");

        if(!TextUtils.isEmpty(job.getExperienceMin()))
            Viewitem.text_job_exp.setText(job.getExperienceMin()+" - "+job.getExperienceMax()+" Year");
        else
            Viewitem.text_job_exp.setText("");


        if(!TextUtils.isEmpty(job.getCompany()))
            Viewitem.text_company_name.setText(job.getCompany());
        else
            Viewitem.text_company_name.setText("");


        if(!TextUtils.isEmpty(job.getJobLoacationCity()))
            Viewitem.text_company_name.setText(job.getJobLoacationCity()+", "+job.getJobLocCountry());
        else
            Viewitem.text_company_name.setText("");
    }


    @Override
    public int getItemViewType(int position) {
          return position;
    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class ViewItem extends RecyclerView.ViewHolder {
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
            applyJobButton.setVisibility(View.GONE);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
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
