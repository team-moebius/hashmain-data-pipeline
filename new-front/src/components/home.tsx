import React from 'react'
import { Redirect } from 'react-router-dom'
import { useSelector } from 'react-redux'
import { ReducerState } from '../reducers/rootReducer'
import HTSSetting from './home/htsSetting'
import Second from './home/second'
import HeaderBar from './wrapper/headerBar'
import LeftMenu from './wrapper/leftMenu'
import FooterBar from './wrapper/footerBar'
import '../style/home.css'

function getContents(menuMode: string) {
  switch (menuMode) {
    case 'android':
      return <HTSSetting />
    default:
      return <Second />
  }
}

function Home() {
  const { menuMode } = useSelector((state: ReducerState) => ({ menuMode: state.home.menuMode }))
  const token = window.localStorage.getItem('token')

  return (
    <>
      {!token ? <Redirect to='/sign' /> : (
        <>
          <HeaderBar />
          <div className='contentsBody'>
            <LeftMenu />
            {getContents(menuMode)}
          </div>
          <FooterBar />
        </>
      )}
    </>
  )
}

export default Home
