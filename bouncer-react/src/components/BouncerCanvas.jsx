import { useEffect, useRef } from "react";


function BouncerCanvas({ bouncers }) {


    const canvasRef = useRef(null);



    useEffect(() => {


        const canvas = canvasRef.current;

        const ctx = canvas.getContext("2d");



        // Clear canvas every update
        ctx.clearRect(
            0,
            0,
            canvas.width,
            canvas.height
        );



        // Draw every bouncer
        bouncers.forEach((bouncer) => {


            ctx.beginPath();



            ctx.arc(

                bouncer.xPos,

                bouncer.yPos,

                bouncer.size,

                0,

                Math.PI * 2

            );



            ctx.fill();



            ctx.closePath();


        });



    }, [bouncers]);




    return (

        <canvas

            ref={canvasRef}

            width={600}

            height={400}

            style={{
                border:"1px solid black"
            }}

        />

    );


}


export default BouncerCanvas;