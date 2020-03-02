import React from 'react'
import { useDispatch } from 'react-redux'
import { Icon, Tabs } from 'antd'
import { MENU_MODE_CHANGE_REQUESTED } from '../../actions/commands/homeActionCommand'
import { menuModeActionType } from '../../actions/homeAction'

function LeftMenu() {
  const menus = ['android', 'apple', 'windows', 'github',
    'gitlab', 'dropbox', 'facebook', 'amazon']
  const dispatch = useDispatch()

  return (
    <div className='leftMenu'>
      <div className='backgroundColor leftHedaer'>Menu</div>
      <div className='backgroundColor' style={{ height: '100%' }}>
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
    </div>
  )
}

export default LeftMenu
