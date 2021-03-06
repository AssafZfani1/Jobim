package zfani.assaf.jobim.views.fragments.MenuFragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import zfani.assaf.jobim.App;
import zfani.assaf.jobim.R;
import zfani.assaf.jobim.views.activities.FillDetails;
import zfani.assaf.jobim.views.fragments.DetailsFragments.FullNameFragment;

public class MyDetailsFragment extends Fragment {

    private boolean needToFillDetails;
    private Activity activity;

    public static MyDetailsFragment newInstance() {

        return new MyDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = getActivity();

        return inflater.inflate(R.layout.my_details, container, false);
    }

    @Override
    public void onStart() {

        super.onStart();

        View view = getView();

        if (view != null) {

            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), 0);

            if (!App.sharedPreferences.contains("FullName") && !needToFillDetails) {

                startActivity(new Intent(activity, FillDetails.class));

                needToFillDetails = true;

            } else {

                ((TextView) view.findViewById(R.id.fullName)).setText(App.sharedPreferences.getString("FullName", ""));

                ((TextView) view.findViewById(R.id.city)).setText(App.sharedPreferences.getString("City", ""));

                int birthYear = App.sharedPreferences.getInt("BirthYear", 0);

                ((TextView) view.findViewById(R.id.birthYear)).setText(birthYear != 0 ? birthYear + "" : "");

                ((TextView) view.findViewById(R.id.email)).setText(App.sharedPreferences.getString("Email", ""));

                activity.getIntent().putExtra("SmallRound", false);

                FullNameFragment.initSelfie(view.findViewById(R.id.selfie));
            }
        }
    }
}