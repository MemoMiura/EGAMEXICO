package com.cursosant.insurance.common.utils

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import javax.inject.Inject
import javax.inject.Singleton

/****
 * Project: ANT Insurance
 * From: com.cursosant.insurance.common.utils
 * Created by Alain Nicolás Tello on 15/07/23 at 14:48
 * All rights reserved 2023.
 *
 * All my Udemy Courses:
 * https://www.udemy.com/user/alain-nicolas-tello/
 * And Frogames formación:
 * https://cursos.frogamesformacion.com/pages/instructor-alain-nicolas
 *
 * Coupons on my Website:
 * www.alainnicolastello.com
 ***/
@Singleton
class NavUtils @Inject constructor() {
    lateinit var navController: NavController
    var actionLoginToRegister: Int = -1
    var actionDeleteAccountToLogin = -1
    var actionDocumentsToPdf = -1
    var actionPolicyDetailToPdf = -1
    var actionPoliciesToPolicyDetail = -1
    var actionSignoutToLogin = -1
    var actionAboutToPrivacy = -1
    var actionRegisterToLogin = -1
    var actionLoginToHome = -1
    var actionQuoteToQuoteResult = -1
    var actionQuoteResultToQuoteResultDetail = -1
    var actionDocSubramosToDocInsurers = -1
    var actionDocInsurersToDocuments = -1
    var actionNotificationsToNotificationDetail = -1

    var actionToAbout = -1
    var actionToPolicies = -1
    var actionToInsurers = -1
    var actionToQuote = -1
    var actionToCallEmergency = -1
    var actionToSinister = -1
    var actionToDocuments = -1
    var actionToContact = -1
    var actionToTickets = -1
    var actionToLibrary = -1
    var actionToNotifications = -1
}