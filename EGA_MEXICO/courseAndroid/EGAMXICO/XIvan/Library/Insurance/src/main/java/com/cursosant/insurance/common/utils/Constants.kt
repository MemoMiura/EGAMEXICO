package com.cursosant.insurance.common.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

/****
 * Project: Insurance
 * From: com.cursosant.insurance.common.utils
 * Created by Alain Nicolás Tello on 25/05/23 at 14:40
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
object Constants {
    const val BASE_URL = "https://users-api.miurabox.com/"

    const val BASE_USER_URL = "https://users-api.miurabox.com/"
    const val BASE_MIURABOX_URL = "https://api.miurabox.com/"
    const val BASE_MULTI_QUOTE = "https://grupoasapi.multicotizador.com/"

    const val PATH_LOGIN = "/app-us-login"
    const val PATH_FORGOT_PASSWORD = "/forgot-password"
    const val PATH_RESEND_ACTIVATION = "resend-activation-email"
    const val PATH_MULTI_QUOTER = "/us-login-multicotizador"
    const val PATH_REGISTER = "/cas-create-user"
    const val PATH_DELETE_ACCOUNT = "/desactivar-usuario-app"
    const val PATH_DISABLE_ACCOUNT = "/desactivar_user_app"

    const val PATH_POLICIES = "/poliza-by-user-app/"
    const val PATH_INSURERS = "/provider-by-user-app/"
    const val PATH_POLICY = "/v1/policies-detail-app/"
    const val PATH_DOCUMENTS = "/archivos/editables/"
    const val PATH_CONTACT = "/external_contact/"
    const val PATH_SINISTER = "/report-siniester"
    const val PATH_CAR_TYPE = "/get-car-type/"
    const val PATH_STATES = "/get-states"
    const val PATH_CAR_BRAND = "/get-car-brand/"
    const val PATH_CAR_YEAR = "/get-car-year/"
    const val PATH_CAR_MODEL = "/get-car-model/"
    const val PATH_CITY = "/get-cities/"
    const val PATH_SUBURB = "/get-cols-by-state-city/"
    const val PATH_CP = "/get-cp-by-state-city-col/"
    const val PATH_SUBURBS_BY_CP = "/get-cols-by-cp/"
    const val PATH_QUOTE_SERVICES = "/get-services"
    const val PATH_QUOTE_SERVICE_RUN = "/run-service/"
    const val PATH_NOTIFICATIONS = "/dataNotificationsSpecific-by-user-app/"
    const val PATH_DOC_SUBRAMOS = "/editablesformat-by-user-app/"
    //const val PATH_DOC_SUBRAMOS = "/editablesformat-by-user-app-ancora/"

    const val P_TYPE = "type"
    const val P_BRAND = "brand"
    const val P_YEAR = "year"
    const val P_STATE_ID = "stateId"
    const val P_CITY = "city"
    const val P_SUBURB = "suburb"
    const val P_CP = "cp"

    const val H_AUTHORIZATION = "Authorization"
    const val H_BEARER = "Bearer "

    const val P_ID = "id"
    const val P_USERNAME = "username"
    const val P_EMAIL = "email"
    const val P_FIRST_NAME = "first_name"
    const val V_FIRST_NAME = "Juan" //getQuote
    const val P_LAST_NAME = "last_name"
    const val V_LAST_NAME = "Peréz" // getQuote
    const val P_PASSWORD = "password"
    const val P_URL_NAME = "urlname"
    const val V_URL_NAME = "grupoasapi"
    const val P_ORGANIZATION = "org"
    const val V_ORGANIZATION = "pruebas"
    //Contact
    const val P_CONTACT_NAME = "from_name"
    const val P_CONTACT_EMAIL = "from_email"
    const val P_CONTACT_MESSAGE = "message"
    const val V_CONTACT_STATUS = "sent"
    //Sinister
    const val P_POLICY_ID = "policy_id"
    //Quote
    const val P_ADDRESS_ONE = "address_1"
    const val P_ADDRESS_TWO = "address_2"
    const val P_BIRTHDATE = "birthdate"
    const val P_CAR_BD = "car_bd"
    const val V_CAR_BD = "27" // TODO: analysis all hardcoded values
    //const val V_CAR_BD = 27
    const val P_CAR_BRAND = "car_brand"
    const val P_CAR_CLASS = "car_class"
    const val P_CAR_MODEL_ID = "car_model"
    const val P_CAR_SERIAL = "car_serial"
    const val V_CAR_SERIAL = "\"NumSerie\""
    const val P_CAR_SEX = "car_sex"
    const val P_CAR_YEAR = "car_year"
    const val P_CIVIL_STATE = "civil_state"
    const val V_CIVIL_STATE = "2"
    //const val V_CIVIL_STATE = 2
    const val P_COB = "cob"
    const val V_COB = "Amplia"
    const val P_COUNTRY = "country"
    const val V_COUNTRY = "México"
    const val P_DD_STATE = "ddEst"
    const val P_DD_CITY = "ddMun"
    //const val P_EMAIL = "email"
    const val P_STATE_ABA_ID = "estado_aba_id"
    const val V_STATE_ABA_ID = "22"
    //const val V_STATE_ABA_ID = 22
    const val P_Q_STATE_ID = "estado_id"
    const val P_END_VALIDITY = "fin_vigencia"
    //const val P_FIRST_NAME = "first_name"
    const val P_START_VALIDITY = "inicio_vigencia"
    const val P_LADA = "lada"
    const val V_LADA = "44"
    //const val P_LAST_NAME = "last_name"
    const val P_MODEL_NAME = "model_name"
    const val P_PHONE = "phone"
    const val V_PHONE = "71233333"
    const val P_Q_FREQUENCY = "quoteFrecuency"
    const val V_Q_FREQUENCY = "12"
    //const val V_Q_FREQUENCY = 12
    const val P_Q_PAYMENT_METHOD = "quotePaymentMethod"
    const val P_RFC = "rfc"
    const val V_RFC = "PELJ801007"
    const val P_SECOND_LAST_NAME = "second_last_name"
    const val V_SECOND_LAST_NAME = "López"
    const val P_SERVICES = "services"
    const val P_STATE_NAME = "state_name"
    //const val P_URL_NAME = "urlname"
    const val P_XML = "xml"
    const val P_ZIP_CODE = "zip_code"
    const val P_FORM_DATA_ID = "form_data_id"
    const val P_SERVICE = "service"
    const val P_ = ""

    const val SUBRAMO_DAMAGES = "Daños"
    const val SUBRAMO_DAMAGES_FIRE = "Incendio"
    const val SUBRAMO_DAMAGES_MULTI = "Diversos"
    const val SUBRAMO_MEDIC_EXPENSES = "Gastos Médicos"
    const val SUBRAMO_HEALTH = "Salud"
    const val SUBRAMO_CAR = "Automóviles"
    const val SUBRAMO_LIFE = "Vida"

    const val SHOW = true
    const val HIDE = false

    const val PROVIDER_DIR = "com.cursosant.antinsurance.fileprovider"
    const val PATH_APP_FILE = "/ant_insurance_files/"

    const val PHONE_911 = "911"

    const val DATE_PATTERN = "dd/MM/yyyy"
    const val DATE_PATTERN_VALIDITY = "yyyy-MM-dd"
    const val DATE_PATTERN_BIRTHDATE_UI = "MM/dd/yyyy"
    const val DATE_PATTERN_BIRTHDATE_PARAM = "yyyy-MM-dd"
    const val DATE_PATTERN_BIRTHDATE = "yyyy-dd-MM"

    const val SUFFIX_PDF = ".pdf"
    const val PREFIX_WEBSITE = "http"

    //Quote
    const val FRQ_MONTHLY = "Mensual"
    const val FRQ_QUARTERLY = "Trimestral"
    const val FRQ_BIANNUAL = "Semestral"
    const val FRQ_ANNUAL = "Contado"
    const val FRQ_MONTHLY_VALUE = "12"
    const val FRQ_QUARTERLY_VALUE = "3"
    const val FRQ_BIANNUAL_VALUE = "6"
    const val FRQ_ANNUAL_VALUE = "1"

    const val COVERAGE_DEDUCTIBLE_DEFAULT = "Amparada"

    //DataStore
    const val K_USER_PREFERENCES = "k_user_preferences"
    val K_EMAIL = stringPreferencesKey("k_email")
    val K_PASSWORD = stringPreferencesKey("k_password")

    //Arguments
    const val ARG_NAME = "name"
    const val ARG_URL = "url"
    const val ARG_ID = "id"
    const val ARG_CODE = "code"
    const val ARG_POLICY_SUBRAMO = "subramo"
    const val ARG_QUOTE_RESPONSE = "quote_response"
    const val ARG_QUOTE_REQUEST = "quote_request"
    const val ARG_SERVICE_RESPONSE = "service_response"
    const val ARG_CAR_DESCRIPTION = "auto_description"
    const val ARG_IS_ANCORA = "is_ancora"
    const val ARG_TITLE = "title"
    const val ARG_DESCRIPTION = "description"
    const val ARG_IMG_URL = "imgUrl"
    const val ARG_THERE_IS_NOTIFY = "there_is_notify"
    const val VALUE_THERE_IS_NOTIFY = "true"

    //Network Response
    const val STATUS_OK = "200"

    //Notifications
    const val TOPIC_NEWS = "news"
    val K_THERE_IS_NOTIFY = booleanPreferencesKey("k_there_is_notifications")
}