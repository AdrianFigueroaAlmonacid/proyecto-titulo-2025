<script>
	import { onMount } from 'svelte';
	import { fade } from 'svelte/transition';
	import { jsPDF } from 'jspdf';
	import {
		getProducts,
		updateProduct,
		deleteProduct,
		createProduct,
		getCategory,
		isAdminUser,
		sellMonth
	} from '$lib/services/api';

	let productos = [];
	let categorias = [];

	let producto = {
		id: null,
		name: '',
		price: '',
		quantity: '',
		expirationDate: '',
		category: ''
	};

	let isEdit = false;
	let categoriaSeleccionada = 'todas';
	let esAdmin = false;

	$: productosFiltrados = productos.filter(
		(p) => categoriaSeleccionada === 'todas' || p.category === categoriaSeleccionada
	);

	onMount(async () => {
		productos = await getProducts();
		categorias = await getCategory();
		esAdmin = isAdminUser();
	});

	function abrirModal(nuevo = true, datos = null) {
		isEdit = !nuevo;

		producto = datos
			? {
					id: datos.id,
					name: datos.name,
					price: datos.price,
					quantity: datos.quantity,
					expirationDate: datos.expirationDate,
					category: datos.categoryChange
			  }
			: {
					id: null,
					name: '',
					price: '',
					quantity: '',
					expirationDate: '',
					category: ''
			  };

		const modal = new bootstrap.Modal(document.getElementById('productoModal'));
		modal.show();
	}

	async function guardarProducto() {
		const token = localStorage.getItem('token');

		if (isEdit && producto.id) {
			const ok = await updateProduct(producto, token);
			if (ok) {
				location.reload();
			}
		} else {
			const ok = await createProduct(producto, token);
			if (ok) {
				location.reload();
			}
		}
	}

	async function eliminarProducto(id) {
		const token = localStorage.getItem('token');
		const resultado = await deleteProduct(id, token);
		if (resultado) {
			const actualizado = await getProducts();
			if (actualizado) {
				productos = actualizado;
			}
		}
	}

	async function generarInforme() {
		const productosVendidos = await sellMonth(); // Llamar a tu servicio

		if (!productosVendidos || productosVendidos.length === 0) {
			console.log('No hay productos vendidos este mes.');
			return;
		}

		const productosAgrupados = {};
		productosVendidos.forEach(p => {
			if (productosAgrupados[p.nameProduct]) {
				productosAgrupados[p.nameProduct] += p.quantity;
			} else {
				productosAgrupados[p.nameProduct] = p.quantity;
			}
		});

		const productosResumen = Object.entries(productosAgrupados).map(([nombre, cantidad]) => ({
			nameProduct: nombre,
			quantity: cantidad
		}));

		const doc = new jsPDF();
		let baseY = 20;
		let productosPorPagina = 5; 

		doc.text('INFORME DE PRODUCTOS VENDIDOS ESTE MES', 20, 10);

		productosResumen.forEach((p, index) => {
			
			if (index > 0 && index % productosPorPagina === 0) {
				doc.addPage();
				baseY = 20;
				doc.text('INFORME DE PRODUCTOS VENDIDOS ESTE MES', 20, 10);
			}

			doc.text(`Producto ${index + 1}:`, 20, baseY);
			doc.text(`Nombre: ${p.nameProduct}`, 20, baseY + 10);
			doc.text(`Cantidad Total Vendida: ${p.quantity}`, 20, baseY + 20);

			baseY += 40; 
		});

		doc.save('informe_productos_vendidos.pdf');
	}

	function formatearFecha(fecha) {
		const date = new Date(fecha);
		if (isNaN(date)) return '';
		const dia = date.getDate().toString().padStart(2, '0');
		const mes = (date.getMonth() + 1).toString().padStart(2, '0');
		const anio = date.getFullYear();
		return `${dia}/${mes}/${anio}`;
	}
</script>

<!-- HTML -->
<div class="container row d-flex justify-content-center text-center" style="max-width: 1200px; margin:50px auto;">
	<div class="text-center"><h1>Administración de Productos</h1></div>

	<div class="d-flex justify-content-between align-items-center mb-3">
		<div>
			<label for="categoriaSeleccionada" class="me-2">Filtrar por categoría:</label>
			<select id="categoriaSeleccionada" bind:value={categoriaSeleccionada} class="form-select">
				<option value="todas">Todas</option>
				{#each categorias as categoria}
					<option value={categoria.name}>{categoria.name}</option>
				{/each}
			</select>
		</div>
		<div>
			<button class="btn btn-success me-2" on:click={generarInforme}>
				<i class="bi bi-archive"></i> Generar Informe
			</button>
		</div>
	</div>

	<div class="table-responsive">
		<table class="table table-bordered table-hover">
			<thead class="table-light">
				<tr>
					<th>Categoría</th>
					<th>Nombre</th>
					<th>Precio Venta</th>
					<th>Stock</th>
					<th>Fecha Vencimiento</th>
					{#if esAdmin}
						<th>Acciones</th>
					{/if}
				</tr>
			</thead>
			<tbody>
				{#each productosFiltrados as p}
					<tr>
						<td>{p.category}</td>
						<td>{p.name}</td>
						<td>${p.price}</td>
						<td>{p.quantity}</td>
						<td>{formatearFecha(p.expirationDate)}</td>
						{#if esAdmin}
							<td>
								<div class="d-flex justify-content-center g-5">
									<button class="btn btn-sm btn-warning me-2" on:click={() => abrirModal(false, p)}>
										<i class="bi bi-pencil-square"></i> Editar
									</button>
									<button class="btn btn-sm btn-danger" on:click={() => eliminarProducto(p.id)}>
										<i class="bi bi-trash"></i> Eliminar
									</button>
								</div>
							</td>
						{/if}
					</tr>
				{/each}
				{#if productos.length === 0}
					<tr>
						<td colspan={esAdmin ? 6 : 5} class="text-center">No hay productos</td>
					</tr>
				{/if}
			</tbody>
		</table>

		{#if esAdmin}
			<div class="d-flex justify-content-end">
				<button class="btn btn-primary" on:click={() => abrirModal(true)}>
					<i class="bi bi-plus-circle"></i> Agregar Producto
				</button>
			</div>
		{/if}
	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="productoModal" tabindex="-1" aria-labelledby="productoModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content" transition:fade>
			<div class="modal-header">
				<h5 class="modal-title" id="productoModalLabel">
					{isEdit ? 'Editar Producto' : 'Agregar Producto'}
				</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
			</div>
			<div class="modal-body">
				<form on:submit|preventDefault={guardarProducto}>
					<div class="mb-3">
						<label class="form-label">Nombre</label>
						<input class="form-control" type="text" bind:value={producto.name} required />
					</div>
					<div class="mb-3">
						<label class="form-label">Precio Venta</label>
						<input class="form-control" type="number" bind:value={producto.price} required />
					</div>
					<div class="mb-3">
						<label class="form-label">Stock</label>
						<input class="form-control" type="number" bind:value={producto.quantity} required />
					</div>
					<div class="mb-3">
						<label class="form-label">Fecha de Vencimiento</label>
						<input class="form-control" type="date" bind:value={producto.expirationDate} required />
					</div>
					<div class="mb-3">
						<label class="form-label">Categoría</label>
						<select class="form-select" bind:value={producto.category} required>
							<option value="" disabled>Selecciona una categoría</option>
							{#each categorias as categoria}
								<option value={categoria.id}>{categoria.name}</option>
							{/each}
						</select>
					</div>
					<div class="d-flex justify-content-end">
						<button type="submit" class="btn btn-success">
							{isEdit ? 'Guardar Cambios' : 'Agregar Producto'}
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
