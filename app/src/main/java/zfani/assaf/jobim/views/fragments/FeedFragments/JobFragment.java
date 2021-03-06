package zfani.assaf.jobim.views.fragments.FeedFragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import zfani.assaf.jobim.R;
import zfani.assaf.jobim.models.Job;
import zfani.assaf.jobim.views.activities.JobInfoActivity;

public class JobFragment extends Fragment {

    @BindView(R.id.tvJobLookingFor)
    TextView tvJobLookingFor;
    @BindView(R.id.ivJobBusinessSymbol)
    ImageView ivJobBusinessSymbol;
    @BindView(R.id.tvJobTitle)
    TextView tvJobTitle;
    @BindView(R.id.tvJobAddress)
    TextView tvJobAddress;
    @BindView(R.id.tvJobDistance)
    TextView tvJobDistance;

    public static JobFragment newInstance(Job job, boolean isComeFromJobInfo) {
        JobFragment jobFragment = new JobFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Job", job);
        bundle.putBoolean("isComeFromJobInfo", isComeFromJobInfo);
        jobFragment.setArguments(bundle);
        return jobFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = requireArguments();
        Job job = bundle.getParcelable("Job");
        boolean isComeFromJobInfo = bundle.getBoolean("isComeFromJobInfo");
        if (job != null) {
            fillJobDetails(view, job, isComeFromJobInfo);
        }
        return view;
    }

    private void fillJobDetails(View view, @NonNull Job job, boolean isComeFromJobInfo) {
        tvJobLookingFor.setText(new StringBuilder(job.getFirm() + " מחפשת " + job.getType()));
        Glide.with(requireContext()).load(Uri.parse("file:///android_asset/" + job.getBusinessNumber() + ".png")).into(ivJobBusinessSymbol);
        if (view.getId() != R.id.clusterLayout) {
            tvJobTitle.setText(job.getTitle());
            tvJobAddress.setText(job.getAddress());
        }
        int distance = job.getDistance();
        tvJobDistance.setText(distance > 1000 ? new DecimalFormat("#.#").format((double) distance / 1000) + " ק\"מ ממני" : distance + " מ\' ממני");
        int bg;
        View layout = view.findViewById(R.id.llContainer);
        if (layout == null) {
            layout = view;
        }
        layout.setBackgroundColor(bg = Color.rgb(job.getColor1(), job.getColor2(), job.getColor3()));
        if (isComeFromJobInfo) {
            layout.setPadding(0, 0, 0, 30);
            view.setEnabled(false);
            view.findViewById(R.id.clMapFragment).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.flWhere).setBackgroundColor(bg);
        }
        view.setOnClickListener(v -> startActivity(new Intent(getActivity(), JobInfoActivity.class).putExtra("Job", job)));
    }
}