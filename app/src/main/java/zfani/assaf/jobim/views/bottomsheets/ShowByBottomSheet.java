package zfani.assaf.jobim.views.bottomsheets;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zfani.assaf.jobim.R;
import zfani.assaf.jobim.adapters.ShowByPagerAdapter;
import zfani.assaf.jobim.viewmodels.ShowByBottomSheetViewModel;

public class ShowByBottomSheet extends BottomSheetDialogFragment {

    @BindView(R.id.etJobType)
    AppCompatEditText etJob;
    @BindView(R.id.etJobLocation)
    AppCompatEditText etLocation;
    @BindView(R.id.etJobFirm)
    AppCompatEditText etFirm;
    @BindView(R.id.rgFragmentsBar)
    RadioGroup rgFragmentsBar;
    @BindView(R.id.vpContainer)
    ViewPager vpContainer;
    private ShowByBottomSheetViewModel showByBottomSheetViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_show_by, container, false);
        ButterKnife.bind(this, view);
        showByBottomSheetViewModel = ViewModelProviders.of(requireActivity()).get(ShowByBottomSheetViewModel.class);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            View bottomSheet = requireDialog().findViewById(R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheet).setPeekHeight(view.getHeight() - Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).getHeight());
        });
    }

    private void initView() {
        AppCompatEditText[] editTexts = new AppCompatEditText[3];
        editTexts[0] = etFirm;
        editTexts[1] = etLocation;
        editTexts[2] = etJob;
        for (EditText editText : editTexts) {
            editText.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    switch (editText.getId()) {
                        case R.id.etJobType:
                            showByBottomSheetViewModel.setJobTypeQuery(s.toString());
                            break;
                        case R.id.etJobLocation:
                            editText.setOnEditorActionListener((v, actionId, event) -> {
                                if (s.length() != 0) {
                                    showByBottomSheetViewModel.setJobLocationQuery(s.toString());
                                }
                                return false;
                            });
                            break;
                        case R.id.etJobFirm:
                            showByBottomSheetViewModel.setJobFirmQuery(s.toString());
                            break;
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
        rgFragmentsBar.setOnCheckedChangeListener((group, checkedId) -> {
            int item;
            switch (checkedId) {
                case R.id.button1:
                    item = 2;
                    break;
                case R.id.button2:
                    item = 1;
                    break;
                default:
                    item = 0;
                    break;
            }
            for (int i = 0; i < editTexts.length; i++) {
                if (i != item) {
                    editTexts[i].setVisibility(View.GONE);
                }
            }
            editTexts[item].setVisibility(View.VISIBLE);
            editTexts[item].requestFocus();
            vpContainer.setCurrentItem(item);
        });
        vpContainer.setAdapter(new ShowByPagerAdapter(getChildFragmentManager()));
        vpContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int id;
                switch (position) {
                    case 2:
                        id = R.id.button1;
                        break;
                    case 1:
                        id = R.id.button2;
                        break;
                    default:
                        id = R.id.button3;
                        break;
                }
                rgFragmentsBar.check(id);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpContainer.setCurrentItem(2);
        vpContainer.setOffscreenPageLimit(3);
        rgFragmentsBar.check(R.id.button1);
        showByBottomSheetViewModel.getChosenLocation().observe(this, s -> etLocation.setText(s));
    }

    @OnClick(R.id.tvAllow)
    void allow() {
        showByBottomSheetViewModel.setFilter(true);
        dismiss();
    }

    @OnClick(R.id.tvCancel)
    void cancel() {
        showByBottomSheetViewModel.cleanUserChoices();
        dismiss();
    }
}
