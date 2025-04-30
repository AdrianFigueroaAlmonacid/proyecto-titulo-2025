<script>
	import { goto } from '$app/navigation';
	let username = '';
	let password = '';
	let error = '';

	const handleLogin = async () => {
		try {
			const response = await fetch('http://localhost:8081/api/v1/auth', {
				method: 'POST',
				headers: { 'Content-Type': 'application/json' },
				body: JSON.stringify({ username, password })
			});
	
			if (response.ok) {
				goto('/users/inicio');
			} else {
				error = response.message || 'Credenciales inválidas';
				console.log(response,"error")
				return;
			}
	
			localStorage.setItem('token', response.token);

		} catch (err) {
			error = 'Error de conexión con el servidor';
			console.log(err,"err")
		}
	};
</script>

<!-- <Navbar /> -->
<div class="container vh-100 d-flex justify-content-center align-items-center">
	<div class="" style="max-width: 500px;">
		<div class="text-center mb-4">
			<h1>Bienvenido a Easy Food</h1>
			<p>Administra tu inventario de productos de manera sencilla.</p>
		</div>
		<div class="card shadow">
			<div class="card-body">
				<form on:submit|preventDefault={handleLogin}>
					<div class="mb-3">
						<label for="username" class="form-label">Usuario</label>
						<input type="text" id="username" class="form-control" bind:value={username} required />
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
					<button type="submit" class="btn btn-primary w-100"> <strong>Iniciar</strong> </button>
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
