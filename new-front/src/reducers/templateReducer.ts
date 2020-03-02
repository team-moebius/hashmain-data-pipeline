import produce from 'immer'

const initMap = {
  age: 10
}

const templateReducer = (state = initMap, action: any) => {
  let nextState = state

  switch (action.type) {
    case 'TEST_REQUESTED':
      nextState = produce(state, (draft) => {
        draft.age = action.age
      })
      break
    default:
      break
  }
  return nextState
}

export default templateReducer
