import * as React from 'react'
import { Redirect } from 'react-router-dom'
import { useSelector } from 'react-redux'
import { ReducerState } from '../reducers/rootReducer'
import Main from './home/main'
import Second from './home/second'
import HeaderBar from './wrapper/headerBar'
import LeftMenu from './wrapper/leftMenu'
import FooterBar from './wrapper/footerBar'
import '../style/home.css'

function getContents(menuMode: string) {
  switch (menuMode) {
    case 'android':
      return <Main />
    default:
      return <Second />
  }
}

function Home() {
  const { token, menuMode } = useSelector((state: ReducerState) => (
    { token: state.common.token, menuMode: state.home.menuMode }
  ))

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
