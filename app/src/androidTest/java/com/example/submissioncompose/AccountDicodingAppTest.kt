package com.example.submissioncompose

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.submissioncompose.model.FakeAccountDataSource
import com.example.submissioncompose.ui.navigation.Screen
import com.example.submissioncompose.ui.theme.SubmissionComposeTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AccountDicodingAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SubmissionComposeTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                AccountDicodingApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("AccountList").performScrollToIndex(2)
        composeTestRule.onNodeWithText(FakeAccountDataSource.dummyAccounts[2].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailScreen.route)
        composeTestRule.onNodeWithText(FakeAccountDataSource.dummyAccounts[2].name).assertIsDisplayed()
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_bookmark).performClick()
        navController.assertCurrentRouteName(Screen.Bookmark.route)
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesBack() {
        composeTestRule.onNodeWithTag("AccountList").performScrollToIndex(2)
        composeTestRule.onNodeWithText(FakeAccountDataSource.dummyAccounts[2].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailScreen.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_bookmarked_not_bookmarked() {
        composeTestRule.onNodeWithText(FakeAccountDataSource.dummyAccounts[2].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailScreen.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.no_favorite)).performClick()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.favorite)).performClick()
    }

    @Test
    fun navHost_clickBookmarked_backToList_andGoToBookmarkedScreen() {
        composeTestRule.onNodeWithTag("AccountList").performScrollToIndex(2)
        composeTestRule.onNodeWithText(FakeAccountDataSource.dummyAccounts[2].name).performClick()
        navController.assertCurrentRouteName(Screen.DetailScreen.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.no_favorite)).performClick()
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
        composeTestRule.onNodeWithStringId(R.string.menu_bookmark).performClick()
        navController.assertCurrentRouteName(Screen.Bookmark.route)
        composeTestRule.onNodeWithTag("BookmarkedList").performScrollToIndex(0)
    }
}