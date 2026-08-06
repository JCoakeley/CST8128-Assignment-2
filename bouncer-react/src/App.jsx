import { useEffect, useState } from "react";

import {
    getBouncers,
    updateBouncer,
    createBouncer
} from "./services/bouncerService";

import BouncerCanvas from "./components/BouncerCanvas";


function App() {


    const [bouncers, setBouncers] = useState([]);

    const [error, setError] = useState("");


    const [newBouncer, setNewBouncer] = useState({

    x: "",
    y: "",
    size: "",
    currentTravel: 0,
    maxTravel: "",
    mvtDirection: 1

});



    // Load bouncers from GlassFish REST API
    useEffect(() => {

        loadBouncers();

    }, []);





    function loadBouncers() {


        getBouncers()

        .then(data => {

            console.log("Bouncers:", data);

            setBouncers(data);

        })


        .catch(error => {

            console.log(error);

            setError("Unable to load bouncers");

        });


    }






    // Handle form input changes
    function handleChange(event){


        const {name,value} = event.target;


        setNewBouncer({

            ...newBouncer,

            [name]: Number(value)

        });


    }





    // Create new bouncer
    function addBouncer(event){


        event.preventDefault();



        createBouncer(newBouncer)


        .then(() => {


            loadBouncers();



            setNewBouncer({

    x:"",
    y:"",
    size:"",
    currentTravel:0,
    maxTravel:"",
    mvtDirection:1

});


        })


        .catch(error => {

            console.log(error);

            setError("Unable to create bouncer");

        });


    }







    // Update bouncer position
    function moveBouncer(id){


        const bouncer = bouncers.find(

            b => b.id === id

        );



        if(!bouncer){

            return;

        }




        const updatedBouncer = {


            ...bouncer,


            x: bouncer.x + 10


        };





        updateBouncer(

            id,

            updatedBouncer

        )


        .then(()=>{


            loadBouncers();


        })


        .catch(error=>{


            console.log(error);


        });


    }








    return (


        <div className="App">


            <h1>
                Bouncer Animation
            </h1>



            {
                error &&

                <p>
                    {error}
                </p>
            }






            <button onClick={loadBouncers}>

                Refresh Bouncers

            </button>





            <h2>
                Create New Bouncer
            </h2>



            <form onSubmit={addBouncer}>


                <label>
                    X Position:
                </label>

                <input

                    type="number"

                    name="x"

                    value={newBouncer.x}

                    onChange={handleChange}

                />

                <br/>




                <label>
                    Y Position:
                </label>

                <input

                    type="number"

                    name="y"

                    value={newBouncer.y}

                    onChange={handleChange}

                />

                <br/>





                <label>
                    Size:
                </label>


                <input

                    type="number"

                    name="size"

                    value={newBouncer.size}

                    onChange={handleChange}

                />

                <br/>





                <label>
                    Maximum Travel:
                </label>


                <input

                    type="number"

                    name="maxTravel"

                    value={newBouncer.maxTravel}

                    onChange={handleChange}

                />

                <br/>





                <label>
                    Direction:
                </label>


                <select

                    name="mvtDirection"

                    value={newBouncer.mvtDirection}

                    onChange={handleChange}

                >

                    <option value="1">
                        Right
                    </option>


                    <option value="-1">
                        Left
                    </option>


                </select>


                <br/>





                <button type="submit">

                    Add Bouncer

                </button>



            </form>







            <h2>
                Canvas View
            </h2>



            <BouncerCanvas

                bouncers={bouncers}

            />








            <h2>
                Bouncer Information
            </h2>





            {

                bouncers.length === 0 ?


                (

                    <p>
                        No bouncers found.
                    </p>

                )


                :


                (

                    bouncers.map(bouncer => (



                        <div

                            key={bouncer.id}

                            style={{

                                border:"1px solid black",

                                margin:"10px",

                                padding:"10px"

                            }}

                        >




                            <h3>

                                Bouncer ID: {bouncer.id}

                            </h3>




                            <p>

                                Position:

                                ({bouncer.x}, {bouncer.y})

                            </p>





                            <p>

                                Size:

                                {bouncer.size}

                            </p>





                            <p>

                                Direction:

                                {bouncer.mvtDirection}

                            </p>





                            <p>

                                Travel:

                                {bouncer.currentTravel}

                                /

                                {bouncer.maxTravel}

                            </p>





                            <button

                                onClick={() =>

                                    moveBouncer(bouncer.id)

                                }

                            >

                                Move Right

                            </button>





                        </div>



                    ))

                )

            }



        </div>


    );


}


export default App;