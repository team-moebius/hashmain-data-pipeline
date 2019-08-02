import { createAction, handleActions } from 'redux-actions';
/* Redux state type */
export interface PageReduxState {
  signing: boolean;
}
/* Initial redux state */
const initialState: PageReduxState = {
  signing: false,
};
/* Action */
export enum ActionTypes {
  SIGN_IN_SUCCESS = 'page/SIGN_IN_SUCCESS',
  SIGN_OUT = 'page/SIGN_OUT',
}
/* Action creator */
export const actionCreators = {
  signInSuccess: createAction(ActionTypes.SIGN_IN_SUCCESS),
  signOut: createAction(ActionTypes.SIGN_OUT),
};
/* Reducer */
export default handleActions<PageReduxState, any>(
  {
    [ActionTypes.SIGN_IN_SUCCESS]: state => {
      return {
        ...state,
        signing: true,
      };
    },
    [ActionTypes.SIGN_OUT]: state => ({
      ...state,
      signing: false,
    }),
  },
  initialState
);
