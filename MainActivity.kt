package com.jmromero.configurador

import android.app.Activity
import android.os.Bundle
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.*

class MainActivity : Activity() {
    private val navy = Color.rgb(23, 37, 84)
    private val blue = Color.rgb(37, 99, 235)
    private lateinit var email: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = navy
        window.navigationBarColor = navy

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.rgb(245, 247, 251))
        }
        val scroll = ScrollView(this)
        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(24), dp(20), dp(28))
        }
        scroll.addView(content)
        root.addView(scroll)
        setContentView(root)

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(dp(18), dp(22), dp(18), dp(22))
            setBackgroundColor(navy)
        }
        header.addView(TextView(this).apply {
            text = "✉"; textSize = 42f; setTextColor(Color.WHITE); gravity = Gravity.CENTER
        })
        header.addView(TextView(this).apply {
            text = "jmromero.exe"; textSize = 27f
            typeface = Typeface.DEFAULT_BOLD; setTextColor(Color.WHITE); gravity = Gravity.CENTER
        })
        header.addView(TextView(this).apply {
            text = "Configurador de correo corporativo"; textSize = 14f
            setTextColor(Color.rgb(219, 234, 254)); gravity = Gravity.CENTER
        })
        content.addView(header, matchWrap())

        addLabel(content, "Correo del cliente")
        email = EditText(this).apply {
            setText("gerencia@refhys.com")
            hint = "nombre@empresa.com"
            textSize = 16f
            singleLine = true
            inputType = android.text.InputType.TYPE_CLASS_TEXT or
                android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            setPadding(dp(14), dp(12), dp(14), dp(12))
            setBackgroundColor(Color.WHITE)
        }
        content.addView(email, matchWrap())

        addLabel(content, "Servidor de correo")
        addInfoCard(content, "ENTRANTE · IMAP", "da4.toservers.com", "Puerto 993 · SSL/TLS")
        addInfoCard(content, "SALIENTE · SMTP", "da4.toservers.com", "Puerto 465 · SSL/TLS")

        content.addView(button("Copiar datos de configuración") {
            val text = """
                Correo: ${email.text.toString().trim()}
                IMAP: da4.toservers.com
                Puerto IMAP: 993
                Seguridad IMAP: SSL/TLS
                SMTP: da4.toservers.com
                Puerto SMTP: 465
                Seguridad SMTP: SSL/TLS
                Usuario: dirección de correo completa
            """.trimIndent()
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("Configuración de correo", text))
            Toast.makeText(this, "Datos copiados", Toast.LENGTH_SHORT).show()
        }, matchWrap())

        content.addView(button("Abrir Gmail") {
            val launch = packageManager.getLaunchIntentForPackage("com.google.android.gm")
            if (launch != null) startActivity(launch)
            else try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(
                    "https://play.google.com/store/apps/details?id=com.google.android.gm")))
            } catch (_: Exception) {
                Toast.makeText(this, "No se pudo abrir Gmail", Toast.LENGTH_SHORT).show()
            }
        }, matchWrap())

        content.addView(button("Ver instrucciones") {
            AlertDialog.Builder(this)
                .setTitle("Cómo agregar el correo en Gmail")
                .setMessage(
                    "1. Abrí Gmail y tocá tu foto de perfil.\n" +
                    "2. Elegí Agregar otra cuenta.\n" +
                    "3. Para una cuenta de hosting habitual, elegí Otra / IMAP.\n" +
                    "4. Escribí la dirección completa y la contraseña del correo.\n" +
                    "5. Si Gmail pide servidores manuales, usá los datos que aparecen aquí.\n\n" +
                    "La contraseña se escribe en Gmail, no en jmromero.exe."
                )
                .setPositiveButton("Entendido", null)
                .show()
        }, matchWrap())

        content.addView(TextView(this).apply {
            text = "Importante: esta app no crea cuentas ni agrega automáticamente la cuenta a Gmail. Guía al usuario y facilita copiar los datos. La contraseña nunca se solicita ni se guarda aquí."
            textSize = 12f
            setTextColor(Color.rgb(75, 85, 99))
            setPadding(dp(4), dp(16), dp(4), dp(4))
        }, matchWrap())
    }

    private fun addLabel(parent: LinearLayout, value: String) {
        parent.addView(TextView(this).apply {
            text = value; textSize = 15f; typeface = Typeface.DEFAULT_BOLD
            setTextColor(navy); setPadding(0, dp(18), 0, dp(8))
        }, matchWrap())
    }

    private fun addInfoCard(parent: LinearLayout, label: String, server: String, detail: String) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(15), dp(13), dp(15), dp(13))
            setBackgroundColor(Color.WHITE)
        }
        card.addView(TextView(this).apply {
            text = label; textSize = 12f; typeface = Typeface.DEFAULT_BOLD; setTextColor(blue)
        })
        card.addView(TextView(this).apply {
            text = server; textSize = 17f; typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.rgb(31, 41, 55)); setPadding(0, dp(5), 0, dp(3))
        })
        card.addView(TextView(this).apply {
            text = detail; textSize = 13f; setTextColor(Color.rgb(75, 85, 99))
        })
        val params = LinearLayout.LayoutParams(-1, -2)
        params.bottomMargin = dp(10)
        parent.addView(card, params)
    }

    private fun button(label: String, action: () -> Unit, params: LinearLayout.LayoutParams): Button {
        return Button(this).apply {
            text = label; isAllCaps = false; textSize = 15f
            setTextColor(Color.WHITE); setBackgroundColor(blue)
            setOnClickListener {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
                action()
            }
            layoutParams = params.apply { height = dp(52); topMargin = dp(10) }
        }
    }

    private fun matchWrap() = LinearLayout.LayoutParams(-1, -2)
    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
}
