import * as React from 'react'
import { connect } from 'react-redux'
import { ReducerState } from '../reducers/rootReducer'
import { testAction, testApiAction } from '../actions/testAction'

interface IProps {
  dispatch: any,
  age: number
}

const Test: React.FC<IProps> = (props) => {
  const { age, dispatch } = props
  return (
    <div>
    This page for reducer/saga Test
      <p>State Test</p>
      <p>{age}</p>
      <button
        type='button'
        onClick={() => testAction(dispatch, { type: 'TEST_REQUESTED', age: age + 10 })}
        value='State Test'>age up!!!</button>
      <p>Api Test</p>
      <button
        type='button'
        onClick={() => testApiAction(dispatch, { type: 'TEST_API_REQUESTED', date: '20200120' })}
        value='Api Test'>Api Call!!!</button>
    </div>
  )
}

export default connect(({ test }: ReducerState) => ({
  age: test.age
}))(Test)
