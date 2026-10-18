JMROMERO.EXE — CONFIGURADOR DE CORREO CORPORATIVO

INCLUYE
- App Android con nombre jmromero.exe.
- Correo de ejemplo gerencia@refhys.com, editable por cliente.
- IMAP da4.toservers.com, puerto 993, SSL/TLS.
- SMTP da4.toservers.com, puerto 465, SSL/TLS.
- Botones para copiar datos, abrir Gmail y ver instrucciones.
- GitHub Actions para compilar un APK de prueba.

IMPORTANTE
La app no puede añadir silenciosamente una cuenta a Gmail. El cliente debe completar
el inicio de sesión/configuración en Gmail. La contraseña no se solicita ni se guarda
en esta aplicación. Los servidores fueron proporcionados por el usuario; conviene
probarlos con una cuenta real antes de distribuir el APK.

COMPILAR DESDE GITHUB (se puede hacer desde el celular)
1. En GitHub, crea un repositorio nuevo llamado jmromero-configurador.
2. Sube todos los archivos y carpetas conservando la estructura.
3. Verifica que esté incluido .github/workflows/build-apk.yml.
4. Abre la pestaña Actions del repositorio y habilita Actions si te lo pide.
5. Entra en "Build Android APK" y toca "Run workflow" si aparece.
6. Espera a que termine con un tilde verde.
7. Abre esa ejecución y descarga el artefacto "jmromero-exe-apk".
8. Descomprime el ZIP descargado: dentro estará app-debug.apk.
9. Toca el APK en el teléfono para instalarlo. Autoriza la instalación desde esa fuente
   solo si confías en el archivo que acabas de compilar.

USO
1. Abre jmromero.exe y cambia el correo de ejemplo por el del cliente.
2. Toca "Copiar datos de configuración" si necesita los servidores.
3. Toca "Abrir Gmail".
4. En Gmail: foto de perfil > Agregar otra cuenta.
5. Para una cuenta de hosting habitual, elige "Otra" / IMAP.
6. Si pide datos manuales, usa IMAP 993 SSL/TLS y SMTP 465 SSL/TLS.
7. El nombre de usuario normalmente es la dirección completa. El cliente introduce
   la contraseña directamente en Gmail.

LIMITACIONES
Este es un proyecto fuente que debe compilarse; todavía no es un APK listo.
Es un APK debug para pruebas, no una app publicada en Play Store.
El nombre jmromero.exe aparece en la pantalla; como no se adjuntó un archivo de logo,
se utiliza un símbolo de correo provisional.
