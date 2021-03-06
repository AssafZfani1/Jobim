package zfani.assaf.jobim.views.fragments.MenuFragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import zfani.assaf.jobim.R;
import zfani.assaf.jobim.models.NewJob;
import zfani.assaf.jobim.utils.GPSTracker;
import zfani.assaf.jobim.views.activities.AddNewJob;

public class MyJobsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RadioGroup myJobsLayout;
    private RecyclerView recyclerView;
    private View message;

    public static Fragment newInstance() {
        return new MyJobsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_jobs, container, false);
        myJobsLayout = view.findViewById(R.id.myJobsLayout);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = view.findViewById(R.id.myJobsRecyclerView);
        message = view.findViewById(R.id.ivLocationMessage);
        myJobsLayout.setOnCheckedChangeListener((radioGroup, i) -> {
            onRefresh();
            setMessageVisibility();
            int messageDrawable = 0;
            switch (i) {
                case R.id.favoriteTab:
                    messageDrawable = R.drawable.no_favorites_message;
                    break;
                case R.id.appliedTab:
                    messageDrawable = R.drawable.no_applies_message;
                    break;
                case R.id.postedTab:
                    messageDrawable = R.drawable.no_posted_jobs_message;
                    break;
            }
            message.setBackgroundResource(messageDrawable);
        });
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                setMessageVisibility();
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                setMessageVisibility();
            }
        });
        NewJob newJob = AddNewJob.newJob;
        if (newJob != null) {
            myJobsLayout.getChildAt(1).setBackgroundResource(R.drawable.middle);
            myJobsLayout.getChildAt(2).setVisibility(View.VISIBLE);
        }
        myJobsLayout.check(newJob == null ? R.id.favoriteTab : R.id.postedTab);
        /*if (newJob != null) {
            DatabaseReference job = JobsAdapter.query.getRef().push();
            String branch = newJob.getBranchName();
            job.setValue(new Job(newJob.getAddress(), false, newJob.getBusinessNumber(), newJob.getDistance(), false,
                    newJob.getFirm() + (branch == null ? "" : " " + newJob.getBranchName()), job.getKey(), true, newJob.getTitle()));
        }*/
        AddNewJob.newJob = null;
        return view;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> swipeRefreshLayout.setRefreshing(false), 3000);
        if (GPSTracker.location != null) {
            refreshList();
        }
    }

    private void refreshList() {
        int messageDrawable = 0;
        String key = "";
        switch (myJobsLayout.getCheckedRadioButtonId()) {
            case R.id.favoriteTab:
                messageDrawable = R.drawable.no_favorites_message;
                key = "favorite";
                break;
            case R.id.appliedTab:
                messageDrawable = R.drawable.no_applies_message;
                key = "applied";
                break;
            case R.id.postedTab:
                messageDrawable = R.drawable.no_posted_jobs_message;
                key = "posted";
                break;
        }
        message.setBackgroundResource(messageDrawable);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.getItemAnimator().setAddDuration(750);
        recyclerView.getItemAnimator().setRemoveDuration(750);
        //recyclerView.setAdapter(new JobsAdapter(key, "true"));
    }

    private void setMessageVisibility() {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        message.setVisibility((adapter == null || adapter.getItemCount() == 0) ? View.VISIBLE : View.INVISIBLE);
    }
}
