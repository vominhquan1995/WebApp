package asia.covisoft.bsandroid.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

import asia.covisoft.bsandroid.R;
import asia.covisoft.bsandroid.activity.NotificationDetailsActivity;
import asia.covisoft.bsandroid.adapter.NotificationRecyclerAdapter;
import asia.covisoft.bsandroid.base.BaseEndlessRecyclerAdapter;
import asia.covisoft.bsandroid.base.BaseLoadingFragment;
import asia.covisoft.bsandroid.dialog.ErrorDialog;
import asia.covisoft.bsandroid.wa.model.Notification;
import asia.covisoft.bsandroid.wa.presenter.NotificationPresenter;
import asia.covisoft.bsandroid.wc.WCDefaultValue;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends BaseLoadingFragment {

    @Override
    public int getRootView() {
        return R.layout.fragment_notification;
    }

    private RecyclerView rvNotification;

    @Override
    public void initView() {

        rvNotification = (RecyclerView) rootView.findViewById(R.id.rvNotification);
    }

    private NotificationRecyclerAdapter notificationAdapter;

    @Override
    public void initValue() {

        rvNotification.setLayoutManager(new LinearLayoutManager(context));

        notificationAdapter = new NotificationRecyclerAdapter(context);
        notificationAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long productId) {

                Notification news = notificationAdapter.getItem(position);
                Intent intent = new Intent(context, NotificationDetailsActivity.class);
                intent.putExtra(NotificationDetailsActivity.NOTIFICATION_ID, news.getID() + "");
                intent.putExtra(NotificationDetailsActivity.IMAGE, news.getImage());
                intent.putExtra(NotificationDetailsActivity.TITLE, news.getPostTitle());
                intent.putExtra(NotificationDetailsActivity.DATE, news.getPostDate());
                intent.putExtra(NotificationDetailsActivity.CONTENT, news.getPostContent());
                startActivity(intent);
            }
        });
        notificationAdapter.setEndlessLoadingListener(rvNotification, new BaseEndlessRecyclerAdapter.OnEndlessLoadListener() {
            @Override
            public void onEndlessLoad(int page) {

                loadNews(page + 1);
            }
        });

        rvNotification.setAdapter(notificationAdapter);
    }

    @Override
    public void initAction() {

        showLoading();
        loadNews(1);
    }

    private NotificationPresenter.ListAllCallback callback = new NotificationPresenter.ListAllCallback() {
        @Override
        public void onResponse(List<Notification> notifications) {
            dismissLoading();

            if (notifications.size() < WCDefaultValue.MAX_ITEM_PER_PAGE) {
                notificationAdapter.setEndlessLoadingEnable(false);
            }

            notificationAdapter.insertLoadmoreItems(notifications);
        }

        @Override
        public void onConnectionTimeOut() {

            dismissLoading();
            ErrorDialog.showConnectionTimeOutDialog(context);
        }
    };

    private void loadNews(int page) {

        NotificationPresenter.listAll(context, page, callback);
    }
}
