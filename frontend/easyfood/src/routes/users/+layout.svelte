<script>
	import Navbar from '$lib/components/Navbar.svelte';
	import Footer from '$lib/components/Footer.svelte';
	let { children } = $props();
</script>

<div class="d-flex flex-column min-vh-100">
	<Navbar />

	<main class="flex-grow-1">
		{@render children()}
	</main>

	<Footer />
</div>
