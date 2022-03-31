// Call the dataTables jQuery plugin
$(document).ready(function() {
  fCargarUsuarios();
  $('#usuarios').DataTable();

  fActualizarEmailUsuario();
});

function fActualizarEmailUsuario() {
  document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}


async function fCargarUsuarios() {

  const request = await fetch('api/usuarios', {
    method: 'GET',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      'Authorization': localStorage.token
    }
  });
  const usuarios = await request.json();


  let listadoHtml = '';

  for (let usuario of usuarios){
    let botonEliminar = '<a class="btn btn-danger btn-circle" href="#" onclick="fDeleteUsuario('+usuario.id+')"><i class="fas fa-trash-alt"></i></a>';
    let telefonoTexto = usuario.telefono == null ? ' - ' : usuario.telefono;

    let usuarioHtml = '<tr><td>'+usuario.id+'</td><td>'+usuario.nombre+ ' '+usuario.apellido+'</td><td>'
                      +usuario.email+'</td><td>'+telefonoTexto
                      +'</td><td>'+botonEliminar+'</td></tr>';

    listadoHtml += usuarioHtml;
  }

  console.log(usuarios);
  document.querySelector('#usuarios tbody').outerHTML = listadoHtml;
}

function getHeaders(){
  return {
    'Accept': 'application/json',
    'Content-Type': 'application/json',
    'Authorization': localStorage.token
  };
}

async function fDeleteUsuario(id) {

  if (!confirm('¿Desea eliminar este usuario?')){
    return;
  }
  const request = await fetch('api/usuario/'+id, {
    method: 'DELETE',
    headers: getHeaders
  });
  location.reload();
}
