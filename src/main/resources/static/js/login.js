// Call the dataTables jQuery plugin
$(document).ready(function() {
    //on Ready
});


async function iniciarSesion(){
    let datos= {};
    datos.email=document.getElementById('txtEmail').value;
    datos.password=document.getElementById('txtContrasena1').value;


        const request = await fetch('api/loginUsuario', {
            method: 'POST',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            },
             body: JSON.stringify(datos)
          });
          const respuesta = await request.text();
          if(respuesta != 'FAIL'){//login correcto
                localStorage.token=respuesta;
                localStorage.email=datos.email;
                window.location.href='usuarios.html'
          }else{
                alert('Las credenciales son incorrectas, intentar nuevmaente');
          }



}

