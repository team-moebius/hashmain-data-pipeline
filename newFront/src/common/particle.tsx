import React from 'react'
import Particles from 'react-particles-js'

function Particle() {
  return (
    <Particles
      style={{ position: 'fixed', opacity: 0.5, height: '100%' }}
      params={{
        particles: {
          number: {
            value: 150
          },
          opacity: {
            value: 0.2
          }
        },
        interactivity: {
          events: {
            onhover: {
              enable: true,
              mode: 'bubble'
            }
          },
          modes: {
            bubble: {
              size: 10,
              distance: 200
            }
          }
        }
      }} />
  )
}

export default Particle
