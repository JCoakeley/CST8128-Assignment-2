const API_URL =
"http://localhost:8080/CST8128-Assignment-2/resources/cst8218.jc040929397.bouncer.business.bouncer";


export function getBouncers(){

    return fetch(API_URL)
        .then(response=>response.json());

}



export function updateBouncer(id,bouncer){

    return fetch(`${API_URL}/${id}`,{

        method:"PUT",

        headers:{
            "Content-Type":"application/json"
        },

        body:JSON.stringify(bouncer)

    });

}

export function createBouncer(bouncer){

    return fetch(API_URL,{
        method:"POST",
        headers:{
            "Content-Type":"application/json"
        },
        body:JSON.stringify(bouncer)
    })
    .then(response=>response.json());

}