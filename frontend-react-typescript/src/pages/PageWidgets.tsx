import { createAction, handleActions } from 'redux-actions';
/* Redux state type */
export interface PageReduxState {
  signing: boolean;
  token: string;
}
/* Initial redux state */
const initialState: PageReduxState = {
  signing: false,
  token: '',
};
/* Action */
export enum ActionTypes {
  SIGN_IN_SUCCESS = 'page/SIGN_IN_SUCCESS',
  SIGN_OUT = 'page/SIGN_OUT',
}
/* Payload */
interface SignInSuccessPayload {
  token: string;
}
/* Action creator */
export const actionCreators = {
  signInSuccess: createAction<SignInSuccessPayload>(ActionTypes.SIGN_IN_SUCCESS),
  signOut: createAction(ActionTypes.SIGN_OUT),
};
/* Reducer */
export default handleActions<PageReduxState, any>(
  {
    [ActionTypes.SIGN_IN_SUCCESS]: (state, action) => {
      return {
        ...state,
        signing: true,
        token: action.payload.token,
      };
    },
    [ActionTypes.SIGN_OUT]: state => ({
      ...state,
      signing: false,
    }),
  },
  initialState
);
