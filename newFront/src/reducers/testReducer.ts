import produce from 'immer'

const initMap = {
  age: 10,
  data: []
}

const testReducer = (state = initMap, action: any) => {
  let nextState = state

  switch (action.type) {
    case 'TEST_REQUESTED':
      nextState = produce(state, (draft) => {
        draft.age = action.age
      })
      break
    case 'TEST_API_SUCCESS':
      nextState = produce(state, (draft) => {
        draft.data = action.data
      })
      break
    default:
      break
  }
  return nextState
}

export default testReducer
