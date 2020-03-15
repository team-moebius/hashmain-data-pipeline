import React from 'react'
import assetSvg from '../../svg/menuIcon/asset.svg'
import ctsSvg from '../../svg/menuIcon/cts.svg'
import forumSvg from '../../svg/menuIcon/forum.svg'
import htsSvg from '../../svg/menuIcon/hts.svg'
import ideaSvg from '../../svg/menuIcon/idea.svg'
import infoSvg from '../../svg/menuIcon/info.svg'
import profileSvg from '../../svg/menuIcon/profile.svg'
import tdsSvg from '../../svg/menuIcon/tds.svg'

export function getSvg(key: string): any {
  let iconImage
  switch (key) {
    case 'cts':
      iconImage = <><img className='customMenuIcon' src={ctsSvg} alt='' /><p className='customMenuIcon'>CTS 설정</p></>
      break
    case 'asset':
      iconImage = <><img className='customMenuIcon' src={assetSvg} alt='' /><p>자산관리</p></>
      break
    case 'forum':
      iconImage = <><img className='customMenuIcon' src={forumSvg} alt='' /><p>코인포럼</p></>
      break
    case 'idea':
      iconImage = <><img className='customMenuIcon' src={ideaSvg} alt='' /><p>아이디어</p></>
      break
    case 'info':
      iconImage = <><img className='customMenuIcon' src={infoSvg} alt='' /><p>이용안내</p></>
      break
    case 'profile':
      iconImage = <><img className='customMenuIcon' src={profileSvg} alt='' /><p>프로필</p></>
      break
    case 'tds':
      iconImage = <><img className='customMenuIcon' src={tdsSvg} alt='' /><p>TDS 설정</p></>
      break
    default:
      iconImage = <><img className='customMenuIcon' src={htsSvg} alt='' /><p>HTS 설정</p></>
      break
  }
  return iconImage
}
