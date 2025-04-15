<script>
	import { onMount } from 'svelte';

	let usuarios = [
		{ fecha: '2025-01-01', nombre: 'Juan', admin: true, rol: 'Administrador' },
		{ fecha: '2025-01-02', nombre: 'Ana', admin: false, rol: 'Vendedor' },
		{ fecha: '2025-01-03', nombre: 'Luis', admin: true, rol: 'Supervisor' }
	];

	let nuevoNombre = '';
	let nuevoAdmin = false;
	let nuevoRol = '';
	let nuevaFecha = '';

	let editandoIndex = -1;
	let modal;
	let mensaje = '';
	let mostrarMensaje = false;

	function abrirModal(editar = false, index = -1) {
		if (editar && index !== -1) {
			const usuario = usuarios[index];
			nuevoNombre = usuario.nombre;
			nuevoAdmin = usuario.admin;
			nuevoRol = usuario.rol;
			nuevaFecha = usuario.fecha;
			editandoIndex = index;
		} else {
			nuevoNombre = '';
			nuevoAdmin = false;
			nuevoRol = '';
			nuevaFecha = '';
			editandoIndex = -1;
		}

		const modalInstance = new bootstrap.Modal(modal);
		modalInstance.show();
	}

	function guardarUsuario() {
		const datos = {
			fecha: nuevaFecha,
			nombre: nuevoNombre,
			admin: nuevoAdmin,
			rol: nuevoRol
		};

		if (editandoIndex === -1) {
			usuarios = [...usuarios, datos];
			mostrarAlerta('Usuario agregado con éxito ✅');
		} else {
			usuarios[editandoIndex] = datos;
			usuarios = [...usuarios];
			mostrarAlerta('Usuario editado correctamente ✏️');
		}

		editandoIndex = -1;
		nuevoNombre = '';
		nuevoAdmin = false;
		nuevoRol = '';
		nuevaFecha = '';
	}

	function eliminarUsuario(index) {
		usuarios.splice(index, 1);
		usuarios = [...usuarios];
		mostrarAlerta('Usuario eliminado 🗑️');
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
	});
</script>

<!-- ALERTA -->
{#if mostrarMensaje}
	<div class="alert alert-success mt-3" role="alert">
		{mensaje}
	</div>
{/if}

<!-- TABLA DE USUARIOS -->
<div style="max-width: 1200px; margin:auto;" class="p-5 d-grid gap-4">
	<div class="text-center"><h1>Administracion de Usuarios</h1></div>

	<table class="table table-bordered table-hover">
		<thead class="table-light">
			<tr>
				<th>Fecha</th>
				<th>Nombre</th>
				<th>Rol</th>
				<th>Admin</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			{#each usuarios as usuario, i}
				<tr>
					<td>{usuario.fecha}</td>
					<td>{usuario.nombre}</td>
					<td>{usuario.rol}</td>
					<td>
						{#if usuario.admin}
							<span class="badge bg-success">Sí</span>
						{:else}
							<span class="badge bg-secondary">No</span>
						{/if}
					</td>
					<td>
						<button class="btn btn-warning me-2" on:click={() => abrirModal(true, i)}>
							<i class="bi bi-pencil-square"></i> Editar</button
						>
						<button class="btn btn-sm btn-danger" on:click={() => eliminarUsuario(i)}>
							<i class="bi bi-trash"></i> Eliminar</button
						>
					</td>
				</tr>
			{/each}
		</tbody>
	</table>
	<!-- BOTÓN AGREGAR -->
	<div class="d-flex justify-content-end align-items-center my-3">
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
				<div class="modal-body">
					<div class="mb-3">
						<label class="form-label">Fecha</label>
						<input type="date" class="form-control" bind:value={nuevaFecha} required />
					</div>
					<div class="mb-3">
						<label class="form-label">Nombre</label>
						<input type="text" class="form-control" bind:value={nuevoNombre} required />
					</div>
					<div class="mb-3">
						<label class="form-label">Rol</label>
						<input type="text" class="form-control" bind:value={nuevoRol} required />
					</div>
					<div class="form-check">
						<input
							class="form-check-input"
							type="checkbox"
							bind:checked={nuevoAdmin}
							id="adminCheck"
						/>
						<label class="form-check-label" for="adminCheck">¿Permiso Admin?</label>
					</div>
				</div>
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
