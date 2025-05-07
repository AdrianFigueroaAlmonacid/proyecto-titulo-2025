<script>
	import { onMount } from 'svelte';
	import { fade } from 'svelte/transition';
	import { getUsers,deleteUser,updateUser,createUser,updatePassword } from '$lib/services/api';

	let usuarios = [
	];

	let nuevoNombre = '';
	let nuevoEmail = '';
	let nuevoId = 0;
	let nuevoUsername = '';
	let nuevoLastname = '';
	let nuevoPassword = '';



	let editandoIndex = -1;
	let modal;
	let mensaje = '';
	let mostrarMensaje = false;

	function abrirModal(editar = false, usuario = null) {
		if (editar && usuario !== null) {
			nuevoNombre = usuario.nombre;
			nuevoEmail = usuario.email;
			nuevoId = usuario.iduser;
			nuevoUsername = usuario.username;
			nuevoLastname = usuario.lastname;
			editandoIndex = 1;
		} else {
			nuevoNombre = '';
			nuevoEmail = '';
			nuevoUsername = '';
			nuevoLastname = '';
			nuevoPassword = '';
			editandoIndex = -1;
		}

		const modalInstance = new bootstrap.Modal(modal);
		modalInstance.show();
	}

	async function guardarUsuario() {

		if (editandoIndex === -1) {
			const datos = {
			name: nuevoNombre,
			email: nuevoEmail,
			username: nuevoUsername,
			lastname: nuevoLastname,
			password: nuevoPassword,
			roles: [
				"USER"
			]		
			};

			const actualizacion = await createUser(datos);
			if(actualizacion){
				mostrarAlerta('Usuario agregado con éxito ✅');
			}else{
				mostrarAlerta('Error al agregar usuario');
			}
		} else {
			const datos = {
				name: nuevoNombre,
				email: nuevoEmail,
				id: nuevoId,
				username: nuevoUsername,
				lastName: nuevoLastname
			
			};
			const datosPassword = {
				id: nuevoId,
				username: nuevoUsername,
				password:nuevoPassword
			
			};

			let actualizacionPassword = false;
			if(datosPassword.password !== ''){
				actualizacionPassword = await updatePassword(datosPassword);
			}

			const actualizacion = await updateUser(datos);

			if(actualizacion){
				if(actualizacionPassword){
					mostrarAlerta('Usuario y password asociada editado con éxito ✅');
				}else{
					mostrarAlerta('Usuario editado con éxito ✅');
				}
			}else{
				mostrarAlerta('Error al editar usuario');
			}
		}
		async function cargarUsuarios() {
			const data = await getUsers();
			if (data) {
					usuarios = data.map(user => ({
						fecha: user.registerDate,
						nombre: user.name,
						admin: user.admin,
						rol: user.position,
						email: user.email,
						lastname: user.lastname,
						username: user.username,
						iduser: user.idUser
					}));
				}
			}

			cargarUsuarios();

		editandoIndex = -1;
		nuevoNombre = '';
		nuevoEmail = '';
		nuevoId = 0;
		nuevoUsername = '';
		nuevoLastname = '';
		nuevoPassword = '';
	}

	async function eliminarUsuario(usuario) {
		const actualizacion = await deleteUser(usuario.iduser);
		if(actualizacion){
				mostrarAlerta('Usuario eliminado 🗑️');
			}else{
				mostrarAlerta('Error al eliminar usuario');
			}
		async function cargarUsuarios() {
		const data = await getUsers();
		if (data) {
				usuarios = data.map(user => ({
					fecha: user.registerDate,
					nombre: user.name,
					admin: user.admin,
					rol: user.position,
					email: user.email,
					lastname: user.lastname,
					username: user.username,
					iduser: user.idUser
				}));
			}
		}

		cargarUsuarios();
	}

	function mostrarAlerta(texto) {
		mensaje = texto;
		mostrarMensaje = true;
		setTimeout(() => {
			mostrarMensaje = false;
		}, 3000);
	}

	onMount(() => {
		if (!window.bootstrap) {
			console.error('Bootstrap JS no está cargado.');
		}
		async function cargarUsuarios() {
			const data = await getUsers();
			if (data) {
					usuarios = data.map(user => ({
						fecha: user.registerDate,
						nombre: user.name,
						admin: user.admin,
						rol: user.position,
						email: user.email,
						lastname: user.lastname,
						username: user.username,
						iduser: user.idUser
					}));
					console.log(usuarios);
				}
			}

			cargarUsuarios();
		});
</script>

<!-- TABLA DE USUARIOS -->
<div style="max-width: 1200px; margin:auto;" class="p-5 d-grid gap-4">
	<div class="text-center"><h1>Administracion de Usuarios</h1></div>

	<div class="table-responsive">
		<table class="table table-bordered table-hover text-center">
			<thead class="table-light">
				<tr>
					<th>Fecha de ingreso</th>
					<th>Username</th>
					<th>Nombre</th>
					<th>Apellido</th>
					<th>Email</th>
					<th>Rol</th>
					<th>Admin</th>
					<th>Acciones</th>
				</tr>
			</thead>
			<tbody>
				{#each usuarios as usuario}
					<tr>
						<td>{usuario.fecha}</td>
						<td>{usuario.username}</td>
						<td>{usuario.nombre}</td>
						<td>{usuario.lastname}</td>
						<td>{usuario.email}</td>
						<td>{usuario.rol}</td>
						<td>
							{#if usuario.admin}
								<span class="badge bg-success">Sí</span>
							{:else}
								<span class="badge bg-secondary">No</span>
							{/if}
						</td>
						<td>
							<div class="d-flex justify-content-center g-5">
								<button class="btn btn-warning me-2" on:click={() => abrirModal(true, usuario)} disabled={usuario.admin}>
									<i class="bi bi-pencil-square"></i> Editar</button
								>
								<button class="btn btn-sm btn-danger" on:click={() => eliminarUsuario(usuario)} disabled={usuario.admin}>
									<i class="bi bi-trash"></i> Eliminar</button
								>
							</div>
						</td>
					</tr>
				{/each}
			</tbody>
		</table>
				<!-- ALERTA -->
		{#if mostrarMensaje}
			<div class="alert alert-success mt-3" role="alert">
				{mensaje}
			</div>
		{/if}
	</div>

	<!-- BOTÓN AGREGAR -->
	<div class="d-flex justify-content-end align-items-center">
		<button class="btn btn-success" on:click={() => abrirModal()}
			><i class="bi bi-plus-circle"></i> Agregar Usuario</button
		>
	</div>
</div>

<!-- MODAL -->
<div bind:this={modal} class="modal fade" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form on:submit|preventDefault={guardarUsuario}>
				<div class="modal-header">
					<h5 class="modal-title">{editandoIndex === -1 ? 'Agregar Usuario' : 'Editar Usuario'}</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"
					></button>
				</div>
				{#if editandoIndex === -1}
					<div class="modal-body">
						<div class="mb-3">
							<label class="form-label">Username</label>
							<input type="text" class="form-control" bind:value={nuevoUsername} required />
						</div>
						<div class="mb-3">
							<label class="form-label">Nombre</label>
							<input type="text" class="form-control" bind:value={nuevoNombre} required />
						</div>
						<div class="mb-3">
							<label class="form-label">Apellido</label>
							<input type="text" class="form-control" bind:value={nuevoLastname} required />
						</div>
						<div class="mb-3">
							<label class="form-label">Email</label>
							<input type="text" class="form-control" bind:value={nuevoEmail} required />
						</div>
						<div class="mb-3">
							<label class="form-label">Password</label>
							<input type="text" class="form-control" bind:value={nuevoPassword} required />
						</div>
					</div>

				{/if}
				{#if editandoIndex !== -1}
						<div class="modal-body">
							<div class="mb-3">
								<label class="form-label">Id</label>
							<div class="readonly-box">{nuevoId}</div>
						</div>
						<div class="mb-3">
							<label class="form-label">Username</label>
							<div class="readonly-box">{nuevoUsername}</div>
						</div>
											<div class="mb-3">
							<label class="form-label">Nombre</label>
							<input type="text" class="form-control" bind:value={nuevoNombre} required />
						</div>
						<div class="mb-3">
							<label class="form-label">Apellido</label>
							<input type="text" class="form-control" bind:value={nuevoLastname} required />
						</div>
						<div class="mb-3">
							<label class="form-label">Email</label>
							<input type="text" class="form-control" bind:value={nuevoEmail} required />
						</div>
						<div class="mb-3">
							<label class="form-label">Password</label>
							<input type="text" class="form-control" bind:value={nuevoPassword} />
						</div>
					</div>
				{/if}
			
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
					<button type="submit" class="btn btn-success" data-bs-dismiss="modal">
						{editandoIndex === -1 ? 'Guardar' : 'Actualizar'}
					</button>
				</div>
			</form>
		</div>
	</div>
</div>
<style>
    .readonly-box {
        border: 1px solid #ced4da;
        padding: 0.375rem 0.75rem;
        border-radius: 0.25rem;
        background-color: #e9ecef;
        user-select: none;
        color: #495057;
    }
</style>