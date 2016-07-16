package in.eweblabs.careeradvance.Search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import in.eweblabs.careeradvance.ApplicationController;
import in.eweblabs.careeradvance.BaseActivityScreen;
import in.eweblabs.careeradvance.Adapter.CountryAdapter;
import in.eweblabs.careeradvance.Entity.Country;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.SharedPreferences.PreHelper;
import in.eweblabs.careeradvance.Sort.CountryComparator;
import in.eweblabs.careeradvance.StaticData.StaticConstant;
import in.eweblabs.careeradvance.Utils.Logger;
import in.eweblabs.careeradvance.Utils.Utils;

/**
 * Created by Anil on 11/18/2015.
 */
public class ChangeCountry extends Fragment {
    ListView lv_country;
    ArrayList<Object>  nationalityArray =  new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_country_screen,container,false);
        ((BaseActivityScreen)getActivity()).setToolbarInitialization(this);
        WidgetMapping(view);
        return view;
    }

    private void WidgetMapping(View view) {
        lv_country = (ListView) view.findViewById(R.id.lv_country);
        ShowCountryChangeView();
    }

    public void ShowCountryChangeView() {

        CountryChangeView();
        final CountryAdapter countryAdapter =  new CountryAdapter(getActivity(),nationalityArray);
        lv_country.setAdapter(countryAdapter);
        countryAdapter.notifyDataSetChanged();
        lv_country.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Utils.hideSoftKeyboard(getActivity(),view);
                Country country = (Country) nationalityArray.get(position);
                PreHelper.storeString(getActivity(), StaticConstant.COUNTRY_CODE, country.getCountryCode());
                countryAdapter.notifyDataSetChanged();
                ((BaseActivityScreen) getActivity()).onBackPressed();

            }
        });

        ((BaseActivityScreen)getActivity()).edit_search_country.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Logger.d("afterTextChanged", "s" + s);
                if (s.length() > 0) {
                    ArrayList<Object> temporaryArray = new ArrayList<>();
                    for (Object curVal : nationalityArray) {
                        Country country = (Country) curVal;
                        if (country.getCountryName().toLowerCase().startsWith(s.toString().toLowerCase())) {
                            temporaryArray.add(curVal);
                        }
                    }
                    nationalityArray.clear();
                    for (Object object : temporaryArray) {
                        Country nationality = (Country) object;
                        nationalityArray.add(nationality);
                    }
                    CountryComparator comparator = new CountryComparator();
                    comparator.sort(CountryComparator.NAME_ATOZ, nationalityArray);
                    countryAdapter.notifyDataSetChanged();
                } else {
                    CountryChangeView();
                    countryAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void CountryChangeView() {
        nationalityArray.clear();
        HashMap<Object,Object> objectArrayList = ApplicationController.getInstance().getDataModel().GetCountryList();
        for(HashMap.Entry entry:objectArrayList.entrySet()){
            Country nationality = (Country) entry.getValue();
            nationalityArray.add(nationality);
        }
        CountryComparator comparator =  new CountryComparator();
        comparator.sort(CountryComparator.NAME_ATOZ, nationalityArray);

    }


}
