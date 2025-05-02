
<script>
	import { onMount } from 'svelte';
	import { fade } from 'svelte/transition';
	import { lowStock } from '$lib/services/api';

	let productosPorVencer = [];
	let productosStockBajo = [];
	let mensaje = '';
	let fechaHoy = '';
	let nameUserAuth = 'usuario';

	// Filtrar productos por fecha de vencimiento
	const obtenerProductosPorVencer = () => {
		const hoy = new Date();
		productosPorVencer = inventario.filter((producto) => {
			const fechaVencimiento = new Date(producto.fechaVencimiento);
			return fechaVencimiento <= hoy.setDate(hoy.getDate() + 7); // productos que vencen en 7 días o menos
		});
	};

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

		// Obtener productos con stock bajo desde la API
		const productos = await lowStock();
		if (productos?.object) {
			productosStockBajo = productos.object.map(p => ({
				nombre: p.name,
				cantidad: p.quantity
			}));
		}

		// Si quieres eliminar esta parte, borra la variable inventario y esta función
		obtenerProductosPorVencer();
	});

	// Si tienes productos con fecha de vencimiento simulados, defínelos aquí
	let inventario = [
		{ nombre: 'Leche blanca', cantidad: 5, fechaVencimiento: '2025-04-15' },
		{ nombre: 'Carne molida', cantidad: 200, fechaVencimiento: '2025-06-01' },
		{ nombre: 'Pollo congelado', cantidad: 3, fechaVencimiento: '2025-04-10' },
		{ nombre: 'Croquetas', cantidad: 5, fechaVencimiento: '2025-05-20' }
	];
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
