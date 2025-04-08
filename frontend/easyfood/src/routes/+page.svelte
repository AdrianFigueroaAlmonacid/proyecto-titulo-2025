<script>
	import { goto } from '$app/navigation';
	let username = '';
	let password = '';

	const handleLogin = () => {
		console.log('Login:', { username, password });

		goto('users/productos');
	};
</script>

<!-- <Navbar /> -->

<div class="container mt-5">
	<div class="row justify-content-center text-center">
		<h1>Bienvenido a Easy Food</h1>
		<p>Administra tu inventario de productos de manera sencilla.</p>
	</div>
	<div class="row justify-content-center">
		<div class="col-md-4">
			<div class="card">
				<div class="card-body">
					<form on:submit|preventDefault={handleLogin}>
						<div class="mb-3">
							<label for="username" class="form-label">Usuario</label>
							<input
								type="text"
								id="username"
								class="form-control"
								bind:value={username}
								required
							/>
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
						<button type="submit" class="btn btn-primary w-100">Iniciar</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
