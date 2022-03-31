$(document).ready(function() {
});

async function fRegistrarUsuarios() {
   let datos= {};
   datos.nombre= document.getElementById('txtNombre').value;
   datos.apellido= document.getElementById('txtApellido').value;
   datos.email= document.getElementById('txtEmail').value;
   datos.telefono= document.getElementById('txtTelefono').value;
   datos.password= document.getElementById('txtPassword').value;

   let repetirPassword = document.getElementById('txtRepeatPassword').value;

   if (datos.password !== repetirPassword){
      alert('Las passwords deben coincidir, por favor verifique nuevamente');
      return;
   }
   const request = await fetch('api/usuarios', {
      method: 'POST',
      headers: {
         'Accept': 'application/json',
         'Content-Type': 'application/json'

      },
      body: JSON.stringify(datos)
   });
   alert('La cuenta fue creada con exito');

   window.location.href = 'ingresar.html';
}

