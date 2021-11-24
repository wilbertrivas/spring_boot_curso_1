package com.cursoJava.cursoJava.controllers;

import com.cursoJava.cursoJava.dao.UsuarioDao;
import com.cursoJava.cursoJava.models.Usuario;
import com.cursoJava.cursoJava.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
/**@RestController es una versión especializada del controlador.
 * Incluye los @Controller y @ResponseBody anotaciones, y como resultado, simplifica la implementación del controlador:*/
public class AuthController {
    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired //porque si vamos a JWTUtils tiene la anotación @controller
                // y esto es para compartir en todos los lugar y para usar los @values para poder cargar la información de las propesties
    private JWTUtils jwtUtils;

    @RequestMapping(value = "api/loginUsuario", method = RequestMethod.POST)
    public String login(@RequestBody Usuario usuario){
         Usuario userLogueado= usuarioDao.obtenerUsuarioPorCrendenciales(usuario);
         if(userLogueado != null){//Se valido el usuario correctamente
             String tokenJWT=jwtUtils.create(userLogueado.getId().toString(),userLogueado.getEmail());
             return tokenJWT;
         }else{
             return "FAIL";
         }
        /*if(usuarioDao.verificarEmailPassword(usuario)){//Inicio
            obtenerUsuarioPorCrendenciales(usuario)
            jwtUtils.create()
            return "OK";

        }else{
            return "FAIL";
        }*/
    }
}
/***
 * @Controller y @RestController en Spring MVC.
 *
 * Podemos usar la primera anotación para los controladores Spring tradicionales, y ha sido parte del marco durante mucho tiempo.
 *
 * Spring 4.0 introdujo la anotación @RestController para simplificar la creación de servicios web RESTful.
 * Es una anotación conveniente que combina @Controller y @ResponseBody , lo que elimina la necesidad de
 * anotar cada método de manejo de solicitudes de la clase del controlador con la anotación @ResponseBody .
 *
 *
 * @controller
 *Podemos anotar controladores clásicos con la anotación @Controller . Esto es simplemente una especialización
 * de la  clase @Component , que nos permite detectar automáticamente las clases de implementación a través del escaneo de classpath.
 * Por lo general, usamos  @Controller en combinación con una anotación @RequestMapping para los métodos de manejo de solicitudes.
 *
 *
 *Veamos un ejemplo rápido del controlador Spring MVC:
 *
 * @Controller
 * @RequestMapping("books")
 * public class SimpleBookController {
 *
 *     @GetMapping("/{id}", produces = "application/json")
 *     public @ResponseBody Book getBook(@PathVariable int id) {
 *         return findBookById(id);
 *     }
 *
 *     private Book findBookById(int id) {
 *         // ...
 *     }
 * }
 *
 *
 *
 * @RestController es una versión especializada del controlador. Incluye los @Controller y
 * @ResponseBody anotaciones, y como resultado, simplifica la implementación del controlador:
 *
 * @RestController
 * @RequestMapping("books-rest")
 * public class SimpleBookRestController {
 *
 *     @GetMapping("/{id}", produces = "application/json")
 *     public Book getBook(@PathVariable int id) {
 *         return findBookById(id);
 *     }
 *
 *     private Book findBookById(int id) {
 *         // ...
 *     }
 * }
 * **/

/**JWT.
 * ANTES: Se guardaba la sesion en el servidor (Le exige más al servidor). Problema si se
 * crea mas de un servidor el usuario tendrá problema al cambiar de servidor porque no existe la sesión
 *
 * MODERNA: se crea la sesión y se envia al usuario(Cliente), al tener varios servidores no tendrá problema.
 *
 *
 *
 *  ESCALAR VERTICALMENTE: Agregar mas memoria al servidor, mas procesamiento y ams almacenamiento
 *
 *   ESCALAR HORIZONTALMENTE: se crea mas sevidores de nuestro servicio (Servidores pequeños).
 *
 *
 *   Cuando trabaajos con JWT, no se guarda nada en el servidor, se crea el JWT en el servidor y lo retorna al Cliente y el cliente lo guarda.
 *
 * */