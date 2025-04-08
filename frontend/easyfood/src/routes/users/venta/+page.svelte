<script>
	// Simulación del inventario
	let inventario = [
		{ id: 1, nombre: 'Producto A', cantidad: 100 },
		{ id: 2, nombre: 'Producto B', cantidad: 50 },
		{ id: 3, nombre: 'Producto C', cantidad: 200 }
	];

	let productoNombre = '';
	let cantidadVenta = 0;
	let mensaje = '';

	const manejarVenta = () => {
		// Buscar el producto en el inventario
		const producto = inventario.find(
			(p) => p.nombre.toLowerCase() === productoNombre.toLowerCase()
		);

		if (producto) {
			if (producto.cantidad >= cantidadVenta) {
				// Restar la cantidad del inventario
				producto.cantidad -= cantidadVenta;
				mensaje = `Venta exitosa. Quedan ${producto.cantidad} unidades de ${producto.nombre}.`;
			} else {
				mensaje = `No hay suficientes unidades de ${producto.nombre}.`;
			}
		} else {
			mensaje = `Producto ${productoNombre} no encontrado en el inventario.`;
		}

		// Limpiar los campos de entrada
		productoNombre = '';
		cantidadVenta = 0;
	};
</script>

<div class="container mt-5">
	<h1 class="text-center">Venta de Productos</h1>

	<div class="row justify-content-center">
		<div class="col-md-8">
			<div class="card">
				<div class="card-body">
					<form on:submit|preventDefault={manejarVenta}>
						<div class="mb-3">
							<label for="producto" class="form-label">Nombre del Producto</label>
							<input
								type="text"
								id="producto"
								class="form-control"
								bind:value={productoNombre}
								required
							/>
						</div>
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
						<button type="submit" class="btn btn-primary w-100">Registrar Venta</button>
					</form>

					{#if mensaje}
						<div
							class="alert mt-3"
							class:alert-success={mensaje.includes('exitosa')}
							class:alert-danger={mensaje.includes('No hay')}
						>
							{mensaje}
						</div>
					{/if}
				</div>
			</div>
		</div>
	</div>
</div>

<style>
	.container {
		max-width: 600px;
	}

	.alert {
		font-size: 1.2rem;
	}
</style>
