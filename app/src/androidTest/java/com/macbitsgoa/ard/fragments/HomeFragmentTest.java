package com.macbitsgoa.ard.fragments;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.macbitsgoa.ard.R;
import com.macbitsgoa.ard.activities.MainActivity;
import com.macbitsgoa.ard.activities.PostDetailsActivity;
import com.macbitsgoa.ard.keys.AuthActivityKeys;
import com.macbitsgoa.ard.models.AnnItem;
import com.macbitsgoa.ard.models.TypeItem;
import com.macbitsgoa.ard.types.PostType;
import com.macbitsgoa.ard.utils.AHC;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.Sort;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by vikramaditya on 6/29/17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeFragmentTest {

    @Rule
    public IntentsTestRule<MainActivity> activityTestRule =
            new IntentsTestRule<>(MainActivity.class, false, false);

    @Before
    public void init() throws Throwable {
        activityTestRule.launchActivity(new Intent().putExtra(AuthActivityKeys.USE_DEFAULT, false));
        activityTestRule
                .runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                                .name("fakeRealm")
                                .inMemory()
                                .initialData(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        for (int i = 0; i < 30; i++) {
                                            AnnItem annItem = realm.createObject(AnnItem.class, "key " + i);
                                            annItem.setData("data " + i);
                                            annItem.setAuthor("author " + i);
                                            annItem.setDate(Calendar.getInstance().getTime());
                                        }
                                    }
                                })
                                .build();
                        final Realm database = Realm.getInstance(realmConfiguration);


                        final List<TypeItem> data = new ArrayList<>();
                        //Generate Announcement type list
                        for (final AnnItem annItem
                                : database.where(AnnItem.class).findAllSorted("date", Sort.DESCENDING)) {
                            data.add(new TypeItem(annItem, PostType.ANNOUNCEMENT));
                            Log.e(AHC.TAG, annItem.toString());
                        }
                        HomeFragment homeFragment = (HomeFragment) activityTestRule.getActivity()
                                .getSupportFragmentManager()
                                .findFragmentById(R.id.frame_content_main);
                        homeFragment.setupData(data);
                    }
                });

    }

    @Test
    public void testHomeFragment() throws Exception {
        onView(withId(R.id.fab_fragment_home_add))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.recyclerView_fragment_home)).check(matches(isDisplayingAtLeast(50))).perform(swipeUp());
        onView(withId(R.id.fab_fragment_home_add))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));

        onView(withId(R.id.recyclerView_fragment_home)).check(matches(isDisplayingAtLeast(50))).perform(swipeDown());
        onView(withId(R.id.fab_fragment_home_add))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
}
