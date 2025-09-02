package com.cursosant.insurance.common.entities

import android.os.Parcelable
import com.cursosant.insurance.common.utils.Constants
import com.cursosant.insurance.common.utils.FormatUtils
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ServiceResponse(
    val error: @RawValue Any?, // puede ser objeto dinámico
    val id: Long?,
    val service: @RawValue Service?, // 👈 usa @RawValue porque Service puede no ser Parcelable
    val quote_coverages: @RawValue List<PolicyCoverage>, // 👈 idem
    val quote_pay_frecuency: @RawValue List<QuotePayFrequency>?,
    val parent_car_form: String? = null,
    val prima_total: String? = null,
    val paquete: String? = null,
    val frecuencia_pago: String? = null,
    val folio_quote: String? = null,
    val fecha_inicio: String? = null,
    val fecha_fin: String? = null,
    val car_company_code: String? = null,
    val parent_id: String?,
    var type: String = "",
    var description: String = "",
    var carDescription: String = ""
) : Parcelable {

    fun getPatternDate() = Constants.DATE_PATTERN_VALIDITY

    fun getContractor() = User.instance?.first_name

    fun getName() = service?.name ?: ""

    fun getTotalAmount(): String {
        return when (type) {
            Constants.FRQ_MONTHLY_VALUE -> {
                description =
                    "Primer pago:${getFirstPay(Constants.FRQ_MONTHLY)} y 11 de ${getSecondPay(Constants.FRQ_MONTHLY)}"
                "Total: ${getTotalAmountByFrequency(Constants.FRQ_MONTHLY)}"
            }

            Constants.FRQ_QUARTERLY_VALUE -> {
                description =
                    "Primer pago:${getFirstPay(Constants.FRQ_QUARTERLY)} y 3 de ${getSecondPay(Constants.FRQ_QUARTERLY)}"
                "Total: ${getTotalAmountByFrequency(Constants.FRQ_QUARTERLY)}"
            }

            Constants.FRQ_BIANNUAL_VALUE -> {
                description =
                    "Primer pago:${getFirstPay(Constants.FRQ_BIANNUAL)} y otro de ${getSecondPay(Constants.FRQ_BIANNUAL)}"
                "Total: ${getTotalAmountByFrequency(Constants.FRQ_BIANNUAL)}"
            }

            Constants.FRQ_ANNUAL_VALUE -> {
                description = "Prima total"
                "${getTotalAmountByFrequency(Constants.FRQ_ANNUAL)}"
            }

            else -> "N/A"
        }
    }

    private fun getTotalAmountByFrequency(frequency: String) =
        FormatUtils.formatCurrency(
            quote_pay_frecuency?.first { it.tipe == frequency }?.total_amount
        )

    private fun getFirstPay(frequency: String) =
        FormatUtils.formatCurrency(
            quote_pay_frecuency?.first { it.tipe == frequency }?.first_pay
        )

    private fun getSecondPay(frequency: String) =
        FormatUtils.formatCurrency(
            quote_pay_frecuency?.first { it.tipe == frequency }?.second_pay
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServiceResponse

        if (service != other.service) return false

        return true
    }

    override fun hashCode(): Int {
        return service?.hashCode() ?: 0
    }
}
