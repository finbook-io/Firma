Firma v1.0

--- ES------------------------------------------------------------------------------------------------------------------

Compatibilidad: Linux

La aplicación puede ser ejecutada de dos formas:
- Ejecutando directamente Firma.sh, que abrirá el programa sin realizar ninguna acción.
- Ejecutando Firma.sh por consola junto a un parámetro, que será el texto a firmar. En este caso, pedirá la contraseña
de la clave privada, y una vez se introduzca, si es correcta, firmará el texto.

El directorio de la clave privada debe estar reflejado en el fichero private_key_path.txt, mientras que el del
certificado en certificate_path.txt. Los datos producidos en la salida, es decir, la firma, es almacenada en el fichero
text_signed.txt en forma de array de bytes