package in.eweblabs.careeradvance.Search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.eweblabs.careeradvance.BaseActivityScreen;
import in.eweblabs.careeradvance.R;

/**
 * Created by Anwar on 8/16/2016.
 */
public class FilterFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_fragment_layout,container,false);
        ((BaseActivityScreen)getActivity()).setToolbarInitialization(this);
        return view;
    }
}
