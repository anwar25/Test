package in.eweblabs.careeradvance.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import in.eweblabs.careeradvance.Entity.RecentSearch;
import in.eweblabs.careeradvance.Entity.UserInfo;
import in.eweblabs.careeradvance.StaticData.DbConstraints;
import in.eweblabs.careeradvance.StaticData.StaticConstant;


/**
 * Created by Akash.Singh on 7/30/2015.
 */
public class CareerAdvanceDBData extends DbConstraints {


    DatabaseManager databaseManager;
    SQLiteDatabase database;

    public CareerAdvanceDBData(Context context) {
        databaseManager = new DatabaseManager(context);

    }

    public void closeDB() {
        database.close();
    }


    public void openDB() {
        database = databaseManager.getReadableDatabase();
    }

    public void InsertUserRecord(UserInfo userInfo){
        openDB();
        database.beginTransaction();
        try {
            ContentValues contentvalues = new ContentValues();
            contentvalues.put(UR_USER_ID,userInfo.getUserId());
            contentvalues.put(UR_USER_USER_EMAIL,userInfo.getUserEmail());
            contentvalues.put(UR_USER_USERNAME,userInfo.getUserName());
            contentvalues.put(UR_USER_USER_GENDER,userInfo.getUserGender());
            contentvalues.put(UR_USER_ACOUNT_TYPE,userInfo.getUserAcountType());
            contentvalues.put(UR_USER_USER_DB,userInfo.getUserDOB());
            contentvalues.put(UR_USER_DESIGNATION,userInfo.getUserDesignation());
            contentvalues.put(UR_USER_COMPANY,userInfo.getUserCompany());
            contentvalues.put(UR_USER_PHONE,userInfo.getUserPhone());
            contentvalues.put(UR_USER_KEY_SKILLS,userInfo.getUserKeySkills());
            contentvalues.put(UR_USER_RESUME_HEADLINE,userInfo.getUserResumeHeadline());
            contentvalues.put(UR_USER_OBJECTIVE,userInfo.getUserObjective());
            contentvalues.put(UR_USER_SALARY_EXPECTATION,userInfo.getUserSalaryExpectation());
            contentvalues.put(UR_USER_LOCATION,userInfo.getUserLocation());
            contentvalues.put(UR_USER_HOME_TOWN_CITY,userInfo.getUserHomeTownCity());
            contentvalues.put(UR_USER_ADDRESS,userInfo.getUserAddress());
            contentvalues.put(UR_USER_ZIP,userInfo.getUserZip());
            contentvalues.put(UR_USER_PERMANENT_ADDRESS,userInfo.getUserPermanentAddress());
            contentvalues.put(UR_USER_EXPERIENCE_YEAR,userInfo.getUserExperienceYear());
            contentvalues.put(UR_USER_EXPERIENCE_MONTH,userInfo.getUserExperienceMonth());
            contentvalues.put(UR_USER_SALARY_CURRENT,userInfo.getUserSalaryCurrency());
            contentvalues.put(UR_USER_CTC,userInfo.getUserCTC());
            contentvalues.put(UR_USER_INDUSTRY,userInfo.getUserIndustry());
            contentvalues.put(UR_USER_FUNCTIONAL_AREA,userInfo.getUserFunctionalArea());
            contentvalues.put(UR_USER_BASIC_EDUCATION_TYPE,userInfo.getUserBasicEducation());
            contentvalues.put(UR_USER_BASIC_EDUCATION_OTHER,userInfo.getUserBasicEducationOther());
            contentvalues.put(UR_USER_USER_MASTER_EDUCATION,userInfo.getUserMasterEducation());
            contentvalues.put(UR_USER_MASTER_EDUCATION_OTHER,userInfo.getUserMasterEducationOther());
            contentvalues.put(UR_USER_DOCTRATE_EDUCATION,userInfo.getUserDoctrateEducation());
            contentvalues.put(UR_USER_DOCTRATE_EDUCATION_OTHER,userInfo.getUserDoctrateEducationOther());
            contentvalues.put(UR_USER_DIPLOMA_COURSE,userInfo.getUserDiplomaCourse());
            contentvalues.put(UR_USER_RESUME_PATH,userInfo.getUserResumePath());
            contentvalues.put(UR_USER_RESUME_TEXT,userInfo.getUserResumeText());
            contentvalues.put(UR_USER_JOB_ALERT,userInfo.getUserJobAlert());
            contentvalues.put(UR_USER_FAST_FORWORD_EMAILS,userInfo.getUserFastForwordEmails());
            contentvalues.put(UR_USER_FAST_FORWORD_CALLS,userInfo.getUserFastForwordCalls());
            contentvalues.put(UR_USER_NOTIFICATION,userInfo.getUserNotification());
            contentvalues.put(UR_USER_COMMUNICATION_CLIENT,userInfo.getUserCommunicationClient());
            contentvalues.put(UR_USER_USER_SPECIAL_OFFER,userInfo.getUserSpecialOffer());
            contentvalues.put(UR_USER_USER_AVATAR,userInfo.getUserAvatar());


            String Query = "Select * From "+USER_RECORD_TABLE_NAME+" Where "+UR_USER_ID+"='"+userInfo.getUserId()+"'";
            Cursor cursor = database.rawQuery(Query, null);
                if (cursor != null && cursor.moveToFirst())
                    database.update(USER_RECORD_TABLE_NAME, contentvalues, UR_USER_ID +"='"+userInfo.getUserId()+"'", null);
                else
                    database.insert(USER_RECORD_TABLE_NAME, null, contentvalues);
            database.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            database.endTransaction();
            // closeDB();
        }
    }


    public void InsertJobKeyword(String keyword){
        openDB();
        database.beginTransaction();
        try {
            ContentValues contentvalues = new ContentValues();
            contentvalues.put(CA_KEYWORD,keyword);
            String Query = "Select * From "+USER_JOB_KEYWORDS_TABLE_NAME+" Where "+CA_KEYWORD+"='"+keyword+"'";
            Cursor cursor = database.rawQuery(Query, null);
            if (cursor != null && cursor.moveToFirst())
            {
                int id = cursor.getInt(cursor.getColumnIndex(CA_ID));
                database.update(USER_JOB_KEYWORDS_TABLE_NAME, contentvalues, CA_ID +"='"+id+"'", null);
            }
            else
                database.insert(USER_JOB_KEYWORDS_TABLE_NAME, null, contentvalues);
            database.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            database.endTransaction();
            // closeDB();
        }
    }

    public void InsertJobLocation(String keyword){
        openDB();
        database.beginTransaction();
        try {
            ContentValues contentvalues = new ContentValues();
            contentvalues.put(CA_LOCATION,keyword);
            String Query = "Select * From "+USER_JOB_LOCATION_TABLE_NAME+" Where "+CA_LOCATION+"='"+keyword+"'";
            Cursor cursor = database.rawQuery(Query, null);
            if (cursor != null && cursor.moveToFirst())
            {
                int id = cursor.getInt(cursor.getColumnIndex(CA_ID));
                database.update(USER_JOB_LOCATION_TABLE_NAME, contentvalues, CA_ID +"='"+id+"'", null);
            }
            else
                database.insert(USER_JOB_LOCATION_TABLE_NAME, null, contentvalues);
            database.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            database.endTransaction();
            // closeDB();
        }
    }

    public void InsertRecentSearchLocation(RecentSearch recentSearch){
        openDB();
        database.beginTransaction();
        try {
            ContentValues contentvalues = new ContentValues();
            contentvalues.put(CA_KEYWORD,recentSearch.getKeyword());
            contentvalues.put(CA_LOCATION,recentSearch.getLocation());
            String Query = "Select * From "+USER_RECENT_SEARCH_TABLE_NAME+" Where "+CA_KEYWORD+"='"+recentSearch.getKeyword()+"' AND "+CA_LOCATION+"='"+recentSearch.getLocation()+"'";
            Cursor cursor = database.rawQuery(Query, null);
            if (cursor != null && cursor.moveToFirst())
            {
                int id  = cursor.getInt(cursor.getColumnIndex(CA_ID));
                database.update(USER_RECENT_SEARCH_TABLE_NAME, contentvalues, CA_ID +"='"+id+"'", null);
            }
            else
                database.insert(USER_RECENT_SEARCH_TABLE_NAME, null, contentvalues);
            database.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            database.endTransaction();
            // closeDB();
        }
    }


    public UserInfo getUserRecord(){
        UserInfo userInfo = new UserInfo();
        openDB();
        database.beginTransaction();
        try {
            String Query = "Select * From " + USER_RECORD_TABLE_NAME ;
            Cursor cursor = database.rawQuery(Query, null);
            if (cursor != null && cursor.moveToFirst()){
                userInfo =  new UserInfo(cursor);
            }
                database.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            database.endTransaction();
            // closeDB();
        }
    return userInfo;
    }


    public ArrayList<Object> getKeywordRecord(){
        ArrayList<Object>  arrayList =  new ArrayList<>();
        openDB();
        database.beginTransaction();
        try {
            String Query = "Select * From " + USER_JOB_KEYWORDS_TABLE_NAME ;
            Cursor cursor = database.rawQuery(Query, null);
            if (cursor != null && cursor.moveToFirst()){
                while (!cursor.isAfterLast()) {
                    arrayList.add(cursor.getString(cursor.getColumnIndex(CA_KEYWORD)));
                    cursor.moveToNext();
                }
            }
            database.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            database.endTransaction();
            // closeDB();
        }
        return arrayList;
    }


    public ArrayList<Object> getJobLocation(){
        ArrayList<Object>  arrayList =  new ArrayList<>();
        openDB();
        database.beginTransaction();
        try {
            String Query = "Select * From " + USER_JOB_LOCATION_TABLE_NAME ;
            Cursor cursor = database.rawQuery(Query, null);
            if (cursor != null && cursor.moveToFirst()){
                while (!cursor.isAfterLast()) {
                    arrayList.add(cursor.getString(cursor.getColumnIndex(CA_LOCATION)));
                    cursor.moveToNext();
                }
            }
            database.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            database.endTransaction();
            // closeDB();
        }
        return arrayList;
    }

    public ArrayList<Object> getRecentSearchRecord(){
        ArrayList<Object>  arrayList =  new ArrayList<>();
        openDB();
        database.beginTransaction();
        try {
            String Query = "Select * From " + USER_RECENT_SEARCH_TABLE_NAME ;
            Cursor cursor = database.rawQuery(Query, null);
            if (cursor != null && cursor.moveToFirst()){
                while (!cursor.isAfterLast()) {
                    arrayList.add(cursor.getString(cursor.getColumnIndex(CA_KEYWORD)));
                    cursor.moveToNext();
                }
            }
            database.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            database.endTransaction();
            // closeDB();
        }
        return arrayList;
    }


    public void resetUserRecordTable() {
        // TODO Auto-generated method stub
        openDB();
        database.beginTransaction();
        try {
            database.delete(USER_RECORD_TABLE_NAME,null,null);
            database.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    public void resetRecentSearchTable() {
        // TODO Auto-generated method stub
        openDB();
        database.beginTransaction();
        try {
            database.delete(USER_RECENT_SEARCH_TABLE_NAME,null,null);
            database.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    public void resetJobLocationTable() {
        // TODO Auto-generated method stub
        openDB();
        database.beginTransaction();
        try {
            database.delete(USER_JOB_LOCATION_TABLE_NAME,null,null);
            database.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    public void resetJobKeywordTable() {
        // TODO Auto-generated method stub
        openDB();
        database.beginTransaction();
        try {
            database.delete(USER_JOB_KEYWORDS_TABLE_NAME,null,null);
            database.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }


}
