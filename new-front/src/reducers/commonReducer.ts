import produce from 'immer'
import { commonActionTypes } from '../actions/commonAction'
import { SIGN_IN_SUCCESS } from '../actions/commands/signActionCommand'

const initMap = {
  loading: {},
  success: {},
  failed: {}
}

const commonReducer = (state = initMap, action: commonActionTypes) => {
  let nextState = state
  nextState = checkLoading(nextState, action)
  switch (action.type) {
    case SIGN_IN_SUCCESS:
      break
    default:
      break
  }
  return nextState
}

export default commonReducer

function checkLoading(nextState: any, action: any) {
  if (action.type.indexOf('_REQUESTED') > -1) {
    const key = action.type.replace('_REQUESTED', '')
    nextState = produce(nextState, (draft: any) => {
      draft.loading[key] = true
      draft.success[key] = false
      draft.failed[key] = false
    })
  } else if (action.type.indexOf('_SUCCESS') > -1) {
    const key = action.type.replace('_SUCCESS', '')
    nextState = produce(nextState, (draft: any) => {
      draft.loading[key] = false
      draft.success[key] = true
      draft.failed[key] = false
    })
  } else if (action.type.indexOf('_FAILED') > -1) {
    const key = action.type.replace('_FAILED', '')
    nextState = produce(nextState, (draft: any) => {
      draft.loading[key] = false
      draft.success[key] = false
      draft.failed[key] = action.msg
    })
  } else if (action.type.indexOf('_RESET') > -1) {
    const key = action.type.replace('_RESET', '')
    nextState = produce(nextState, (draft: any) => {
      draft.loading[key] = false
      draft.success[key] = false
      draft.failed[key] = false
    })
  }
  return nextState
}
