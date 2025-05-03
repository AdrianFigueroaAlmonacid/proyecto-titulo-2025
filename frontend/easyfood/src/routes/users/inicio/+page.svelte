<script>
	import { onMount } from 'svelte';
	import { fade } from 'svelte/transition';
	import { lowStock, expiringSoon } from '$lib/services/api';

	let productosPorVencer = [];
	let productosStockBajo = [];
	let mensaje = '';
	let fechaHoy = '';
	let nameUserAuth = 'usuario';

	// Mensaje de buenos días según la hora
	const obtenerMensajeDeBienvenida = () => {
		const hora = new Date().getHours();
		if (hora < 12) {
			mensaje = '¡Buenos días!';
		} else if (hora < 18) {
			mensaje = '¡Buenas tardes!';
		} else {
			mensaje = '¡Buenas noches!';
		}
	};

	// Obtener la fecha de hoy en formato dd-mm-yyyy
	const obtenerFechaHoy = () => {
		const hoy = new Date();
		const dia = String(hoy.getDate()).padStart(2, '0');
		const mes = String(hoy.getMonth() + 1).padStart(2, '0');
		const año = hoy.getFullYear();
		fechaHoy = `${dia}-${mes}-${año}`;
	};

	onMount(async () => {
		obtenerMensajeDeBienvenida();
		obtenerFechaHoy();

		// Productos con bajo stock
		const productosStock = await lowStock();
		if (productosStock?.object) {
			productosStockBajo = productosStock.object.map((p) => ({
				nombre: p.name,
				cantidad: p.quantity
			}));
		}

		// Productos por vencer
		const productosVencimiento = await expiringSoon();
if (productosVencimiento?.object) {
	productosPorVencer = productosVencimiento.object
		.filter(p => !!p.date)
		.map(p => {
			const fecha = new Date(p.date);
			if (isNaN(fecha)) {
				console.warn('Fecha inválida en producto:', p);
				return null;
			}

			const dia = String(fecha.getDate()).padStart(2, '0');
			const mes = String(fecha.getMonth() + 1).padStart(2, '0');
			const año = fecha.getFullYear();
			const fechaFormateada = `${dia}-${mes}-${año}`;

			return {
				nombre: p.name,
				fechaVencimiento: fechaFormateada
			};
		})
		.filter(Boolean);
}

	});
</script>

<div class="container mt-5 d-flex justify-content-center align-items-center row m-auto">
	<div>
		<h1>{mensaje} {nameUserAuth}</h1>
		<h2>Este es el resumen de hoy <strong>{fechaHoy}</strong></h2>
	</div>

	<div class="row text-center mt-5">
		<!-- Resumen de productos por vencer -->
		<div class="col-md-6">
			<h3>Productos por Vencer</h3>
			{#if productosPorVencer.length > 0}
				<div class="alert alert-danger" role="alert">
					<ul class="list-group">
						{#each productosPorVencer as producto}
							<li class="list-group-item text-start">
								<strong>{producto.nombre}</strong> - Vence el:
								<strong>{producto.fechaVencimiento}</strong>
							</li>
						{/each}
					</ul>
				</div>
			{:else}
				<p>No hay productos próximos a vencer.</p>
			{/if}
		</div>

		<!-- Resumen de productos con stock bajo -->
		<div class="col-md-6">
			<h3>Productos con Stock Bajo</h3>
			{#if productosStockBajo.length > 0}
				<div class="alert alert-warning">
					<ul class="list-group">
						{#each productosStockBajo as producto}
							<li class="list-group-item text-start">
								<strong>{producto.nombre}</strong> - Stock: <strong>{producto.cantidad}</strong> unidades
							</li>
						{/each}
					</ul>
				</div>
			{:else}
				<p>No hay productos con stock bajo.</p>
			{/if}
		</div>
	</div>
</div>

<style>
	.container {
		max-width: 1000px;
	}

	h1 {
		color: #007bff;
	}

	.list-group-item {
		font-size: 1.2rem;
	}

	.list-group-item strong {
		color: #343a40;
	}

	.alert {
		font-size: 1rem;
	}
</style>
