<script>
	import { onMount } from 'svelte';
	import { fade } from 'svelte/transition';
	// Simulación de datos de inventario
	let inventario = [
		{ id: 1, nombre: 'Producto A', cantidad: 100, fechaVencimiento: '2025-05-10' },
		{ id: 2, nombre: 'Producto B', cantidad: 5, fechaVencimiento: '2025-04-15' },
		{ id: 3, nombre: 'Producto C', cantidad: 200, fechaVencimiento: '2025-06-01' },
		{ id: 4, nombre: 'Producto D', cantidad: 3, fechaVencimiento: '2025-04-10' },
		{ id: 5, nombre: 'Producto E', cantidad: 50, fechaVencimiento: '2025-05-20' }
	];

	let productosPorVencer = [];
	let productosStockBajo = [];
	let mensaje = '';
	let fechaHoy = '';

	// Filtrar productos por fecha de vencimiento
	const obtenerProductosPorVencer = () => {
		const hoy = new Date();
		productosPorVencer = inventario.filter((producto) => {
			const fechaVencimiento = new Date(producto.fechaVencimiento);
			return fechaVencimiento <= hoy.setDate(hoy.getDate() + 7); // productos que vencen en 7 días o menos
		});
	};

	// Filtrar productos con stock bajo
	const obtenerProductosStockBajo = () => {
		productosStockBajo = inventario.filter((producto) => producto.cantidad <= 10);
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
		const mes = String(hoy.getMonth() + 1).padStart(2, '0'); // Los meses son base 0
		const año = hoy.getFullYear();
		fechaHoy = `${dia}-${mes}-${año}`;
	};

	onMount(() => {
		obtenerProductosPorVencer();
		obtenerProductosStockBajo();
		obtenerMensajeDeBienvenida();
		obtenerFechaHoy();
	});
</script>

<div class="container mt-5">
	<div>
		<h1>{mensaje}</h1>
		<p><strong>Fecha:</strong> {fechaHoy}</p>
	</div>

	<div class="row text-center">
		<!-- Resumen de productos por vencer -->
		<div class="col-md-6">
			<h3>Productos por Vencer</h3>
			{#if productosPorVencer.length > 0}
				<div class="alert alert-danger" role="alert">
					<ul class="list-group">
						{#each productosPorVencer as producto}
							<li class="list-group-item">
								<strong>{producto.nombre}</strong> - Vence el: {producto.fechaVencimiento}
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
				<ul class="list-group">
					{#each productosStockBajo as producto}
						<li class="list-group-item">
							<strong>{producto.nombre}</strong> - Stock: {producto.cantidad} unidades
						</li>
					{/each}
				</ul>
			{:else}
				<p>No hay productos con stock bajo.</p>
			{/if}
		</div>
	</div>
</div>

<style>
	.container {
		max-width: 900px;
	}

	h1 {
		color: #007bff;
	}

	.list-group-item {
		font-size: 1.1rem;
	}

	.list-group-item strong {
		color: #343a40;
	}

	.alert {
		font-size: 1rem;
	}
</style>
