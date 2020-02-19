Firma v1.0

--- ES------------------------------------------------------------------------------------------------------------------

El directorio de la clave privada debe estar reflejado en el fichero private_key_path.txt, mientras que el del
certificado en certificate_path.txt. Los datos producidos en la salida, es decir, la firma, es almacenada en el fichero
text_signed.txt en forma de array de bytes.

En cuanto a la entrada del programa, cabe destacar que si es ejecutado sin ningún argumento adicional, se abrirá y no 
realizará ninguna operación. No obstante, si se le pasa un texto como parámetro, la aplicación buscará la clave privada 
y el certificado, firmará el texto, y generará una salida.

En la carpeta resources, se ha incluido un certificado y una clave privada genérica para probar. La contraseña de la 
clave privada es "password". Este conjunto clave privada-certificado ha sido generado por https://www.baeldung.com/