const URL = 'http://localhost:8081/api/v1/';
let KEY = '';

// auth login
let error = '';

export const postLogin = async (username, password) => {
	try {
		const response = await fetch(`${URL}auth`, {
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify({ username, password })
            
		});
        console.log(response)
		if (response.ok) {
			KEY = response.headers.get('Authorization');
			localStorage.setItem('token', KEY);
            localStorage.setItem('username', username);

			return response;
         
		} else {
			console.log(response, 'error');
			return response;
		}
	} catch (err) {
		console.log(err, 'err');
	}
};

// llamada productos bajo stock
export const lowStock = async () => {
    const token = localStorage.getItem('token'); 
    try {
        const response = await fetch(`${URL}product/low-stock`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token.trim()}`
            }
        });

        if (response.ok) {
            const data = await response.json(); 
            console.log(data, "productos con poco stock");
            return data;
        } else {
            console.log("Error en respuesta:", response.status);
            return null;
        }

    } catch (err) {
        console.log("Error de conexión:", err);
        return null;
    }
};

// llamada productos bajo stock
export const lowStockCount = async () => {
    const token = localStorage.getItem('token'); 
    try {
        const response = await fetch(`${URL}product/low-stock/count`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token.trim()}`
            }
        });

        if (response.ok) {
            const data = await response.json(); 
            console.log(data, "Cantidad produuctos con bajo stock");
            return data.object;
        } else {
            console.log("Error en respuesta:", response.status);
            return null;
        }

    } catch (err) {
        console.log("Error de conexión:", err);
        return null;
    }
};

// llamada cantidad productos por vencer
export const expiringSoonCount = async () => {
    const token = localStorage.getItem('token'); 

    try {
       
        const response = await fetch(`${URL}product/expiring-soon/count`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token.trim()}`
            }
        });

        if (response.ok) {
            const data = await response.json(); 
            console.log(data, "Cantidad productos por vencer");
            return data.object;
        } else {
            console.log("Error en respuesta:", response.status);
            return null;
        }

    } catch (err) {
        console.log("Error de conexión:", err);
        return null;
    }
};

// llamada productos por vencer
export const expiringSoon = async () => {
    const token = localStorage.getItem('token'); 

    try {
       
        const response = await fetch(`${URL}product/expiring-soon`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token.trim()}`
            }
        });

        if (response.ok) {
            const data = await response.json(); 
            console.log(data, "productos por vencer");
            return data;
        } else {
            console.log("Error en respuesta:", response.status);
            return null;
        }

    } catch (err) {
        console.log("Error de conexión:", err);
        return null;
    }
};

// llamada a vender producto
export const sellProduct = async ({ quantity, id }) => {
    const token = localStorage.getItem('token'); 

    try {
       
        const response = await fetch(`${URL}product/sell`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token.trim()}`
            },
            body: JSON.stringify({ quantity, id })
        });

        if (response.ok) {
            const data = await response.json(); 
            console.log(data, "Venta realizada");
            return data;
        } else {
            console.log("Error en respuesta:", response.status);
            return null;
        }

    } catch (err) {
        console.log("Error de conexión:", err);
        return null;
    }
};

// llamada a productos del negocio
export const getProducts = async () => {
	const token = localStorage.getItem('token'); 

	try {
		const response = await fetch(`${URL}product`, {
			method: 'GET',
			headers: {
				'Authorization': `Bearer ${token.trim()}`
			}
		});

		if (response.ok) {
			const data = await response.json();
	        return data.object.content; 
		} else {
			console.log("Error al obtener productos:", response.status);
			return null;
		}

	} catch (err) {
		console.log("Error de conexión:", err);
		return null;
	}
};


// actualizar producto
export async function updateProduct(product, token) {

    try {
        const response = await fetch(`${URL}product`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token.trim()}`
            },
            body: JSON.stringify(product)
        });

        if (response.ok) {
            console.log("Producto actualizado correctamente");
            return true;
        } else {
            console.log("Error al actualizar producto:", response.status);
            const errorText = await response.text();
            console.log("Respuesta del servidor:", errorText);
            return false;
        }
    } catch (error) {
        console.error('Error al actualizar producto:', error);
        return false;
    }
}


// agregar productos

export async function createProduct(product, token) {
	try {
		const response = await fetch(`${URL}product`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				'Authorization': `Bearer ${token.trim()}`
			},
			body: JSON.stringify(product)
		});

		if (response.ok) {
			console.log("Producto creado correctamente");
			return true;
		} else {
			console.log("Error al crear producto:", response.status);
			const errorText = await response.text();
			console.log("Respuesta del servidor:", errorText);
			return false;
		}
	} catch (error) {
		console.error('Error al crear producto:', error);
		return false;
	}
}

// borrar producto
export async function deleteProduct(id, token) {
	try {
		const response = await fetch(`${URL}product/${id}`, {
			method: 'DELETE',  
			headers: {
				'Authorization': `Bearer ${token.trim()}`
			}
		});

		if (response.ok) {
			console.log("Producto eliminado correctamente");
			return true;
		} else {
			console.log("Error al eliminar producto:", response.status);
			return false;
		}
	} catch (error) {
		console.error('Error al eliminar producto:', error);
		return false;
	}
}


// log out
export function logout() {
	localStorage.removeItem('token');
	window.location.href = '/';
}


// seleccionar categoria

export const getCategory = async () => {
	const token = localStorage.getItem('token');

	try {
		const response = await fetch(`${URL}category/all`, {
			method: 'GET',
			headers: {
				'Authorization': `Bearer ${token.trim()}`
			}
		});

		if (response.ok) {
			const data = await response.json();
            console.log(data)
	        return data.object; 
		} else {
			console.log("Error al obtener categorias:", response.status);
			return null;
		}

	} catch (err) {
		console.log("Error de conexión:", err);
		return null;
	}
};