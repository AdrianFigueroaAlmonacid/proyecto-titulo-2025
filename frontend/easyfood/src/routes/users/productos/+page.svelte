<script>
	import { onMount } from 'svelte';
	let productos = [
		{ id: 1, nombre: 'Arroz', precio: 1500, cantidad: 50 },
		{ id: 2, nombre: 'Azúcar', precio: 1200, cantidad: 30 },
		{ id: 3, nombre: 'Harina', precio: 1000, cantidad: 40 }
	];

	function editarProducto(id) {
		// Aquí iría la lógica para editar el producto
		console.log(`Editar producto con id: ${id}`);
	}

	function borrarProducto(id) {
		// Aquí iría la lógica para borrar el producto
		productos = productos.filter((producto) => producto.id !== id);
	}

	onMount(async () => {
		// Si en el futuro haces una llamada a una API, puedes usar este código
		// productos = await fetch('/api/products').then((res) => res.json());
	});
</script>

<div
	class="container row d-flex justify-content-center text-center"
	style="max-width: 1200px; margin:auto;"
>
	<h1>Lista de Productos</h1>
	<table class="table">
		<thead>
			<tr>
				<th>Nombre</th>
				<th>Precio</th>
				<th>Cantidad</th>
				<th>Acciones</th>
				<!-- Columna para los botones -->
			</tr>
		</thead>
		<tbody>
			{#each productos as producto}
				<tr>
					<td>{producto.nombre}</td>
					<td>{producto.precio}</td>
					<td>{producto.cantidad}</td>
					<td>
						<!-- Botones de editar y borrar -->
						<button class="btn btn-warning me-2" on:click={() => editarProducto(producto.id)}>
							Editar
						</button>
						<button class="btn btn-danger" on:click={() => borrarProducto(producto.id)}>
							Borrar
						</button>
					</td>
				</tr>
			{/each}
		</tbody>
	</table>

	<div class="d-flex justify-content-end">
		<button type="button" class="btn btn-success me-3">Agregar Producto</button>
	</div>
</div>
