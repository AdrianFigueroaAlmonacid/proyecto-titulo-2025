const URL= "http://localhost:8081/api/v1/"
let KEY = ""



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

        localStorage.setItem('token',KEY);
        console.log(response.headers.get('Authorization') ,"token prueba")

            return response;
        } else {
            console.log(response,"error")
            return response;
        }


    } catch (err) {
        console.log(err,"err")
    }
};


// llamada productos inicio

export const lowStock = async () => {
    // localStorage.getItem('token')
    console.log(KEY,"key prueba")
    try {
        const response = await fetch(`${URL}low-stock`, {
            method: 'GET',
            headers: { 'Content-Type': 'application/json','Authorization':`Bearer ${KEY}`}
    
        });

        console.log(response)
        // if (response.ok) {
        // localStorage.setItem('token', response.token);

        //     return response;
        // } else {
        //     console.log(response,"error")
        //     return response;
        // }


    } catch (err) {
        console.log(err,"err")
    }
};