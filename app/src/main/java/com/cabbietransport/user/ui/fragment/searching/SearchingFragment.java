package com.cabbietransport.user.ui.fragment.searching;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.cabbietransport.user.MvpApplication;
import com.cabbietransport.user.ui.activity.main.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.View;

import com.cabbietransport.user.R;
import com.cabbietransport.user.base.BaseBottomSheetDialogFragment;
import com.cabbietransport.user.data.network.model.Datum;

import java.util.HashMap;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cabbietransport.user.common.Constants.BroadcastReceiver.INTENT_FILTER;
import static com.cabbietransport.user.common.Constants.RIDE_REQUEST.DEST_ADD;
import static com.cabbietransport.user.common.Constants.RIDE_REQUEST.DEST_LAT;
import static com.cabbietransport.user.common.Constants.RIDE_REQUEST.DEST_LONG;
import static com.cabbietransport.user.common.Constants.Status.EMPTY;

public class SearchingFragment extends BaseBottomSheetDialogFragment implements SearchingIView {

    private SearchingPresenter<SearchingFragment> presenter = new SearchingPresenter<>();

    public SearchingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_searching;
    }

    @Override
    public void initView(View view) {
        setCancelable(false);
        getDialog().setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            View bottomSheetInternal = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheetInternal).setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        ButterKnife.bind(this, view);
        presenter.attachView(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.cancel)
    public void onViewClicked() {
        alertCancel();
    }

    private void alertCancel() {
        new AlertDialog.Builder(getContext())
                .setMessage(R.string.are_sure_you_want_to_cancel_the_request)
                .setCancelable(true)
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    if (MvpApplication.DATUM != null) {
                        showLoading();
                        Datum datum = MvpApplication.DATUM;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("request_id", datum.getId());
                        presenter.cancelRequest(map);
                    }
                }).setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.cancel())
                .show();
    }

    @Override
    public void onSuccess(Object object) {
        try {
            hideLoading();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        MvpApplication.RIDE_REQUEST.remove(DEST_ADD);
        MvpApplication.RIDE_REQUEST.remove(DEST_LAT);
        MvpApplication.RIDE_REQUEST.remove(DEST_LONG);

        baseActivity().sendBroadcast(new Intent(INTENT_FILTER));
        ((MainActivity) Objects.requireNonNull(getContext())).changeFlow(EMPTY);
        dismissAllowingStateLoss();
    }

    @Override
    public void onError(Throwable e) {
        handleError(e);
        baseActivity().sendBroadcast(new Intent(INTENT_FILTER));
        ((MainActivity) Objects.requireNonNull(getContext())).changeFlow(EMPTY);
    }
}
