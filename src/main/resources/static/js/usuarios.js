// Call the dataTables jQuery plugin
$(document).ready(function() {
    cargarUsuario();
  $('#usuarios').DataTable();
  actualizarEmailUsuario();
});

function getHeaders(){
    return{
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.token
    };
}
function actualizarEmailUsuario(){
    document.getElementById('txt_email_usuario').outerHTML=localStorage.email;
}

async function cargarUsuario(){
      const request = await fetch('api/listUsuarios', {
        method: 'GET',
        headers: getHeaders()
      });
      const usuarios = await request.json();

 let listUsuariohtml='';
     for(let usuario of usuarios){
     let botonEliminar='<a href="#" onClick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle btn-sm">'+ '<i class="fas fa-trash"></i>'+'</a>';
        listUsuariohtml +='<tr><td>'+usuario.id+'</td>'+
                               '<td>'+usuario.nombre +' '+ usuario.apellido+'</td>'+
                               '<td>'+usuario.email +'</td>'+
                               '<td>'+usuario.telefono +'</td>'+
                               '<td>'+botonEliminar+'</td>'+'</tr>';
     }
    let usuario =document.querySelector('#usuarios tbody').outerHTML=listUsuariohtml;
      console.log(usuarios);
}

async function eliminarUsuario(id){
    //alert(id);
    if(confirm('Desea eliminar el usuario?')){//COnfirmamos que si
        const request = await fetch('api/usuario/'+id, {
            method: 'DELETE',
            headers: getHeaders()
          });
          //const usuarios = await request.json();
          location.reload();
    }
}
