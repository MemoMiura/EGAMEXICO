package com.cursosant.insurance.common.entities

data class PolicyReceipt(val recibo_numero: Int,
                         val status: String,
                         val fecha_inicio: String,
                         val fecha_fin: String,
                         val prima_total: String,
                         val id: Long){
    fun getStatusByGroup(): String {
        val statusGrouped = when(status) {
            "Liquidado",
            "Conciliado" -> "Pagado"
            else -> status
        }
        return statusGrouped
    }
}
/*
(1,'Pagado'),
(2, 'Cancelado'),
(3,'Prorrogado'),
(4,'Pendiente de pago'),
(5,'Liquidado'),
(6,'Conciliado'),
(7,'Cerrado'),
(0, 'Desactivado'),
(8,'Precancelado') ,
(9,'Pago parcial'),
(10,'Anulado'),
(11,'PreAnulado')
*/
