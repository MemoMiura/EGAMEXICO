package com.cursosant.insurance.common.utils

import com.cursosant.insurance.R

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.utils
 * Created by Alain Nicolás Tello on 02/02/23 at 14:37
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
enum class TypeError(val resMsg: Int) {
    UNKNOWN(R.string.common_error_unknow),
    LOGIN(R.string.login_error),
    LOGIN_SAVE_USER(R.string.login_error),
    FORGOT_PASSWORD(R.string.login_recovery_email_sent_error_unknown),
    RESEND_ACTIVATION(R.string.login_activation_email_sent_error_unknown),
    REGISTER(R.string.register_error),
    POLICIES(R.string.policies_error),
    POLICY(R.string.policy_error),
    PDF_DOWNLOAD(R.string.pdf_download_error),
    PDF_LOAD(R.string.pdf_load_error),
    PDF_COMPAT(R.string.pdf_compat_error),
    DOCUMENTS(R.string.documents_error),
    INSURERS(R.string.insurers_error),
    SIGN_OUT(R.string.sign_out_error),
    CONTACT_MESSAGE(R.string.contact_send_message_error),
    DELETE_ACCOUNT(R.string.delete_account_error),
    SINISTER_REPORT(R.string.sinister_report_error),
    SINISTER_SENT_REPORT(R.string.sinister_report_sent_error),
    LOGIN_MULTI_QUOTER(R.string.quoter_error),
    QUOTE_GENDERS(R.string.quote_genders_error),
    QUOTE_TYPES(R.string.quote_types_error),
    QUOTE_STATES(R.string.quote_states_error),
    QUOTE_BRANDS(R.string.quote_brands_error),
    QUOTE_YEARS(R.string.quote_years_error),
    QUOTE_MODELS(R.string.quote_models_error),
    QUOTE_CITIES(R.string.quote_cities_error),
    QUOTE_SUBURBS(R.string.quote_suburbs_error),
    QUOTE_SUBURBS_BY_CP(R.string.quote_suburbs_by_cp_error),
    QUOTE_CP(R.string.quote_cp_error),
    QUOTE_COVERAGES(R.string.quote_coverages_error),
    QUOTE_FREQUENCIES(R.string.quote_frequencies_error),
    QUOTE(R.string.quote_error),
    NOTIFICATIONS(R.string.notifications_error),
    SUBRAMOS(R.string.subramos_error),
    CONNECT(R.string.common_error_connect)
}