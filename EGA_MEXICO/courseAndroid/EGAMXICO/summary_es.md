# Explicación del resumen de cambios

Este documento describe en español las modificaciones realizadas previamente para corregir el módulo de pólizas en la aplicación móvil:

1. **Ajuste del modelo `Policy`**: Se actualizó la entidad para que el campo `aseguradora` se interprete como un objeto `Aseguradora`, alineándolo con el formato que entrega el API. Esto evita que la lista de pólizas aparezca vacía cuando la información sí está disponible.
2. **Actualización del diseño de los ítems de póliza**: Se modificó la vista de cada tarjeta de póliza (`item_policy.xml`) para que muestre el alias de la aseguradora a partir del objeto anidado recibido del API.

Gracias a estos ajustes, la aplicación ahora reconoce correctamente los datos de las pólizas que llegan desde el servidor y los despliega en pantalla.
