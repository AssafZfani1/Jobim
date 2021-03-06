package zfani.assaf.jobim.views.fragments.NewJobFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import zfani.assaf.jobim.R;
import zfani.assaf.jobim.utils.AlertHelper;
import zfani.assaf.jobim.views.activities.AddNewJob;

public class FirmFragment extends Fragment {

    private EditText firmName;
    private EditText branchName;
    private View support;

    public static FirmFragment newInstance() {
        return new FirmFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.firm_fragment, container, false);
        firmName = view.findViewById(R.id.firmName);
        branchName = view.findViewById(R.id.branchName);
        support = view.findViewById(R.id.tvSupport);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        support.setOnClickListener(view -> AlertHelper.displayDialog(getActivity(), R.layout.add_new_job_dialog, -1));
    }

    public boolean isValidValue() {
        String firmNameText = firmName.getText().toString(), branchNameText = branchName.getText().toString();
        boolean result = !firmNameText.isEmpty();
        if (result) {
            AddNewJob.newJob.setFirm(firmNameText);
            if (!branchNameText.isEmpty()) {
                AddNewJob.newJob.setBranchName(branchNameText);
            }
        } else {
            Toast.makeText(getActivity(), "חובה למלא את שם החברה", Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}
