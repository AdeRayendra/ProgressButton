/*
 * Copyright (C) 2016 Nihas Kalam.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nihaskalam.sample;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;


/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class FixedTimeProgressButtonInstrumentedTest {
    private Activity activity;
    private String completeStateText, idleStateText, cancelStateText;
    private static final String emptyString = "";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);


    @Before
    public void setUp() {
        activity = mActivityRule.getActivity();
        completeStateText = activity.getResources().getString(R.string.Complete);
        idleStateText = activity.getResources().getString(R.string.submit);
        cancelStateText = activity.getResources().getString(R.string.Cancel);
    }

    @Test
    public void verifyProgressCycleWithCompleteStateIcon() throws InterruptedException {
        // Click submit button.
        Espresso.onView(allOf(withId(R.id.circularButton2), withText(idleStateText)))
                .perform(click());
        Thread.sleep(MainActivity.sweepDuration + MainActivity.sweepDuration/3);

        // Check the text and compound drawable.
        Espresso.onView(withId(R.id.circularButton2))
                .check(matches(allOf(withText(emptyString), Utils.withCompoundDrawable(R.drawable.ic_action_accept))));
        Utils.clickAndShowIdleState(idleStateText, R.id.circularButton2);
    }

    @Test
    public void verifyProgressCycleWithCompleteStateText() throws InterruptedException {
        // Click submit button.
        Espresso.onView(allOf(withId(R.id.circularButton2), withText(idleStateText)))
                .perform(click());
        Thread.sleep(MainActivity.sweepDuration + MainActivity.sweepDuration/3);

        // Check the text and compound drawable.
        Espresso.onView(withId(R.id.circularButton2))
                .check(matches(allOf(withText(completeStateText), not(Utils.withCompoundDrawable(R.drawable.ic_action_accept)))));

        Utils.clickAndShowIdleState(idleStateText, R.id.circularButton2);
    }

    @Test
    public void verifyProgressCycleWithCancelStateIcon() throws InterruptedException {
        // Click submit button.
        Espresso.onView(allOf(withId(R.id.circularButton2), withText(idleStateText)))
                .perform(click());
        Thread.sleep(Constants.SUBMIT_TO_PROGRESS_MORPH_DURATION);
        Espresso.onView(withId(R.id.circularButton2)).perform(click());
        Thread.sleep(Constants.MORPH_DURATION);

        // Check the text and compound drawable.
        Espresso.onView(withId(R.id.circularButton2))
                .check(matches(allOf(withText(emptyString), Utils.withCompoundDrawable(R.drawable.ic_action_cross))));
        Utils.clickAndShowIdleState(idleStateText, R.id.circularButton2);
    }

    @Test
    public void verifyProgressCycleWithCancelStateText() throws InterruptedException {
        // Click submit button.
        Espresso.onView(allOf(withId(R.id.circularButton2), withText(idleStateText)))
                .perform(click());
        Thread.sleep(Constants.SUBMIT_TO_PROGRESS_MORPH_DURATION);
        Espresso.onView(withId(R.id.circularButton2)).perform(click());
        Thread.sleep(Constants.MORPH_DURATION);

        // Check the text and compound drawable.
        Espresso.onView(withId(R.id.circularButton2))
                .check(matches(allOf(withText(completeStateText), not(Utils.withCompoundDrawable(R.drawable.ic_action_cross)))));
        Utils.clickAndShowIdleState(idleStateText, R.id.circularButton2);
    }
}
