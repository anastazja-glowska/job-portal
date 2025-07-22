package pl.anastazjaglowska.jobportal.entity;


public interface IRecruiterJobs {

    Long getTotalCandidates();
    int getJob_post_id();
    int getLocationId();
    String getJob_title();
    String getCity();
    String getState();
    String getCountry();
    int getCompanyId();
    String getName();

}
