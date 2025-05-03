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

		if (response.ok) {
			KEY = response.headers.get('Authorization');

			localStorage.setItem('token', KEY);

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