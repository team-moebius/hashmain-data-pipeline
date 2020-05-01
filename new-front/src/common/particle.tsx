import React from 'react'
// import Particles from 'react-particles-js'
import bgimage from '../svg/bgimage.svg'

function Particle() {
  return (
    <img src={bgimage} style={{ position: 'fixed', opacity: 0.5, height: '100%' }} alt='' />
    // <Particles
    //   style={{ position: 'fixed', opacity: 0.5, height: '100%' }}
    //   params={{
    //     particles: {
    //       number: {
    //         value: 150
    //       },
    //       opacity: {
    //         value: 0.2
    //       }
    //     },
    //     interactivity: {
    //       events: {
    //         onhover: {
    //           enable: true,
    //           mode: 'bubble'
    //         }
    //       },
    //       modes: {
    //         bubble: {
    //           size: 10,
    //           distance: 200
    //         }
    //       }
    //     }
    //   }} />
  )
}

export default Particle
