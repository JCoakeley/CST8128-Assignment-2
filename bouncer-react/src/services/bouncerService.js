const API_URL =
"http://localhost:8080/CST8128-Assignment-2/resources/cst8218.jc040929397.bouncer.business.bouncer";


// GET ALL BOUNCERS
export function getBouncers(){

    return fetch(API_URL)

    .then(response => {

        if(!response.ok){
            throw new Error("Failed to load bouncers");
        }

        return response.json();

    });

}



// UPDATE BOUNCER
export function updateBouncer(id, bouncer){

    return fetch(`${API_URL}/${id}`,{

        method:"PUT",

        headers:{
            "Content-Type":"application/json"
        },

        body:JSON.stringify(bouncer)

    })

    .then(response => {

        if(!response.ok){
            throw new Error("Failed to update bouncer");
        }

        return response;

    });

}



// CREATE BOUNCER
export function createBouncer(bouncer){

    return fetch(API_URL,{

        method:"POST",

        headers:{
            "Content-Type":"application/json"
        },

        body:JSON.stringify(bouncer)

    })

    .then(response => {

        if(!response.ok){
            throw new Error("Failed to create bouncer");
        }

        return response.json();

    });

}