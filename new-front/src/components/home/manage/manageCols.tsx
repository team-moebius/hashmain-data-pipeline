import React from 'react'
import numeral from 'numeral'

export function manageCols() {
  return [{
    title: <div style={{ height: '30px', width: '0' }} />,
    dataIndex: '',
    key: '',
    algin: 'center',
    className: 'firstCol',
    render: () => <div className='firstCol' style={{ height: '30px', width: '0' }} />
  }, {
    title: 'col1',
    dataIndex: 'ownCoin',
    key: 'ownCoin',
    algin: 'center',
    render: (text: string) => <p>{text}</p>
  }, {
    title: 'col2',
    dataIndex: 'average',
    key: 'average',
    algin: 'center',
    render: (text: number) => (Number.isNaN(text) || text === 0 ? '-' : numeral(text).format('0,0'))
  }, {
    title: 'col3',
    dataIndex: 'totalVol',
    key: 'totalVol',
    algin: 'center',
    render: (text: number) => (Number.isNaN(text) || text === 0 ? '-' : numeral(text).format('0,0'))
  }, {
    title: 'col4',
    dataIndex: 'assessment',
    key: 'assessment',
    algin: 'center',
    render: (text: number) => (Number.isNaN(text) || text === 0 ? '-' : numeral(text).format('0,0'))
  }, {
    title: 'col5',
    dataIndex: 'profit',
    key: 'profit',
    algin: 'center',
    render: (text: number) => (
      Number.isNaN(text) || text === 0 ? '-'
        : <p className={text < 0 ? 'lossColor' : 'profitColor'}>{numeral(text).format('0.00')}%</p>
    )
  }]
}
