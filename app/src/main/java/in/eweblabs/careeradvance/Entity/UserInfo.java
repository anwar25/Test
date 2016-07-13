package in.eweblabs.careeradvance.Entity;

import android.database.Cursor;

import in.eweblabs.careeradvance.StaticData.DbConstraints;

/**
 * Created by akash.singh on 11/20/2015.
 */
public class UserInfo extends DbConstraints{
    String message;
    String userId;
    String userEmail;
    String userName;
    String userGender;
    String userAcountType;
    String userDOB;
    String userDesignation;
    String userCompany;
    String userPhone;
    String userKeySkills;
    String userResumeHeadline;
    String userObjective;
    String userSalaryExpectation;
    String userLocation;
    String userHomeTownCity;
    String userAddress;
    String userZip;
    String userPermanentAddress;
    String userExperienceYear;
    String userExperienceMonth;
    String userSalaryCurrency;
    String userCTC;
    String userIndustry;
    String userFunctionalArea;
    String userBasicEducation;
    String userBasicEducationOther;
    String userMasterEducation;
    String userMasterEducationOther;
    String userDoctrateEducation;
    String userDoctrateEducationOther;
    String userDiplomaCourse;
    String userResumePath;
    String userResumeText;
    String userJobAlert;
    String userFastForwordEmails;
    String userFastForwordCalls;
    String userNotification;
    String userCommunicationClient;
    String userSpecialOffer;
    String userAvatar;

    public UserInfo(){


        setUserId("");
        setUserEmail("");
        setUserName("");
        setUserGender("");
        setUserAcountType("");
        setUserDOB("");
        setUserDesignation("");
        setUserCompany("");
        setUserPhone("");
        setUserKeySkills("");
        setUserResumeHeadline("");
        setUserObjective("");
        setUserSalaryExpectation("");
        setUserLocation("");
        setUserHomeTownCity("");
        setUserAddress("");
        setUserZip("");
        setUserPermanentAddress("");
        setUserExperienceYear("");
        setUserExperienceMonth("");
        setUserSalaryCurrency("");
        setUserCTC("");
        setUserIndustry("");
        setUserFunctionalArea("");
        setUserBasicEducation("");
        setUserBasicEducationOther("");
        setUserMasterEducation("");
        setUserMasterEducationOther("");
        setUserDoctrateEducation("");
        setUserDoctrateEducationOther("");
        setUserDiplomaCourse("");
        setUserResumePath("");
        setUserResumeText("");
        setUserJobAlert("");
        setUserFastForwordEmails("");
        setUserFastForwordCalls("");
        setUserNotification("");
        setUserCommunicationClient("");
        setUserSpecialOffer("");
        setUserAvatar("");

    }

    public UserInfo(Cursor cursor){
        setUserId(cursor.getString(cursor.getColumnIndex(UR_USER_ID)));
        setUserEmail(cursor.getString(cursor.getColumnIndex(UR_USER_USER_EMAIL)));
        setUserName(cursor.getString(cursor.getColumnIndex(UR_USER_USERNAME)));
        setUserGender(cursor.getString(cursor.getColumnIndex(UR_USER_USER_GENDER)));
        setUserAcountType(cursor.getString(cursor.getColumnIndex(UR_USER_ACOUNT_TYPE)));
        setUserDOB(cursor.getString(cursor.getColumnIndex(UR_USER_USER_DB)));
        setUserDesignation(cursor.getString(cursor.getColumnIndex(UR_USER_DESIGNATION)));
        setUserCompany(cursor.getString(cursor.getColumnIndex(UR_USER_COMPANY)));
        setUserPhone(cursor.getString(cursor.getColumnIndex(UR_USER_PHONE)));
        setUserKeySkills(cursor.getString(cursor.getColumnIndex(UR_USER_KEY_SKILLS)));
        setUserResumeHeadline(cursor.getString(cursor.getColumnIndex(UR_USER_RESUME_HEADLINE)));
        setUserObjective(cursor.getString(cursor.getColumnIndex(UR_USER_OBJECTIVE)));
        setUserSalaryExpectation(cursor.getString(cursor.getColumnIndex(UR_USER_SALARY_EXPECTATION)));
        setUserLocation(cursor.getString(cursor.getColumnIndex(UR_USER_LOCATION)));
        setUserHomeTownCity(cursor.getString(cursor.getColumnIndex(UR_USER_HOME_TOWN_CITY)));
        setUserAddress(cursor.getString(cursor.getColumnIndex(UR_USER_ADDRESS)));
        setUserZip(cursor.getString(cursor.getColumnIndex(UR_USER_ZIP)));
        setUserPermanentAddress(cursor.getString(cursor.getColumnIndex(UR_USER_PERMANENT_ADDRESS)));
        setUserExperienceYear(cursor.getString(cursor.getColumnIndex(UR_USER_EXPERIENCE_YEAR)));
        setUserExperienceMonth(cursor.getString(cursor.getColumnIndex(UR_USER_EXPERIENCE_MONTH)));
        setUserSalaryCurrency(cursor.getString(cursor.getColumnIndex(UR_USER_SALARY_CURRENT)));
        setUserCTC(cursor.getString(cursor.getColumnIndex(UR_USER_CTC)));
        setUserIndustry(cursor.getString(cursor.getColumnIndex(UR_USER_INDUSTRY)));
        setUserFunctionalArea(cursor.getString(cursor.getColumnIndex(UR_USER_FUNCTIONAL_AREA)));
        setUserBasicEducation(cursor.getString(cursor.getColumnIndex(UR_USER_BASIC_EDUCATION_TYPE)));
        setUserBasicEducationOther(cursor.getString(cursor.getColumnIndex(UR_USER_BASIC_EDUCATION_OTHER)));
        setUserMasterEducation(cursor.getString(cursor.getColumnIndex(UR_USER_USER_MASTER_EDUCATION)));
        setUserMasterEducationOther(cursor.getString(cursor.getColumnIndex(UR_USER_MASTER_EDUCATION_OTHER)));
        setUserDoctrateEducation(cursor.getString(cursor.getColumnIndex(UR_USER_DOCTRATE_EDUCATION)));
        setUserDoctrateEducationOther(cursor.getString(cursor.getColumnIndex(UR_USER_DOCTRATE_EDUCATION_OTHER)));
        setUserDiplomaCourse(cursor.getString(cursor.getColumnIndex(UR_USER_DIPLOMA_COURSE)));
        setUserResumePath(cursor.getString(cursor.getColumnIndex(UR_USER_RESUME_PATH)));
        setUserResumeText(cursor.getString(cursor.getColumnIndex(UR_USER_RESUME_TEXT)));
        setUserJobAlert(cursor.getString(cursor.getColumnIndex(UR_USER_JOB_ALERT)));
        setUserFastForwordEmails(cursor.getString(cursor.getColumnIndex(UR_USER_FAST_FORWORD_EMAILS)));
        setUserFastForwordCalls(cursor.getString(cursor.getColumnIndex(UR_USER_FAST_FORWORD_CALLS)));
        setUserNotification(cursor.getString(cursor.getColumnIndex(UR_USER_NOTIFICATION)));
        setUserCommunicationClient(cursor.getString(cursor.getColumnIndex(UR_USER_COMMUNICATION_CLIENT)));
        setUserSpecialOffer(cursor.getString(cursor.getColumnIndex(UR_USER_USER_SPECIAL_OFFER)));
        setUserAvatar(cursor.getString(cursor.getColumnIndex(UR_USER_USER_AVATAR)));


    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserAcountType() {
        return userAcountType;
    }

    public void setUserAcountType(String userAcountType) {
        this.userAcountType = userAcountType;
    }

    public String getUserDOB() {
        return userDOB;
    }

    public void setUserDOB(String userDOB) {
        this.userDOB = userDOB;
    }

    public String getUserDesignation() {
        return userDesignation;
    }

    public void setUserDesignation(String userDesignation) {
        this.userDesignation = userDesignation;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserKeySkills() {
        return userKeySkills;
    }

    public void setUserKeySkills(String userKeySkills) {
        this.userKeySkills = userKeySkills;
    }

    public String getUserResumeHeadline() {
        return userResumeHeadline;
    }

    public void setUserResumeHeadline(String userResumeHeadline) {
        this.userResumeHeadline = userResumeHeadline;
    }

    public String getUserObjective() {
        return userObjective;
    }

    public void setUserObjective(String userObjective) {
        this.userObjective = userObjective;
    }

    public String getUserSalaryExpectation() {
        return userSalaryExpectation;
    }

    public void setUserSalaryExpectation(String userSalaryExpectation) {
        this.userSalaryExpectation = userSalaryExpectation;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserHomeTownCity() {
        return userHomeTownCity;
    }

    public void setUserHomeTownCity(String userHomeTownCity) {
        this.userHomeTownCity = userHomeTownCity;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserZip() {
        return userZip;
    }

    public void setUserZip(String userZip) {
        this.userZip = userZip;
    }

    public String getUserPermanentAddress() {
        return userPermanentAddress;
    }

    public void setUserPermanentAddress(String userPermanentAddress) {
        this.userPermanentAddress = userPermanentAddress;
    }

    public String getUserExperienceYear() {
        return userExperienceYear;
    }

    public void setUserExperienceYear(String userExperienceYear) {
        this.userExperienceYear = userExperienceYear;
    }

    public String getUserExperienceMonth() {
        return userExperienceMonth;
    }

    public void setUserExperienceMonth(String userExperienceMonth) {
        this.userExperienceMonth = userExperienceMonth;
    }

    public String getUserSalaryCurrency() {
        return userSalaryCurrency;
    }

    public void setUserSalaryCurrency(String userSalaryCurrency) {
        this.userSalaryCurrency = userSalaryCurrency;
    }

    public String getUserCTC() {
        return userCTC;
    }

    public void setUserCTC(String userCTC) {
        this.userCTC = userCTC;
    }

    public String getUserIndustry() {
        return userIndustry;
    }

    public void setUserIndustry(String userIndustry) {
        this.userIndustry = userIndustry;
    }

    public String getUserFunctionalArea() {
        return userFunctionalArea;
    }

    public void setUserFunctionalArea(String userFunctionalArea) {
        this.userFunctionalArea = userFunctionalArea;
    }

    public String getUserBasicEducation() {
        return userBasicEducation;
    }

    public void setUserBasicEducation(String userBasicEducation) {
        this.userBasicEducation = userBasicEducation;
    }

    public String getUserBasicEducationOther() {
        return userBasicEducationOther;
    }

    public void setUserBasicEducationOther(String userBasicEducationOther) {
        this.userBasicEducationOther = userBasicEducationOther;
    }

    public String getUserMasterEducation() {
        return userMasterEducation;
    }

    public void setUserMasterEducation(String userMasterEducation) {
        this.userMasterEducation = userMasterEducation;
    }

    public String getUserMasterEducationOther() {
        return userMasterEducationOther;
    }

    public void setUserMasterEducationOther(String userMasterEducationOther) {
        this.userMasterEducationOther = userMasterEducationOther;
    }

    public String getUserDoctrateEducation() {
        return userDoctrateEducation;
    }

    public void setUserDoctrateEducation(String userDoctrateEducation) {
        this.userDoctrateEducation = userDoctrateEducation;
    }

    public String getUserDoctrateEducationOther() {
        return userDoctrateEducationOther;
    }

    public void setUserDoctrateEducationOther(String userDoctrateEducationOther) {
        this.userDoctrateEducationOther = userDoctrateEducationOther;
    }

    public String getUserDiplomaCourse() {
        return userDiplomaCourse;
    }

    public void setUserDiplomaCourse(String userDiplomaCourse) {
        this.userDiplomaCourse = userDiplomaCourse;
    }

    public String getUserResumePath() {
        return userResumePath;
    }

    public void setUserResumePath(String userResumePath) {
        this.userResumePath = userResumePath;
    }

    public String getUserResumeText() {
        return userResumeText;
    }

    public void setUserResumeText(String userResumeText) {
        this.userResumeText = userResumeText;
    }

    public String getUserJobAlert() {
        return userJobAlert;
    }

    public void setUserJobAlert(String userJobAlert) {
        this.userJobAlert = userJobAlert;
    }

    public String getUserFastForwordEmails() {
        return userFastForwordEmails;
    }

    public void setUserFastForwordEmails(String userFastForwordEmails) {
        this.userFastForwordEmails = userFastForwordEmails;
    }

    public String getUserFastForwordCalls() {
        return userFastForwordCalls;
    }

    public void setUserFastForwordCalls(String userFastForwordCalls) {
        this.userFastForwordCalls = userFastForwordCalls;
    }

    public String getUserNotification() {
        return userNotification;
    }

    public void setUserNotification(String userNotification) {
        this.userNotification = userNotification;
    }

    public String getUserCommunicationClient() {
        return userCommunicationClient;
    }

    public void setUserCommunicationClient(String userCommunicationClient) {
        this.userCommunicationClient = userCommunicationClient;
    }

    public String getUserSpecialOffer() {
        return userSpecialOffer;
    }

    public void setUserSpecialOffer(String userSpecialOffer) {
        this.userSpecialOffer = userSpecialOffer;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
