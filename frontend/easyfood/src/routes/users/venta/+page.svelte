<script>
	import { onMount } from 'svelte';
	import { fade } from 'svelte/transition';
	import { getProducts, sellProduct } from '$lib/services/api';

	let inventario = [];
	let productoSeleccionado = null;
	let cantidadVenta = 0;
	let mensaje = '';
	let busqueda = '';

	$: productosFiltrados = inventario.filter(p =>
		p.name.toLowerCase().includes(busqueda.toLowerCase())
	);

	onMount(async () => {
		const productos = await getProducts();
		console.log('Productos recibidos:', productos);
		if (productos) {
			inventario = productos;
		}
	});

	const manejarVenta = async () => {
		if (!productoSeleccionado) {
			mensaje = 'Por favor, selecciona un producto.';
			return;
		}

		const producto = inventario.find(p => p.id === productoSeleccionado);

		if (!producto) {
			mensaje = 'Producto no encontrado.';
			return;
		}

		if (cantidadVenta <= 0) {
			mensaje = 'La cantidad debe ser mayor a 0.';
			return;
		}

		const resultado = await sellProduct({
			id: productoSeleccionado,
			quantity: cantidadVenta
		});

		if (resultado) {
			mensaje = `Venta exitosa: ${cantidadVenta} unidades de ${producto.name}.`;
			const productosActualizados = await getProducts();
			if (productosActualizados) {
				inventario = productosActualizados;
			}
		} else {
			mensaje = 'Error al realizar la venta, no se pueden vender más productos de los que hay en stock.';
		}

		productoSeleccionado = null;
		busqueda = '';
		cantidadVenta = 0;
	};
</script>


<div class="d-flex justify-content-center mt-5">
	<div class="container mt-5">
		<h1 class="text-center">Venta de Productos</h1>

		<div class="row justify-content-center">
			<div class="col-md-8">
				<div class="card">
					<div class="card-body">
						<form on:submit|preventDefault={manejarVenta}>
							<div class="mb-3">
								<label for="busqueda" class="form-label">Buscar Producto</label>
								<input
									type="text"
									id="busqueda"
									class="form-control"
									placeholder="Escribe el nombre del producto"
									bind:value={busqueda}
								/>
							</div>

							<ul class="list-group mb-3" style="max-height: 200px; overflow-y: auto;">
								{#each productosFiltrados as producto}
									<li
										class="list-group-item list-group-item-action"
										on:click={() => {
											productoSeleccionado = producto.id;
											busqueda = producto.name;
										}}
										style="cursor: pointer;"
									>
										{producto.name} (Stock: {producto.quantity}, Vencimiento: {producto.expirationDate})
									</li>
								{/each}
								{#if productosFiltrados.length === 0}
									<li class="list-group-item text-muted">No se encontraron productos.</li>
								{/if}
							</ul>

							<div class="mb-3">
								<label for="cantidad" class="form-label">Cantidad a Vender</label>
								<input
									type="number"
									id="cantidad"
									class="form-control"
									bind:value={cantidadVenta}
									required
									min="1"
								/>
							</div>

							<button type="submit" class="btn btn-primary w-100">
								<i class="bi bi-bag-plus"></i> Registrar Venta
							</button>
						</form>

						{#if mensaje}
							<div
								class="alert mt-3"
								class:alert-success={mensaje.includes('exitosa')}
								class:alert-danger={!mensaje.includes('exitosa')}
							>
								{mensaje}
							</div>
						{/if}
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<style>
	.container {
		max-width: 700px;
	}

	.alert {
		font-size: 1.2rem;
	}

	.list-group-item-action:hover {
		background-color: #f0f0f0;
	}
</style>
