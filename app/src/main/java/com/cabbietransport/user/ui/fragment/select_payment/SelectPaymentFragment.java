package com.cabbietransport.user.ui.fragment.select_payment;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cabbietransport.user.MvpApplication;
import com.cabbietransport.user.R;
import com.cabbietransport.user.base.BaseFragment;
import com.cabbietransport.user.base.BasePresenter;
import com.cabbietransport.user.common.Constants;
import com.cabbietransport.user.data.SharedHelper;
import com.cabbietransport.user.data.network.model.EstimateFare;
import com.cabbietransport.user.data.network.model.Service;
import com.cabbietransport.user.telr.TelrHelper;
import com.cabbietransport.user.ui.activity.main.MainActivity;
import com.cabbietransport.user.ui.fragment.book_ride.BookRideFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cabbietransport.user.common.Constants.RIDE_REQUEST.CARD_LAST_FOUR;
import static com.cabbietransport.user.common.Constants.RIDE_REQUEST.PAYMENT_MODE;

public class SelectPaymentFragment extends BaseFragment {

    @BindView(R.id.telr_saved_card)
    TextView telrSavedCard;

    String serviceName;
    Service service;
    EstimateFare estimateFare;
    Double walletAmount;

    BasePresenter<SelectPaymentFragment> presenter = new BasePresenter<>();

    public SelectPaymentFragment() {}

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_payment;
    }

    @Override
    protected View initView(View view) {
        ButterKnife.bind(this, view);
        presenter.attachView(this);

        String savedCard = SharedHelper.getKey(getContext(), TelrHelper.CARD_LAST_FOUR);
        if(savedCard != null && savedCard.length() > 0)
            telrSavedCard.setText(getString(R.string.card_, savedCard));

        Bundle args = getArguments();
        if (args != null) {
            serviceName = args.getString("service_name");
            service = (Service) args.getSerializable("mService");
            estimateFare = (EstimateFare) args.getSerializable("estimate_fare");
            walletAmount = Objects.requireNonNull(estimateFare).getWalletBalance();
        }
        return view;
    }

    @OnClick({R.id.select_card, R.id.select_cash})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_cash:
                MvpApplication.RIDE_REQUEST.put(PAYMENT_MODE, Constants.PaymentMode.CASH);
                break;

            case R.id.select_card:
                MvpApplication.RIDE_REQUEST.put(PAYMENT_MODE, Constants.PaymentMode.TELR);
                MvpApplication.RIDE_REQUEST.put(CARD_LAST_FOUR, SharedHelper.getKey(getContext(), TelrHelper.CARD_LAST_FOUR));
                break;
        }

        Bundle bundle = new Bundle();
        bundle.putString("service_name", serviceName);
        bundle.putSerializable("mService", service);
        bundle.putSerializable("estimate_fare", estimateFare);
        bundle.putDouble("use_wallet", walletAmount);
        BookRideFragment bookRideFragment = new BookRideFragment();
        bookRideFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().remove(this);
        ((MainActivity) Objects.requireNonNull(getActivity())).changeFragment(bookRideFragment);

    }
}
