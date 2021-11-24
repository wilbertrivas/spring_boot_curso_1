// Call the dataTables jQuery plugin
$(document).ready(function() {
    //on Ready
});


async function registrarUsuario(){
        let datos= {};
        datos.nombre=document.getElementById('txtNombre').value;
        datos.apellido=document.getElementById('txtApellido').value;
        datos.email=document.getElementById('txtEmail').value;
        datos.telefono=document.getElementById('txtTelefono').value;
        datos.password=document.getElementById('txtContrasena1').value;

        let repetirContrasena =document.getElementById('txtContrasena2').value;
        if(repetirContrasena != datos.password){
            alert('Las contraseñas son diferentes');
            return;
        }else{
            const request = await fetch('api/registrarUsuario', {
                method: 'POST',
                headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
                },
                 body: JSON.stringify(datos)
              });
              alert("La cuenta fue creada con exito!");
              window.location.href= 'login.html';
        }
  }

