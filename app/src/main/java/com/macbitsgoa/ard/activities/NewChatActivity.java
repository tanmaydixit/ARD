package com.macbitsgoa.ard.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.macbitsgoa.ard.R;
import com.macbitsgoa.ard.adapters.NewChatAdapter;
import com.macbitsgoa.ard.keys.UserItemKeys;
import com.macbitsgoa.ard.models.UserItem;
import com.macbitsgoa.ard.utils.AHC;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class NewChatActivity extends BaseActivity {

    @BindView(R.id.pb_activity_new_chat)
    ProgressBar progressBar;

    @BindView(R.id.rv_activity_new_chat)
    RecyclerView userRV;

    @BindView(R.id.toolbar_activity_new_chat)
    Toolbar toolbar;

    final DatabaseReference adminsRef = getRootReference().child(AHC.FDR_ADMINS);
    final DatabaseReference usersRef = getRootReference().child(AHC.FDR_USERS);

    NewChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RealmResults<UserItem> adminsList = database.where(UserItem.class)
                .equalTo("admin", true)
                .notEqualTo("uid", getUser().getUid())
                .findAllSorted("name");
        RealmResults<UserItem> usersList = database.where(UserItem.class)
                .equalTo("admin", false)
                .notEqualTo("uid", getUser().getUid())
                .findAllSorted("name");

        adminsList.addChangeListener(new RealmChangeListener<RealmResults<UserItem>>() {
            @Override
            public void onChange(RealmResults<UserItem> userItems) {
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() > 0) {
                    progressBar.setIndeterminate(false);
                    progressBar.setProgress(100);
                }
            }
        });
        usersList.addChangeListener(new RealmChangeListener<RealmResults<UserItem>>() {
            @Override
            public void onChange(RealmResults<UserItem> userItems) {
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() > 0) {
                    progressBar.setIndeterminate(false);
                    progressBar.setProgress(100);
                }
            }
        });
        adapter = new NewChatAdapter(adminsList, usersList, this);
        userRV.setLayoutManager(new LinearLayoutManager(this));
        userRV.setHasFixedSize(true);
        userRV.setAdapter(adapter);

        //TODO normal users shouldn't be able to see other non admins

        adminsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String uid = child.getKey();
                    String name = child.child(UserItemKeys.NAME).getValue(String.class);
                    String email = child.child(UserItemKeys.EMAIL).getValue(String.class);
                    String photoUrl = child.child(UserItemKeys.PHOTO_URL).getValue(String.class);
                    String desc = child.child(UserItemKeys.DESC).getValue(String.class);
                    UserItem ui = database.where(UserItem.class).equalTo("uid", uid).findFirst();
                    database.beginTransaction();
                    if (ui == null) {
                        ui = database.createObject(UserItem.class, uid);
                    }
                    ui.setAdmin(true);
                    ui.setDesc(desc);
                    ui.setEmail(email);
                    ui.setName(name);
                    ui.setPhotoUrl(photoUrl);
                    database.commitTransaction();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String uid = child.getKey();
                    String name = child.child(UserItemKeys.NAME).getValue(String.class);
                    String email = child.child(UserItemKeys.EMAIL).getValue(String.class);
                    String photoUrl = child.child(UserItemKeys.PHOTO_URL).getValue(String.class);
                    UserItem ui = database.where(UserItem.class).equalTo("uid", uid).findFirst();
                    database.beginTransaction();
                    if (ui == null) {
                        ui = database.createObject(UserItem.class, uid);
                        ui.setAdmin(false);
                    }
                    ui.setEmail(email);
                    ui.setName(name);
                    ui.setPhotoUrl(photoUrl);
                    database.commitTransaction();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
