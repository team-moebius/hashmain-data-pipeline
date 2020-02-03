import * as React from 'react'
import { Redirect } from 'react-router-dom'
// import { RouteComponentProps, withRouter } from 'react-router-dom'
import { connect } from 'react-redux'
import { ReducerState } from '../reducers/rootReducer'

// import routerPush from '../common/router/routerPush' 
// import { Button } from 'antd'

// const Home: React.FC<RouteComponentProps> = props => {

interface IHomeProps {
  dispatch: any,
  isLogin: boolean
}

function Home({ isLogin, dispatch}: IHomeProps) {
// const Home: React.FC<IHomeProps> = props => {
  return (
    <div>
      {!isLogin && <Redirect to='/sign'/>}
      {/* <Button type='primary' onClick={() => routerPush.push(props, 'Test')}>
        <a href='/Test'>go to test page</a>
      </Button> */}
    </div>
  )
}




export default connect(({ sign }: ReducerState) => ({
  isLogin: sign.isLogin
}))(Home)