package in.eweblabs.careeradvance.Data;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.eweblabs.careeradvance.ApplicationController;
import in.eweblabs.careeradvance.Entity.City;
import in.eweblabs.careeradvance.Entity.Country;
import in.eweblabs.careeradvance.Entity.Currency;
import in.eweblabs.careeradvance.Entity.Job;
import in.eweblabs.careeradvance.Entity.Response;
import in.eweblabs.careeradvance.Entity.UserInfo;
import in.eweblabs.careeradvance.StaticData.StaticConstant;

/**
 * Created by Akash.Singh on 6/4/2015.
 * There has parse xml string.
 */
public class HJSONParsing {

    public HashMap<Object,Object> parseCountryData(String JSONResponse){
        HashMap<Object,Object> countryList = new HashMap<Object,Object>();
        try{
            JSONArray jsonArray =  new JSONArray(JSONResponse);
            for (int i=0;i<jsonArray.length();i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String countryCode = jsonObject.getString(StaticConstant.COUNTRY_CODE);
                String countryName = jsonObject.getString(StaticConstant.COUNTRYNAME);
                Country country = new Country(countryCode,countryName);
                countryList.put(country.getCountryCode(),country);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return countryList;
    }

    public HashMap<Object,Object> parseCityData(String JSONResponse){
        HashMap<Object,Object> cityList = new HashMap<Object,Object>();
        try{
            JSONArray jsonArray =  new JSONArray(JSONResponse);
            for (int i=0;i<jsonArray.length();i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String countryCode = jsonObject.getString(StaticConstant.CITY_CODE);
                String countryName = jsonObject.getString(StaticConstant.CITY_NAME);
                City city = new City(countryCode,countryName);
                cityList.put(city.getCityCode(),city);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return cityList;
    }

    public HashMap<Object,Object> parseCurrencyData(String JSONResponse){
        HashMap<Object,Object> annualSalaryList = new HashMap<Object,Object>();
        try{
            JSONArray jsonArray =  new JSONArray(JSONResponse);
            for (int i=0;i<jsonArray.length();i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String currencyCode = jsonObject.getString(StaticConstant.CURRENCY_CODE);
                String currencyName = jsonObject.getString(StaticConstant.CURRENCY_NAME);
                Currency currency = new Currency(currencyCode,currencyName);
                annualSalaryList.put(currency.getCurrencyCode(),currency);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return annualSalaryList;
    }




    public Object ParseLoginDetail(Context context,String Result){
        UserInfo userInfo =  new UserInfo();
        try {
            JSONObject jsonObjectLogin =  new JSONObject(Result);

            if(jsonObjectLogin.has(StaticConstant.MESSAGE)){
                userInfo.setMessage(jsonObjectLogin.getString(StaticConstant.MESSAGE));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_ID)){
                userInfo.setUserId(jsonObjectLogin.getString(StaticConstant.USER_ID));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_EMAIL)){
                userInfo.setUserEmail(jsonObjectLogin.getString(StaticConstant.USER_EMAIL));
            }
            if(jsonObjectLogin.has(StaticConstant.USERNAME)){
                userInfo.setUserName(jsonObjectLogin.getString(StaticConstant.USERNAME));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_GENDER)){
                userInfo.setUserGender(jsonObjectLogin.getString(StaticConstant.USER_GENDER));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_ACOUNT_TYPE)){
                userInfo.setUserAcountType(jsonObjectLogin.getString(StaticConstant.USER_ACOUNT_TYPE));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_DB)){
                userInfo.setUserDOB(jsonObjectLogin.getString(StaticConstant.USER_DB));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_DESIGNATION)){
                userInfo.setUserDesignation(jsonObjectLogin.getString(StaticConstant.USER_DESIGNATION));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_COMPANY)){
                userInfo.setUserCompany(jsonObjectLogin.getString(StaticConstant.USER_COMPANY));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_PHONE)){
                userInfo.setUserPhone(jsonObjectLogin.getString(StaticConstant.USER_PHONE));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_KEY_SKILLS)){
                userInfo.setUserKeySkills(jsonObjectLogin.getString(StaticConstant.USER_KEY_SKILLS));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_RESUME_HEADLINE)){
                userInfo.setUserResumeHeadline(jsonObjectLogin.getString(StaticConstant.USER_RESUME_HEADLINE));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_OBJECTIVE)){
                userInfo.setUserObjective(jsonObjectLogin.getString(StaticConstant.USER_OBJECTIVE));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_SALARY_EXPECTATION)){
                userInfo.setUserSalaryExpectation(jsonObjectLogin.getString(StaticConstant.USER_SALARY_EXPECTATION));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_LOCATION)){
                userInfo.setUserLocation(jsonObjectLogin.getString(StaticConstant.USER_LOCATION));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_HOME_TOWN_CITY)){
                userInfo.setUserHomeTownCity(jsonObjectLogin.getString(StaticConstant.USER_HOME_TOWN_CITY));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_ADDRESS)){
                userInfo.setUserAddress(jsonObjectLogin.getString(StaticConstant.USER_ADDRESS));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_ZIP)){
                userInfo.setUserZip(jsonObjectLogin.getString(StaticConstant.USER_ZIP));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_PERMANENT_ADDRESS)){
                userInfo.setUserPermanentAddress(jsonObjectLogin.getString(StaticConstant.USER_PERMANENT_ADDRESS));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_EXPERIENCE_YEAR)){
                userInfo.setUserExperienceYear(jsonObjectLogin.getString(StaticConstant.USER_EXPERIENCE_YEAR));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_EXPERIENCE_MONTH)){
                userInfo.setUserExperienceMonth(jsonObjectLogin.getString(StaticConstant.USER_EXPERIENCE_MONTH));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_SALARY_CURRENT)){
                userInfo.setUserPermanentAddress(jsonObjectLogin.getString(StaticConstant.USER_SALARY_CURRENT));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_CTC)){
                userInfo.setUserExperienceYear(jsonObjectLogin.getString(StaticConstant.USER_CTC));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_INDUSTRY)){
                userInfo.setUserIndustry(jsonObjectLogin.getString(StaticConstant.USER_INDUSTRY));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_FUNCTIONAL_AREA)){
                userInfo.setUserFunctionalArea(jsonObjectLogin.getString(StaticConstant.USER_FUNCTIONAL_AREA));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_BASIC_EDUCATION_TYPE)){
                userInfo.setUserBasicEducation(jsonObjectLogin.getString(StaticConstant.USER_BASIC_EDUCATION_TYPE));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_BASIC_EDUCATION_OTHER)){
                userInfo.setUserBasicEducationOther(jsonObjectLogin.getString(StaticConstant.USER_BASIC_EDUCATION_OTHER));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_MASTER_EDUCATION)){
                userInfo.setUserMasterEducation(jsonObjectLogin.getString(StaticConstant.USER_MASTER_EDUCATION));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_MASTER_EDUCATION_OTHER)){
                userInfo.setUserMasterEducationOther(jsonObjectLogin.getString(StaticConstant.USER_MASTER_EDUCATION_OTHER));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_DOCTRATE_EDUCATION)){
                userInfo.setUserDoctrateEducation(jsonObjectLogin.getString(StaticConstant.USER_DOCTRATE_EDUCATION));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_DIPLOMA_COURSE)){
                userInfo.setUserDiplomaCourse(jsonObjectLogin.getString(StaticConstant.USER_DIPLOMA_COURSE));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_RESUME_PATH)){
                userInfo.setUserResumePath(jsonObjectLogin.getString(StaticConstant.USER_RESUME_PATH));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_RESUME_TEXT)){
                userInfo.setUserResumeText(jsonObjectLogin.getString(StaticConstant.USER_RESUME_TEXT));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_JOB_ALERT)){
                userInfo.setUserJobAlert(jsonObjectLogin.getString(StaticConstant.USER_JOB_ALERT));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_FAST_FORWORD_EMAILS)){
                userInfo.setUserFastForwordEmails(jsonObjectLogin.getString(StaticConstant.USER_FAST_FORWORD_EMAILS));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_FAST_FORWORD_CALLS)){
                userInfo.setUserFastForwordCalls(jsonObjectLogin.getString(StaticConstant.USER_FAST_FORWORD_CALLS));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_NOTIFICATION)){
                userInfo.setUserNotification(jsonObjectLogin.getString(StaticConstant.USER_NOTIFICATION));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_COMMUNICATION_CLIENT)){
                userInfo.setUserCommunicationClient(jsonObjectLogin.getString(StaticConstant.USER_COMMUNICATION_CLIENT));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_SPECIAL_OFFER)){
                userInfo.setUserSpecialOffer(jsonObjectLogin.getString(StaticConstant.USER_SPECIAL_OFFER));
            }
            if(jsonObjectLogin.has(StaticConstant.USER_AVATAR)){
                userInfo.setUserAvatar(jsonObjectLogin.getString(StaticConstant.USER_AVATAR));
            }

            if(!TextUtils.isEmpty(userInfo.getUserId()))
            {
                ApplicationController.getInstance().getCareerAdvanceDBData().InsertUserRecord(userInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userInfo;
    }

    public Object ParseUpdateDetail(Context context,String Result){
        Response response =  new Response();
        try {
            JSONObject jsonObjectLogin =  new JSONObject(Result);

            if(jsonObjectLogin.has(StaticConstant.MESSAGE)){
                response.setMessage(jsonObjectLogin.getString(StaticConstant.MESSAGE));
            }
            if(jsonObjectLogin.has(StaticConstant.SUCCESS)){
                response.setSuccess(jsonObjectLogin.getString(StaticConstant.SUCCESS));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }

    public Object ParseApplyJob(Context context, String Result) {
        Response response =  new Response();
        try {
            JSONObject jsonObjectLogin =  new JSONObject(Result);

            if(jsonObjectLogin.has(StaticConstant.MESSAGE)){
                response.setMessage(jsonObjectLogin.getString(StaticConstant.MESSAGE));
            }
            if(jsonObjectLogin.has(StaticConstant.SUCCESS)){
                response.setSuccess(jsonObjectLogin.getString(StaticConstant.SUCCESS));
            }

            if(jsonObjectLogin.has("jobDetail"))
                response.setMessage(jsonObjectLogin.getString("jobDetail"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }
    public Object ParseForgetPassword(Context context, String Result) {
        Response response =  new Response();
        try {
            JSONObject jsonObjectLogin =  new JSONObject(Result);

            if(jsonObjectLogin.has(StaticConstant.MESSAGE)){
                response.setMessage(jsonObjectLogin.getString(StaticConstant.MESSAGE));
            }
            if(jsonObjectLogin.has(StaticConstant.SUCCESS)){
                response.setSuccess(jsonObjectLogin.getString(StaticConstant.SUCCESS));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Object ParseJobKeyword(Context context, String Result) {
        Response response =  new Response();
        try{
            JSONObject jsonObject =  new JSONObject(Result);
            if(jsonObject.has(StaticConstant.SUCCESS)){
                response.setSuccess(jsonObject.getString(StaticConstant.SUCCESS));
            }
            if(jsonObject.has(StaticConstant.KEYOWRDS)){
                Object object =  jsonObject.get(StaticConstant.KEYOWRDS);
                if(object instanceof JSONArray){
                    JSONArray jsonArray = jsonObject.getJSONArray(StaticConstant.KEYOWRDS);
                    if(jsonArray.length()>0)
                        ApplicationController.getInstance().getCareerAdvanceDBData().resetJobKeywordTable();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String jsonObjectkeyword =  jsonArray.getString(i);
                        ApplicationController.getInstance().getCareerAdvanceDBData().InsertJobKeyword(jsonObjectkeyword);
                    }

                }
                else if(jsonObject instanceof JSONObject){
                    ApplicationController.getInstance().getCareerAdvanceDBData().resetJobKeywordTable();
                    ApplicationController.getInstance().getCareerAdvanceDBData().InsertJobKeyword(
                            jsonObject.getString(StaticConstant.KEYOWRDS));

                }

            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public Object ParseJobSearch(Context context, String Result) {
        Response response =  new Response();
        try {
            JSONObject jsonObjectLogin =  new JSONObject(Result);

            if(jsonObjectLogin.has(StaticConstant.MESSAGE)){
                response.setMessage(jsonObjectLogin.getString(StaticConstant.MESSAGE));
            }
            if(jsonObjectLogin.has(StaticConstant.SUCCESS)){
                response.setSuccess(jsonObjectLogin.getString(StaticConstant.SUCCESS));
            }
            if(jsonObjectLogin.has(StaticConstant.JOBDETAIL)){
                Object object = jsonObjectLogin.get(StaticConstant.JOBDETAIL);
                ArrayList<Job> jobArrayList =  new ArrayList<Job>();
                if(object instanceof JSONArray){
                    JSONArray jsonArray =  jsonObjectLogin.getJSONArray(StaticConstant.JOBDETAIL);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        jobArrayList.add(ParseSingleJob(jsonObject));
                    }
                }
                else if(object instanceof  JSONObject){
                    JSONObject jsonObject = jsonObjectLogin.getJSONObject(StaticConstant.JOBDETAIL);
                    jobArrayList.add(ParseSingleJob(jsonObject));
                }
                response.setJobArrayList(jobArrayList);
                
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }


    public Object ParseAppliedJobSearch(Context context, String Result) {
        Response response =  new Response();
        try {
            JSONObject jsonObjectLogin =  new JSONObject(Result);

            if(jsonObjectLogin.has(StaticConstant.MESSAGE)){
                response.setMessage(jsonObjectLogin.getString(StaticConstant.MESSAGE));
            }
            if(jsonObjectLogin.has(StaticConstant.SUCCESS)){
                response.setSuccess(jsonObjectLogin.getString(StaticConstant.SUCCESS));
            }
            if(jsonObjectLogin.has(StaticConstant.RESULTS)){
                Object object = jsonObjectLogin.get(StaticConstant.RESULTS);
                ArrayList<Job> jobArrayList =  new ArrayList<Job>();
                if(object instanceof JSONArray){
                    JSONArray jsonArray =  jsonObjectLogin.getJSONArray(StaticConstant.RESULTS);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        jobArrayList.add(ParseSingleJob(jsonObject));
                    }
                }
                else if(object instanceof  JSONObject){
                    JSONObject jsonObject = jsonObjectLogin.getJSONObject(StaticConstant.RESULTS);
                    jobArrayList.add(ParseSingleJob(jsonObject));
                }
                response.setJobArrayList(jobArrayList);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    public Object ParseJobLocation(Context context, String Result) {
        Response response =  new Response();
        try{
            JSONObject jsonObject =  new JSONObject(Result);
            if(jsonObject.has(StaticConstant.SUCCESS)){
                response.setSuccess(jsonObject.getString(StaticConstant.SUCCESS));
            }
            if(jsonObject.has(StaticConstant.KEYOWRDS)){
                Object object =  jsonObject.get(StaticConstant.KEYOWRDS);
                if(object instanceof JSONArray){
                    JSONArray jsonArray = jsonObject.getJSONArray(StaticConstant.KEYOWRDS);
                    if(jsonArray.length()>0)
                        ApplicationController.getInstance().getCareerAdvanceDBData().resetJobLocationTable();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String jsonObjectkeyword =  jsonArray.getString(i);
                        ApplicationController.getInstance().getCareerAdvanceDBData().InsertJobLocation(jsonObjectkeyword);
                    }

                }
                else if(jsonObject instanceof JSONObject){
                    ApplicationController.getInstance().getCareerAdvanceDBData().resetJobLocationTable();
                    ApplicationController.getInstance().getCareerAdvanceDBData().InsertJobLocation(
                            jsonObject.getString(StaticConstant.KEYOWRDS));

                }

            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    private Job ParseSingleJob(JSONObject jsonObject) throws JSONException {
        Job job =  new Job();
        job.setJob_id(jsonObject.getString(StaticConstant.JOB_ID));
        job.setEmp_id(jsonObject.getString(StaticConstant.JOB_EMP_ID));
        job.setJob_title(jsonObject.getString(StaticConstant.JOB_TITLE));
        job.setJob_desc(jsonObject.getString(StaticConstant.JOB_DESC));
        job.setKeywords(jsonObject.getString(StaticConstant.JOB_KEYWORDS));
        job.setExperience_min(jsonObject.getString(StaticConstant.JOB_EXPERIENCE_MIN));
        job.setExperience_max(jsonObject.getString(StaticConstant.JOB_EXPERIENCE_MA));
        job.setCtc_currency(jsonObject.getString(StaticConstant.JOB_CTC_CURRENCY));
        job.setCtc_min(jsonObject.getString(StaticConstant.JOB_CTC_MIN));
        job.setCtc_max(jsonObject.getString(StaticConstant.JOB_CTC_MAX));
        job.setSalary_status(jsonObject.getString(StaticConstant.JOB_SALARY_STATUS));
        job.setOther_salary_detail(jsonObject.getString(StaticConstant.JOB_OTHER_SALARY_DETAIL));
        job.setVacancies(jsonObject.getString(StaticConstant.JOB_VACANCIES));
        job.setJob_loc_country(jsonObject.getString(StaticConstant.JOB_LOC_COUNTRY));
        job.setJob_loc_state(jsonObject.getString(StaticConstant.JOB_LOC_STATE));
        job.setJob_loacation_city(jsonObject.getString(StaticConstant.JOB_LOCATION_CITY));
        job.setIndustry(jsonObject.getString(StaticConstant.JOB_INDUSTRY));
        job.setOther_industry(jsonObject.getString(StaticConstant.JOB_OTHER_INDUSTRY));
        job.setFunctional_area(jsonObject.getString(StaticConstant.JOB_FUNCTIONAL_AREA));
        job.setUg_qualification(jsonObject.getString(StaticConstant.JOB_UQ_QUALIFICATION));
        job.setPg_qualification(jsonObject.getString(StaticConstant.JOB_PG_QUALIFICATION));
        job.setDoctrate(jsonObject.getString(StaticConstant.JOB_DOCTRATE));
        job.setCandidate_profile(jsonObject.getString(StaticConstant.JOB_CANDIDATE_PROFILE));
        job.setCompany(jsonObject.getString(StaticConstant.JOB_COMPANY));
        job.setHiring_for(jsonObject.getString(StaticConstant.JOB_HIRING_FOR));
        job.setAbout_company(jsonObject.getString(StaticConstant.JOB_ABOUT_COMPANY));
        job.setWebsite(jsonObject.getString(StaticConstant.JOB_WEBSITE));
        job.setContact_person(jsonObject.getString(StaticConstant.JOB_CONTACT_PERSON));
        job.setContact_number(jsonObject.getString(StaticConstant.JOB_CONTACT_NUMBER));
        job.setEmail_id(jsonObject.getString(StaticConstant.JOB_EMAIL_ID));
        job.setSend_query(jsonObject.getString(StaticConstant.JOB_SEND_QUERY));
        job.setDate_post(jsonObject.getString(StaticConstant.JOB_DATE_POST));
        job.setAddress(jsonObject.getString(StaticConstant.JOB_ADDRESS));
        job.setJob_type(jsonObject.getString(StaticConstant.JOB_TYPE));
        job.setShared_url(jsonObject.getString(StaticConstant.SHARED_URL));
        return  job;
    }
}
