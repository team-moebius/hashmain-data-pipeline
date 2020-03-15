import { all } from 'redux-saga/effects'
import signSagas from './sign/signSagaIndex'
import homeSagas from './home/homeSagaIndex'

function* rootSaga() {
  yield all([
    homeSagas(),
    signSagas()
  ])
}

export default rootSaga
