/* eslint-disable jsx-a11y/control-has-associated-label */
import React from 'react'
import { InputNumber, Icon } from 'antd'

interface IColsType {
  title: string,
  dataIndex: string,
  key: string,
  render: (value?: any, record?: any, index?: number) => any
}

export function htsTableCols(type: string, stdCoin: string, assets: string, data: any, setData: any): Array<IColsType> {
  return [{
    title: 'title',
    dataIndex: 'title',
    key: 'title',
    render: (text) => <p>{text}</p>
  }, {
    title: 'option',
    dataIndex: '',
    key: '',
    render: () => <a href='#fake' onClick={() => { console.log('option') }}><Icon type='sync' /></a>
  }, {
    title: 'price1',
    dataIndex: 'price1',
    key: 'price1',
    render: (price, record, index = 0) => (
      <>
        <InputNumber
          style={{ textAlign: 'right' }}
          className='tableInputNumber'
          defaultValue={price}
          onChange={(value) => {
            if (typeof value === 'number') {
              setData(dataChange(index, data, value, 'price'))
            }
          }}
          formatter={(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
          parser={(value = '') => value.replace(/\$\s?|(,*)/g, '')}
        />
        <p style={{ display: 'inline-block', marginLeft: '5px' }}>{stdCoin}</p>
      </>
    )
  }, {
    title: 'amount',
    dataIndex: 'amount',
    key: 'amount',
    render: (price, record, index = 0) => (
      <>
        <InputNumber
          className='tableInputNumber'
          defaultValue={price}
          onChange={(value) => {
            if (typeof value === 'number') {
              setData(dataChange(index, data, value, 'amount'))
            }
          }}
          formatter={(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
          parser={(value = '') => value.replace(/\$\s?|(,*)/g, '')}
        />
        <p style={{ display: 'inline-block', marginLeft: '5px' }}>{assets}</p>
      </>
    )
  }, {
    title: 'rate',
    dataIndex: 'rate',
    key: 'rate',
    render: (text: any) => <p>{text}%</p>
  },
  {
    title: 'price2',
    dataIndex: 'price2',
    key: 'price2',
    render: (price, record) => <p>{`${record.price1 * record.amount} ${stdCoin}`}</p>
  }]
}

function dataChange(index: number, data: Array<{}>, value: number, key: string): Array<{}> {
  const tempData: any[] = []
  data.forEach((elm: any, idx: number) => {
    if (idx === index) {
      if (key === 'price') {
        tempData.push({
          ...elm,
          price1: value
        })
      } else {
        tempData.push({
          ...elm,
          amount: value
        })
      }
    } else {
      tempData.push(elm)
    }
  })
  return tempData
}
