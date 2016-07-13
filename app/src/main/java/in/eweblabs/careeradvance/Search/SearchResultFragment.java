package in.eweblabs.careeradvance.Search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.eweblabs.careeradvance.Adapter.JobItemAdapter;
import in.eweblabs.careeradvance.BaseActivityScreen;
import in.eweblabs.careeradvance.Entity.Job;
import in.eweblabs.careeradvance.Interface.IRefreshList;
import in.eweblabs.careeradvance.R;

/**
 * Created by Akash.Singh on 1/9/2016.
 */
public class SearchResultFragment extends Fragment implements IRefreshList {
    ArrayList<Job> jobArrayList = new ArrayList<>();
    JobItemAdapter jobItemAdapter;
    RecyclerView recycler_view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_search_result_screen,container,false);
        ((BaseActivityScreen)getActivity()).SetToolbarInitialization(this);
        jobArrayList = (ArrayList<Job>) getArguments().getSerializable("Job");
        recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view);
        CreateJob();
        return view;
    }

    private void CreateJob() {
        recycler_view.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(llm);
        jobItemAdapter = new JobItemAdapter(getActivity(),jobArrayList,this);
        recycler_view.setAdapter(jobItemAdapter);
        jobItemAdapter.notifyDataSetChanged();

    }

    @Override
    public void onRefresh(Object object) {
        Job job = (Job) object;
        JobDetailFragment jobDetailFragment =  new JobDetailFragment();
        Bundle bundle =  new Bundle();
        bundle.putSerializable("JobDetail",job);
        jobDetailFragment.setArguments(bundle);
        ((BaseActivityScreen) getActivity()).onReplaceFragment(jobDetailFragment,true);
    }
}
