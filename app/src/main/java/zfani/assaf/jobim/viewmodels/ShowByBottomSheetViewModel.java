package zfani.assaf.jobim.viewmodels;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShowByBottomSheetViewModel extends ViewModel {

    private List<String> chosenJobTypeList;
    private MutableLiveData<String> jobTypeQuery, jobLocationQuery, chosenLocation, jobFirmQuery;
    private String chosenFirm;
    private MutableLiveData<Boolean> filter;

    public ShowByBottomSheetViewModel() {
        chosenJobTypeList = new ArrayList<>();
        jobTypeQuery = new MutableLiveData<>();
        jobLocationQuery = new MutableLiveData<>();
        chosenLocation = new MutableLiveData<>();
        jobFirmQuery = new MutableLiveData<>();
        filter = new MutableLiveData<>(false);
    }

    public List<String> getChosenJobTypeList() {
        return chosenJobTypeList;
    }

    public void setChosenJobType(String chosenJobType) {
        if (chosenJobType == null) {
            chosenJobTypeList.clear();
            return;
        }
        if (chosenJobTypeList.contains(chosenJobType)) {
            chosenJobTypeList.remove(chosenJobType);
        } else {
            chosenJobTypeList.add(chosenJobType);
        }
    }

    public MutableLiveData<String> getJobTypeQuery() {
        return jobTypeQuery;
    }

    public void setJobTypeQuery(String jobTypeQuery) {
        this.jobTypeQuery.setValue(jobTypeQuery);
    }

    public MutableLiveData<String> getJobLocationQuery() {
        return jobLocationQuery;
    }

    public void setJobLocationQuery(String jobLocationQuery) {
        this.jobLocationQuery.setValue(jobLocationQuery);
    }

    public MutableLiveData<String> getChosenLocation() {
        return chosenLocation;
    }

    public void setChosenLocation(String chosenLocation) {
        this.chosenLocation.setValue(chosenLocation);
    }

    public MutableLiveData<String> getJobFirmQuery() {
        return jobFirmQuery;
    }

    public void setJobFirmQuery(String jobFirmQuery) {
        this.jobFirmQuery.setValue(jobFirmQuery);
    }

    public String getChosenFirm() {
        return chosenFirm;
    }

    public void setChosenFirm(String chosenFirm) {
        this.chosenFirm = chosenFirm;
    }

    public MutableLiveData<Boolean> getFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter.setValue(filter);
    }

    public void cleanUserChoices() {
        setJobTypeQuery(null);
        setChosenJobType(null);
        setJobLocationQuery(null);
        setChosenLocation(null);
        setJobFirmQuery(null);
        setChosenFirm(null);
    }
}
