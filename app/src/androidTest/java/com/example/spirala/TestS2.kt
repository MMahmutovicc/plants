package com.example.spirala

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TestS2 {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(
        NovaBiljkaActivity::class.java
    )
    @get:Rule
    val intentsTestRule = IntentsTestRule(NovaBiljkaActivity::class.java)
    @Test
    fun premaloKaraktera() {
        onView(withId(R.id.nazivET)).perform(typeText("N"))
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(),click())

        onView(withId(R.id.nazivET)).check(matches(not(hasErrorText(""))))
        onView(withId(R.id.porodicaET)).check(matches(not(hasErrorText(""))))
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(not(hasErrorText(""))))
        onView(withId(R.id.jeloET)).check(matches(not(hasErrorText(""))))
    }

    @Test
    fun previseKaraktera() {
        onView(withId(R.id.nazivET)).perform(typeText("Duzina ovog stringa je veca od 20 karaktera<"))
        onView(withId(R.id.porodicaET)).perform(typeText("Duzina ovog stringa je veca od 20 karaktera"))
        onView(withId(R.id.medicinskoUpozorenjeET)).perform(typeText("Ispravna duzina"))

        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.nazivET)).check(matches(hasErrorText("Neispravna duzina")))
        onView(withId(R.id.porodicaET)).check(matches(hasErrorText("Neispravna duzina")))
        onView(withId(R.id.medicinskoUpozorenjeET)).check(matches(not(hasErrorText("Neispravna duzina"))))
    }
    @Test
    fun dvaIstaJela() {
        onView(withId(R.id.jeloET)).perform(typeText("Grah"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())

        onView(withId(R.id.jeloET)).perform(replaceText("grah"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())

        onView(withId(R.id.jeloET)).check(matches(hasErrorText("Jelo vec spremljeno")))

    }
    @Test
    fun dvaIstaJelaNakonIzmjene() {
        onView(withId(R.id.jeloET)).perform(typeText("Grah"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())

        onView(withId(R.id.jeloET)).perform(replaceText("gulas"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())

        onData(allOf(`is`("gulas"))).inAdapterView(allOf(withId(R.id.jelaLV), isDisplayed())).perform(click())

        onView(withId(R.id.jeloET)).perform(replaceText("grah"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onView(withId(R.id.jeloET)).check(matches(hasErrorText("Jelo vec spremljeno")))
    }
    @Test
    fun maloKarakteraNakonIzmjene() {
        onView(withId(R.id.jeloET)).perform(typeText("Grah"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())

        onView(withId(R.id.jeloET)).perform(replaceText("gulas"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onData(allOf(`is`("gulas"))).inAdapterView(withId(R.id.jelaLV)).perform(click())
        onView(withId(R.id.jeloET)).perform(replaceText("g"))
        onView(withId(R.id.dodajJeloBtn)).perform(scrollTo(), click())
        onView(withId(R.id.jeloET)).check(matches(hasErrorText("Neispravna duzina")))
    }
    @Test
    fun nijedanKlimatskiTip() {
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.ktTV)).check(matches(not(hasErrorText(equalTo("")))))
    }
    @Test
    fun bezProfilaOkusa() {
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.poTV)).check(matches(not(hasErrorText(equalTo("")))))
    }
    @Test
    fun bezDodanihJela() {
        onView(withId(R.id.dodajBiljkuBtn)).perform(scrollTo(), click())
        onView(withId(R.id.jTV)).check(matches(not(hasErrorText(equalTo("")))))
    }

}