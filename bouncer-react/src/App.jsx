import { useEffect, useState } from "react";

import {
    getBouncers,
    updateBouncer
} from "./services/bouncerService";

import BouncerCanvas from "./components/BouncerCanvas";


function App() {


    const [bouncers, setBouncers] = useState([]);
    const [error, setError] = useState("");



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




    // Update a bouncer position
    function moveBouncer(id) {


        const bouncer = bouncers.find(
            b => b.id === id
        );


        if (!bouncer) {
            return;
        }



        const updatedBouncer = {

            ...bouncer,

            xPos: bouncer.xPos + 10

        };



        updateBouncer(
            id,
            updatedBouncer
        )

        .then(() => {

            loadBouncers();

        })

        .catch(error => {

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
                                ({bouncer.xPos}, {bouncer.yPos})
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