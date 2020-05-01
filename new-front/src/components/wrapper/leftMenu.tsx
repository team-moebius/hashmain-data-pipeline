import React from 'react'
import { useDispatch } from 'react-redux'
import { Tabs } from 'antd'
import { HOME_MENU_CHANGE_REQUESTED } from '../../actionCmds/homeActionCmd'
import { homeMenuActionType } from '../../actions/homeAction'
import { getSvg } from './loadIcon'
import '../../style/menuIcon.css'

function LeftMenu() {
  const menus = ['hts', 'cts', 'tds', 'asset', 'idea', 'forum', 'info', 'profile']
  const dispatch = useDispatch()

  return (
    <div className='leftMenu'>
      <div className='backgroundColor leftHedaer'>Menu</div>
      <div className='backgroundColor' style={{ height: '1252px', marginTop: '-20px' }}>
        <Tabs
          style={{ marginTop: '25px' }}
          defaultActiveKey='1'
          tabPosition='right'
          className='leftTabs'
          tabBarGutter={30}
          onChange={(key) => { dispatch(homeMenuActionType({ type: HOME_MENU_CHANGE_REQUESTED, menuMode: key })) }}
        >
          {menus.map((key) => (
            <Tabs.TabPane tab={getSvg(key)} key={key}>
              <></>
            </Tabs.TabPane>
          ))}
        </Tabs>
      </div>
    </div>
  )
}

export default LeftMenu
