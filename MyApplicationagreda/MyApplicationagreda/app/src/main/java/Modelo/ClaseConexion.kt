package Modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {

    fun cadenaConexion(): Connection?{

        try {
            val url = "jdbc:oracle:thin:@ 192.168.73.123:1521:xe"
            val usuario = "system"
            val contrasena = "itr2020"

            val connection = DriverManager.getConnection(url, usuario, contrasena)

            return connection
        }catch (e:Exception){
            println("Este es el error $e")
            return null
        }
    }
}
