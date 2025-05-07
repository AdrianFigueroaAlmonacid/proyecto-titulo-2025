<script>
	import { postLogin } from '$lib/services/api';
	import { goto } from '$app/navigation';

	let username = '';
	let password = '';
	let error = '';

	async function handleLogin() {
		try {
			const response = await postLogin(username, password);
			console.log(response);
			if (response.ok) {
				goto('/users/inicio');
			} else {
				error = response.message || 'Credenciales inválidas';
				console.log(response, 'error');
				return;
			}
		} catch (err) {
			error = 'Error de conexión con el servidor';
			console.log(err, 'err');
		}
	}
</script>

<div class="inicio vh-100 d-flex justify-content-center align-items-center">
	<div class="container" style="max-width: 500px;">

		<div class="card shadow">
			
			<div class="card-body">

				<div class="text-center mb-4">
					<h1>Bienvenido a Easy Food</h1>
					<p>Administra tu inventario de productos de manera sencilla.</p>
				</div>
				<form on:submit|preventDefault={() => handleLogin(username, password)}>
					<div class="mb-3">
						<label for="username" class="form-label">Usuario</label>
						<input type="text" id="username" class="form-control " bind:value={username} required />
					</div>
					<div class="mb-3">
						<label for="password" class="form-label">Contraseña</label>
						<input
							type="password"
							id="password"
							class="form-control"
							bind:value={password}
							required
						/>
					</div>
					<button type="submit" class="btn btn-primary w-100"> <strong>Ingresar</strong> </button>
				</form>
				{#if error}
					<div class="alert alert-danger mt-3" role="alert">
						{error}
					</div>
				{/if}
			</div>
		</div>
	</div>
</div>

<style>
.inicio{
	background-image: url("/img/fondo-easyfood.png");
	background-position: center;
	background-repeat: no-repeat;
	background-size: cover;
}


</style>