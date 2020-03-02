import React, { useState } from 'react'
import { Row, Col, Menu, Icon } from 'antd'
import { useSelector } from 'react-redux'
import { Redirect } from 'react-router-dom'
import { ReducerState } from '../reducers/rootReducer'
import ResultView from './sign/resultView'
import Login from './sign/login'
import Join from './sign/join'
import '../style/sign.css'

function Sign() {
  const [viewMode, setViewMode] = useState('login')
  const { signDone } = useSelector((state: ReducerState) => ({ signDone: state.sign.signDone }))
  const token = window.localStorage.getItem('token')

  if (token) { return <Redirect to='/' /> }

  return (
    <>
      <Row type='flex' justify='space-around' align='middle' style={{ height: '100vh' }}>
        <Col span={10}>
          <div className='signPageBox'>
            {signDone ? <ResultView /> : (
              <>
                <Menu
                  onClick={(e) => setViewMode(e.key)}
                  selectedKeys={[viewMode]}
                  mode='horizontal'
                  style={{ textAlign: 'center', width: '100%' }}
                >
                  <Menu.Item key='login' style={{ width: '180px' }}>
                    <Icon type='key' />로그인
                  </Menu.Item>
                  <Menu.Item key='join' style={{ width: '180px' }}>
                    <Icon type='user-add' />회원가입
                  </Menu.Item>
                </Menu>
                {viewMode === 'login' ? <Login /> : <Join />}
              </>
            )}
          </div>
        </Col>
      </Row>
    </>
  )
}

export default Sign
