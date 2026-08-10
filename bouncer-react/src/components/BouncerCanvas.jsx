import { useEffect, useRef } from "react";


function BouncerCanvas({ bouncers }) {


    const canvasRef = useRef(null);



    useEffect(() => {


        const canvas = canvasRef.current;

        const ctx = canvas.getContext("2d");



        // Clear canvas
        ctx.clearRect(
            0,
            0,
            canvas.width,
            canvas.height
        );



        console.log("Drawing bouncers:", bouncers);



        bouncers.forEach(bouncer => {


            console.log(
                "Bouncer:",
                bouncer.x,
                bouncer.y,
                bouncer.size
            );



            ctx.beginPath();



            ctx.arc(

                Number(bouncer.x),

                Number(bouncer.y),

                Number(bouncer.size),

                0,

                Math.PI * 2

            );



            ctx.fill();



        });



    }, [bouncers]);





    return (

        <canvas

            ref={canvasRef}

            width="800"

            height="500"

            style={{

                border:"1px solid black"

            }}

        />

    );


}


export default BouncerCanvas;