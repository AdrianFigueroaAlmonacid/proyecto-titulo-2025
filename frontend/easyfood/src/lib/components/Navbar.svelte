
<script>
import { onMount } from 'svelte';
import { fade } from 'svelte/transition';
import { logout, isAdminUser } from '$lib/services/api';
let isAdmin = false;
onMount(() => {
		isAdmin = isAdminUser();
	});
	import { page } from '$app/stores';
	$: currentPath = $page.url.pathname;
</script>


<nav class="navbar navbar-expand-lg">
	<div class="container-fluid">
		<a class="navbar-brand" href="/users/inicio">Easy Food</a>
		<button
			class="navbar-toggler"
			type="button"
			data-bs-toggle="collapse"
			data-bs-target="#navbarNav"
			aria-controls="navbarNav"
			aria-expanded="false"
			aria-label="Toggle navigation"
		>
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav ms-auto gap-5">
				<li class="nav-item">
					<a class="nav-link {currentPath === '/users/inicio' ? 'active' : ''}  fw-bold" href="/users/inicio">Inicio</a>
				</li>
				<li class="nav-item">
					<a class="nav-link {currentPath === '/users/productos' ? 'active' : ''} fw-bold" href="/users/productos">Productos</a>
				</li>
				<li class="nav-item">
					<a class="nav-link {currentPath === '/users/venta' ? 'active' : ''} fw-bold" href="/users/venta">Venta</a>
				</li>
				<li class="nav-item  {isAdmin ? '' : 'd-none'} ">
					<a class="nav-link {currentPath === '/users/manejarUsuarios' ? 'active' : ''} fw-bold" href="/users/manejarUsuarios">Manejo Usuarios</a>
				</li>

				<li>
					<button type="button" class="btn btn-danger fw-bold" on:click={logout}>
						Cerrar sesión <i class="bi bi-box-arrow-in-left"></i>
					</button>
				</li>
			</ul>
		</div>
	</div>
</nav>

<style>
	.navbar {
		background-color: #007bff !important; /* Cambia esto por el color que desees */
	}

	.navbar-brand {
		font-weight: bold;
		color: white !important;
	}

	.nav-link {
		font-size: 1.2rem;
		color: white !important;
	}

	.nav-link.active {
		margin-top: 0;
	font-weight: bold;
	border-bottom: 2px solid #fff;
}
</style>
