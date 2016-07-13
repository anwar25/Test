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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import in.eweblabs.careeradvance.ApplicationController;
import in.eweblabs.careeradvance.Entity.Currency;
import in.eweblabs.careeradvance.Entity.UserInfo;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.Sort.CityComparator;
import in.eweblabs.careeradvance.Sort.CurrencyComparator;
import in.eweblabs.careeradvance.UI.CustomTextWatcher;
import in.eweblabs.careeradvance.Utils.TextUtility;

/**
 * Created by akash.singh on 11/24/2015.
 */
public class  YourCurrentEmploymentDetails extends Fragment implements View.OnClickListener {
    EditText edit_years,edit_months,edit_salary_1,edit_salary_2,edit_enter_a_headline_for_your_profile,edit_what_are_you_key_skills,
            edit_which_functional_area_do_you_work_in,edit_select_the_industry_and_functional_are_that_are_interest_in,edit_please_select_your_basic_education,edit_please_select_your_basic_education_other,edit_please_select_your_master_education,edit_please_select_your_master_education_other,edit_please_select_your_doctorate_education,edit_please_select_your_doctorate_education_other,edit_enter_the_certificate;
    TextInputLayout input_please_select_your_basic_education_other,input_please_select_your_master_education_other,input_please_select_your_doctorate_education_other;
   
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.your_current_employment_details,container,false);
        MidWidgetMapping(view);
        return view;
    }

    private void MidWidgetMapping(View view) {
        edit_years = (EditText) view.findViewById(R.id.edit_years);
        edit_months = (EditText) view.findViewById(R.id.edit_months);
        edit_salary_1 = (EditText) view.findViewById(R.id.edit_salary_1);
        edit_salary_2 = (EditText) view.findViewById(R.id.edit_salary_2);
        edit_enter_a_headline_for_your_profile = (EditText) view.findViewById(R.id.edit_enter_a_headline_for_your_profile);
        edit_what_are_you_key_skills = (EditText) view.findViewById(R.id.edit_what_are_you_key_skills);
        edit_which_functional_area_do_you_work_in = (EditText) view.findViewById(R.id.edit_which_functional_area_do_you_work_in);
        edit_select_the_industry_and_functional_are_that_are_interest_in = (EditText) view.findViewById(R.id.edit_select_the_industry_and_functional_are_that_are_interest_in);
        edit_please_select_your_basic_education = (EditText) view.findViewById(R.id.edit_please_select_your_basic_education);
        edit_please_select_your_master_education = (EditText) view.findViewById(R.id.edit_please_select_your_master_education);
        edit_please_select_your_doctorate_education = (EditText) view.findViewById(R.id.edit_please_select_your_doctorate_education);
        edit_please_select_your_basic_education_other = (EditText) view.findViewById(R.id.edit_please_select_your_basic_education_other);
        edit_please_select_your_master_education_other = (EditText) view.findViewById(R.id.edit_please_select_your_master_education_other);
        edit_please_select_your_doctorate_education_other = (EditText) view.findViewById(R.id.edit_please_select_your_doctorate_education_other);
        edit_enter_the_certificate = (EditText) view.findViewById(R.id.edit_enter_the_certificate);

        input_please_select_your_basic_education_other = (TextInputLayout) view.findViewById(R.id.input_please_select_your_basic_education_other);
        input_please_select_your_master_education_other = (TextInputLayout) view.findViewById(R.id.input_please_select_your_master_education_other);
        input_please_select_your_doctorate_education_other = (TextInputLayout) view.findViewById(R.id.input_please_select_your_doctorate_education_other);

        edit_please_select_your_basic_education_other.addTextChangedListener(new CustomTextWatcher(input_please_select_your_basic_education_other));
        edit_please_select_your_master_education_other.addTextChangedListener(new CustomTextWatcher(input_please_select_your_master_education_other));
        edit_please_select_your_doctorate_education_other.addTextChangedListener(new CustomTextWatcher(input_please_select_your_doctorate_education_other));
        //edit_years.setOnFocusChangeListener(this);
        edit_years.setOnClickListener(this);
        edit_years.setOnClickListener(this);
        edit_months.setOnClickListener(this);
        edit_salary_1.setOnClickListener(this);
        edit_salary_2.setOnClickListener(this);
        edit_which_functional_area_do_you_work_in.setOnClickListener(this);
        edit_select_the_industry_and_functional_are_that_are_interest_in.setOnClickListener(this);
        edit_please_select_your_basic_education.setOnClickListener(this);
        edit_please_select_your_master_education.setOnClickListener(this);
        edit_please_select_your_doctorate_education.setOnClickListener(this);
        edit_enter_the_certificate.setOnClickListener(this);

        initialization();

    }

    private void initialization() {

        InitializationExperienceYear();
        InitializationExperienceMonth();
        InitializationSalaryCurrency();
        InitializationCurrentSalary();

        edit_enter_a_headline_for_your_profile.setText(TextUtility.checkIsStringEmpty(((UpdateProfileScreen) getParentFragment()).userInfo.getUserResumeHeadline()));
        edit_what_are_you_key_skills.setText(TextUtility.checkIsStringEmpty(((UpdateProfileScreen) getParentFragment()).userInfo.getUserKeySkills()));
        edit_which_functional_area_do_you_work_in.setText(TextUtility.checkIsStringEmpty(((UpdateProfileScreen) getParentFragment()).userInfo.getUserFunctionalArea()));
        edit_select_the_industry_and_functional_are_that_are_interest_in.setText(TextUtility.checkIsStringEmpty(((UpdateProfileScreen) getParentFragment()).userInfo.getUserIndustry()));
        edit_enter_the_certificate.setText(TextUtility.checkIsStringEmpty(((UpdateProfileScreen) getParentFragment()).userInfo.getUserDiplomaCourse()));

        InitializationBasic();
        InitializationMaster();
        InitializationDoctorate();
    }

    private void InitializationCurrentSalary() {
        String Amount = "";
        String Amount_Value = ((UpdateProfileScreen)getParentFragment()).userInfo.getUserCTC();
        List<String> objectNameArrayListAmount = Arrays.asList(getResources().getStringArray(R.array.amount));
        List<String> objectCodeArrayListAmount = Arrays.asList(getResources().getStringArray(R.array.amount_value));
        for (int i = 0; i < objectNameArrayListAmount.size(); i++) {
            if(!TextUtils.isEmpty(Amount_Value) && objectCodeArrayListAmount.get(i).equalsIgnoreCase(Amount_Value)){
                Amount = objectNameArrayListAmount.get(i);
                break;
            }
        }

        edit_salary_2.setText(TextUtility.checkIsStringEmpty(Amount));

    }

    private void InitializationSalaryCurrency() {
        HashMap<Object,Object> objectArrayListCurr = ApplicationController.getInstance().getDataModel().GetCurrencyList();
        String CurrencyCode = ((UpdateProfileScreen)getParentFragment()).userInfo.getUserSalaryCurrency();
        Currency currency = (Currency) objectArrayListCurr.get(CurrencyCode);
        if(currency!=null)
            edit_salary_1.setText(TextUtility.checkIsStringEmpty(currency.getCurrencyName()));
        else
            edit_salary_1.setText("");


    }

    private void InitializationExperienceMonth() {
        String Month = "";
        String Month_Value = ((UpdateProfileScreen) getParentFragment()).userInfo.getUserExperienceMonth();
        List<String> objectNameArrayList = Arrays.asList(getResources().getStringArray(R.array.work_experience_month));
        List<String> objectCodeArrayList = Arrays.asList(getResources().getStringArray(R.array.work_experience_month_value));
        for (int i = 0; i < objectNameArrayList.size(); i++) {
            if(!TextUtils.isEmpty(Month_Value) && objectCodeArrayList.get(i).equalsIgnoreCase(Month_Value)){
                Month  = objectNameArrayList.get(i);
                break;
            }
        }

        if(!TextUtils.isEmpty(Month_Value) && Month_Value.equalsIgnoreCase("99"))
            edit_months.setVisibility(View.INVISIBLE);
        edit_months.setText(TextUtility.checkIsStringEmpty(Month));

    }

    private void InitializationExperienceYear() {
        String Year = "";
        String Year_Value = ((UpdateProfileScreen) getParentFragment()).userInfo.getUserExperienceYear();
        List<String> objectNameArrayListY = Arrays.asList(getResources().getStringArray(R.array.work_experience_year));
        List<String> objectCodeArrayListY = Arrays.asList(getResources().getStringArray(R.array.work_experience_year_value));
        for (int i = 0; i < objectNameArrayListY.size(); i++) {
            if(!TextUtils.isEmpty(Year_Value) && objectCodeArrayListY.get(i).equalsIgnoreCase(Year_Value)){
                Year  = objectNameArrayListY.get(i);
                break;
            }
        }
        edit_years.setText(Year);
    }

    private void InitializationBasic() {
        List<String> objectNameArrayList = Arrays.asList(getResources().getStringArray(R.array.select_your_Basic_Education));
        List<String> objectCodeArrayList = Arrays.asList(getResources().getStringArray(R.array.select_your_Basic_Education_value));
        final String DisplayName[] = new String[objectNameArrayList.size()];
        final String DisplayCode[] = new String[objectCodeArrayList.size()];

        for (int i = 0; i < DisplayName.length; i++) {
            DisplayCode[i] = objectCodeArrayList.get(i);
            DisplayName[i] = objectNameArrayList.get(i);
            if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserBasicEducation())&& ((UpdateProfileScreen)getParentFragment()).userInfo.getUserBasicEducation().equalsIgnoreCase(DisplayCode[i]))
            {
                edit_please_select_your_basic_education.setText(TextUtility.checkIsStringEmpty(DisplayName[i]));
                edit_please_select_your_basic_education.setTag(TextUtility.checkIsStringEmpty(DisplayCode[i]));
                input_please_select_your_basic_education_other.setVisibility(View.GONE);
            }
            else if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserBasicEducationOther())&& ((UpdateProfileScreen)getParentFragment()).userInfo.getUserBasicEducationOther().equalsIgnoreCase(DisplayCode[i]))
            {
                edit_please_select_your_basic_education.setText(getString(R.string.other));
                input_please_select_your_basic_education_other.setVisibility(View.VISIBLE);
                edit_please_select_your_basic_education_other.setText(TextUtility.checkIsStringEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserBasicEducationOther()));
                edit_please_select_your_basic_education_other.setTag(TextUtility.checkIsStringEmpty(DisplayCode[i]));
            }
        }
    }

    private void InitializationMaster() {
        List<String> objectNameArrayList = Arrays.asList(getResources().getStringArray(R.array.select_your_Masters_Education));
        List<String> objectCodeArrayList = Arrays.asList(getResources().getStringArray(R.array.select_your_Masters_Education_value));
        final String DisplayName[] = new String[objectNameArrayList.size()];
        final String DisplayCode[] = new String[objectCodeArrayList.size()];

        for (int i = 0; i < DisplayName.length; i++) {
            DisplayCode[i] = objectCodeArrayList.get(i);
            DisplayName[i] = objectNameArrayList.get(i);
            if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserMasterEducation())&& ((UpdateProfileScreen)getParentFragment()).userInfo.getUserMasterEducation().equalsIgnoreCase(DisplayCode[i]))
            {
                edit_please_select_your_master_education.setText(TextUtility.checkIsStringEmpty(DisplayName[i]));
                edit_please_select_your_master_education.setTag(TextUtility.checkIsStringEmpty(DisplayCode[i]));
                input_please_select_your_master_education_other.setVisibility(View.GONE);
            }
            else if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserMasterEducationOther())&& ((UpdateProfileScreen)getParentFragment()).userInfo.getUserMasterEducation().equalsIgnoreCase(DisplayCode[i]))
            {
                edit_please_select_your_master_education.setText(getString(R.string.other));
                input_please_select_your_master_education_other.setVisibility(View.VISIBLE);
                edit_please_select_your_master_education_other.setText(TextUtility.checkIsStringEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserMasterEducation()));
                edit_please_select_your_master_education_other.setTag(TextUtility.checkIsStringEmpty(DisplayCode[i]));
            }
        }
    }

    private void InitializationDoctorate() {
        List<String> objectNameArrayList = Arrays.asList(getResources().getStringArray(R.array.select_your_doctorate_Education));
        List<String> objectCodeArrayList = Arrays.asList(getResources().getStringArray(R.array.select_your_doctorate_Education_value));
        final String DisplayName[] = new String[objectNameArrayList.size()];
        final String DisplayCode[] = new String[objectCodeArrayList.size()];

        for (int i = 0; i < DisplayName.length; i++) {
            DisplayCode[i] = objectCodeArrayList.get(i);
            DisplayName[i] = objectNameArrayList.get(i);
            if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserDoctrateEducation())&& ((UpdateProfileScreen)getParentFragment()).userInfo.getUserDoctrateEducation().equalsIgnoreCase(DisplayCode[i]))
            {
                edit_please_select_your_doctorate_education.setText(TextUtility.checkIsStringEmpty(DisplayName[i]));
                edit_please_select_your_doctorate_education.setTag(TextUtility.checkIsStringEmpty(DisplayCode[i]));
                input_please_select_your_doctorate_education_other.setVisibility(View.GONE);
            }
            else if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserDoctrateEducationOther())&& ((UpdateProfileScreen)getParentFragment()).userInfo.getUserDoctrateEducationOther().equalsIgnoreCase(DisplayCode[i]))
            {
                edit_please_select_your_doctorate_education.setText(getString(R.string.other));
                input_please_select_your_doctorate_education_other.setVisibility(View.VISIBLE);
                edit_please_select_your_doctorate_education_other.setText(TextUtility.checkIsStringEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserDoctrateEducationOther()));
                edit_please_select_your_doctorate_education_other.setTag(TextUtility.checkIsStringEmpty(DisplayCode[i]));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_years:
                ChooseYears();
                break;
            case R.id.edit_months:
                ChooseMonth();
                break;
            case R.id.edit_salary_1:
                ChooseSalary1();
                break;
            case R.id.edit_salary_2:
                ChooseSalary2();
                break;
            case R.id.edit_select_the_industry_and_functional_are_that_are_interest_in:
                ChooseYourIndustry();
                break;
            case R.id.edit_which_functional_area_do_you_work_in:
                ChooseYourFunctionArea();
                break;
            case R.id.edit_please_select_your_basic_education:
                ChooseYourBasic();
                break;
            case R.id.edit_please_select_your_master_education:
                ChooseYourMaster();
                break;
            case R.id.edit_please_select_your_doctorate_education:
                ChooseYourDoctorate();
                break;

        }
    }

    private void ChooseYourDoctorate() {
        List<String> objectNameArrayList = Arrays.asList(getResources().getStringArray(R.array.select_your_doctorate_Education));
        List<String> objectCodeArrayList = Arrays.asList(getResources().getStringArray(R.array.select_your_doctorate_Education_value));
        final String DisplayName[] = new String[objectNameArrayList.size()];
        final String DisplayCode[] = new String[objectCodeArrayList.size()];
        int Position = 0;
        for (int i = 0; i < DisplayName.length; i++) {
            DisplayCode[i] = objectCodeArrayList.get(i);
            DisplayName[i] = objectNameArrayList.get(i);
            if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserDoctrateEducation())&& ((UpdateProfileScreen)getParentFragment()).userInfo.getUserDoctrateEducation().equalsIgnoreCase(DisplayCode[i]))
            {
                Position = i;
            }
            else if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserDoctrateEducationOther())&& ((UpdateProfileScreen)getParentFragment()).userInfo.getUserDoctrateEducationOther().equalsIgnoreCase(DisplayCode[i]))
            {
                Position = i;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.select_doctorate_education))
                .setSingleChoiceItems(DisplayName, Position, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(!DisplayCode[which].equalsIgnoreCase("9999"))
                        {
                            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserDoctrateEducation(DisplayCode[which]);
                            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserDoctrateEducationOther("");
                            edit_please_select_your_doctorate_education.setText(DisplayName[which]);
                            edit_please_select_your_doctorate_education.setTag(DisplayCode[which]);
                            input_please_select_your_doctorate_education_other.setVisibility(View.GONE);
                        }
                        else {
                            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserDoctrateEducation(DisplayCode[which]);
                            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserDoctrateEducationOther("");
                            edit_please_select_your_doctorate_education.setText(getString(R.string.other));
                            edit_please_select_your_doctorate_education_other.setText(TextUtility.checkIsStringEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserDoctrateEducationOther()));
                            edit_please_select_your_doctorate_education_other.setTag(TextUtility.checkIsStringEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserDoctrateEducationOther()));
                            input_please_select_your_doctorate_education_other.setVisibility(View.VISIBLE);
                        }



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

    private void ChooseYourMaster() {
        List<String> objectNameArrayList = Arrays.asList(getResources().getStringArray(R.array.select_your_Masters_Education));
        List<String> objectCodeArrayList = Arrays.asList(getResources().getStringArray(R.array.select_your_Masters_Education_value));
        final String DisplayName[] = new String[objectNameArrayList.size()];
        final String DisplayCode[] = new String[objectCodeArrayList.size()];
        int Position = 0;
        for (int i = 0; i < DisplayName.length; i++) {
            DisplayCode[i] = objectCodeArrayList.get(i);
            DisplayName[i] = objectNameArrayList.get(i);
            if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserMasterEducation())&& ((UpdateProfileScreen)getParentFragment()).userInfo.getUserMasterEducation().equalsIgnoreCase(DisplayCode[i]))
                Position = i;
            else if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserMasterEducationOther())&& ((UpdateProfileScreen)getParentFragment()).userInfo.getUserMasterEducationOther().equalsIgnoreCase(DisplayCode[i]))
                Position = i;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.select_master_education))
                .setSingleChoiceItems(DisplayName, Position, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(!DisplayCode[which].equalsIgnoreCase("9999"))
                        {
                            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserMasterEducation(DisplayCode[which]);
                            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserMasterEducationOther("");
                            edit_please_select_your_master_education.setText(DisplayName[which]);
                            edit_please_select_your_master_education.setTag(DisplayCode[which]);
                            input_please_select_your_master_education_other.setVisibility(View.GONE);
                        }
                        else {
                            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserMasterEducation(DisplayCode[which]);
                            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserMasterEducationOther("");
                            edit_please_select_your_master_education.setText(getString(R.string.other));
                            edit_please_select_your_master_education_other.setText(TextUtility.checkIsStringEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserMasterEducationOther()));
                            edit_please_select_your_master_education_other.setTag(TextUtility.checkIsStringEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserMasterEducationOther()));
                            input_please_select_your_master_education_other.setVisibility(View.VISIBLE);
                        }
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

    private void ChooseYourBasic() {
        List<String> objectNameArrayList = Arrays.asList(getResources().getStringArray(R.array.select_your_Basic_Education));
        List<String> objectCodeArrayList = Arrays.asList(getResources().getStringArray(R.array.select_your_Basic_Education_value));
        final String DisplayName[] = new String[objectNameArrayList.size()];
        final String DisplayCode[] = new String[objectCodeArrayList.size()];
        int Position = 0;
        for (int i = 0; i < DisplayName.length; i++) {
            DisplayCode[i] = objectCodeArrayList.get(i);
            DisplayName[i] = objectNameArrayList.get(i);
            if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserBasicEducation())&& ((UpdateProfileScreen)getParentFragment()).userInfo.getUserBasicEducation().equalsIgnoreCase(DisplayCode[i]))
            {
                Position = i;
            }
            else if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserBasicEducationOther())&& ((UpdateProfileScreen)getParentFragment()).userInfo.getUserBasicEducationOther().equalsIgnoreCase(DisplayCode[i]))
            {
                Position = i;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.select_basic_education))
                .setSingleChoiceItems(DisplayName, Position, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(!DisplayCode[which].equalsIgnoreCase("9999"))
                        {
                            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserBasicEducation(DisplayCode[which]);
                            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserBasicEducationOther("");
                            edit_please_select_your_basic_education.setText(DisplayName[which]);
                            edit_please_select_your_basic_education.setTag(DisplayCode[which]);
                            input_please_select_your_basic_education_other.setVisibility(View.GONE);
                        }
                        else {
                            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserBasicEducation(DisplayCode[which]);
                            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserBasicEducationOther("");
                            edit_please_select_your_basic_education.setText(getString(R.string.other));
                            edit_please_select_your_basic_education_other.setText(TextUtility.checkIsStringEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserBasicEducationOther()));
                            edit_please_select_your_basic_education_other.setTag(TextUtility.checkIsStringEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserBasicEducationOther()));
                            input_please_select_your_basic_education_other.setVisibility(View.VISIBLE);
                        }
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

    private void ChooseYourFunctionArea() {
        List<String> objectNameArrayList = Arrays.asList(getResources().getStringArray(R.array.select_function_your_area));
        final String DisplayName[] = new String[objectNameArrayList.size()];
        int Position = 0;
        for (int i = 0; i < DisplayName.length; i++) {
            DisplayName[i] = objectNameArrayList.get(i);
            if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserFunctionalArea())&& ((UpdateProfileScreen)getParentFragment()).userInfo.getUserFunctionalArea().equalsIgnoreCase(DisplayName[i]))
            {
                Position = i;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.select_your_function_ares))
                .setSingleChoiceItems(DisplayName, Position, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((UpdateProfileScreen)getParentFragment()).userInfo.setUserFunctionalArea(DisplayName[which]);
                        edit_which_functional_area_do_you_work_in.setText(DisplayName[which]);
                        edit_which_functional_area_do_you_work_in.setTag(DisplayName[which]);
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

    private void ChooseYourIndustry() {
        List<String> objectNameArrayList = Arrays.asList(getResources().getStringArray(R.array.select_your_industry));
        final String DisplayName[] = new String[objectNameArrayList.size()];
        int Position = 0;
        for (int i = 0; i < DisplayName.length; i++) {
            DisplayName[i] = objectNameArrayList.get(i);
            if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserIndustry())&& ((UpdateProfileScreen)getParentFragment()).userInfo.getUserIndustry().equalsIgnoreCase(DisplayName[i]))
            {
                Position = i;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.select_your_industry))
                .setSingleChoiceItems(DisplayName, Position, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((UpdateProfileScreen)getParentFragment()).userInfo.setUserIndustry(DisplayName[which]);
                        edit_select_the_industry_and_functional_are_that_are_interest_in.setText(DisplayName[which]);
                        edit_select_the_industry_and_functional_are_that_are_interest_in.setTag(DisplayName[which]);
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

    private void ChooseSalary1() {
        HashMap<Object,Object> objectArrayList = ApplicationController.getInstance().getDataModel().GetCurrencyList();
        final String CurrencyName[] = new String[objectArrayList.size()];
        final String DisplayName[] = new String[objectArrayList.size()];
        final String CurrencyCode[] = new String[objectArrayList.size()];
        int i = 0;
        ArrayList<Object> currencyArray =  new ArrayList<>();

        for(HashMap.Entry entry:objectArrayList.entrySet()){
            Currency currency = (Currency) entry.getValue();
            currencyArray.add(currency);
        }
        CurrencyComparator comparator =  new CurrencyComparator();
        comparator.sort(CityComparator.NAME_ATOZ,currencyArray);
        int Position = 1;
        for (Object object:currencyArray) {
            Currency city = (Currency) object;
            CurrencyName[i] = city.getCurrencyName();
            DisplayName[i] = city.getCurrencyName()+" ("+city.getCurrencyCode() +")";
            CurrencyCode[i] = city.getCurrencyCode();
            if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserSalaryCurrency())&& ((UpdateProfileScreen)getParentFragment()).userInfo.getUserSalaryCurrency().equalsIgnoreCase(
                    city.getCurrencyCode()))
            {
                Position = i;
            }
            i++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.select_currency))
                .setSingleChoiceItems(DisplayName, Position, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Currency currency = new Currency(CurrencyCode[which], CurrencyName[which]);
                        ((UpdateProfileScreen)getParentFragment()).userInfo.setUserSalaryCurrency(CurrencyCode[which]);
                        edit_salary_1.setText(currency.getCurrencyName());
                        edit_salary_1.setTag(CurrencyCode[which]);
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

    private void ChooseSalary2() {
        List<String> objectNameArrayList = Arrays.asList(getResources().getStringArray(R.array.amount));
        List<String> objectCodeArrayList = Arrays.asList(getResources().getStringArray(R.array.amount_value));
        final String DisplayName[] = new String[objectNameArrayList.size()];
        final String DisplayCode[] = new String[objectCodeArrayList.size()];
        int Position = 0;
        for (int i = 0; i < DisplayName.length; i++) {
            DisplayCode[i] = objectCodeArrayList.get(i);
            DisplayName[i] = objectNameArrayList.get(i);
            if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserCTC())&& ((UpdateProfileScreen)getParentFragment()).userInfo.getUserCTC().equalsIgnoreCase(DisplayCode[i]))
            {
                Position = i;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.select_salary))
                .setSingleChoiceItems(DisplayName, Position, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((UpdateProfileScreen)getParentFragment()).userInfo.setUserCTC(DisplayCode[which]);
                        edit_salary_2.setText(DisplayName[which]);
                        edit_salary_2.setTag(DisplayCode[which]);
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


    private void ChooseYears() {
            List<String> objectNameArrayList = Arrays.asList(getResources().getStringArray(R.array.work_experience_year));
            List<String> objectCodeArrayList = Arrays.asList(getResources().getStringArray(R.array.work_experience_year_value));
            final String DisplayName[] = new String[objectNameArrayList.size()];
            final String DisplayCode[] = new String[objectCodeArrayList.size()];
        int Position = 0;
        for (int i = 0; i < objectNameArrayList.size(); i++) {
            DisplayCode[i] = objectCodeArrayList.get(i);
            DisplayName[i] = objectNameArrayList.get(i);
            if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserExperienceYear())&& ((UpdateProfileScreen)getParentFragment()).userInfo.getUserExperienceYear().equalsIgnoreCase(objectNameArrayList.get(i)))
            {
                Position = i;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.years))
                    .setSingleChoiceItems(DisplayName, Position, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ((UpdateProfileScreen)getParentFragment()).userInfo.setUserExperienceYear(DisplayCode[which]);
                            edit_years.setText(DisplayName[which]);
                            edit_years.setTag(DisplayCode[which]);
                            if (!DisplayCode[which].equalsIgnoreCase("99")) {
                                edit_months.setVisibility(View.VISIBLE);

                            } else {
                                edit_months.setVisibility(View.INVISIBLE);
                            }
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

    private void ChooseMonth() {
        List<String> objectNameArrayList = Arrays.asList(getResources().getStringArray(R.array.work_experience_month));
        List<String> objectCodeArrayList = Arrays.asList(getResources().getStringArray(R.array.work_experience_month_value));
        final String DisplayName[] = new String[objectNameArrayList.size()];
        final String DisplayCode[] = new String[objectCodeArrayList.size()];
        int Position = 0;
        for (int i = 0; i < DisplayName.length; i++) {
            DisplayCode[i] = objectCodeArrayList.get(i);
            DisplayName[i] = objectNameArrayList.get(i);
            if(!TextUtils.isEmpty(((UpdateProfileScreen)getParentFragment()).userInfo.getUserExperienceMonth())&& ((UpdateProfileScreen)getParentFragment()).userInfo.getUserExperienceMonth().equalsIgnoreCase(DisplayCode[i]))
            {
                Position = i;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.months))
                .setSingleChoiceItems(DisplayName, Position, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        edit_months.setText(DisplayName[which]);
                        edit_months.setTag(DisplayCode[which]);
                        ((UpdateProfileScreen) getParentFragment()).userInfo.setUserExperienceMonth(DisplayCode[which]);
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


    public UserInfo CheckContactInformationStatus() {
        if(input_please_select_your_basic_education_other.getVisibility() == View.VISIBLE
                && TextUtils.isEmpty(edit_please_select_your_basic_education_other.getText().toString()))      {
            input_please_select_your_basic_education_other.setError(getString(R.string.select_basic_education));
            return null;
        }
        else if(input_please_select_your_master_education_other.getVisibility() == View.VISIBLE
                && TextUtils.isEmpty(edit_please_select_your_master_education_other.getText().toString()))      {
            input_please_select_your_master_education_other.setError(getString(R.string.select_master_education));
            return null;
        }
        else if(input_please_select_your_doctorate_education_other.getVisibility() == View.VISIBLE
                && TextUtils.isEmpty(edit_please_select_your_doctorate_education_other.getText().toString()))      {
            input_please_select_your_doctorate_education_other.setError(getString(R.string.select_doctorate_education));
            return null;
        }
        ((UpdateProfileScreen) getParentFragment()).userInfo.setUserResumeHeadline(edit_enter_a_headline_for_your_profile.getText().toString());
        ((UpdateProfileScreen) getParentFragment()).userInfo.setUserKeySkills(edit_what_are_you_key_skills.getText().toString());
        ((UpdateProfileScreen) getParentFragment()).userInfo.setUserFunctionalArea(edit_which_functional_area_do_you_work_in.getText().toString());
        ((UpdateProfileScreen) getParentFragment()).userInfo.setUserIndustry(edit_select_the_industry_and_functional_are_that_are_interest_in.getText().toString());
        ((UpdateProfileScreen) getParentFragment()).userInfo.setUserDiplomaCourse(edit_enter_the_certificate.getText().toString());
        return ((UpdateProfileScreen)getParentFragment()).userInfo;
    }
}
