package zfani.assaf.jobim.views.fragments.DetailsFragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import zfani.assaf.jobim.App;
import zfani.assaf.jobim.R;
import zfani.assaf.jobim.utils.GPSTracker;

public class CityFragment extends Fragment {

    private AutoCompleteTextView city;

    public static CityFragment newInstance() {
        return new CityFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.city_fragment, container, false);
        city = view.findViewById(R.id.city);
        String address = App.sharedPreferences.getString("City", null);
        if (address != null) {
            city.setText(address);
        } else {
            address = GPSTracker.getAddressFromLatLng(getActivity(), null);
            if (address != null) {
                city.setText(address.substring(address.lastIndexOf(", ") + 2));
            }
        }
        //String cityText = address;
        city.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                /*String text = editable.toString();
                if (!text.isEmpty() && !text.equalsIgnoreCase(cityText) && text.length() >= 2) {
                    ListFragment.initData(false, requireActivity(), null, text);
                }*/
            }
        });
        view.findViewById(R.id.resetButton).setOnClickListener(view1 -> city.setText(""));
        return view;
    }

    public boolean isValidValue() {
        String cityText = city.getText().toString();
        boolean result = !cityText.isEmpty();
        if (result) {
            App.sharedPreferences.edit().putString("City", cityText).apply();
        } else {
            Toast.makeText(getActivity(), "חובה לבחור עיר מגורים מהרשימה", Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}
