package in.eweblabs.careeradvance.Account.UpdateProfile;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import in.eweblabs.careeradvance.ApplicationController;
import in.eweblabs.careeradvance.Entity.City;
import in.eweblabs.careeradvance.Entity.Country;
import in.eweblabs.careeradvance.Entity.UserInfo;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.Sort.CityComparator;
import in.eweblabs.careeradvance.Sort.CountryComparator;
import in.eweblabs.careeradvance.StaticData.StaticConstant;
import in.eweblabs.careeradvance.UI.CustomTextWatcher;

/**
 * Created by Anil on 11/24/2015.
 */
public class YourContactInformation extends Fragment implements View.OnClickListener {
    TextInputLayout input_first_name,input_email_address,input_objective,input_address,input_home_down_city,input_postal_code,input_contact_number;
    EditText edit_first_name,edit_email_address,edit_objective,edit_Where_are_you_currently_located,edit_province,
            edit_address,edit_home_down_city,edit_postal_code,edit_contact_number;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.your_contact_information,container,false);

        MidWidgetMapping(view);
        return view;
    }

    private void MidWidgetMapping(View view) {
        input_first_name = (TextInputLayout) view.findViewById(R.id.input_first_name);
        input_email_address = (TextInputLayout) view.findViewById(R.id.input_email_address);
        input_objective = (TextInputLayout) view.findViewById(R.id.input_objective);
        input_address = (TextInputLayout) view.findViewById(R.id.input_address);
        input_home_down_city = (TextInputLayout) view.findViewById(R.id.input_home_down_city);
        input_postal_code = (TextInputLayout) view.findViewById(R.id.input_postal_code);
        input_contact_number = (TextInputLayout) view.findViewById(R.id.input_contact_number);

        edit_first_name = (EditText) view.findViewById(R.id.edit_first_name);
        edit_email_address = (EditText) view.findViewById(R.id.edit_email_address);
        edit_objective = (EditText) view.findViewById(R.id.edit_objective);
        edit_Where_are_you_currently_located = (EditText) view.findViewById(R.id.edit_Where_are_you_currently_located);
        edit_province = (EditText) view.findViewById(R.id.edit_province);
        edit_address = (EditText) view.findViewById(R.id.edit_address);
        edit_home_down_city = (EditText) view.findViewById(R.id.edit_home_down_city);
        edit_postal_code = (EditText) view.findViewById(R.id.edit_postal_code);
        edit_contact_number = (EditText) view.findViewById(R.id.edit_contact_number);

        initialization();
        edit_first_name.addTextChangedListener(new CustomTextWatcher(input_first_name));
        edit_objective.addTextChangedListener(new CustomTextWatcher(input_objective));
        edit_address.addTextChangedListener(new CustomTextWatcher(input_address));
        edit_home_down_city.addTextChangedListener(new CustomTextWatcher(input_objective));
        edit_postal_code.addTextChangedListener(new CustomTextWatcher(input_postal_code));
        edit_contact_number.addTextChangedListener(new CustomTextWatcher(input_contact_number));
        edit_Where_are_you_currently_located.setOnClickListener(this);
        edit_province.setOnClickListener(this);

    }
    String stateUser = "";
    String countryUser = "";
    private void initialization() {

        String UserLocation = ((UpdateProfileScreen)getParentFragment()).userInfo.getUserLocation();

        if(!TextUtils.isEmpty(UserLocation))
        {
            String[] userLocation = UserLocation.split(", ");
            stateUser = userLocation[0];
            countryUser = userLocation[1];
        }

        if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserName()))
            edit_first_name.setText(((UpdateProfileScreen)getParentFragment()).userInfo.getUserName());
        if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserEmail()))
            edit_email_address.setText(((UpdateProfileScreen)getParentFragment()).userInfo.getUserEmail());
        if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserObjective()))
            edit_objective.setText(((UpdateProfileScreen)getParentFragment()).userInfo.getUserObjective());
        if(!TextUtils.isEmpty(countryUser))
        {
            HashMap<Object,Object> objectArrayList = ApplicationController.getInstance().getDataModel().GetCountryList();
            Country country  = (Country) objectArrayList.get(countryUser);
            if(country!=null)
                edit_Where_are_you_currently_located.setText(country.getCountryName());
        }
        if(!TextUtils.isEmpty(stateUser))
        {
            HashMap<Object,Object> objectArrayList = ApplicationController.getInstance().getDataModel().GetCityList();

            City city  = (City) objectArrayList.get(stateUser);
            if(stateUser!=null)
            edit_province.setText(city.getCityName());


        }
        if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserObjective()))
            edit_address.setText(((UpdateProfileScreen)getParentFragment()).userInfo.getUserAddress());
        if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserHomeTownCity()))
            edit_home_down_city.setText(((UpdateProfileScreen)getParentFragment()).userInfo.getUserHomeTownCity());
        if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserZip()))
            edit_postal_code.setText(((UpdateProfileScreen)getParentFragment()).userInfo.getUserZip());
        if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserPhone()))
            edit_contact_number.setText(((UpdateProfileScreen)getParentFragment()).userInfo.getUserPhone());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_Where_are_you_currently_located:
                ChooseYouCurrentlyLocation();
                break;
            case R.id.edit_province:
                ChooseYourProvinceLocation();
                break;
        }
    }

    private void ChooseYouCurrentlyLocation() {
            HashMap<Object,Object> objectArrayList = ApplicationController.getInstance().getDataModel().GetCountryList();
            final String CountryName[] = new String[objectArrayList.size()];
            final String DisplayName[] = new String[objectArrayList.size()];
            final String CountryCode[] = new String[objectArrayList.size()];
            int i = 0;
            ArrayList<Object> countryArray =  new ArrayList<>();

            for(HashMap.Entry entry:objectArrayList.entrySet()){
                Country country = (Country) entry.getValue();
                countryArray.add(country);
            }
            CountryComparator comparator =  new CountryComparator();
            comparator.sort(CountryComparator.NAME_ATOZ,countryArray);
            int Position = 1;
            for (Object object:countryArray) {
                Country country = (Country) object;
                CountryName[i] = country.getCountryName();
                DisplayName[i] = country.getCountryName()+" ("+country.getCountryCode() +")";
                CountryCode[i] = country.getCountryCode();
                if((!TextUtils.isEmpty(countryUser)&& countryUser.equalsIgnoreCase(country.getCountryCode())))
                {
                    Position = i;
                }
                i++;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.search_country_text))
                    .setSingleChoiceItems(DisplayName, Position, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Country country = new Country(CountryCode[which], CountryName[which]);
                            edit_Where_are_you_currently_located.setText(country.getCountryName());
                            edit_Where_are_you_currently_located.setTag(country);
                            edit_province.requestFocus();
                            edit_province.setCursorVisible(true);
                            countryUser = country.getCountryCode();
                            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserLocation(stateUser + StaticConstant.Regular_Expression_LOCATION+ countryUser);
                            dialog.dismiss();

                        }
                    });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

            builder.create().show();


    }

    private void ChooseYourProvinceLocation() {
        HashMap<Object,Object> objectArrayList = ApplicationController.getInstance().getDataModel().GetCityList();
        final String CityName[] = new String[objectArrayList.size()];
        final String DisplayName[] = new String[objectArrayList.size()];
        final String CityCode[] = new String[objectArrayList.size()];
        int i = 0;
        ArrayList<Object> cityArray =  new ArrayList<>();

        for(HashMap.Entry entry:objectArrayList.entrySet()){
            City country = (City) entry.getValue();
            cityArray.add(country);
        }
        CityComparator comparator =  new CityComparator();
        comparator.sort(CityComparator.NAME_ATOZ,cityArray);
        int Position = 1;
        for (Object object:cityArray) {
            City city = (City) object;
            CityName[i] = city.getCityName();
            DisplayName[i] = city.getCityName()+" ("+city.getCityCode() +")";
            CityCode[i] = city.getCityCode();
            if(!TextUtils.isEmpty(stateUser)&& stateUser.equalsIgnoreCase(city.getCityCode()))
            {
                Position = i;
            }
            i++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.search_province_text))
                .setSingleChoiceItems(DisplayName, Position, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        City city = new City(CityCode[which], CityName[which]);
                        edit_province.setText(city.getCityName());
                        edit_province.setTag(city);
                        stateUser   = city.getCityCode();
                        ((UpdateProfileScreen)getParentFragment()).userInfo.setUserLocation(stateUser + StaticConstant.Regular_Expression_LOCATION + countryUser);
                        edit_address.requestFocus();
                        edit_address.setCursorVisible(true);
                        dialog.dismiss();

                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.create().show();


    }

    public UserInfo CheckContactInformationStatus(){
        if(TextUtils.isEmpty(edit_first_name.getText().toString()))      {
            input_first_name.setError(getString(R.string.hint_select_your_first_name));
           return ((UpdateProfileScreen)getParentFragment()).userInfo;
        }
        else if(!TextUtils.isEmpty(stateUser) && TextUtils.isEmpty(countryUser))
        {
            Toast.makeText(getActivity(),"please select Country",Toast.LENGTH_LONG).show();
            return ((UpdateProfileScreen)getParentFragment()).userInfo;
        }
        else if(TextUtils.isEmpty(stateUser) && !TextUtils.isEmpty(countryUser))
        {
            Toast.makeText(getActivity(),"please select province",Toast.LENGTH_LONG).show();
            return ((UpdateProfileScreen)getParentFragment()).userInfo;
        }
        if(!TextUtils.isEmpty(edit_first_name.getText().toString()))
            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserName(edit_first_name.getText().toString());
        if(!TextUtils.isEmpty(edit_email_address.getText().toString()))
            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserEmail(edit_email_address.getText().toString());
        if(!TextUtils.isEmpty(edit_objective.getText().toString()))
            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserObjective(edit_objective.getText().toString());
        if(!TextUtils.isEmpty(edit_postal_code.getText().toString()))
            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserZip(edit_postal_code.getText().toString());
        if(!TextUtils.isEmpty(edit_contact_number.getText().toString()))
            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserPhone(edit_contact_number.getText().toString());
        if(!TextUtils.isEmpty(edit_address.getText().toString()))
            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserAddress(edit_address.getText().toString());
        return ((UpdateProfileScreen)getParentFragment()).userInfo;
    }



}
