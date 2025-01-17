package com.example.pracainynierska

public enum class ViewRoutes(val viewName: String) {
    HOMEPAGE("HomepageView"),
    PROFILE("ProfileView"),
    STATISTICS("StatisticView"), // TODO: RENAME TO CATEGORYVIEW
    CALENDAR("CalendarsView"),
    SHOP("ShopView"),
    ACHIEVEMENTS("AchievementsView"),
    ADDCATEGORY("AddCategoryView"),
    ADDTASK("AddTaskView"),
    EDITTASK("EditTaskView"),
    SETTINGS("SettingsView"),
    LOGIN("LoginView"),
    REGISTER("RegisterView"),
    FORGOTPASSWORD("ForgotPasswordView"),
    CHANGEFORGOTPASSWORD("ChangeForgotPasswordView")
}