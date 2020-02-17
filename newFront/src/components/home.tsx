import * as React from 'react'
import { Redirect } from 'react-router-dom'
import { useSelector } from 'react-redux'
import { ReducerState } from '../reducers/rootReducer'

function Home() {
  const { isLogin } = useSelector((state: ReducerState) => (
    { isLogin: state.home.isLogin }
  ))

  return (
    <div>
      {!isLogin && <Redirect to='/sign' />}
    </div>
  )
}

export default Home
