/* eslint-disable jsx-a11y/anchor-is-valid */
import React from 'react'

function FooterBar() {
  return (
    <div className='footerBar backgroundColor'>
      <p style={{ fontSize: '18px', fontWeight: 'bold', display: 'inline-block' }}>
        H<span style={{ color: '#ff3a7d' }}>Λ</span>SHM<span style={{ color: '#ff3a7d' }}>Λ</span>IN
      </p>
      <div style={{ display: 'inline-block', marginLeft: '50px' }}>
        <a href='' style={{ color: '#b7c8f5', margin: '5px' }}>News</a> |
        <a href='' style={{ color: '#b7c8f5', margin: '5px' }}>About us</a> |
        <a href='' style={{ color: '#b7c8f5', margin: '5px' }}>Support</a> |
        <a href='' style={{ color: '#b7c8f5', margin: '5px' }}>Terms of use</a> |
        <a href='' style={{ color: '#b7c8f5', margin: '5px' }}>Privacy policy</a>
      </div>
      <div style={{ marginTop: '45px' }}>
        <p style={{ textAlign: 'right', width: '100%' }}>Hashmainpro@google.com</p>
        <hr style={{ border: '1.5px solid white' }} />
        <p>Copyright © 2020 Hashmain Technologies Holding Company. All Rights Reserved</p>
      </div>
    </div>
  )
}

export default FooterBar
