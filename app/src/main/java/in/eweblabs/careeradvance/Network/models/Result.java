package in.eweblabs.careeradvance.Network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Anwar on 7/16/2016.
 */
public class Result {
    @SerializedName("job_id")
    @Expose
    private String jobId;
    @SerializedName("emp_id")
    @Expose
    private String empId;
    @SerializedName("job_title")
    @Expose
    private String jobTitle;
    @SerializedName("job_desc")
    @Expose
    private String jobDesc;
    @SerializedName("keywords")
    @Expose
    private String keywords;
    @SerializedName("experience_min")
    @Expose
    private String experienceMin;
    @SerializedName("experience_max")
    @Expose
    private String experienceMax;
    @SerializedName("ctc_currency")
    @Expose
    private String ctcCurrency;
    @SerializedName("ctc_min")
    @Expose
    private String ctcMin;
    @SerializedName("ctc_max")
    @Expose
    private String ctcMax;
    @SerializedName("salary_status")
    @Expose
    private String salaryStatus;
    @SerializedName("other_salary_detail")
    @Expose
    private String otherSalaryDetail;
    @SerializedName("vacancies")
    @Expose
    private String vacancies;
    @SerializedName("job_loc_country")
    @Expose
    private String jobLocCountry;
    @SerializedName("job_loc_state")
    @Expose
    private String jobLocState;
    @SerializedName("job_loacation_city")
    @Expose
    private String jobLoacationCity;
    @SerializedName("industry")
    @Expose
    private String industry;
    @SerializedName("other_industry")
    @Expose
    private String otherIndustry;
    @SerializedName("functional_area")
    @Expose
    private String functionalArea;
    @SerializedName("ug_qualification")
    @Expose
    private String ugQualification;
    @SerializedName("pg_qualification")
    @Expose
    private String pgQualification;
    @SerializedName("doctrate")
    @Expose
    private String doctrate;
    @SerializedName("candidate_profile")
    @Expose
    private String candidateProfile;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("hiring_for")
    @Expose
    private String hiringFor;
    @SerializedName("about_company")
    @Expose
    private String aboutCompany;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("contact_person")
    @Expose
    private String contactPerson;
    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("send_query")
    @Expose
    private String sendQuery;
    @SerializedName("date_post")
    @Expose
    private String datePost;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("job_type")
    @Expose
    private String jobType;

    @SerializedName("shared_url")
    @Expose
    private String sharedUrl;


    public String getSharedUrl() {
        return sharedUrl;
    }

    public void setSharedUrl(String sharedUrl) {
        this.sharedUrl = sharedUrl;
    }

    /**
     *
     * @return
     * The jobId
     */
    public String getJobId() {
        return jobId;
    }

    /**
     *
     * @param jobId
     * The job_id
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     *
     * @return
     * The empId
     */
    public String getEmpId() {
        return empId;
    }

    /**
     *
     * @param empId
     * The emp_id
     */
    public void setEmpId(String empId) {
        this.empId = empId;
    }

    /**
     *
     * @return
     * The jobTitle
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     *
     * @param jobTitle
     * The job_title
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     *
     * @return
     * The jobDesc
     */
    public String getJobDesc() {
        return jobDesc;
    }

    /**
     *
     * @param jobDesc
     * The job_desc
     */
    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    /**
     *
     * @return
     * The keywords
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     *
     * @param keywords
     * The keywords
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     *
     * @return
     * The experienceMin
     */
    public String getExperienceMin() {
        return experienceMin;
    }

    /**
     *
     * @param experienceMin
     * The experience_min
     */
    public void setExperienceMin(String experienceMin) {
        this.experienceMin = experienceMin;
    }

    /**
     *
     * @return
     * The experienceMax
     */
    public String getExperienceMax() {
        return experienceMax;
    }

    /**
     *
     * @param experienceMax
     * The experience_max
     */
    public void setExperienceMax(String experienceMax) {
        this.experienceMax = experienceMax;
    }

    /**
     *
     * @return
     * The ctcCurrency
     */
    public String getCtcCurrency() {
        return ctcCurrency;
    }

    /**
     *
     * @param ctcCurrency
     * The ctc_currency
     */
    public void setCtcCurrency(String ctcCurrency) {
        this.ctcCurrency = ctcCurrency;
    }

    /**
     *
     * @return
     * The ctcMin
     */
    public String getCtcMin() {
        return ctcMin;
    }

    /**
     *
     * @param ctcMin
     * The ctc_min
     */
    public void setCtcMin(String ctcMin) {
        this.ctcMin = ctcMin;
    }

    /**
     *
     * @return
     * The ctcMax
     */
    public String getCtcMax() {
        return ctcMax;
    }

    /**
     *
     * @param ctcMax
     * The ctc_max
     */
    public void setCtcMax(String ctcMax) {
        this.ctcMax = ctcMax;
    }

    /**
     *
     * @return
     * The salaryStatus
     */
    public String getSalaryStatus() {
        return salaryStatus;
    }

    /**
     *
     * @param salaryStatus
     * The salary_status
     */
    public void setSalaryStatus(String salaryStatus) {
        this.salaryStatus = salaryStatus;
    }

    /**
     *
     * @return
     * The otherSalaryDetail
     */
    public String getOtherSalaryDetail() {
        return otherSalaryDetail;
    }

    /**
     *
     * @param otherSalaryDetail
     * The other_salary_detail
     */
    public void setOtherSalaryDetail(String otherSalaryDetail) {
        this.otherSalaryDetail = otherSalaryDetail;
    }

    /**
     *
     * @return
     * The vacancies
     */
    public String getVacancies() {
        return vacancies;
    }

    /**
     *
     * @param vacancies
     * The vacancies
     */
    public void setVacancies(String vacancies) {
        this.vacancies = vacancies;
    }

    /**
     *
     * @return
     * The jobLocCountry
     */
    public String getJobLocCountry() {
        return jobLocCountry;
    }

    /**
     *
     * @param jobLocCountry
     * The job_loc_country
     */
    public void setJobLocCountry(String jobLocCountry) {
        this.jobLocCountry = jobLocCountry;
    }

    /**
     *
     * @return
     * The jobLocState
     */
    public String getJobLocState() {
        return jobLocState;
    }

    /**
     *
     * @param jobLocState
     * The job_loc_state
     */
    public void setJobLocState(String jobLocState) {
        this.jobLocState = jobLocState;
    }

    /**
     *
     * @return
     * The jobLoacationCity
     */
    public String getJobLoacationCity() {
        return jobLoacationCity;
    }

    /**
     *
     * @param jobLoacationCity
     * The job_loacation_city
     */
    public void setJobLoacationCity(String jobLoacationCity) {
        this.jobLoacationCity = jobLoacationCity;
    }

    /**
     *
     * @return
     * The industry
     */
    public String getIndustry() {
        return industry;
    }

    /**
     *
     * @param industry
     * The industry
     */
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    /**
     *
     * @return
     * The otherIndustry
     */
    public String getOtherIndustry() {
        return otherIndustry;
    }

    /**
     *
     * @param otherIndustry
     * The other_industry
     */
    public void setOtherIndustry(String otherIndustry) {
        this.otherIndustry = otherIndustry;
    }

    /**
     *
     * @return
     * The functionalArea
     */
    public String getFunctionalArea() {
        return functionalArea;
    }

    /**
     *
     * @param functionalArea
     * The functional_area
     */
    public void setFunctionalArea(String functionalArea) {
        this.functionalArea = functionalArea;
    }

    /**
     *
     * @return
     * The ugQualification
     */
    public String getUgQualification() {
        return ugQualification;
    }

    /**
     *
     * @param ugQualification
     * The ug_qualification
     */
    public void setUgQualification(String ugQualification) {
        this.ugQualification = ugQualification;
    }

    /**
     *
     * @return
     * The pgQualification
     */
    public String getPgQualification() {
        return pgQualification;
    }

    /**
     *
     * @param pgQualification
     * The pg_qualification
     */
    public void setPgQualification(String pgQualification) {
        this.pgQualification = pgQualification;
    }

    /**
     *
     * @return
     * The doctrate
     */
    public String getDoctrate() {
        return doctrate;
    }

    /**
     *
     * @param doctrate
     * The doctrate
     */
    public void setDoctrate(String doctrate) {
        this.doctrate = doctrate;
    }

    /**
     *
     * @return
     * The candidateProfile
     */
    public String getCandidateProfile() {
        return candidateProfile;
    }

    /**
     *
     * @param candidateProfile
     * The candidate_profile
     */
    public void setCandidateProfile(String candidateProfile) {
        this.candidateProfile = candidateProfile;
    }

    /**
     *
     * @return
     * The company
     */
    public String getCompany() {
        return company;
    }

    /**
     *
     * @param company
     * The company
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     *
     * @return
     * The hiringFor
     */
    public String getHiringFor() {
        return hiringFor;
    }

    /**
     *
     * @param hiringFor
     * The hiring_for
     */
    public void setHiringFor(String hiringFor) {
        this.hiringFor = hiringFor;
    }

    /**
     *
     * @return
     * The aboutCompany
     */
    public String getAboutCompany() {
        return aboutCompany;
    }

    /**
     *
     * @param aboutCompany
     * The about_company
     */
    public void setAboutCompany(String aboutCompany) {
        this.aboutCompany = aboutCompany;
    }

    /**
     *
     * @return
     * The website
     */
    public String getWebsite() {
        return website;
    }

    /**
     *
     * @param website
     * The website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     *
     * @return
     * The contactPerson
     */
    public String getContactPerson() {
        return contactPerson;
    }

    /**
     *
     * @param contactPerson
     * The contact_person
     */
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    /**
     *
     * @return
     * The contactNumber
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     *
     * @param contactNumber
     * The contact_number
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     *
     * @return
     * The emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     *
     * @param emailId
     * The email_id
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     *
     * @return
     * The sendQuery
     */
    public String getSendQuery() {
        return sendQuery;
    }

    /**
     *
     * @param sendQuery
     * The send_query
     */
    public void setSendQuery(String sendQuery) {
        this.sendQuery = sendQuery;
    }

    /**
     *
     * @return
     * The datePost
     */
    public String getDatePost() {
        return datePost;
    }

    /**
     *
     * @param datePost
     * The date_post
     */
    public void setDatePost(String datePost) {
        this.datePost = datePost;
    }

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The jobType
     */
    public String getJobType() {
        return jobType;
    }

    /**
     *
     * @param jobType
     * The job_type
     */
    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    @Override
    public String toString() {
        return "Result{" +
                "jobId='" + jobId + '\'' +
                ", empId='" + empId + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobDesc='" + jobDesc + '\'' +
                ", keywords='" + keywords + '\'' +
                ", experienceMin='" + experienceMin + '\'' +
                ", experienceMax='" + experienceMax + '\'' +
                ", ctcCurrency='" + ctcCurrency + '\'' +
                ", ctcMin='" + ctcMin + '\'' +
                ", ctcMax='" + ctcMax + '\'' +
                ", salaryStatus='" + salaryStatus + '\'' +
                ", otherSalaryDetail='" + otherSalaryDetail + '\'' +
                ", vacancies='" + vacancies + '\'' +
                ", jobLocCountry='" + jobLocCountry + '\'' +
                ", jobLocState='" + jobLocState + '\'' +
                ", jobLoacationCity='" + jobLoacationCity + '\'' +
                ", industry='" + industry + '\'' +
                ", otherIndustry='" + otherIndustry + '\'' +
                ", functionalArea='" + functionalArea + '\'' +
                ", ugQualification='" + ugQualification + '\'' +
                ", pgQualification='" + pgQualification + '\'' +
                ", doctrate='" + doctrate + '\'' +
                ", candidateProfile='" + candidateProfile + '\'' +
                ", company='" + company + '\'' +
                ", hiringFor='" + hiringFor + '\'' +
                ", aboutCompany='" + aboutCompany + '\'' +
                ", website='" + website + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                ", sendQuery='" + sendQuery + '\'' +
                ", datePost='" + datePost + '\'' +
                ", address='" + address + '\'' +
                ", jobType='" + jobType + '\'' +
                '}';
    }
}
