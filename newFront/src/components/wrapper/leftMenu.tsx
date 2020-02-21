import React from 'react'
import { useDispatch } from 'react-redux'
import { Icon, Tabs } from 'antd'
import { MENU_MODE_CHANGE_REQUESTED } from '../../actionCmds/homeActionCmd'
import { menuModeActionType } from '../../actions/homeAction'

function LeftMenu() {
  const menus = ['android', 'apple', 'windows', 'github',
    'gitlab', 'dropbox', 'facebook', 'amazon']
  const dispatch = useDispatch()

  return (
    <div className='leftMenu'>
      <Tabs
        style={{ marginTop: '25px' }}
        defaultActiveKey='1'
        tabPosition='right'
        className='leftTabs'
        tabBarGutter={30}
        onChange={(key) => { dispatch(menuModeActionType({ type: MENU_MODE_CHANGE_REQUESTED, menuMode: key })) }}
      >
        {menus.map((key) => (
          <Tabs.TabPane tab={<Icon style={{ fontSize: '40px' }} type={key} />} key={key}>
            <></>
          </Tabs.TabPane>
        ))}
      </Tabs>
    </div>
  )
}

export default LeftMenu
