<script>
	import { onMount } from 'svelte';
	import { fade } from 'svelte/transition';
	import { jsPDF } from 'jspdf'; // Importa jsPDF

	// Lista de productos
	let productos = [
		{
			id: 1,
			nombre: 'Pan',
			precioCompra: 500,
			precioVenta: 1000,
			stock: 20,
			fechaVencimiento: '2025-06-01',
			categoria: 'Alimentos'
		},
		{
			id: 2,
			nombre: 'Leche',
			precioCompra: 700,
			precioVenta: 1200,
			stock: 15,
			fechaVencimiento: '2025-04-30',
			categoria: 'Lácteos'
		},
		{
			id: 3,
			nombre: 'Yogurt',
			precioCompra: 300,
			precioVenta: 700,
			stock: 30,
			fechaVencimiento: '2025-05-20',
			categoria: 'Lácteos'
		},
		{
			id: 4,
			nombre: 'Arroz',
			precioCompra: 400,
			precioVenta: 800,
			stock: 50,
			fechaVencimiento: '2026-06-01',
			categoria: 'Alimentos'
		}
	];

	// Lista de categorías únicas
	let categorias = ['Alimentos', 'Lácteos'];

	// Datos del producto en el modal
	let producto = {
		id: null,
		nombre: '',
		precioCompra: '',
		precioVenta: '',
		stock: '',
		fechaVencimiento: '',
		categoria: ''
	};
	let isEdit = false;

	// Estado del filtro de categoría
	let categoriaSeleccionada = 'todas'; // Estado para seleccionar la categoría o 'todas'

	// Función para filtrar productos por categoría seleccionada
	$: productosFiltrados = productos.filter(
		(p) => categoriaSeleccionada === 'todas' || p.categoria === categoriaSeleccionada
	);

	// Función para abrir el modal y editar o agregar un producto
	function abrirModal(nuevo = true, datos = null) {
		isEdit = !nuevo;
		producto = datos
			? { ...datos }
			: {
					id: null,
					nombre: '',
					precioCompra: '',
					precioVenta: '',
					stock: '',
					fechaVencimiento: '',
					categoria: ''
				};
		const modal = new bootstrap.Modal(document.getElementById('productoModal'));
		modal.show();
	}

	// Guardar producto (editar o agregar)
	function guardarProducto() {
		// Si es un nuevo producto, agregar la categoría a la lista si no existe
		if (!isEdit && !categorias.includes(producto.categoria)) {
			categorias = [...categorias, producto.categoria];
		}

		if (isEdit) {
			productos = productos.map((p) => (p.id === producto.id ? { ...producto } : p));
		} else {
			producto.id = Date.now(); // ID temporal
			productos = [...productos, { ...producto }];
		}

		// Cerrar el modal
		bootstrap.Modal.getInstance(document.getElementById('productoModal')).hide();
	}

	// Eliminar un producto
	function eliminarProducto(id) {
		productos = productos.filter((p) => p.id !== id);
	}

	// Generar informe en formato PDF
	function generarInforme() {
		const doc = new jsPDF();

		doc.text('INFORME DE PRODUCTOS', 20, 10);
		doc.text('\n', 20, 20);

		productosFiltrados.forEach((p, index) => {
			doc.text(`Producto ${index + 1}:`, 20, 30 + index * 10);
			doc.text(`Categoría: ${p.categoria}`, 20, 35 + index * 10);
			doc.text(`Nombre: ${p.nombre}`, 20, 40 + index * 10);
			doc.text(`Stock: ${p.stock}`, 20, 45 + index * 10);
			doc.text(`Fecha de Vencimiento: ${formatearFecha(p.fechaVencimiento)}`, 20, 50 + index * 10);
			doc.text(`Precio de Compra: $${p.precioCompra}`, 20, 55 + index * 10);
			doc.text(`Precio de Venta: $${p.precioVenta}`, 20, 60 + index * 10);
			doc.text('\n', 20, 65 + index * 10); // Espacio entre productos
		});

		doc.save('informe_productos.pdf');
	}

	// Función para formatear la fecha en formato d/m/a
	function formatearFecha(fecha) {
		const date = new Date(fecha);
		const dia = date.getDate().toString().padStart(2, '0');
		const mes = (date.getMonth() + 1).toString().padStart(2, '0');
		const anio = date.getFullYear();
		return `${dia}/${mes}/${anio}`;
	}
</script>

<!-- Tabla -->
<div
	class="container row d-flex justify-content-center text-center"
	style="max-width: 1200px; margin:auto;"
>
	<div class="d-flex justify-content-between align-items-center mb-3">
		<div>
			<!-- Selector de categoría -->
			<label for="categoriaSeleccionada" class="me-2">Filtrar por categoría:</label>
			<select id="categoriaSeleccionada" bind:value={categoriaSeleccionada} class="form-select">
				<option value="todas">Todas</option>
				{#each categorias as categoria}
					<option value={categoria}>{categoria}</option>
				{/each}
			</select>
		</div>
		<div>
			<!-- Botones -->
			<button class="btn btn-success me-2" on:click={generarInforme}>Generar Informe</button>
		</div>
	</div>

	<table class="table table-bordered table-hover">
		<thead class="table-light">
			<tr>
				<th>Categoría</th>
				<th>Nombre</th>
				<th>Precio Compra</th>
				<th>Precio Venta</th>
				<th>Stock</th>
				<th>Fecha Vencimiento</th>
				<th>Acciones</th>
			</tr>
		</thead>
		<tbody>
			{#each productosFiltrados as p}
				<tr>
					<td>{p.categoria}</td>
					<td>{p.nombre}</td>
					<td>${p.precioCompra}</td>
					<td>${p.precioVenta}</td>
					<td>{p.stock}</td>
					<td>{formatearFecha(p.fechaVencimiento)}</td>
					<td>
						<button class="btn btn-sm btn-warning me-2" on:click={() => abrirModal(false, p)}
							>Editar</button
						>
						<button class="btn btn-sm btn-danger" on:click={() => eliminarProducto(p.id)}
							>Eliminar</button
						>
					</td>
				</tr>
			{/each}
			{#if productos.length === 0}
				<tr>
					<td colspan="7" class="text-center">No hay productos</td>
				</tr>
			{/if}
		</tbody>
	</table>

	<div d-flex justi>
		<button class="btn btn-primary" on:click={() => abrirModal(true)}>Agregar Producto</button>
	</div>
</div>

<!-- Modal -->
<div
	class="modal fade"
	id="productoModal"
	tabindex="-1"
	aria-labelledby="productoModalLabel"
	aria-hidden="true"
>
	<div class="modal-dialog">
		<div class="modal-content" transition:fade>
			<div class="modal-header">
				<h5 class="modal-title" id="productoModalLabel">
					{isEdit ? 'Editar Producto' : 'Agregar Producto'}
				</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"
				></button>
			</div>
			<div class="modal-body">
				<form on:submit|preventDefault={guardarProducto}>
					<div class="mb-3">
						<label class="form-label">Nombre</label>
						<input class="form-control" type="text" bind:value={producto.nombre} required />
					</div>
					<div class="mb-3">
						<label class="form-label">Precio Compra</label>
						<input class="form-control" type="number" bind:value={producto.precioCompra} required />
					</div>
					<div class="mb-3">
						<label class="form-label">Precio Venta</label>
						<input class="form-control" type="number" bind:value={producto.precioVenta} required />
					</div>
					<div class="mb-3">
						<label class="form-label">Stock</label>
						<input class="form-control" type="number" bind:value={producto.stock} required />
					</div>
					<div class="mb-3">
						<label class="form-label">Fecha de Vencimiento</label>
						<input
							class="form-control"
							type="date"
							bind:value={producto.fechaVencimiento}
							required
						/>
					</div>
					<div class="mb-3">
						<label class="form-label">Categoría</label>
						<input class="form-control" type="text" bind:value={producto.categoria} required />
					</div>
					<button type="submit" class="btn btn-success">
						{isEdit ? 'Guardar Cambios' : 'Agregar Producto'}
					</button>
				</form>
			</div>
		</div>
	</div>
</div>
